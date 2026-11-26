<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Form</title>
</head>
<body>
<form:form action="/editedScreenshot?screenshotId=${screenshot.id}" modelAttribute="screenshot">
    URL:
    <form:input path="imageUrl" value="${screenshot.imageUrl}"/>
    <form:errors path="imageUrl"/><br/>

    Description:
    <form:input path="description" value="${screenshot.description}"/>
    <form:errors path="description"/><br/>

    <input type="submit"/><form:errors/>
</form:form>
</body>
</html>