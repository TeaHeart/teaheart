package org.example.model;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    @TableId(type = IdType.AUTO)
    private Integer id;
    private Integer gid;
    private Double price;
    private Integer amount;
    @TableField(exist = false)
    private Goods goods;
}
