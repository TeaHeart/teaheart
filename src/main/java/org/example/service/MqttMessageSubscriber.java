package org.example.service;

import org.springframework.stereotype.Component;
import com.github.tocrhz.mqtt.annotation.MqttSubscribe;
import com.github.tocrhz.mqtt.annotation.NamedValue;
import com.github.tocrhz.mqtt.annotation.Payload;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MqttMessageSubscriber {
    @MqttSubscribe("test/{id}")
    public void sub(String topic, @NamedValue("id") String id, @Payload String payload) {
        log.info("{} {} {}", topic, id, payload);
    }
}
