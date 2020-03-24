<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<form action="${loginUrl}" method="POST">
    <div class="form-group">
        <label for="username">Username</label>
        <input type="text" class="form-control" id="username" name="username" placeholder="username">
    </div>
    <button type="submit" class="btn btn-default">Give me a hint.</button>    
</form>

 <c:out value="${hint.passwordHint}"/>

<c:import url="/WEB-INF/jsp/common/footer.jsp" />