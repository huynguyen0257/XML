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
<html lang="en">
<head>
    <link href="main.css" rel="stylesheet" type="text/css">
    <script src="js/index.js"></script>
    <title>Smart Choice</title>
</head>
<body>
<header>
    <h1>SMART CHOICE</h1>
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
    <div class="suggest-container">
        <h2>Search by your wishes</h2>
        <form>
            <div class="suggest-box">
                <table>
                    <tr>
                        <th>CPU</th>
                        <td>
                            <select name="cpu">
                                <option value="" selected="true">No selected</option>
                                <c:forEach var="processor" items="${PROCESSOR}">
                                    <option value="<c:out value="${processor}"/>"><c:out value="${processor}"/></option>
                                </c:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>RAM</th>
                        <td>
                            <select name="ram">
                                <option value="" selected="true">No selected</option>
                                <x:forEach select="$ram/rams/ram">
                                    <option value="<x:out select="memory"/>">
                                        <x:out select="memory"/> GB
                                    </option>
                                </x:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Monitor's size</th>
                        <td>
                            <select name="monitorSize">
                                <option value="" selected="true">No selected</option>
                                <x:forEach select="$monitor/monitors/monitor">
                                    <option value="<x:out select="size"/>">
                                        <x:out select="size"/> Inch
                                    </option>
                                </x:forEach>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>Price</th>
                        <td>
                            <select name="price">
                                <option value="" selected="true">No selected</option>
                                <option value="0-10">~ 10 milion VND</option>
                                <option value="10-20">10 -> 20 milion VND</option>
                                <option value="20-0">20 ~ milion VND</option>
                            </select>
                        </td>
                    </tr>
                </table>
                <input type="button" onclick="getAdvice(this.form)" value="Get Advice"/>
            </div>
        </form>
    </div>
<%--    <c:if test="${not empty INFO}">--%>
<%--        <x:transform doc="${INFO}" xslt="${ADVICE_LAPTOP_LIST_XSLT}"/>--%>
<%--    </c:if>--%>
<%--    <c:if test="${empty INFO}">--%>
<%--        Have some problems--%>
<%--    </c:if>--%>
    <div class="some-laptops">
        <h2>Some advices</h2>
        <div class="items" id="items">
        </div>
    </div>
</div>

<footer>
    <h4>Nguyen Gia Huy - SE63158</h4>
</footer>
</body>
</html>

