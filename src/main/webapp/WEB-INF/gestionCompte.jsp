<!-- ####### HEADER ######## -->
<%@include file="includes/header.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ####### NAVBAR ######## -->
<%@include file="includes/header.jsp"%>


<!-- ####### CONTENT ######## -->

<div class="container">
	<div class="row">
	<div class="col-md-3 py-5">
		<img src="images/${participant.noParticipant}.jpg" alt="${participant.nom}" class="img-thumbnail">
	</div>
		<div class="col-md-6">
			<h1 class="text-center">Mon Profil</h1>
			<form action="ServletGestionCompte" method="post" enctype="multipart/form-data">
				<div class="form-group">
					<label for="pseudo">Pseudo :</label> 
					<input type="text" class="form-control" id="pseudo" name="pseudo" placeholder="Ex : XxDaRkSaSuKeDu72xX" value="${participant.pseudo}"> 
				</div>
				<div class="form-group">
					<label for="prenom">Prénom :</label> 
					<input type="text" class="form-control" id="prenom" name="prenom" placeholder="Prénom" value="${participant.prenom}"> 
				</div>
				<div class="form-group">
					<label for="nom">Nom :</label> 
					<input type="text" class="form-control" id="nom" name="nom" placeholder="Nom" value="${participant.nom}"> 
				</div>
				<div class="form-group">
					<label for="telephone">Téléphone :</label> 
					<input type="text" class="form-control" id="telephone" name="telephone" placeholder="Numéro de téléphone" maxlength="10" value="${participant.telephone}"> 
				</div>
				<div class="form-group">
					<label for="mail">Mail :</label> 
					<input type="text" class="form-control" id="mail" name="mail" placeholder="Mail" value="${participant.mail}"> 
				</div>
				<div class="form-group">
					<label for="motDePasse">Mot de passe :</label> 
					<input type="password" class="form-control" id="motDePasse" name="motDePasse" placeholder="Mot de passe" > 
				</div>
				<div class="form-group">
					<label for="confirmationMotDePasse">Confirmation :</label> 
					<input type="password" class="form-control" id="confirmationMotDePasse" name="confirmationMotDePasse" placeholder="Confirmation du mot de passe"> 
				</div>
				<div class="form-group">
				    <label for="site">Site de rattachement :</label>
				    <select class="form-control" id="site" name="site">
				    	<c:forEach var="site" items="${listeSite}">
				    		<option value="${site.noSite}" <c:if test="${participant.site.noSite == site.noSite}"> selected </c:if>> ${site.nomSite}</option>
				    	</c:forEach>
				    </select>
				 </div>
				 <div class="form-group">
				    <label for="photo">Ma photo :</label>
					<input type="file" class="form-control-file" id="photo" name="photo">
			 	</div>
			 	<div>
			 		<a href="#" class="btn btn-danger float-left">Annuler</a>
					<button type="submit" class="btn btn-primary float-right">Enregistrer</button>
				</div>
			</form>
		</div>
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>