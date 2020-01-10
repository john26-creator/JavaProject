<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form action="/TestServlet/creationUtilisateur" method="post">
		<label for="name">Nom :</label> 
		<input type="text" id="name" name="name" size="30" maxlength="60" /> <br />
		
		<label for="emailUtilisateur">Adresse email:</label>
		<input type="email" id="emailUtilisateur" name="emailUtilisateur" size="30" maxlength="60" />
		<br />       
		
	    <label	for="passwordUtilisateur">Password (8 characters minimum):</label> 
		<input	type="password" id="pass" name="passwordUtilisateur" size="30" maxlength="60"	required />
		<br />
		
		<div class="button">
			<button type="submit">Connexion</button>
		</div>
	</form>
</body>
</html>