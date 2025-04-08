<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="dingcan.*"%>
<%
session.invalidate();
out.print("登出成功");
response.setHeader("refresh", "1; url=login.jsp");
%>