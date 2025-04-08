package org.example.config;

import org.example.service.NodeService;
import org.example.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;

@Configuration
public class SchedulingConfig implements SchedulingConfigurer {
    @Autowired
    private ZCloudConfiguration.ZCloudConfig zCloudConfig;

    @Autowired
    private NodeService nodeService;

    @Autowired
    private PolicyService policyService;

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        taskRegistrar.addFixedDelayTask(nodeService::sync, zCloudConfig.getUpdateInterval());
        taskRegistrar.addFixedDelayTask(policyService::control, zCloudConfig.getUpdateInterval());
    }
}
