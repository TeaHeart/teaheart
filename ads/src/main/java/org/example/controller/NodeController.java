package org.example.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.entity.Node;
import org.example.entity.NodeType;
import org.example.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/node")
public class NodeController {
    @Autowired
    private NodeService nodeService;

    @PostMapping
    public boolean save(@RequestBody Node node) {
        return nodeService.save(node);
    }

    @DeleteMapping("/{id}")
    public boolean removeById(@PathVariable Integer id) {
        return nodeService.removeById(id);
    }

    @PutMapping
    public boolean updateById(@RequestBody Node node) {
        return nodeService.updateById(node);
    }

    @GetMapping
    public List<Node> list() {
        return nodeService.list();
    }

    @GetMapping("/{id}")
    public Node getById(@PathVariable Integer id) {
        return nodeService.getById(id);
    }

    @GetMapping("/address/{address}")
    public List<Node> getLikeAddress(@PathVariable String address) {
        return nodeService.getLikeAddress(address);
    }

    @GetMapping("/type/{type}")
    public List<Node> listByType(@PathVariable NodeType type) {
        return nodeService.listByType(type);
    }
}
