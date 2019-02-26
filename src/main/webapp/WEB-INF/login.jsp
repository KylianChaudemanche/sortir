<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="Login" name="titre" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->
<div class="container">
	<div class="row">
		<div class="col-md-4 mx-auto mt-4">
		
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
			  <button type="submit" class="btn btn-success mt-3">Connexion</button>
			</form>
			
		</div>
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>