<%@ page import="ru.javawebinar.topjava.util.DateTimeUtil" %><%--
  Created by IntelliJ IDEA.
  User: Анатолий
  Date: 21.09.2019
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib uri="http://topjava.javawebinar.ru/functions" prefix="function" %>

<html>
<head>
    <title>User meals</title>
    <style type="text/css">
        .exceeded {
            color: red;
        }

        .normal {
            color: green;
        }
    </style>
</head>
<body>
<h3>
    <a href="index.html">Home</a>
</h3>

<h2>Meal List</h2>

<table border="1" cellpadding="8" cellspacing="0">
    <thead>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
        <th>Операции</th>
    </tr>
    </thead>

    <tbody>
    <c:forEach items="${meals}" var="meal">
        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
            <td>
<%--                <%=DateTimeUtil.toString(meal.getDateTime())%>--%>
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
                <a href="meals?action=update&id=${meal.id}">Редактировать</a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
