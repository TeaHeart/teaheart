package org.example.service;

import java.util.List;
import org.example.controller.advice.Result;
import org.example.entity.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(value = "UserFeign", url = "http://localhost:8080")
public interface UserFeign {
    @GetMapping("/user")
    Result<List<User>> list();
}
