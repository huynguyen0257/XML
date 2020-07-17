<%--
  Created by IntelliJ IDEA.
  User: giahuy
  Date: 7/13/20
  Time: 2:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<html>
<head>
    <link href="main.css" rel="stylesheet" type="text/css">
    <title>Smart Choice</title>
</head>
<body>
<header>
    <h1>SMART CHOICE</h1>
</header>
<x:parse xml="${requestScope.LIST_LAPTOP_XML}" var="laptops"/>
<c:if test="${not empty BRAND}">
    <x:transform doc="${BRAND}" xslt="${NAV_XSLT}"/>
</c:if>
<c:if test="${empty BRAND}">
    Can not load brand
</c:if>
<div class="content">
    <h1><c:out value="${requestScope.BRAND_NAME}"/></h1>
    <div class="list-laptop">
        <x:forEach select="$laptops/laptops/laptop">
            <div class="laptop">
                <a href="MainServlet?btAction=showDetailLaptop&laptopId=<x:out select="id"/>">
                    <img src="<x:out select="image"/>"/>
                </a>
                <div class="laptop-info">
                    <a href="MainServlet?btAction=showDetailLaptop&laptopId=<x:out select="id"/>">
                        <h3><x:out select="name"/></h3>
                    </a>
                    <h4><x:out select="price"/> VND</h4>
                    <p>CPU : <x:out select="processor/name"/> | RAM : <x:out select="ram/memory"/> GB | Display : <x:out
                            select="monitor/size"/> Inch</p>
                </div>
            </div>
        </x:forEach>
    </div>
    <ul class="paging">
        <x:if select="$laptops/laptops/pageNumber[text() != 1]">
            <li><a href="MainServlet?btAction=showAllLaptop&amp;brandId=<c:out value="${requestScope.brandId}"/>&amp;pageNumber=<x:out select="$laptops/laptops/previous"/>"><<</a></li>
        </x:if>
        <li><a class="active" href="#" style="border:none !important;"><x:out select="$laptops/laptops/pageNumber"/></a></li>
        <x:if select="$laptops/laptops/haveNext[text() = 'true']">
            <li><a href="MainServlet?btAction=showAllLaptop&amp;brandId=<c:out value="${requestScope.brandId}"/>&amp;pageNumber=<x:out select="$laptops/laptops/next"/>">>></a></li>
        </x:if>
    </ul>
</div>
<footer>
    <h4>Nguyen Gia Huy - SE63158</h4>
</footer>
</body>
</html>
