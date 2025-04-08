package org.example.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum NodeType {
    NULL("null"),
    SENSOR_A("Sensor-A"),
    SENSOR_B("Sensor-B"),
    SENSOR_C("Sensor-C"),
    ;

    @EnumValue
    @JsonValue
    private final String type;

    @Override
    public String toString() {
        return type;
    }
}
