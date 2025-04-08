package org.example.zcloud;

import cn.hutool.core.io.IoUtil;
import cn.hutool.json.JSONObject;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import javax.websocket.*;
import java.io.Closeable;
import java.io.IOException;
import java.net.URI;

@Slf4j
@ClientEndpoint
public class ZCloudSocketClient implements Closeable {
    private final String uid;
    private final String key;
    private Session session;

    @SneakyThrows
    public ZCloudSocketClient(String uid, String key, String url) {
        this.uid = uid;
        this.key = key;
        ContainerProvider.getWebSocketContainer().connectToServer(this, new URI(url));
    }

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
        sendMessage(new JSONObject() {{
            put("method", "authenticate");
            put("uid", uid);
            put("key", key);
        }}.toString());
        log.info("Connected to server");
    }

    @OnClose
    public void onClose(Session session, CloseReason reason) {
        this.session = null;
        log.info("Disconnected from server: {}", reason);
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("Received message: {}", message);
    }

    @OnError
    public void onError(Session session, Throwable error) {
        log.error("Error: {}", error.getMessage());
    }

    @Override
    public void close() throws IOException {
        IoUtil.close(session);
    }

    @SneakyThrows
    public void sendMessage(String message) {
        if (session != null && session.isOpen()) {
            session.getBasicRemote().sendText(message);
        }
    }
}
