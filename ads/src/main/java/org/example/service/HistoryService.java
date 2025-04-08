package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.History;

import java.util.List;
import java.util.Map;

public interface HistoryService extends IService<History> {
    boolean saveByMac(History history);

    List<History> listHistoryByNodeId(Integer nodeId, Integer begin, Integer limit);

    History getLastHistoryWithNodeByNodeId(Integer nodeId);

    List<History> listLast();

    Map<Integer, Map<String, List<Double>>> summary(Integer[] nodeIdArray);
}
