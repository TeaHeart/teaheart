<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html class="over_hidden h100b">
<head>
    <%@include file="/WEB-INF/jsp/common/common_header.jsp" %>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta name="renderer" content="webkit">
    <title>保存</title>
</head>
<body class="over_hidden h100b">
<div id="saveApp">
    <form id="saveForm" action="${path}/taskmanage/taskinfo.do?method=saveTaskType${ctp:csrfSuffix()}">
        <div class="form_area">
            <div class="one_row common_tabs_body color_gray2 font_size14">
                <table border="0" cellspacing="0" cellpadding="0">
                    <tbody>
                    <tr>
                        <th nowrap="nowrap">
                            <span class="color_red">*</span>
                            <label class="margin_r_10" for="key">key</label>
                        </th>
                        <td width="100%">
                            <div class="common_txtbox_wrap">
                                <input class="validate" type="text" id="key" name="key"
                                       validate="name:'key',type:'string',notNullWithoutTrim:true">
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <th nowrap="nowrap">
                            <span class="color_red">*</span>
                            <label class="margin_r_10" for="value">value</label>
                        </th>
                        <td width="100%">
                            <div class="common_txtbox_wrap">
                                <input class="validate" type="text" id="value" name="value"
                                       validate="name:'value',type:'string',notNullWithoutTrim:true">
                            </div>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </form>
</div>
</body>
<%@include file="/WEB-INF/jsp/common/common_footer.jsp" %>
<script type="text/javascript">
    function OK() {
        if (!$("#saveForm").validate()) {
            return null;
        } else {
            return $("#saveForm").formobj();
        }
    }
</script>
</html>