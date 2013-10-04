<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
    <style type="text/css">
        #message{
            border: 3px solid blue;
            font: 12pt sans-serif;
            width: 75%;
            padding-top: 20px;
        }
    </style>
</head>
<body>
<h2>Guest book</h2>
<form action="" method="POST">
    Имя: <input name="name" type="text" size="20"/><br/>
    Сообщение: <textarea name="message" cols="20" rows="5"></textarea><br/>
    <input type="submit"/>
</form>
<hr/>
<c:forEach items="${requestScope.records}" var="rec">
    <div id="message">
        <b>№ сообщения: ${rec.id}</b><br/>
        <i>Пользователь: ${rec.user}&nbsp;&nbsp;&nbsp; Дата: ${rec.strDate}</i><br/><hr>
        Сообщение: ${rec.message}<br/>
    </div>
</c:forEach>

</body>
</html>