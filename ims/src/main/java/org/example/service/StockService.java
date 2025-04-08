package org.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.StockMapper;
import org.example.model.Stock;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StockService extends ServiceImpl<StockMapper, Stock> {
    public List<Stock> listWithGoods() {
        return baseMapper.selectAllWithGoods();
    }

    public Stock getOneWithGoodsById(int id) {
        return baseMapper.selectOneWithGoods(id);
    }

    public List<Stock> listWithGoodsByGid(int gid) {
        return baseMapper.selectWithGoodsByGid(gid);
    }
}
