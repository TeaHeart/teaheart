<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="dingcan.*"%>
<%
if ("POST".equals(request.getMethod())) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    int r = DB.update("insert into user values(default, ?, ?, false)", username, password);
    if (r > 0) {
        out.print("注册成功");
        response.setHeader("refresh", "1; url=login.jsp");
    } else {
        out.print("注册失败");
        response.setHeader("refresh", "1; url=register.jsp");
    }
    return;
}
%>
<form action="register.jsp" method="post">
	用户名<input type="text" name="username"/><br/>
    密码<input type="password" name="password"/><br/>
	<button type="submit">注册</button>
</form>
<a href="login.jsp">登录</a>