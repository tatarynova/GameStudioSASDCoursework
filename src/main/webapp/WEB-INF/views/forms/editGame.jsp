<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Form</title>
</head>
<body>
<form:form action="/editedGame?studioid=${studio.id}&gameid=${game.id}" modelAttribute="game">
    Title:
    <form:input path="title" value="${game.title}"/>
    <form:errors path="title"/><br/>

    Description:
    <form:input path="description" value="${game.description}"/>
    <form:errors path="description"/><br/>

    Price:
    <form:input path="price" value="${game.price}"/>
    <form:errors path="price"/><br/>

    Difficulty level:
    <form:input path="difficultyLevel" value="${game.difficultyLevel}"/>
    <form:errors path="difficultyLevel"/><br/>


    <p>Select Genres:</p>
    <c:forEach var="genre" items="${genres}">
        <label>
            <input type="checkbox" name="genreIds" value="${genre.id}" <c:if test="${game.genres != null && game.genres.contains(genre)}">checked</c:if>/>
                ${genre.name}
        </label><br/>
    </c:forEach>

    <input type="submit"/><form:errors/>
</form:form>
</body>
</html>