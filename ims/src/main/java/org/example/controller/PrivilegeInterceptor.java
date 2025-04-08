package org.example.controller;

import org.example.model.Privilege;
import org.example.model.User;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import static org.example.model.Privilege.*;

public class PrivilegeInterceptor implements HandlerInterceptor {
    private static final Map<String, EnumSet<Privilege>> METHOD_MAP = new HashMap<>();
    private static final Map<String, EnumSet<Privilege>> PATH_MAP = new HashMap<>();

    static {
        METHOD_MAP.put("POST", EnumSet.of(USER_INSERT, GOODS_INSERT, STOCK_INSERT));
        METHOD_MAP.put("DELETE", EnumSet.of(USER_DELETE, GOODS_DELETE, STOCK_DELETE));
        METHOD_MAP.put("PUT", EnumSet.of(USER_UPDATE, GOODS_UPDATE, STOCK_UPDATE));
        METHOD_MAP.put("GET", EnumSet.of(USER_SELECT, GOODS_SELECT, STOCK_SELECT));

        PATH_MAP.put("users", EnumSet.of(USER_INSERT, USER_DELETE, USER_UPDATE, USER_SELECT));
        PATH_MAP.put("goods", EnumSet.of(GOODS_INSERT, GOODS_DELETE, GOODS_UPDATE, GOODS_SELECT));
        PATH_MAP.put("stock", EnumSet.of(STOCK_INSERT, STOCK_DELETE, STOCK_UPDATE, STOCK_SELECT));
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String method = request.getMethod();
        EnumSet<Privilege> p1 = METHOD_MAP.get(method);
        if (p1 == null) {
            return true;
        }
        String path = request.getServletPath();
        String[] split = path.split("/", 4);
        EnumSet<Privilege> p2;
        if (split.length <= 2 || (p2 = PATH_MAP.get(split[2])) == null) {
            return true;
        }
        User user = (User) request.getSession().getAttribute("user");
        int privilege = user.getPrivilege();
        for (Privilege p : Privilege.values()) {
            if (p1.contains(p) && p2.contains(p) && !p.hasPrivilege(privilege)) {
                throw new BusinessException("没有权限");
            }
        }
        return true;
    }
}
