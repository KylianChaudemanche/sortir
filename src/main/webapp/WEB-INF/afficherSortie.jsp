<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="${sortie.getNom()}" name="titre" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<h1 class="mx-auto text-center">Afficher une sortie</h1>
			<table class="table col-md-6 float-left bg-light">
				<tr>
					<td>Nom de la sortie : </td>
					<td>${sortie.getNom()}</td>
				</tr>
				<tr>
					<td>Date et heure de la sortie : </td>
					<td>${sortie.getDateDebut()}</td>
				</tr>
				<tr>
					<td>Date limite d'insctiption : </td>
					<td>${sortie.getDateCloture()}</td>
				</tr>
				<tr>
					<td>Nombre de places : </td>
					<td>${sortie.getNbInscriptionsMax()}</td>
				</tr>
				<tr>
					<td>Durée : </td>
					<td>${sortie.getDuree()} minutes</td>
				</tr>
				<tr>
					<td>Description et infos : </td>
					<td>${sortie.getDescriptionInfos()}</td>
				</tr>
			</table>
			<table class="table col-md-6 float-right bg-light">
				<tr>
					<td>Ville organisatrice : </td>
					<td>${sortie.getLieu().getVille().getNomVille() }</td>
				</tr>
				<tr>
					<td>Lieu : </td>
					<td>${sortie.getLieu().getNomLieu()}</td>
				</tr>
				<tr>
					<td>Adresse : </td>
					<td>${sortie.getLieu().getAdresse()}</td>
				</tr>
				<tr>
					<td>Code postal : </td>
					<td>${sortie.getLieu().getVille().getCodePostal()}</td>
				</tr>
				<tr>
					<td>Latitude : </td>
					<td>${sortie.getLieu().getLatitude()}</td>
				</tr>
				<tr>
					<td>Longitude : </td>
					<td>${sortie.getLieu().getLongitude()}</td>
				</tr>
			</table>
		</div>
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>