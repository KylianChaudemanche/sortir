<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="Accueil" name="titre" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->

<div class="container">
	<div class="row">
		<div class="col-md-6">
			<form>
			  <div class="form-group row">
			    <label for="site" class="col-sm-2 col-form-label col-form-label-sm">Site</label>
			    <div class="col-sm-10">
					<select name="site" id="site" class="form-control form-control-sm">
					  <option>Rennes</option>
					  <option>Nantes</option>
					</select>
			    </div>
			  </div>
			  <div class="form-group row">
			    <label for="recherche" class="col-sm-2 col-form-label col-form-label-sm">Le nom du site contient: </label>
			    <div class="col-sm-10">
					<input type="text" class="form-control form-control-lg" id="recherche" name="recherche" placeholder="rechercher...">
			    </div>
			  </div>
			   <div class="form-group row">
				   <div class="col-md-6">
					   <label for="dateDebut" class="col-sm-2 col-form-label col-form-label-sm">Entre le: </label>
					    <div class="col-sm-10">
							<input type="date" class="form-control form-control-lg" id="dateDebut" name="dateDebut">
					    </div>
				   </div>
				  <div class="col-md-6">
					   <label for="dateFin" class="col-sm-2 col-form-label col-form-label-sm">Et le: </label>
					    <div class="col-sm-10">
							<input type="date" class="form-control form-control-lg" id="dateFin" name="dateFin">
					    </div>
				   </div>
			  </div>
			</form>
		</div>
		
		<div class="col-md-6">
			

		</div>
		
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>