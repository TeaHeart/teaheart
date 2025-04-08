package org.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.GoodsMapper;
import org.example.model.Goods;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GoodsService extends ServiceImpl<GoodsMapper, Goods> {
    public List<Goods> getLikeName(String name) {
        return lambdaQuery()
                .like(Goods::getName, name)
                .list();
    }
}
