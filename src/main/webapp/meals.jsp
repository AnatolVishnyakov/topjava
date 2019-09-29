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
    <jsp:useBean id="meals" scope="request"
                 type="java.util.List<ru.javawebinar.topjava.model.MealWithExceed>"/>
    <c:forEach var="meal" items="${meals}">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed"/>
        <tr class="${meal.exceed ? 'exceeded' : 'normal'}">
            <td>
                <%=DateTimeUtil.toString(meal.getDateTime())%>
            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td>
<%--                <a href="WEB-INF/jsp/editMeal.jsp">Редактировать</a>--%>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
