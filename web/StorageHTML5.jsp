<%--
  Created by IntelliJ IDEA.
  User: giahuy
  Date: 7/5/20
  Time: 2:06 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <script type="text/javascript">
        function addRow(tableId, cells) {
            var tableElem = document.getElementById(tableId);
            var newRow = tableElem.insertRow(tableElem.rows.length);
            var newCell;
            for (var i = 0; i < cells.length; i++) {
                newCell = newRow.insertCell(newRow.cells.length);
                newCell.innerHTML = cells[i] +'<br/>' +
                    '<input type="button" value="Add To Cart" onclick="addToCart(\'' +
                    cells[i]+'\')">'

                newCell.width = 200;
            }
            return newRow;
        }

        function addToCart(selectedItem) {
            if (typeof(sessionStorage) !== "undefined"){
                if (sessionStorage.cart == null){
                    sessionStorage.cart = '';
                }
                sessionStorage.cart += selectedItem+";";
            }else {
                alert("brower is not supported storage!!!")
            }
        }
    </script>
</head>
<body onload="addRow('tbItem',['Footbal','Shoes','Shirt','Clothes']);">
<h1>HTML 5 with Session Storage Demo</h1>
<table id="tbItem" border="1">

</table>
<input type="button" value="View Cart" onclick="window.open('StorageDemoShow.html',null,null)"/>
</body>
</html>
