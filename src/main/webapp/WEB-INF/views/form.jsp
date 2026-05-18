<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>${mode == 'add' ? 'Добавить' : 'Редактировать'} событие</title>
    <style>
        form { width: 400px; margin: 50px auto; padding: 20px; border: 1px solid #ddd; border-radius: 5px; }
        label { display: block; margin: 10px 0 5px; }
        input, select, textarea { width: 100%; padding: 8px; margin-bottom: 10px; box-sizing: border-box; }
        button { width: 100%; padding: 10px; background-color: #4CAF50; color: white; border: none; cursor: pointer; }
        button:hover { background-color: #45a049; }
        .back { display: block; text-align: center; margin-top: 10px; }
    </style>
</head>
<body>
    <form action="/event-form" method="post">
        <h2>${mode == 'add' ? 'Новое событие' : 'Редактирование'}</h2>

        <c:if test="${mode == 'edit'}">
            <input type="hidden" name="id" value="${event.id}" />
        </c:if>

        <label>Тип события:</label>
        <select name="type" required>
            <option value="BIRTHDAY" ${event.type.name() == 'BIRTHDAY' ? 'selected' : ''}>День рождения</option>
            <option value="MEETING" ${event.type.name() == 'MEETING' ? 'selected' : ''}>Встреча</option>
        </select>

        <label>Дата:</label>
        <input type="date" name="date" value="${event.date}" required />

        <label>Описание:</label>
        <textarea name="description" required>${event.description}</textarea>

        <label id="extraLabel">Дополнительно:</label>
        <input type="text" name="extraData" value="${event.type.name() == 'BIRTHDAY' ? event.name : event.location}" required />

        <button type="submit">${mode == 'add' ? 'Добавить' : 'Сохранить'}</button>

        <a href="/events" class="back">← Назад к списку</a>
    </form>

    <script>
        document.querySelector('select[name="type"]').addEventListener('change', function(e) {
            const label = document.getElementById('extraLabel');
            label.textContent = e.target.value === 'BIRTHDAY' ? 'Имя именинника:' : 'Место встречи:';
        });
    </script>
</body>
</html>