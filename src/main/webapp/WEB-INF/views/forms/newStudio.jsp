<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Form</title>
</head>
<body>
<form:form action="/addStudio" modelAttribute="studio">
    Name:
    <form:input path="name"/>
    <form:errors path="name"/><br/>

    Headquarters:
    <form:input path="headquarters"/>
    <form:errors path="headquarters"/><br/>

    Specialism:
    <form:input path="specialism"/>
    <form:errors path="specialism"/><br/>

    <input type="submit"/><form:errors/>
</form:form>
</body>
</html>