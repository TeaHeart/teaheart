<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="dingcan.*"%>
<%
if ("POST".equals(request.getMethod())) {
    String name = request.getParameter("name");
    Double price = Double.parseDouble(request.getParameter("price"));
    int r = DB.update("insert into dish values(default, ?, ?)", name, price);
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
    菜名<input type="text" name="name"/><br/>
    价格<input type="text" name="price"/><br/>
    <button type="submit">添加</button>
</form>
<a href="list.jsp">返回</a>