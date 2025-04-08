package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.entity.SystemConfig;
import org.example.mapper.SystemConfigMapper;
import org.example.service.SystemConfigService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SystemConfigServiceImpl extends ServiceImpl<SystemConfigMapper, SystemConfig> implements SystemConfigService {
    @Override
    public List<SystemConfig> getLikeByKey(String key) {
        return lambdaQuery()
                .like(SystemConfig::getKey, key)
                .list();
    }
}
