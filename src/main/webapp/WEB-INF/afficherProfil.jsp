<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="Gestion de compte" name="titre"/>
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->

<div class="container">
	<div class="row">
		<div class="col-md-6 mx-auto">
			<h1 class="text-center">${participant.pseudo}</h1>	
			<div class="card mx-auto shadow">
				<object data="../image/${participant.noParticipant}" class="img-rounded" type="image/jpg">
		      		<img src="../image/batman" class="img-thumbnail">
		    	</object>
				<hr>
				<div class="card-body mx-auto">
					<p class="card-text"><i data-feather="user" class="mr-2"></i>${participant.prenom} ${participant.nom}</p>
					<p class="card-text"><i data-feather="phone" class="mr-2"></i>${participant.telephone}</p>
					<p class="card-text"><i data-feather="at-sign" class="mr-2"></i>${participant.mail}</p>
					<p class="card-text"><i data-feather="home" class="mr-2"></i>${participant.site.nomSite}</p>
				</div>
			</div>
			<a href="${urlPrecedente}" class="btn btn-primary m-3">Retour</a>
		</div>
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>