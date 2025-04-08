package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.example.model.Stock;

import java.util.List;

@Mapper
public interface StockMapper extends BaseMapper<Stock> {
    @Select("select s.id as id, s.price as price, s.amount as amount, " +
            "g.id as 'goods.id', g.name as 'goods.name' " +
            "from stock s " +
            "left join goods g on s.gid = g.id")
    List<Stock> selectAllWithGoods();

    @Select("select s.id as id, s.price as price, s.amount as amount, " +
            "g.id as 'goods.id', g.name as 'goods.name' " +
            "from stock s " +
            "left join goods g on s.gid = g.id " +
            "where s.id = #{id}")
    Stock selectOneWithGoods(int id);

    @Select("select s.id as id, s.price as price, s.amount as amount, " +
            "g.id as 'goods.id', g.name as 'goods.name' " +
            "from stock s " +
            "left join goods g on s.gid = g.id " +
            "where g.id = #{gid}")
    List<Stock> selectWithGoodsByGid(int gid);
}
