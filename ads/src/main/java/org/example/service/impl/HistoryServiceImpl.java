package org.example.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.example.config.ZCloudDataConverter;
import org.example.controller.advice.BusinessException;
import org.example.entity.History;
import org.example.entity.Node;
import org.example.mapper.HistoryMapper;
import org.example.service.HistoryService;
import org.example.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
public class HistoryServiceImpl extends ServiceImpl<HistoryMapper, History> implements HistoryService {
    @Autowired
    private NodeService nodeService;

    @Autowired
    private ZCloudDataConverter zCloudDataConverter;

    @Override
    public boolean saveByMac(History history) {
        // TODO 子查询
        Integer nodeId = nodeService.lambdaQuery()
                .eq(Node::getAddress, history.getNode().getAddress())
                .oneOpt()
                .map(Node::getId)
                .orElseThrow(() -> new BusinessException("Node not found"));
        history.setNodeId(nodeId);
        return save(history);
    }

    @Override
    public List<History> listHistoryByNodeId(Integer nodeId, Integer begin, Integer limit) {
        return lambdaQuery()
                .eq(History::getNodeId, nodeId)
                .orderByDesc(History::getTime)
                .last(StrUtil.format("limit {}, {}", begin, limit))
                .list();
    }

    @Override
    public History getLastHistoryWithNodeByNodeId(Integer nodeId) {
        // TODO 多表查询
        return lambdaQuery()
                .eq(History::getNodeId, nodeId)
                .orderByDesc(History::getTime)
                .last("limit 1")
                .oneOpt()
                .map(history -> {
                    Node node = nodeService.lambdaQuery()
                            .eq(Node::getId, nodeId)
                            .oneOpt()
                            .orElseThrow(() -> new BusinessException("Node not found"));
                    node.setHistoryList(Collections.singletonList(history));
                    history.setNode(node);
                    return history;
                }).orElse(null);
    }

    @Override
    public List<History> listLast() {
        return lambdaQuery()
                .orderByDesc(History::getTime)
                .last(StrUtil.format("limit 0, 20"))
                .list();
    }

    @Override
    public Map<Integer, Map<String, List<Double>>> summary(Integer[] nodeIdArray) {
        // TODO 多表查询 自动封装
        Map<Integer, Map<String, List<Double>>> map = new LinkedHashMap<>();
        for (Integer nodeId : nodeIdArray) {
            Map<String, List<Double>> historyMap = new HashMap<>();
            for (History history : listHistoryByNodeId(nodeId, 0, 20)) {
                Map<String, String> dataMap = zCloudDataConverter.convert(history.getData());
                for (Map.Entry<String, String> entry : dataMap.entrySet()) {
                    String key = entry.getKey();
                    String value = entry.getValue();
                    historyMap.computeIfAbsent(key, k -> new ArrayList<>()).add(Double.parseDouble(value));
                }
            }
            map.put(nodeId, historyMap);
        }
        return map;
    }
}
