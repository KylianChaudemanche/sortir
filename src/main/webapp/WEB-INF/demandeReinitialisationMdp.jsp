<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="Reinitialiser mon Mot de Passe" name="titre" />
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
		
<div class="container mt-5">
	<div class="row">

			<c:choose>
		        <c:when test = "${ empty noParticipant }">
				  <div class="col-md-4 mx-auto mt-4 bg-light rounded shadow bg-light p-3">
		          	<form method="POST" action="">
					  <div class="form-group">
					    	<label for="identifiant"><strong>Adresse email</strong></label>
							<input type="text" class="form-control" id="mail" name="mail" aria-describedby="mailHelp" placeholder="ex : nom.prenom2020@campus-eni.fr" required>
					   </div>
					   <div class="col-12 text-center">
					  		<button type="submit" class="btn btn-success mt-3">Réinitialiser mon mot de passe</button>
					   </div>
					</form>
				  </div>
		         </c:when>
		         
		         <c:otherwise>
				  <div class="col-md-6 mx-auto mt-4 bg-light rounded shadow bg-light p-3 text-center">
			         <h5>Un lien de réinitialisation vous a été envoyé par mail.</h5>
			          <div class="col-12 text-center">
						  	<a href="<%=request.getContextPath()%>/login" class="btn btn-success mt-3">OK</a>
					  </div>
					  <a href="<%=request.getContextPath()%>/reinitialisationMdp?id=${ noParticipant }&token=${ token.getToken() }">
							Lien réinitialisation
					  </a>
					</div>
		         </c:otherwise>
		     </c:choose>
		      
	</div>
</div>
<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>

<script>
</script>