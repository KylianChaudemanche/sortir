<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="Login" name="titre" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar-login.jsp"%>


<!-- ####### CONTENT ######## -->
<c:if test="${ (!empty loginFailed) && loginFailed }">
	<div class="col-12 alert alert-danger text-center" role="alert">
		<b>Informations de connexion invalides</b>
	</div>
</c:if>
		
<div class="container mt-5">
	<div class="row">
		<div class="col-md-4 mx-auto mt-4 bg-light rounded shadow p-3">
			<form method="POST" action="">
			  <div class="form-group">
			    <label for="mail"><strong>Mail / Pseudo</strong></label>
			    <c:choose>
					<c:when test="${ empty mail }">  
						<input type="email" class="form-control" id="mail" name="mail" aria-describedby="emailHelp" placeholder="ex : nom.prenom2020@campus-eni.fr" required>
  					</c:when>
					<c:otherwise>  
						<input type="email" class="form-control" id="mail" name="mail" aria-describedby="emailHelp" placeholder="ex : nom.prenom2020@campus-eni.fr" value="${ mail }" required>
  					</c:otherwise>
				</c:choose>
			   </div>
			  <div class="form-group">
			    <label for="motDePasse"><strong>Mot de Passe</strong></label>
			    <input type="password" class="form-control" id="motDePasse" name="motDePasse" required>
			  	<a href="#" class="text-muted text-secondary small ml-1">Mot de passe oublié</a>
			  </div>
			  <div class="form-check">
			  	<c:choose>
					<c:when test="${ empty mail }">  
			    		<input type="checkbox" class="form-check-input" id="seSouvenir" name="seSouvenir" value="on">
  					</c:when>
					<c:otherwise>  
			    		<input type="checkbox" class="form-check-input" id="seSouvenir" name="seSouvenir" value="on" checked>
  					</c:otherwise>
				</c:choose>
			    <label class="form-check-label" for="seSouvenir">Se souvenir de moi</label>
			  </div>
			  <input type="hidden" id="media-width" name="media-width" value="">
			  <button type="submit" class="btn btn-success mt-3">Connexion</button>
			</form>
			
		</div>
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>

<script>
$( document ).ready(function() {
	var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
	$('#media-width').val(width);
	console.log(width);
});
</script>