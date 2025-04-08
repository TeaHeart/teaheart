<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dingcan.*"%>
<%
if ("POST".equals(request.getMethod())) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    List<User> list = DB.query("select * from user where username = ? and password = ?", User::converter,username, password);
    if (!list.isEmpty()) {
        session.setAttribute("user", list.get(0));
        out.print("登录成功");
        response.setHeader("refresh", "1; url=index.jsp");
    } else {
        out.print("登录失败");
        response.setHeader("refresh", "1; url=login.jsp");
    }
    return;
}
%>
<form action="login.jsp" method="post">
    用户名<input type="text" name="username"/><br/>
    密码<input type="password" name="password"/><br/>
    <button type="submit">登录</button>
</form>
<a href="register.jsp">注册</a>