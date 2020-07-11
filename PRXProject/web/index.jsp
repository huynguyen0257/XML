<%--
  Created by IntelliJ IDEA.
  User: giahuy
  Date: 6/21/20
  Time: 2:12 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <link href="main.css" rel="stylesheet" type="text/css">
    <title>Smart Choice</title>
</head>
<body>
<div class="header">
    <h1>SMART CHOICE</h1>
</div>
<div class="navBar">
    <ul>
        <li>LG</li>
        <li>DELL</li>
    </ul>
</div>
<div class="content">
    <div class="suggest-container">
        <div class="abc"></div>
    </div>
    <div class="some-laptops">
        <h1>Some advices</h1>
<%--        <c:import url="WEB-INF/xsl/index.xsl" var="xslt"/>--%>
        <c:if test="${not empty INFO}">
            <x:transform doc="${INFO}" xslt="${xslt}"/>
        </c:if>
        <c:if test="${empty INFO}">
            Have some problems
        </c:if>
    </div>
</div>
<div class="footer">
    <h4>I am a footer</h4>
</div>
</body>
</html>
