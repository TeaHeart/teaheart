package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.config.ZCloudDataConverter;
import org.example.entity.Policy;
import org.example.mapper.PolicyMapper;
import org.example.service.HistoryService;
import org.example.service.NodeService;
import org.example.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.EvaluationContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class PolicyServiceImpl extends ServiceImpl<PolicyMapper, Policy> implements PolicyService {
    @Autowired
    private NodeService nodeService;

    @Autowired
    private HistoryService historyService;

    @Autowired
    private ZCloudDataConverter zCloudDataConverter;

    private SpelExpressionParser parser = new SpelExpressionParser();

    @Override
    public List<Policy> listEnabledWithNode() {
        // TODO 多表查询
        List<Policy> list = listByEnabled(true);
        for (Policy policy : list) {
            // TODO 历史 数据表为空时空指针异常
            policy.setSourceNode(historyService.getLastHistoryWithNodeByNodeId(policy.getSourceId()).getNode());
            policy.setTargetNode(historyService.getLastHistoryWithNodeByNodeId(policy.getTargetId()).getNode());
        }
        return list;
    }

    @Override
    public List<Policy> listByEnabled(boolean enabled) {
        return lambdaQuery()
                .eq(Policy::getEnabled, enabled)
                .list();
    }

    @Override
    public void control() {
        for (Policy policy : listEnabledWithNode()) {
            String data = policy.getSourceNode().getHistoryList().get(0).getData();
            Map<String, String> dataMap = zCloudDataConverter.convert(data);
            EvaluationContext context = new StandardEvaluationContext();
            for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                context.setVariable(entry.getKey(), Double.parseDouble(entry.getValue()));
            }
            // A2>1000 -> #A2>1000
            String expression = policy.getExpression().replaceAll("\\b(A[0-6])\\b", "#$1");
            if (Boolean.TRUE.equals(parser.parseExpression(expression).getValue(context, Boolean.class))) {
                nodeService.sendMessage(policy.getTargetNode().getAddress(), policy.getCommand());
            }
        }
    }
}
