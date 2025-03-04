package com.seeyon.apps.app.manager;

import com.seeyon.apps.app.po.App;
import com.seeyon.ctp.common.exceptions.BusinessException;
import com.seeyon.ctp.util.FlipInfo;
import com.seeyon.ctp.util.annotation.AjaxAccess;

import java.util.List;
import java.util.Map;

public interface AppManager {
    @AjaxAccess
    void save(App app) throws BusinessException;

    @AjaxAccess
    void delete(List<String> ids) throws BusinessException;

    @AjaxAccess
    FlipInfo list(FlipInfo flipInfo, Map<String, Object> params) throws BusinessException;
}
