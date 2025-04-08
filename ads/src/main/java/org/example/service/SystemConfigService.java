package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.SystemConfig;

import java.util.List;

public interface SystemConfigService extends IService<SystemConfig> {
    List<SystemConfig> getLikeByKey(String key);
}
