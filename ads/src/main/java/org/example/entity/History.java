package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class History {
    private Integer id;

    private Integer nodeId;

    private LocalDateTime time;

    private String data;

    @TableField(exist = false)
    private Node node;
}
