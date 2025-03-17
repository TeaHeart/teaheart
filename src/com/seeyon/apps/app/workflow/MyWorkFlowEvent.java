package com.seeyon.apps.app.workflow;

import com.seeyon.cap4.form.bean.FormBean;
import com.seeyon.cap4.form.bean.FormDataMasterBean;
import com.seeyon.cap4.form.bean.FormFieldBean;
import com.seeyon.cap4.form.bean.FormTableBean;
import com.seeyon.cap4.form.modules.engin.base.formData.CAP4FormDataManager;
import com.seeyon.cap4.form.service.CAP4FormManager;
import com.seeyon.ctp.common.AppContext;
import com.seeyon.ctp.common.constants.ApplicationCategoryEnum;
import com.seeyon.ctp.util.FlipInfo;
import com.seeyon.ctp.util.annotation.Inject;
import com.seeyon.ctp.workflow.event.AbstractWorkflowEvent;
import com.seeyon.ctp.workflow.event.WorkflowEventData;
import com.seeyon.ctp.workflow.event.WorkflowEventResult;
import com.seeyon.v3x.services.form.FormFactory;
import com.seeyon.v3x.services.form.FormUtils;
import com.seeyon.v3x.services.form.bean.FormExport;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.function.LongSupplier;

// https://mall.seeyon.com/help/
// https://open.seeyoncloud.com/v5devCIP/961/963.html
// http://open.seeyon.com/book/ctp/bpmEvent.html#%E6%B5%81%E7%A8%8B%E7%9B%91%E5%90%AC%E6%8E%A5%E5%8F%A3
// https://open.seeyoncloud.com/v5devCAP/94/355/359/457/461.html
// https://open.seeyoncloud.com/seeyonapi/728/750.html
// https://open.seeyoncloud.com/seeyonapi/728/734.html
@Slf4j
public class MyWorkFlowEvent extends AbstractWorkflowEvent {
    private final String formCode = "cwgs_form_001";
    private final String[] valiFieldAry = {"流水号"};

    @Inject
    private CAP4FormManager cap4FormManager;
    @Inject
    private CAP4FormDataManager cap4FormDataManager;
    @Inject
    private FormFactory formFactory;

    @Override
    public String getId() {
        return getClass().getCanonicalName();
    }

    @Override
    public String getLabel() {
        return getClass().getSimpleName();
    }

    @Override
    public ApplicationCategoryEnum getAppName() {
        return ApplicationCategoryEnum.form;
    }

    // 模拟流水号生成, 也可以直接用CAP里的
    private final LongSupplier flowId = new LongSupplier() {
        private int lastDay;
        private int count;

        public synchronized long getAsLong() {
            LocalDate localDate = LocalDate.now();
            int year = localDate.getYear();
            int month = localDate.getMonthValue();
            int day = localDate.getDayOfMonth();
            if (day != lastDay) {
                count = 0;
            }
            lastDay = day;
            if (count == 9999) {
                throw new RuntimeException("count is full");
            }
            return year % 100 * 1_0000_0000L + month * 100_0000 + day * 1_0000 + ++count;
        }
    };

    // region 底表操作
    private void saveOrUpdateUnFlowData(WorkflowEventData data) throws Exception {
        Map<String, Object> businessData = data.getBusinessData();

        FormBean formBean = (FormBean) businessData.get("formBean");
        FormDataMasterBean formDataMasterBean = (FormDataMasterBean) businessData.get("formDataBean");

        // 流程发起事件后, 模拟流水号生成并存入数据库
        FormTableBean masterTableBean = formBean.getMasterTableBean();
        FormFieldBean formFieldBean = masterTableBean.getFieldBeanByDisplay("流水号");
        String columnName = formFieldBean.getColumnName();
        if (formDataMasterBean.getFieldValue(columnName) == null) {
            formDataMasterBean.addFieldValue(columnName, flowId.getAsLong());
            cap4FormManager.saveOrUpdateFormData(formDataMasterBean, formBean.getId(), true, new ArrayList<>());
        }

        // 存到底表
        Long formId = formBean.getId();
        FormExport formExport = FormUtils.createFormExport(formId, formDataMasterBean);
        String loginName = AppContext.currentUserLoginName();
        // 会根据 valiFieldAry 判断是添加还是更新
        formFactory.importBusinessFormData(loginName, formCode, formExport, valiFieldAry, new HashMap<>());
    }

