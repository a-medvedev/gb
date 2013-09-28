<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<h2>Test.jsp</h2>
<form action="" method="POST">
    Имя: <input name="name" type="text" size="20"/>
    Сообщение: <textarea name="message" cols="20" rows="5"></textarea>
    <input type="submit"/>
</form>
${text1}

<c:forEach items="records" var="rec">
    ${rec}<br>
</c:forEach>

</body>
</html>