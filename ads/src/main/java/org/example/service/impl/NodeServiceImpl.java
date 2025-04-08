package org.example.service.impl;

import cn.hutool.json.JSONObject;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.Node;
import org.example.entity.NodeType;
import org.example.mapper.NodeMapper;
import org.example.service.NodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.util.List;

@Slf4j
@Service
public class NodeServiceImpl extends ServiceImpl<NodeMapper, Node> implements NodeService {
    @Lazy
    @Autowired
    private WebSocketSession session;

    @Override
    @SneakyThrows
    public boolean sendMessage(String address, String command) {
        String message = new JSONObject() {{
            put("method", "control");
            put("addr", address);
            put("data", command);
        }}.toString();
        log.debug("Send message: {}", message);
        session.sendMessage(new TextMessage(message));
        return true;
    }

    @Override
    public void sync() {
        for (Node node : list()) {
            sendMessage(node.getAddress(), node.getCommand());
        }
    }

    @Override
    public List<Node> getLikeAddress(String address) {
        return lambdaQuery()
                .like(Node::getAddress, address)
                .list();
    }

    @Override
    public List<Node> listByType(NodeType type) {
        return lambdaQuery()
                .eq(Node::getType, type)
                .list();
    }
}
