<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dingcan.*"%>
<%
String name = request.getParameter("name");
String sql = "select * from dish";
List<String> args = new ArrayList<>();
if (name != null && !name.isEmpty()) {
    sql += " where name like ?";
    args.add("%" + name + "%");
}
List<Dish> list = DB.query(sql, Dish::converter, args.toArray());
%>
<a href="<%=request.getContextPath()%>/index.jsp">主页</a>
<form action="dish.jsp" method="get">
    菜名<input type="text" name="name"/><br/>
    <button type="submit">搜索</button>
</form>
<table border="1">
    <tr>
        <th>ID</th>
        <th>菜名</th>
        <th>价格</th>
        <th>操作</th>
    </tr>
<%
for (Dish item : list) {
%>
    <tr>
        <th><%=item.id%></th>
        <th><%=item.name%></th>
        <th><%=item.price%></th>
        <th><a href="buy.jsp?dishId=<%=item.id%>">下单</a></th>
    </tr>
<%
}
%>
</table>