<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="Reinitialisation mot de passe" name="titre" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar-login.jsp"%>


<!-- ####### CONTENT ######## -->
<div class="container mt-4">
	<div class="row">
		<div class="col-md-6 mx-auto mt-4 bg-light rounded shadow bg-light p-3 text-center">
         <h5>Votre mot de passe a été modifié avec succès</h5>
          <div class="col-12 text-center">
			  	<a href="<%=request.getContextPath()%>/login" class="btn btn-success mt-3">Me connecter</a>
		  </div>
		</div>
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>

<script>
</script>