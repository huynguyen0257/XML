<%--
  Created by IntelliJ IDEA.
  User: giahuy
  Date: 7/13/20
  Time: 2:02 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<html>
<head>
    <link href="css/main.css" rel="stylesheet" type="text/css">
    <title>Smart Choice</title>
</head>
<body>
<header>
    <h1>ERROR PAGE</h1>
</header>
<x:parse xml="${RAM}" var="ram"/>
<x:parse xml="${MONITOR}" var="monitor"/>
<c:if test="${not empty BRAND}">
    <x:transform doc="${BRAND}" xslt="${NAV_XSLT}"/>
</c:if>
<c:if test="${empty BRAND}">
    Have some problems
</c:if>
<div class="content">
    <h2>
        <c:out value="${requestScope.ERROR}"/>
    </h2>
</div>

<footer>
    <h4>Nguyen Gia Huy - SE63158</h4>
</footer>
</body>
</html>
