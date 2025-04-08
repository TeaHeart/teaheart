package org.example.controller;

import org.example.entity.History;
import org.example.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/history")
public class HistoryController {
    @Autowired
    private HistoryService historyService;

    @PostMapping
    public boolean save(@RequestBody History history) {
        return historyService.save(history);
    }

    @DeleteMapping("/{id}")
    public boolean removeById(@PathVariable Integer id) {
        return historyService.removeById(id);
    }

    @PutMapping
    public boolean updateById(@RequestBody History history) {
        return historyService.updateById(history);
    }

    @GetMapping
    public List<History> list() {
        return historyService.list();
    }

    @GetMapping("/{id}")
    public History getById(@PathVariable Integer id) {
        return historyService.getById(id);
    }

    @GetMapping("/last")
    public List<History> listLast() {
        return historyService.listLast();
    }

    @GetMapping("/nodeId/{nodeId}")
    public List<History> listHistoryByNodeId(@PathVariable Integer nodeId,
                                             @RequestParam(defaultValue = "0") Integer begin,
                                             @RequestParam(defaultValue = "20") Integer limit) {
        return historyService.listHistoryByNodeId(nodeId, begin, limit);
    }

    @GetMapping("/summary/{nodeIdArray}")
    public Map<Integer, Map<String, List<Double>>> summary(@PathVariable Integer[] nodeIdArray) {
        return historyService.summary(nodeIdArray);
    }
}
