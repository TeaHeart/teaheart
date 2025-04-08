<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dingcan.*"%>
<%
if ("POST".equals(request.getMethod())) {
    Integer userId = Integer.parseInt(request.getParameter("userId"));
    Integer dishId = Integer.parseInt(request.getParameter("dishId"));
    int r = DB.update("insert into `order` values(default, ?, ?)", userId, dishId);
    if (r > 0) {
        out.print("添加成功");
        response.setHeader("refresh", "1; url=list.jsp");
    } else {
        out.print("添加失败");
        response.setHeader("refresh", "1; url=add.jsp");
    }
    return;
}
List<User> users = DB.query("select * from user", User::converter);
List<Dish> dishes = DB.query("select * from dish", Dish::converter);
%>
<form action="add.jsp" method="post">
    用户
    <select name="userId">
        <option value="" selected></option>
<%
for (User item : users) {    
%>
        <option value="<%=item.id%>"><%=item.username%></option>
<%
}
%>
    </select><br/>
    菜品
    <select name="dishId">
        <option value="" selected></option>
<%
for (Dish item : dishes) {    
%>
        <option value="<%=item.id%>"><%=item.name%></option>
<%
}
%>
    </select><br/>
    <button type="submit">添加</button>
</form>
<a href="list.jsp">返回</a>