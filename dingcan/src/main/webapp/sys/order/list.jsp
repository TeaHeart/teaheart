<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.stream.*"%>
<%@ page import="dingcan.*"%>
<%
String username = request.getParameter("username");
String sql = "select * from `order`";
List<String> args = new ArrayList<>();
if (username != null && !username.isEmpty()) {
    sql += " where user_id in (select id from user where username like ?)";
    args.add("%" + username + "%");
}
List<Order> list = DB.query(sql, Order::converter, args.toArray());
Map<Integer, User> users = DB.query("select * from user", User::converter).stream().collect(Collectors.toMap(item -> item.id, item -> item));
Map<Integer, Dish> dishes = DB.query("select * from dish", Dish::converter).stream().collect(Collectors.toMap(item -> item.id, item -> item));
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
        <th>菜品</th>
        <th>操作</th>
    </tr>
<%
for (Order item : list) {
%>
    <tr>
        <th><%=item.id%></th>
        <th><%=users.get(item.userId).username%></th>
        <th><%=dishes.get(item.dishId).name%></th>
        <th>
            <a href="edit.jsp?id=<%=item.id%>">修改</a>
            <a href="del.jsp?id=<%=item.id%>">删除</a>
        </th>
    </tr>
<%
}
%>
</table>