<%@ page contentType="text/html;charset=UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx"
       value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
    <title>答题页面</title>
   <link href="<%=request.getContextPath()%>/view/common/assets/h5/css/normalize.css" rel="stylesheet" type="text/css">
    <link href="<%=request.getContextPath()%>/view/common/assets/h5/css/answer-end.css" rel="stylesheet" type="text/css">
</head>
<body>
    <header class="title">
        <a class="r_return" onclick="popPage()"></a>
        智联测评
    </header>
    <div class="content img">
        <img src="./assets/h5/image/ok.jpg"/>
        <div class="rt">
            <h3>已成功提交</h3>
            <p>您的结果已成功提交，感谢您的配合。</p>
        </div>
    </div>
</body>
</html>