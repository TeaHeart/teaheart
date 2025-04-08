package org.example.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Node {
    private Integer id;

    private NodeType type;

    private String address;

    private String command;

    private String description;

    @TableField(exist = false)
    private List<History> historyList;
}
