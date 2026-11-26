<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Independent Game Studios</title>
</head>
<body>
<h1>Independent Game Studios</h1>
<a href="/">Return to Homepage</a><br/>

<div style="border:1px solid #ccc; margin:10px; padding:10px;">
    <form action="/search1">
        <label>Filter by studio:</label>
        <input type="text" name="studio"/>
        <input type="submit"/>
    </form>
    <form action="/search2">
        <label>Filter by game:</label>
        <input type="text" name="game"/>
        <input type="submit"/>
    </form>
</div>

<c:forEach var="studio" items="${studios}">
<div style="border:1px solid #ccc; margin:10px; padding:10px;">
    <h2>${studio.name} (${studio.specialism})</h2>
    <p><b>Headquarters:</b> ${studio.headquarters}</p>
    <p><b>Bio:</b> ${studio.profile.bio}</p>
    <p><b>Social:</b> ${studio.profile.socialMediaHandle}</p>
    <p><b>Website:</b> ${studio.profile.website}</p>

    <h3>Games</h3>
    <ul>
        <c:forEach var="game" items="${studio.games}">
            <li>
                <b>${game.title}</b> - ${game.description} (£${game.price})
                <p>Difficulty level: ${game.difficultyLevel}/5</p>
                <c:forEach var="genre" items="${game.genres}">
                    <p>#${genre.name}</p>
                </c:forEach>
            </li>
        </c:forEach>
    </ul>
    <h3>Reviews</h3>
    <ul>
        <c:forEach var="game" items="${studio.games}">
            <c:forEach var="review" items="${game.reviews}">
                <li>
                    <b>${review.reviewerName}</b> rated <b>${game.title}</b>: ${review.rating}/5<br/>
                    "${review.comment}"
                </li>
            </c:forEach>
        </c:forEach>
    </ul>
    <h3>Screenshot gallery</h3>
    <ul>
        <c:forEach var="screenshot" items="${studio.screenshots}">
            <img src="${screenshot.imageUrl}" height="150" alt="Alt text: ${screenshot.description} (screenshot not displaying)"/>
        </c:forEach>
    </ul>
    <h3>Awards</h3>
    <ul>
        <c:forEach var="award" items="${studio.awards}">
            <li>${award.title} (${award.year})</li>
        </c:forEach>
    </ul>
</div>
</c:forEach>
</body>
</html>
