<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<form action="/TestServlet/index" method="post">
		<label for="emailUtilisateur">Adresse email</label> <input type="email" id="emailUtilisateur" 
		        name="emailUtilisateur" value="<c:out value="${cookie.mail.value}"/>" size="30" maxlength="60" />
		        
		<span class="erreur">${form.erreurs['emailUtilisateur']}</span> <br />
	    <label	for="passwordUtilisateur">Password (8 characters minimum):</label> 
		<input	type="password" id="pass" name="passwordUtilisateur" size="30" maxlength="60"	required />
		<span class="erreur">${form.erreurs['passwordUtilisateur']}</span> <br />
		
		<div class="button">
			<button type="submit">Connexion</button>
		</div>
	</form>
</body>
</html>

