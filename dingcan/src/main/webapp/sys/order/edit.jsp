<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dingcan.*"%>
<%
Integer id = Integer.parseInt(request.getParameter("id"));
if ("POST".equals(request.getMethod())) {
    Integer userId = Integer.parseInt(request.getParameter("userId"));
    Integer dishId = Integer.parseInt(request.getParameter("dishId"));
    int r = DB.update("update `order` set user_id = ?, dish_id = ? where id = ?", userId, dishId, id);
    if (r > 0) {
        out.print("修改成功");
        response.setHeader("refresh", "1; url=list.jsp");
    } else {
        out.print("修改失败");
        response.setHeader("refresh", "1; url=edit.jsp?id=" + id);
    }
    return;
}
Order order = DB.query("select * from `order` where id = ?", Order::converter, id).get(0);
List<User> users = DB.query("select * from user", User::converter);
List<Dish> dishes = DB.query("select * from dish", Dish::converter);
%>
<form action="edit.jsp" method="post">
    <!-- ID --><input type="hidden" name="id" value="<%=order.id%>"/><br/>
    用户
    <select name="userId">
<%
for (User item : users) {    
%>
        <option value="<%=item.id%>" <%=order.userId.equals(item.id) ? "selected" : ""%>><%=item.username%></option>
<%
}
%>
    </select><br/>
    菜品
    <select name="dishId">
<%
for (Dish item : dishes) {    
%>
        <option value="<%=item.id%>" <%=order.dishId.equals(item.id) ? "selected" : ""%>><%=item.name%></option>
<%
}
%>
    </select><br/>
    <button type="submit">修改</button>
</form>
<a href="list.jsp">返回</a>