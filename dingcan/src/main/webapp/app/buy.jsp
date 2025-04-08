<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dingcan.*"%>
<%
Integer userId = ((User) session.getAttribute("user")).id;
Integer dishId = Integer.parseInt(request.getParameter("dishId"));
int r = DB.update("insert into `order` values(default, ?, ?)", userId, dishId);
if (r > 0) {
    out.print("下单成功");
} else {
    out.print("下单失败");
}
response.setHeader("refresh", "1; url=dish.jsp");
%>