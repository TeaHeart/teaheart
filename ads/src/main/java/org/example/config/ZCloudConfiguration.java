package org.example.config;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import feign.Feign;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.entity.History;
import org.example.entity.Node;
import org.example.service.HistoryService;
import org.example.service.SystemConfigService;
import org.example.zcloud.ZCloudFeignClient;
import org.springframework.cloud.openfeign.support.SpringMvcContract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

@Slf4j
@Configuration
public class ZCloudConfiguration {
    @Bean
    public ZCloudConfig zCloudConfig(SystemConfigService systemConfigService) {
        // TODO 自动封装配置对象
        ZCloudConfig config = new ZCloudConfig();
        systemConfigService.getLikeByKey("ads.zcloud")
                .forEach(systemConfig -> {
            switch (systemConfig.getKey()) {
                case "ads.zcloud.uid": {
                    config.setUid(systemConfig.getValue());
                    break;
                }
                case "ads.zcloud.key": {
                    config.setKey(systemConfig.getValue());
                    break;
                }
                case "ads.zcloud.socket-server": {
                    config.setSocketServer(systemConfig.getValue());
                    break;
                }
                case "ads.zcloud.web-server": {
                    config.setWebServer(systemConfig.getValue());
                    break;
                }
                case "ads.zcloud.update-internal": {
                    config.setUpdateInterval(Integer.parseInt(systemConfig.getValue()));
                    break;
                }
                default: {
                    log.warn("unknown config: {}={}", systemConfig.getKey(), systemConfig.getValue());
                    break;
                }
            }
        });
        log.info("Load ZCloudConfig from database: {}", config);
        return config;
    }

    @Bean
    public WebSocketSession webSocketSession(ZCloudConfig zCloudConfig, HistoryService historyService, ZCloudDataConverter zCloudDataConverter) throws ExecutionException, InterruptedException {
        return new StandardWebSocketClient().doHandshake(new TextWebSocketHandler() {
            @Override
            public void afterConnectionEstablished(WebSocketSession session) throws Exception {
                String message = new JSONObject() {{
                    put("method", "authenticate");
                    put("uid", zCloudConfig.getUid());
                    put("key", zCloudConfig.getKey());
                }}.toString();
                log.info("Send message: {}", message);
                session.sendMessage(new TextMessage(message));
            }

            @Override
            public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
                super.handleTransportError(session, exception);
            }

            @Override
            protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
                try {
                    // log.info("Received message: {}", message.getPayload());
                    JSONObject obj = JSONUtil.parseObj(message.getPayload());
                    if ("message".equals(obj.get("method"))) {
                        String address = (String) obj.get("addr");
                        String data = (String) obj.get("data");
                        Map<String, String> dataMap = zCloudDataConverter.convert(data);
                        if (!dataMap.containsKey("TYPE")) {
                            historyService.saveByMac(History.builder()
                                    .node(Node.builder()
                                            .address(address)
                                            .build())
                                    .data(data)
                                    .build());
                            log.debug("Saved data: {}", message.getPayload());
                        }
                    }
                } catch (Exception e) {
                    log.warn(Optional.ofNullable(e.getCause()).orElse(e).getMessage());
                }
            }
        }, zCloudConfig.getSocketServer()).get();
    }

    @Bean
    public ZCloudFeignClient zCloudFeignClient(ZCloudConfig zCloudConfig) {
        return Feign.builder()
                .requestInterceptor(template -> template.header("X-ApiKey", zCloudConfig.getKey()))
                .contract(new SpringMvcContract())
                .target(ZCloudFeignClient.class, zCloudConfig.getWebServer());
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ZCloudConfig {
        private String uid;
        private String key;
        private String socketServer;
        private String webServer;
        private Integer updateInterval;
    }
}