    private void deleteUnFlowData(WorkflowEventData data) throws Exception {
        // formFactory 没有给相关方法, 自己查数据库删除
        // 1. 获取流水号
        Map<String, Object> businessData = data.getBusinessData();
        Object flowId = businessData.get("流水号");
        if (flowId == null) {
            log.warn("flowId is null");
            return;
        }
        // 2. 根据流水号去查询底表对应的数据
        FormBean unFlowFormBean = cap4FormManager.getFormByFormCode(formCode);
        FormTableBean unFlowMasterTableBean = unFlowFormBean.getMasterTableBean();
        FormFieldBean unFlowFormFieldBean = unFlowMasterTableBean.getFieldBeanByDisplay("流水号");
        String columnName = unFlowFormFieldBean.getColumnName();
        FormDataMasterBean unFlowFormDataMasterBean = cap4FormDataManager.selectMasterDataList(
                new FlipInfo() {{
                    setTotal(1);
                }},
                unFlowMasterTableBean,
                new String[]{"id", columnName},
                new HashMap<String, Object>() {{
                    put(columnName, flowId);
                }}
        ).stream().findFirst().orElse(null);
        if (unFlowFormDataMasterBean == null) {
            log.warn("not found, flowId: {}", flowId);
            return;
        }
        // 3. 删除
        boolean deleteFlag = cap4FormDataManager.deleteForm(unFlowFormDataMasterBean.getId(), unFlowFormBean);
        log.info("delete result: {}", deleteFlag);
    }
    // endregion

    // region 流程事件
    // 发起前事件
    public WorkflowEventResult onBeforeStart(WorkflowEventData data) {
        log.info("onBeforeStart {}", data.getBusinessData());
        return new WorkflowEventResult();
    }

    // 发起事件
    public void onStart(WorkflowEventData data) {
        log.info("onStart {}", data.getBusinessData());
        try {
            saveOrUpdateUnFlowData(data);
        } catch (Exception e) {
            log.error("添加到底表失败", e);
            throw new RuntimeException(e);
        }
    }

    // 终止前事件
    public WorkflowEventResult onBeforeStop(WorkflowEventData data) {
        log.info("onBeforeStop {}", data.getBusinessData());
        WorkflowEventResult result = new WorkflowEventResult();
        try {
            deleteUnFlowData(data);
        } catch (Exception e) {
            log.error("删除到底表失败", e);
            result.setAlertMessage(e.getMessage());
        }
        return result;
    }

    // 终止事件
    public void onStop(WorkflowEventData data) {
        log.info("onStop {}", data.getBusinessData());
    }

    // 撤销前事件
    public WorkflowEventResult onBeforeCancel(WorkflowEventData data) {
        log.info("onBeforeCancel {}", data.getBusinessData());
        WorkflowEventResult result = new WorkflowEventResult();
        try {
            deleteUnFlowData(data);
        } catch (Exception e) {
            log.error("删除到底表失败", e);
            result.setAlertMessage(e.getMessage());
        }
        return result;
    }

    // 撤销事件
    public void onCancel(WorkflowEventData data) {
        log.info("onCancel {}", data.getBusinessData());
    }

    // 结束事件
    public void onProcessFinished(WorkflowEventData data) {
        log.info("onProcessFinished {}", data.getBusinessData());
        try {
            saveOrUpdateUnFlowData(data);
        } catch (Exception e) {
            log.error("更新到底表失败", e);
            throw new RuntimeException(e);
        }
    }
    // endregion

    // region 节点事件
    // 处理前事件
    public WorkflowEventResult onBeforeFinishWorkitem(WorkflowEventData data) {
        log.info("onBeforeFinishWorkitem {}", data.getBusinessData());
        WorkflowEventResult result = new WorkflowEventResult();
        try {
            saveOrUpdateUnFlowData(data);
        } catch (Exception e) {
            log.error("更新到底表失败", e);
            result.setAlertMessage(e.getMessage());
        }
        return result;
    }

    // 处理事件
    public void onFinishWorkitem(WorkflowEventData data) {
        log.info("onFinishWorkitem {}", data.getBusinessData());
    }

    // 回退前事件
    public WorkflowEventResult onBeforeStepBack(WorkflowEventData data) {
        log.info("onBeforeStepBack {}", data.getBusinessData());
        WorkflowEventResult result = new WorkflowEventResult();
        try {
            deleteUnFlowData(data);
        } catch (Exception e) {
            log.error("删除到底表失败", e);
            result.setAlertMessage(e.getMessage());
        }
        return result;
    }

    // 回退事件
    public void onStepBack(WorkflowEventData data) {
        log.info("onStepBack {}", data.getBusinessData());
    }

    // 取回前事件
    public WorkflowEventResult onBeforeTakeBack(WorkflowEventData data) {
        log.info("onBeforeTakeBack {}", data.getBusinessData());
        WorkflowEventResult result = new WorkflowEventResult();
        try {
            deleteUnFlowData(data);
        } catch (Exception e) {
            log.error("删除到底表失败", e);
            result.setAlertMessage(e.getMessage());
        }
        return result;
    }

    // 取回事件
    public void onTakeBack(WorkflowEventData data) {
        log.info("onTakeBack {}", data.getBusinessData());
    }
    // endregion
}
