<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dingcan.*"%>
<%
String username = request.getParameter("username");
String sql = "select * from user";
List<String> args = new ArrayList<>();
if (username != null && !username.isEmpty()) {
    sql += " where username like ?";
    args.add("%" + username + "%");
}
List<User> list = DB.query(sql, User::converter, args.toArray());
%>
<a href="<%=request.getContextPath()%>/index.jsp">主页</a>
<form action="list.jsp" method="get">
    用户名<input type="text" name="username"/><br/>
    <button type="submit">搜索</button>
</form>
<a href="add.jsp">添加</a>
<table border="1">
    <tr>
        <th>ID</th>
        <th>用户名</th>
        <th>密码</th>
        <th>是管理员</th>
        <th>操作</th>
    </tr>
<%
for (User item : list) {
%>
    <tr>
        <th><%=item.id%></th>
        <th><%=item.username%></th>
        <th><%=item.password%></th>
        <th><%=item.isAdmin ? "是" : ""%></th>
        <th>
            <a href="edit.jsp?id=<%=item.id%>">修改</a>
            <a href="del.jsp?id=<%=item.id%>">删除</a>
        </th>
    </tr>
<%
}
%>
</table>