<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="dingcan.*"%>
<%
User user = (User) session.getAttribute("user");
%>
欢迎<%=user.username%>
<a href="logout.jsp">登出</a><br/>
<%
if (user.isAdmin) {
%>
<a href="sys/user/list.jsp">用户管理</a>
<a href="sys/dish/list.jsp">菜品管理</a>
<a href="sys/order/list.jsp">订单管理</a><br/>
<%
}
%>
<a href="app/dish.jsp">订餐</a>
<a href="app/order.jsp">订单</a><br/>
