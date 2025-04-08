package org.example.controller.advice;

import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpStatus;
import lombok.extern.slf4j.Slf4j;
import org.example.config.PrivilegeConfig;
import org.example.entity.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {
    private PrivilegeConfig privilegeConfig;

    public AuthInterceptor(PrivilegeConfig privilegeConfig) {
        this.privilegeConfig = privilegeConfig;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            throw new BusinessException(HttpStatus.HTTP_UNAUTHORIZED, "Please login first");
        }
        String method = request.getMethod();
        String path = request.getServletPath();
        String req = StrUtil.format("{} {}", method, path);
        if (privilegeConfig.getPrivilege(user.getRole()).stream().noneMatch(req::startsWith)) {
            throw new BusinessException(HttpStatus.HTTP_FORBIDDEN, "Forbidden access " + req);
        }
        return true;
    }
}
