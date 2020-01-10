<%@ page import="java.util.List, com.test.model.*"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" type="text/css" href="table.css">
<title>Insert title here</title>
</head>
<body
	onload="document.getElementById('VoitureFields').style.visibility = 'visible';document.getElementById('MotoFields').style.visibility = 'hidden';">

	<div>
		<strong> ${requestScope.message}</strong>
	</div>

	<form action="/TestServlet/toto" method="post">
		<fieldset>
			<legend>Type de V&eacute;hicule</legend>
			<span> <input type="radio" id="voiture" name="typeVehicule"
				value="Voiture"
				onclick="document.getElementById('VoitureFields').style.visibility = 'visible';document.getElementById('MotoFields').style.visibility = 'hidden';"
				checked> <label for="voiture">Voiture</label>
			</span> <span> <input type="radio" id="moto" name="typeVehicule"
				value="Moto"
				onclick="document.getElementById('VoitureFields').style.visibility = 'hidden';document.getElementById('MotoFields').style.visibility = 'visible';">
				<label for="moto">Moto</label>
			</span>
		</fieldset>
		<br />
		<fieldset id="common">
			<legend>Attributs V&eacute;hicule</legend>
			<div>
				<label for="immatriculation">immatriculation :</label> <input
					type="text" id="immatriculation" name="immatriculation">
			</div>
			<div>
				<label for="marque">marque :</label> <input type="text" id="marque"
					name="marque">
			</div>
			<div>
				<label for="modele">modele :</label> <input type="text" id="modele"
					name="modele">
			</div>
		</fieldset>
		<br />
		<fieldset id="MotoFields">
			<legend>Attribut Moto</legend>
			<div>
				<label for="puissance">puissance :</label> <input type="text"
					id="puissance" name="puissance">
			</div>

		</fieldset>
		<br />
		<fieldset id="VoitureFields">
			<legend>Attributs Voiture</legend>
			<div>
				<label for="couleur">couleur :</label> <input type="text"
					id="couleur" name="couleur">
			</div>

			<div>
				<label for="annee">annee :</label> <input type="text" id="annee"
					name="annee">
			</div>
		</fieldset>
		<div class="button">
			<button type="submit">Ajouter v&eacute;hicule</button>
		</div>
	</form>
	<br />
	<br />
	<br />

	<table>
		<thead>
			<tr>
				<th>Immatriculation</th>
				<th>Marque</th>
				<th>Modèle</th>
				<th>Nombre de roues</th>
				<th>Couleur</th>
				<th>Puissance</th>
				<th>Année</th>
				<th>Supprimer</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${requestScope.vehicules}" var="vehicule">
				<tr>
					<td><c:out value="${vehicule.value.immatriculation}" /></td>
					<td><c:out value="${vehicule.value.modele}" /></td>
					<td><c:out value="${vehicule.value.nbreRoues}" /></td>
					<td><c:out value="${vehicule.value.marque}" /></td>
					<c:choose>
						<c:when test="${vehicule.value.typeVehicule == 'Voiture'}">
							<td><c:out value="${vehicule.value.couleur}" /></td>
							<td></td>
							<td><c:out value="${vehicule.value.annee}" /></td>
						</c:when>
						<c:when test="${vehicule.value.typeVehicule =='Moto'}">
							<td></td>
							<td><c:out value="${vehicule.value.puissance}" /></td>
							<td></td>
						</c:when>
					</c:choose>
					<td><a
						href="/TestServlet/toto/remove?immat=${vehicule.value.immatriculation}">Supprimer</a></td>
				<tr>
			</c:forEach>
		</tbody>
	</table>
	<form action="/TestServlet/disconnect" method="post">
		<div class="button">
			<button type="submit">Deconnexion</button>
		</div>
	</form>
</body>
</html>