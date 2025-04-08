package org.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.mapper.UserMapper;
import org.example.model.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService extends ServiceImpl<UserMapper, User> {
    public List<User> getLikeUsername(String username) {
        return lambdaQuery()
                .like(User::getUsername, username)
                .list();
    }
}
