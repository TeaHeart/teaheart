package org.example.entity;

import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Role {
    NULL("null"),
    FARMER("农民"),
    TECHNICIAN("技术员"),
    ADMINISTRATOR("管理员"),
    ;

    @EnumValue
    @JsonValue
    private final String description;

    @Override
    public String toString() {
        return description;
    }
}
