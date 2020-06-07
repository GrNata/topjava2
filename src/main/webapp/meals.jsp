<%--
  Created by IntelliJ IDEA.
  User: Nata
  Date: 05.06.2020
  Time: 10:47
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="ru.javawebinar.topjava.util.TimeUtil" %>
<%@ page import="ru.javawebinar.topjava.util.MealsUtil" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt" %>

<html>
<head>
    <title>Meals</title>

    <style>
        .normal {color: green}
        .exceeded {color: red}
    </style>

</head>

<body>
<br>
    <h3><a href="index.html">Home</a> </h3>
    <hr>
    <h2>Meals</h2> </br></br>
    <a href="meals?action=new">Add Meal</a>
    <hr>

    <table>
        <thread>
            <tr align="center" style="font-width: bold">
                <td>Дата/Время</td>
                <td>Описание</td>
                <td>Калории</td>
                <td>Превышение</td>
                <td>Действия</td>
            </tr>
        </thread>

        <c:forEach items="${mealTo}" var="meal">
            <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.model.MealTo"/>
            <tr class="${meal.excess ? 'exceeded' : 'normal'}">
<%--            <tr style="background: ${meal.excess ? '#FC8B98' : '#8BFCAF'}">--%>
               <td><%=TimeUtil.toString(meal.getDateTime())%></td>
        <%--        <td>${localDateTimeFormat.parse(meal.dateTime)}</td>    --%>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td>${meal.excess}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a> </td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a> </td>
            </tr>
        </c:forEach>

    </table>

</body>
</html>
