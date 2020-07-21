<%--
  Created by IntelliJ IDEA.
  User: giahuy
  Date: 7/13/20
  Time: 11:33 AM
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
    <h1>SMART CHOICE</h1>
</header>
<x:parse xml="${requestScope.ADVICE_LAPTOP}" var="adviceLaptops"/>
<x:parse xml="${requestScope.CURRENT_LAPTOP}" var="currentLaptop"/>
<c:if test="${not empty BRAND}">
    <x:transform doc="${BRAND}" xslt="${NAV_XSLT}"/>
</c:if>
<c:if test="${empty BRAND}">
    Have some problems
</c:if>
<div class="content">
    <div class="laptop-detail">
        <div class="laptop-detail-name">
            <x:out select="$currentLaptop/laptop/name"/>
        </div>
        <div class="laptop-detail-image">
            <img src="<x:out select="$currentLaptop/laptop/image"/>"/>
        </div>
        <div class="laptop-detail-info">
            <table>
                <tbody>
                <tr>
                    <th><h3>PRICE :</h3></th>
                    <td><h3><x:out select="$currentLaptop/laptop/price"/></h3></td>
                </tr>
                <tr>
                    <th>MODEL :</th>
                    <td><x:out select="$currentLaptop/laptop/model"/></td>
                </tr>
                <tr>
                    <x:set var="processor" select="$currentLaptop/laptop/processor"/>
                    <th>CPU :</th>
                    <td><x:out select="$processor/name"/> (Core : <x:out select="$processor/core"/> | Thread : <x:out
                            select="$processor/thread"/> | Clock : <x:out select="$processor/baseClock"/> - <x:out
                            select="$processor/boostClock"/> | Cache : <x:out select="$processor/cache"/>)
                    </td>
                </tr>
                <tr>
                    <th>RAM :</th>
                    <td><x:out select="$currentLaptop/laptop/ram/memory"/> GB</td>
                </tr>
                <tr>
                    <th>LCD :</th>
                    <td><x:out select="$currentLaptop/laptop/monitor/size"/> Inch</td>
                </tr>
                <tr>
                    <th>Weight :</th>
                    <td><x:out select="$currentLaptop/laptop/weight"/> Kg</td>
                </tr>
                </tbody>
            </table>
        </div>
    </div>
    <hr/>
    <div class="some-laptops">
        <h2>Maybe you like</h2>
        <div class="items">
            <x:forEach select="$adviceLaptops/laptops/laptop">
                <div class="item">
                    <a href="MainServlet?btAction=showDetailLaptop&laptopId=<x:out select="id"/>">
                        <img src="<x:out select="image"/>"/>
                    </a>
                    <div class="item-info">
                        <a href="MainServlet?btAction=showDetailLaptop&laptopId=<x:out select="id"/>">
                            <h3><x:out select="name"/></h3>
                        </a>
                        <h4><x:out select="price"/> VND</h4>
                        <p>CPU : <x:out select="processor/name"/> | RAM : <x:out select="ram/memory"/> GB | Display :
                            <x:out select="monitor/size"/> Inch</p>
<%--                        <a href="MainServlet?btAction=compareLaptop&amp;id1=<x:out select="id"/>&amp;id2=<x:out select="$currentLaptop/laptop/id"/>"><h4>Compare</h4></a>--%>
                        <a href="compare.jsp?id1=<x:out select="id"/>&amp;id2=<x:out select="$currentLaptop/laptop/id"/>"><h4>Compare</h4></a>
                    </div>
                </div>
            </x:forEach>
        </div>
    </div>
</div>
<footer>
    <h4>Nguyen Gia Huy - SE63158</h4>
</footer>
</body>
</html>
