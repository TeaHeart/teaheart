package org.example.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.example.entity.User;

import java.util.List;

public interface UserService extends IService<User> {
    List<User> getLikeUsername(String username);

    User login(User user);

    boolean logout();

    boolean changePassword(User user);

    User me();
}
