<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="${sortie.getNom() }]" name="titre" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>



<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->
<div class="container">
	<div class="row">
		<div class="col-md-6">
			<h1 class="align-center"></h1>
			<p>Nom de la sortie : ${sortie.getNom()}</p>
			<p>Date et heure de la sortie : ${sortie.getDateDebut()}</p>
			<p>Date limite d'insctiption : ${sortie.getDateCloture()}</p>
			<p>Nombre de places : ${sortie.getNbInscriptionsMax()}</p>
			<p>Durée : ${sortie.getDuree()}</p>
			<p>Description et infos : ${sortie.getDescriptionInfos()}</p>
		</div>
		<div class="col-md-6">
			<p>Ville organisatrice : ${sortie.getLieux().getVille() }</p>
			<p>Lieu : ${sortie.getLieux().getNomLieu()}</p>
			<p>Adresse : ${sortie.getLieux().getAdresse()}</p>
			<p>Code postal : ${sortie.getLieux().getCodePostal()}</p>
			<p>Latitude : ${sortie.getLieux().getLatitude()}</p>
			<p>Longitude : ${sortie.getLieux().getLongitude()}</p>
		</div>
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>