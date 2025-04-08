<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="dingcan.*"%>
<%
Integer id = Integer.parseInt(request.getParameter("id"));
int r = DB.update("delete from dish where id = ?", id);
if (r > 0) {
    out.print("删除成功");
} else {
    out.print("删除失败");
}
response.setHeader("refresh", "1; url=list.jsp");
%>