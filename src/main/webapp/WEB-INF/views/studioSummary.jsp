<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<html>
<head>
    <title>Studio Summary</title>
</head>
<body>
<div style="border:1px solid #ccc; margin:10px; padding:10px;">
    <p><a href ="editStudio?id=${studio.id}">Edit studio details</a></p>
    <p><a href ="editStudioProfile?id=${studio.id}">Add/Edit studio profile details</a></p>
    <p><a href ="newGame?id=${studio.id}">Add a game with genres</a></p>
    <p><a href ="newScreenshot?studioid=${studio.id}">Add a screenshot</a></p>
    <p><a href ="newAward?studioid=${studio.id}">Add an award</a></p>
    <p>More edit and delete options are below</p>
    <p><a href="/admin">Back to studio overview</a></p>
</div>
<h1>${studio.name} (${studio.specialism})</h1>
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

                <a href ="newReview?studioid=${studio.id}&gameid=${game.id}">Add a review for game</a>
                <a href ="editGame?studioid=${studio.id}&gameid=${game.id}">Edit game</a>
                <a href ="deleteGame?studioid=${studio.id}&gameid=${game.id}">Delete game</a>
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
                <a href ="editReview?studioId=${studio.id}&reviewId=${review.id}">Edit review</a>
                <a href ="deleteReview?studioId=${studio.id}&reviewId=${review.id}">Delete review</a>
            </c:forEach>
        </c:forEach>
    </ul>
    <h3>Screenshot gallery</h3>
    <ul>
        <c:forEach var="screenshot" items="${studio.screenshots}">
            <img src="${screenshot.imageUrl}" height="150" alt="Alt text: ${screenshot.description} (screenshot not displaying)"/>
            <a href ="editScreenshot?screenshotId=${screenshot.id}">Edit screenshot</a>
            <a href ="deleteScreenshot?screenshotId=${screenshot.id}">Delete screenshot</a>
        </c:forEach>
    </ul>
    <h3>Awards</h3>
    <ul>
        <c:forEach var="award" items="${studio.awards}">
            <li>${award.title} (${award.year})</li>
            <a href ="editAward?awardId=${award.id}">Edit award</a>
            <a href ="deleteAward?awardId=${award.id}">Delete award</a>
        </c:forEach>
    </ul>
</body>
</html>