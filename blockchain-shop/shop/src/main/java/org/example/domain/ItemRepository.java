package org.example.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemRepository {
    @TableId(type = IdType.AUTO)
    Integer id;
    Integer accountId;
    Integer itemInfoId;
    String token;
    Boolean isAvailable;

    @TableField(exist = false)
    Account account;
    @TableField(exist = false)
    ItemInfo itemInfo;
}
