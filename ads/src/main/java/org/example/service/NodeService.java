package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.Node;
import org.example.entity.NodeType;

import java.util.List;

public interface NodeService extends IService<Node> {
    boolean sendMessage(String address, String command);

    void sync();

    List<Node> getLikeAddress(String address);

    List<Node> listByType(NodeType type);
}
