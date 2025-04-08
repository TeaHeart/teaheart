package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.domain.ItemInfo;
import org.example.mapper.ItemInfoMapper;
import org.example.service.ItemInfoService;
import org.springframework.stereotype.Service;

@Service
public class ItemInfoServiceImpl extends ServiceImpl<ItemInfoMapper, ItemInfo> implements ItemInfoService {

}
