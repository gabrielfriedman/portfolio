<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:import url="/WEB-INF/jsp/common/header.jsp" />

<c:url var="userSettings" value="/userSettings"/>
<form action="${userSettings}" method="POST">
    <div class="form-group">
        <label for="existingPassword">Current Password</label>
        <input type="password" class="form-control" id="existingPassword" name="existingPassword">
    </div>
    <div class="form-group">
        <label for="newPassword">New Password</label>
        <input type="password" class="form-control" id="newPassword" name="newPassword">
    </div>
    <button type="submit" class="btn btn-default">Update Password</button>    
</form>


<c:import url="/WEB-INF/jsp/common/footer.jsp" />