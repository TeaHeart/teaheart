package org.example.controller;

import org.example.controller.advice.Result;
import org.example.entity.User;
import org.example.service.UserFeign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/fegin")
public class FeginController {
    @Autowired
    private UserFeign userFeign;

    @GetMapping
    public Result<List<User>> list() {
        Result<List<User>> result = userFeign.list();
        result.setMessage("From UserFeign");
        return result;
    }
}
