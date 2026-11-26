<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Admin - Manage Studios</title>
</head>
<body>
    <h1>Studio Administration</h1>
    <a href="/">Return to Homepage</a><br/><br/>
    <a href="/newStudio">Add New Studio</a>
    <ul>
        <c:forEach var="studio" items="${studios}">
            <li>
                ${studio.name}
                <a href ="studio?id=${studio.id}">View studio details</a>
                <a href ="deleteStudio?id=${studio.id}">Delete this studio</a>
            </li>
        </c:forEach>
    </ul>
</body>
</html>
