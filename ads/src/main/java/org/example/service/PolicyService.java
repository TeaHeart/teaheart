package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.Policy;

import java.util.List;

public interface PolicyService extends IService<Policy> {
    List<Policy> listEnabledWithNode();

    List<Policy> listByEnabled(boolean enabled);

    void control();
}
