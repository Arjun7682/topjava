<%@ page import="ru.javawebinar.topjava.util.DateUtil" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Edit meal</title>
</head>
<body>
<section>
    <form method="post" action="meals">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.Meal" scope="request"/>
        <h2>Редактирование</h2>
        <input type="hidden" name="id" value="${meal.id}">
        <table border="0" cellpadding="8" cellspacing="0">
            <tr>
                <th>Дата:</th>
                <th><input type="datetime-local" name="date" size=30 value="${DateUtil.format(meal.dateTime)}"></th>
            </tr>
            <tr>
                <th>Описание:</th>
                <th><input type="text" name="description" size=30 value="${meal.description}"></th>
            </tr>
            <tr>
                <th>Калории:</th>
                <th><input type="text" name="calories" size=30 value="${meal.calories}"></th>
            </tr>
        </table>
        <br>
        <button type="submit">Сохранить</button>
        <button onclick="window.history.back()">Отменить</button>
    </form>
</section>
</body>
</html>
