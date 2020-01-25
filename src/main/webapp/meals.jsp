<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>

<section>
    <h3><spring:message code="meal.title"/></h3>

    <form method="post" action="meals/filter">
        <h3><spring:message code="meal.title"/></h3>

        <form method="post" action="meals/filter">
            <dl>
                <dt>From Date:</dt>
                <dt><spring:message code="meal.startDate"/>:</dt>
                <dd><input type="date" name="startDate" value="${param.startDate}"></dd>
            </dl>
            <dl>
                <dt>To Date:</dt>
                <dt><spring:message code="meal.endDate"/>:</dt>
                <dd><input type="date" name="endDate" value="${param.endDate}"></dd>
            </dl>
            <dl>
                <dt>From Time:</dt>
                <dt><spring:message code="meal.startTime"/>:</dt>
                <dd><input type="time" name="startTime" value="${param.startTime}"></dd>
            </dl>
            <dl>
                <dt>To Time:</dt>
                <dt><spring:message code="meal.endTime"/>:</dt>
                <dd><input type="time" name="endTime" value="${param.endTime}"></dd>
            </dl>
            <button type="submit">Filter</button>
            <button type="submit"><spring:message code="meal.filter"/></button>
        </form>
        <hr/>
        <a href="meals?action=create">Add Meal</a>
        <hr/>
        <hr>
        <a href="meals/create"><spring:message code="meal.add"/></a>
        <hr>
        <table border="1" cellpadding="8" cellspacing="0">
            <thead>
            <tr>
                <th>Date</th>
                <th>Description</th>
                <th>Calories</th>
                <th><spring:message code="meal.dateTime"/></th>
                <th><spring:message code="meal.description"/></th>
                <th><spring:message code="meal.calories"/></th>
                <th></th>
                <th></th>
            </tr>

            </td>
            <td>${meal.description}</td>
            <td>${meal.calories}</td>
            <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
            <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            <td><a href="meals/update?id=${meal.id}"><spring:message code="common.update"/></a></td>
            <td><a href="meals/delete?id=${meal.id}"><spring:message code="common.delete"/></a></td>
            </tr>
            </c:forEach>
        </table>
</section>
<jsp:include page="fragments/footer.jsp"/>
</body>
</html>
