<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Органайзер - События</title>
    <style>
        table { border-collapse: collapse; width: 80%; margin: 20px auto; }
        th, td { border: 1px solid #ddd; padding: 10px; text-align: left; }
        th { background-color: #4CAF50; color: white; }
        .btn { padding: 5px 10px; text-decoration: none; color: white; border-radius: 3px; }
        .btn-edit { background-color: #2196F3; }
        .btn-delete { background-color: #f44336; }
        .btn-add { background-color: #4CAF50; display: inline-block; margin: 20px auto; }
        .container { text-align: center; }
    </style>
</head>
<body>
    <div class="container">
        <h1>Мои события</h1>

        <form action="/events" method="get" style="margin: 20px;">
            <input type="date" name="date" />
            <button type="submit">Найти</button>
            <a href="/events"><button type="button">Показать все</button></a>
        </form>

        <a href="/event-form" class="btn btn-add">Добавить событие</a>

        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Тип</th>
                    <th>Дата</th>
                    <th>Описание</th>
                    <th>Детали</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:choose>
                    <c:when test="${empty events}">
                        <tr>
                            <td colspan="6" style="text-align: center;">Событий не найдено</td>
                        </tr>
                    </c:when>
                    <c:otherwise>
                        <c:forEach var="event" items="${events}">
                            <tr>
                                <td>${event.id}</td>
                                <td>${event.type.label}</td>
                                <td>${event.date}</td>
                                <td>${event.description}</td>
                                <td>
                                    <c:choose>
                                        <c:when test="${event.type.name() == 'BIRTHDAY'}">
                                            ${event.name}
                                        </c:when>
                                        <c:when test="${event.type.name() == 'MEETING'}">
                                            ${event.location}
                                        </c:when>
                                    </c:choose>
                                </td>
                                <td>
                                    <a href="/event-form?id=${event.id}" class="btn btn-edit">Edit</a>
                                    <form action="/delete-event" method="post" style="display: inline;">
                                        <input type="hidden" name="id" value="${event.id}" />
                                        <button type="submit" class="btn btn-delete"
                                                onclick="return confirm('Удалить событие?')">Delete</button>
                                    </form>
                                </td>
                            </tr>
                        </c:forEach>
                    </c:otherwise>
                </c:choose>
            </tbody>
        </table>
    </div>
</body>
</html>