<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="Annuler une sortie" name="titre"/>
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->

<div class="container">
	<div class="row">
		<div class="col-md-6 mx-auto">
			<h1 class="text-center my-4">Annuler une sortie</h1>	
				<form method="POST">
				<div class="card shadow">
					<div class="card-body mx-auto">
						<p class="card-text">Nom de la sortie : ${sortie.getNom()}</p>
						<p class="card-text">Date de la sortie : <fmt:formatDate value="${ sortie.getDateDebut() }" pattern="dd/MM/yyyy HH'h'mm" /></p>
						<p class="card-text">Ville organisatrice : ${sortie.getLieu().getVille().getNomVille()}</p>
						<p class="card-text">Lieu : ${sortie.getLieu().getAdresse()} ${sortie.getLieu().getVille().getCodePostal()} ${sortie.getLieu().getVille().getNomVille()}</p>
						<div class="form-group">
		  					<label for="motifAnnulation">Motif : </label>
							<textarea class="form-control" id="motifAnnulation" name="motifAnnulation" rows="5" required></textarea>
						</div>
					</div>
				</div>
				<a href="${urlPrecedente}" class="btn btn-primary m-3">Retour</a>
				<input type="submit" class="btn btn-danger float-right m-3" value="Annuler la sortie">
			</form>
		</div>
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>