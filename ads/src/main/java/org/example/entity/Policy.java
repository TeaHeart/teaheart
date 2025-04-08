package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Policy {
    private Integer id;

    private Integer sourceId;

    private Integer targetId;

    private Boolean enabled;

    private String expression;

    private String command;

    private String description;

    @TableField(exist = false)
    private Node sourceNode;

    @TableField(exist = false)
    private Node targetNode;
}
