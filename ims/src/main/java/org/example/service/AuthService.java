package org.example.service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.example.controller.BusinessException;
import org.example.mapper.UserMapper;
import org.example.model.Privilege;
import org.example.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

@Service
public class AuthService extends ServiceImpl<UserMapper, User> {
    @Autowired
    private HttpSession httpSession;

    public User login(User user) {
        User find = lambdaQuery()
                .eq(User::getUsername, user.getUsername())
                .eq(User::getPassword, user.getPassword())
                .one();
        if (find == null) {
            throw new BusinessException("用户名或密码错误");
        }
        if (!Privilege.USER_LOGIN.hasPrivilege(find.getPrivilege())) {
            throw new BusinessException("没有权限");
        }
        httpSession.setAttribute("user", find);
        return find;
    }

    public boolean logout() {
        httpSession.invalidate();
        return true;
    }

    public boolean changePassword(User user) {
        User me = me();
        if (!Privilege.USER_CHANGE_PASSWORD.hasPrivilege(me.getPrivilege())) {
            throw new BusinessException("没有权限");
        }
        return lambdaUpdate()
                .eq(User::getId, me.getId())
                .set(User::getPassword, user.getPassword())
                .update() && logout();
    }

    public User me() {
        return (User) httpSession.getAttribute("user");
    }
}
