<%--
  Created by IntelliJ IDEA.
  User: Nata
  Date: 06.06.2020
  Time: 9:06
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Form Meal</title>

    <style>
        dl {
            background: no-repeat scroll 0 0 #FAFAFA;
            margin: 8px 0;
            padding: 0;
        }
        dt {
            display: inline-block;
            width: 170px;
        }
        dd {
            display: inline-block;
            margin-left: 8px;
            vertical-align: top;
        }
    </style>
</head>
<body>
    <section>
        <h3><a href="">back to Meals</a> </h3>
        <hr>
        <h2>Meal</h2>

           <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>

            <form method="post" action="meals">
                <input type="hidden" name="id" value="${meal.id}">
                <dl>
                    <dt>Дата/Время </dt>
                    <dd><input type="datetime-local" name="date" value="${meal.dateTime}" size=20></dd>
                </dl>
                <dl>
                    <dt>Описание   </dt>
                    <dd><input type="text" name="description" value="${meal.description}" size=20></dd>
                </dl>
                <dl>
                    <dt>Калории    </dt>
                    <dd><input type="number" name="calories" value="${meal.calories}" size=20></dd>
                </dl>
                <dl>
                <button type="submit">Save</button>
                <button onclick="window.history.back()">Cancel</button>
                </dl>
            </form>

    </section>


</body>
</html>
