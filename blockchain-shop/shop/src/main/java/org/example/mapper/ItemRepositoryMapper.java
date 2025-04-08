package org.example.mapper;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.example.domain.ItemRepository;

import java.util.List;

@Mapper
public interface ItemRepositoryMapper extends BaseMapper<ItemRepository> {
    @Override
    @Select("select ir.id    as id,\n" +
            "       account_id,\n" +
            "       username,\n" +
            "       password,\n" +
            "       a.token  as atoken,\n" +
            "       item_info_id,\n" +
            "       name,\n" +
            "       description,\n" +
            "       ir.token as token,\n" +
            "       is_available\n" +
            "from item_repository as ir\n" +
            "         inner join account a on ir.account_id = a.id\n" +
            "         inner join item_info i on ir.item_info_id = i.id")
    @Results({
            @Result(column = "id", property = "id"),
            @Result(column = "account_id", property = "accountId"),
            @Result(column = "item_info_id", property = "itemInfoId"),
            @Result(column = "token", property = "token"),
            @Result(column = "is_available", property = "isAvailable"),
            @Result(column = "account_id", property = "account.id"),
            @Result(column = "username", property = "account.username"),
            @Result(column = "password", property = "account.password"),
            @Result(column = "atoken", property = "account.token"),
            @Result(column = "item_info_id", property = "itemInfo.id"),
            @Result(column = "name", property = "itemInfo.name"),
            @Result(column = "description", property = "itemInfo.description")
    })
    List<ItemRepository> selectList(Wrapper<ItemRepository> queryWrapper);
}
