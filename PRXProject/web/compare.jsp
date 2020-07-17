<%--
  Created by IntelliJ IDEA.
  User: giahuy
  Date: 7/15/20
  Time: 2:58 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/xml" prefix="x" %>
<html>
<head>
    <link href="main.css" rel="stylesheet" type="text/css">
    <script src="js/main.js"></script>
    <title>Smart Choice</title>
</head>
<body onload="initComparePage()">
<header>
    <h1>SMART CHOICE</h1>
</header>
<c:if test="${not empty BRAND}">
    <x:transform doc="${BRAND}" xslt="${NAV_XSLT}"/>
</c:if>
<c:if test="${empty BRAND}">
    Have some problems
</c:if>
<div class="content">
    <table border="1" class="compare" id="compare-table">
        <tr>
            <th class="weight" id="option-box">
                Select Weight :
                <select id="select-compare-type-box" name="compareWeight" class="select-box-weight" onchange="onChangeCompareType(this.value)">
                    <option value="all" >
                        BY ALL
                    </option>
                    <option value="power">
                        BY POWER
                    </option>
                    <option value="compactness">
                        BY COMPACTNESS
                    </option>
                </select>
            </th>
            <td class="laptop-compare-image">
                <img src="#"/>
            </td>
            <td class="laptop-compare-image">
                <img src="#"/>
            </td>
        </tr>
        <tr>
            <th><h2>Name</h2></th>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <th><h2>Mark</h2></th>
            <td><h2></h2></td>
            <td><h2></h2></td>
        </tr>
        <tr>
            <th>CPU</th>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <th>Core</th>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <th>Thread</th>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <th>Base Clock</th>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <th>Boost Clock</th>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <th>Cache</th>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <th>RAM</th>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <th>Monitor</th>
            <td></td>
            <td></td>
        </tr>
        <tr>
            <th>Weight</th>
            <td></td>
            <td></td>
        </tr>
    </table>
</div>
<footer>
    <h4>Nguyen Gia Huy - SE63158</h4>
</footer>
</body>
</html>
