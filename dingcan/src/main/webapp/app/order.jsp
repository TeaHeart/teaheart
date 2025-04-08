<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="java.util.stream.*"%>
<%@ page import="dingcan.*"%>
<%
String username = ((User) session.getAttribute("user")).username;
List<Order> list = DB.query("select * from `order` where user_id in (select id from user where username = ?)", Order::converter, username);
Map<Integer, Dish> dishes = DB.query("select * from dish", Dish::converter).stream().collect(Collectors.toMap(item -> item.id, item -> item));
%>
<a href="<%=request.getContextPath()%>/index.jsp">主页</a>
<table border="1">
    <tr>
        <th>ID</th>
        <th>用户名</th>
        <th>菜品</th>
    </tr>
<%
for (Order item : list) {
%>
    <tr>
        <th><%=item.id%></th>
        <th><%=username%></th>
        <th><%=dishes.get(item.dishId).name%></th>
    </tr>
<%
}
%>
</table>