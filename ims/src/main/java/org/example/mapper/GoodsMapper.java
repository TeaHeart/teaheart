package org.example.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.model.Goods;

@Mapper
public interface GoodsMapper extends BaseMapper<Goods> {
}
