<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<%--<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>--%>
<html>
<head>
    <title>Meal list</title>
    <style>
        .normal {
            color: green;
        }

        .excess {
            color: red;
        }

        da {
            border: 2px solid black;
            width: 800px;
            height: 100px;
            background-color: antiquewhite;
            margin-left: 210px;
            display: block;
        }
        dtaa {
            display: inline-block;
            width: 100px;
            margin-left: 15px;
        }
        dtab {
            display: inline-block;
            width: 100px;
            margin-left: 50px;
        }
        dtba {
            display: inline-block;
            width: 100px;
            margin-left: 270px;
        }
        dtbb {
            display: inline-block;
            width: 100px;
            margin-left: 10px;
        }
        ddaa {
            display: inline-block;
            margin-left: 15px;
            vertical-align: top;
        }
        ddab {
            display: inline-block;
            margin-left: 10px;
            vertical-align: top;
        }
        ddba {
            display: inline-block;
            margin-left: 225px;
            vertical-align: top;
        }
        ddbb {
            display: inline-block;
            margin-left: 38px;
            vertical-align: top;
        }
        bda {
            color: red;
            margin-left: 300px;
        }
        bdb {
            color: blue;
            margin-left: 10px;
        }
        ta {
            margin-left: 600px;
            text-decoration-color: blue;
        }
    </style>
</head>
<body>
<section>
    <h3><a href="index.html">Home</a></h3>
    <hr/>
    <h2>Meals</h2>
    <a href="meals?action=create">Add Meal</a>
    <br><br>

    <form method="post" action="meals">
        <da>
            <div>
                <dtaa>От даты</dtaa>
                <dtab>До даты</dtab>
                <dtba>От времени</dtba>
                <dtbb>До времери</dtbb>

                <ddaa><input type="date" name="startDate" var="startTime" value="${startDate}"></ddaa>
                <ddab><input type="date" name="endDate" var="endTime" value="${endDate}"></ddab>
                <ddba><input type="time" name="startTime" value="${startTime}"></ddba>
                <ddbb><input type="time" name="endTime" value="${endTime}"></ddbb>
                <br><hr><br>
                <bda><button onclick="window.history.back()">Отменить</button></bda>
                <bdb><button type="submit">Отфильтровать</button></bdb>
                <br>
            </div>
        </da>
    </form>

    <table border="1" cellpadding="8" cellspacing="0" align="center" bgcolor="#f0ffff">
        <thead>
        <tr>
            <th>Дата</th>
            <th>Описание</th>
            <th>Калории</th>
            <th></th>
            <th></th>
        </tr>
        </thead>
        <c:forEach items="${mealsTo}" var="meal">
            <jsp:useBean id="meal" type="ru.javawebinar.topjava.to.MealTo"/>
            <tr class="${meal.excess ? 'excess' : 'normal'}">
                <td>
                        <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                        <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                        <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                        ${fn:formatDateTime(meal.dateTime)}
                </td>
                <td>${meal.description}</td>
                <td>${meal.calories}</td>
                <td><a href="meals?action=update&id=${meal.id}">Update</a></td>
                <td><a href="meals?action=delete&id=${meal.id}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</section>
</body>
</html>