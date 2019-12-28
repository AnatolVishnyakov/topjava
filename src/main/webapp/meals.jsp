<%--
  Date: 21.09.2019
--%>
<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>

<html>
<head>
    <title>Calories management</title>
    <style type="text/css">
        .excess {
            color: red;
        }

        .normal {
            color: green;
        }
    </style>
</head>
<body>
<section>
    <h2>
        <a href="index.jsp">Home</a>
    </h2>

    <h3>Meals</h3>

    <form id="filter" method="get">
        <div class="row">
            <input type="hidden" name="filter">
            <div>
                <label for="startDate">От даты</label>
                <input type="date" name="startDate" id="startDate">
            </div>
            <div>
                <label for="endDate">До даты</label>
                <input type="date" name="endDate" id="endDate">
            </div>
            <div>
                <label for="startTime">От времени</label>
                <input type="time" name="startTime" id="startTime">
            </div>
            <div>
                <label for="endTime">До времени</label>
                <input type="time" name="endTime" id="endTime">
            </div>
        </div>

        <div>
            <button type="button" onclick="window.history.back()">Отменить</button>
            <button type="submit">Отфильтровать</button>
        </div>
    </form>

    <a href="meals?action=create">Add Meal</a>
    <hr>

    <table border="1" cellpadding="8" cellspacing="0">
        <thead>
        <tr>
            <th>Дата/Время</th>
            <th>Описание</th>
            <th>Калории</th>
            <th></th>
            <th></th>
        </tr>
        </thead>

        <tbody>
        <jsp:useBean id="meals" scope="request"
                     type="java.util.List<ru.javawebinar.topjava.to.MealTo>"/>
        <c:forEach var="meal" items="${meals}">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--                    <%=DateTimeUtil.toString(meal.getDateTime())%>--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>
                    <a href="meals?action=update&id=${meal.id}">Update</a>
                </td>
                <td>
                    <a href="meals?action=delete&id=${meal.id}">Delete</a>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</section>
</body>
</html>
