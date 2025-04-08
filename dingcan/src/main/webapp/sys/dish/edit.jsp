<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dingcan.*"%>
<%
Integer id = Integer.parseInt(request.getParameter("id"));
if ("POST".equals(request.getMethod())) {
    String name = request.getParameter("name");
    Double price = Double.parseDouble(request.getParameter("price"));
    int r = DB.update("update dish set name = ?, price = ? where id = ?", name, price, id);
    if (r > 0) {
        out.print("修改成功");
        response.setHeader("refresh", "1; url=list.jsp");
    } else {
        out.print("修改失败");
        response.setHeader("refresh", "1; url=edit.jsp?id=" + id);
    }
    return;
}
Dish item = DB.query("select * from dish where id = ?", Dish::converter, id).get(0);
%>
<form action="edit.jsp" method="post">
    <!-- ID --><input type="hidden" name="id" value="<%=item.id%>"/><br/>
    菜名<input type="text" name="name" value="<%=item.name%>"/><br/>
    价格<input type="text" name="price" value="<%=item.price%>"/><br/>
    <button type="submit">修改</button>
</form>
<a href="list.jsp">返回</a>