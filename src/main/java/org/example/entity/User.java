package org.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

/**
 * <p>
 * </p>
 *
 * @author TeaHeart
 * @since 2025-03-09
 */
@Getter
@Setter
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private String username;

    private String password;
}
