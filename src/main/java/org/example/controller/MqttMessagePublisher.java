package org.example.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.github.tocrhz.mqtt.publisher.MqttPublisher;

@RestController
@RequestMapping("/mqtt")
public class MqttMessagePublisher {
    @Autowired
    private MqttPublisher mqttPublisher;

    @GetMapping("/{id}/{message}")
    public String pub(@PathVariable String id, @PathVariable String message) {
        mqttPublisher.send("test/" + id, message);
        return "ok";
    }
}
