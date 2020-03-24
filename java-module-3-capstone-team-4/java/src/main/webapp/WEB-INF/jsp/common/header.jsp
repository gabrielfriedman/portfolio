<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
   	<meta charset="UTF-8">
	<title>National Park Weather Information System</title>
			<c:url value="/css/site.css" var="cssURL" />
   			<link rel="stylesheet" type="text/css" href="${cssURL}">
</head>

<body>
	<header>
		<c:url value="/" var="homePageHref" />
		<c:url value="img/logo.png" var="logoImg"/>
		<a href="${homePageHref}">
			<img src="${logoImg}" id="logo" />
		</a>
		<h1>National Park Weather Information System</h1>
    </header>
    <nav>
        <ul>
            <li><a href="${homePageHref}">Home</a></li>
            <c:url value="/survey" var="survey" />       
            <li><a href="${survey}">Survey</a></li>
      		<c:url value="/favorites" var="favorites" />      
            <li><a href="${favorites}">Favorite Parks</a></li>  
            <c:url var="registerUrl" value="/register"/>
			<li><a href="${registerUrl}">Register</a></li>   
			
			<c:if test="${not empty appCurrentUser}">
				<c:url value="/userSettings" var="userSettings"/>
				<li><a href="${userSettings}"><c:out value="${appCurrentUser.username}" /></a></li>
				<c:url var="logoffUrl" value="/"/>
				<li>
					<form action="${logoffUrl}" method="POST" class="navbar-form">
						<button type="submit" class="btn btn-primary">Log Off</button>
					</form>
				</li>
			</c:if>
        </ul>
       
    </nav>
    
