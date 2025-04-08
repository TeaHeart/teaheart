<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dingcan.*"%>
<%
Integer id = Integer.parseInt(request.getParameter("id"));
if ("POST".equals(request.getMethod())) {
    String username = request.getParameter("username");
    String password = request.getParameter("password");
    Boolean isAdmin = Boolean.valueOf(request.getParameter("isAdmin"));
    int r = DB.update("update user set username = ?, password = ?, is_admin = ? where id = ?", username, password, isAdmin, id);
    if (r > 0) {
        out.print("修改成功");
        response.setHeader("refresh", "1; url=list.jsp");
    } else {
        out.print("修改失败");
        response.setHeader("refresh", "1; url=edit.jsp?id=" + id);
    }
    return;
}
User item = DB.query("select * from user where id = ?", User::converter, id).get(0);
%>
<form action="edit.jsp" method="post">
    <!-- ID --><input type="hidden" name="id" value="<%=item.id%>"/><br/>
    用户名<input type="text" name="username" value="<%=item.username%>"/><br/>
    密码<input type="password" name="password" value="<%=item.password%>"/><br/>
    是管理员<input type="checkbox" name="isAdmin" value="true" <%=item.isAdmin ? "checked" : ""%>/><br/>
    <button type="submit">修改</button>
</form>
<a href="list.jsp">返回</a>