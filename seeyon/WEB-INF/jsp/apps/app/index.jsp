<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html class="over_hidden h100b">
<head>
    <%@include file="/WEB-INF/jsp/common/common_header.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="renderer" content="webkit">
    <title>应用</title>
</head>
<body class="over_hidden h100b">
<div>
    <div>
        <div id="toolbar"></div>
    </div>
    <div id="layout_bottom">
        <table id="table" style="display: none"></table>
    </div>
</div>
</body>
<%@include file="/WEB-INF/jsp/common/common_footer.jsp" %>
<script type="text/javascript" src="${path}/apps_res/app/js/index.js${ctp:resSuffix()}"></script>
<script type="text/javascript">
    $().ready(function () {
        app.initToolbar();
        app.initGrid();
    });
</script>
</html>