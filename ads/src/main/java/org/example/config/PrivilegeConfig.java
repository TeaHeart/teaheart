package org.example.config;

import lombok.Data;
import org.example.controller.advice.BusinessException;
import org.example.entity.Role;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Data
@Configuration
@ConfigurationProperties("ads.privilege")
public class PrivilegeConfig {
    private List<String> farmer;
    private List<String> technician;
    private List<String> administrator;

    public List<String> getPrivilege(Role role) {
        switch (role) {
            case FARMER: {
                return farmer;
            }
            case TECHNICIAN: {
                return technician;
            }
            case ADMINISTRATOR: {
                return administrator;
            }
            default: {
                throw new BusinessException("Invalid role");
            }
        }
    }
}
