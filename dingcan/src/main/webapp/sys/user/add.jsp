<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="dingcan.*"%>
<%
if ("POST".equals(request.getMethod())) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    Boolean isAdmin = Boolean.valueOf(request.getParameter("isAdmin"));
    int r = DB.update("insert into user values(default, ?, ?, ?)", username, password, isAdmin);
    if (r > 0) {
        out.print("添加成功");
        response.setHeader("refresh", "1; url=list.jsp");
    } else {
        out.print("添加失败");
        response.setHeader("refresh", "1; url=add.jsp");
    }
    return;
}
%>
<form action="add.jsp" method="post">
    用户名<input type="text" name="username"/><br/>
    密码<input type="text" name="password"/><br/>
    是管理员<input type="checkbox" name="isAdmin" value="true"/><br/>
    <button type="submit">添加</button>
</form>
<a href="list.jsp">返回</a>