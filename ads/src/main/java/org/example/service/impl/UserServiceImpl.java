package org.example.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.controller.advice.BusinessException;
import org.example.entity.User;
import org.example.mapper.UserMapper;
import org.example.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Autowired
    private HttpSession session;

    @Override
    public List<User> getLikeUsername(String username) {
        return lambdaQuery()
                .like(User::getUsername, username)
                .list();
    }

    @Override
    public User login(User user) {
        User find = lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword())
                .oneOpt()
                .orElseThrow(() -> new BusinessException("username or password incorrect"));
        session.setAttribute("user", find);
        return find;
    }

    @Override
    public boolean logout() {
        session.invalidate();
        return true;
    }

    @Override
    public boolean changePassword(User user) {
        return lambdaUpdate()
                .eq(User::getId, me().getId())
                .set(User::getPassword, user.getPassword())
                .update() && logout();
    }

    @Override
    public User me() {
        return (User) session.getAttribute("user");
    }
}
