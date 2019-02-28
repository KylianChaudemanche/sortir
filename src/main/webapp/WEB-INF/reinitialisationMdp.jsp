<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="Reinitialisation mot de passe" name="titre" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar-login.jsp"%>


<!-- ####### CONTENT ######## -->
<c:if test="${ !empty message }">
	<div class="col-12 alert alert-${ typeMessage } text-center" role="alert">
		<b>${ message }</b>
	</div>
</c:if>
<h2 class="text-center font-weight-bold mt-4">Choisissez votre nouveau mot de passe</h2>		
<div class="container mt-4">
	<div class="row">
		<div class="col-md-6 mx-auto mt-4 bg-light rounded shadow p-3">
			<form method="POST" action="">
			  <div class="form-group">
			    <label for="motDePasse"><strong>Nouveau mot de passe</strong></label>
			    <input type="password" class="form-control" id="motDePasse" name="motDePasse" required>
			    <small class="text-muted">Entre 8 et 50 caractères composés d'au moins une minuscule, une majuscule et d'un chiffre. </small>
			  </div>
			   <div class="form-group">
			    <label for="motDePasse"><strong>Confirmer mot de passe</strong></label>
			    <input type="password" class="form-control" id="confirmerMotDePasse" name="confirmerMotDePasse" required>
			    <small class="text-muted">Entre 8 et 50 caractères composés d'au moins une minuscule, une majuscule et d'un chiffre. </small>
			  </div>
			  <input type="hidden" name="noParticipant" value="${ participant.getNoParticipant() }">
			  <input type="hidden" name="token" value="${ token }">
			  <button type="submit" class="btn btn-success mt-3">Valider</button>
			</form>
		</div>
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>

<script>
</script>