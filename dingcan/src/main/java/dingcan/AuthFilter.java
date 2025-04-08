package dingcan;

import java.io.*;
import java.util.*;
import java.util.logging.Logger;

import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

@WebFilter("/*")
public class AuthFilter implements Filter {
    private static final Logger log = Logger.getLogger(AuthFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");

        String[] path = req.getServletPath().split("/");
        path[0] = req.getContextPath();

        log.info(String.format("%s:%s -> %s %s%s", req.getRemoteAddr(), req.getRemotePort(), req.getMethod(), req.getContextPath(), req.getServletPath()));

        User user = (User) req.getSession().getAttribute("user");

        if (user == null) {
            if (!"login.jsp".equals(path[1]) && !"register.jsp".equals(path[1])) {
                resp.sendRedirect(path[0] + "/login.jsp");
                return;
            }
        } else {
            if ("login.jsp".equals(path[1]) || "register.jsp".equals(path[1])) {
                resp.sendRedirect(path[0] + "/index.jsp");
                return;
            } else if ("sys".equals(path[1]) && !user.isAdmin) {
                resp.sendRedirect(path[0] + "/index.jsp");
                return;
            }
        }
        chain.doFilter(request, response);
    }
}
