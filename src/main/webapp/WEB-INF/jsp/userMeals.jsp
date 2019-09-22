<%--
  Created by IntelliJ IDEA.
  User: Анатолий
  Date: 21.09.2019
  Time: 18:45
  To change this template use File | Settings | File Templates.
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
<h3><a href="../../index.html">Home</a></h3>
<table>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    <jsp:useBean id="mealWithExceeds" scope="request"
                 type="java.util.List<ru.javawebinar.topjava.model.MealWithExceed>"/>
    <c:forEach var="meal" items="${mealWithExceeds}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
            <td>
                    ${function:format(meal.dateTime)}
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
