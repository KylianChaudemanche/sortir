<!-- ####### HEADER ######## -->
<%@include file="includes/header.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->

<div class="container">
	<div class="row">
		<div class="col-md-3 py-5">
			<object data="<%=request.getContextPath()%>/image/${participant.noParticipant}" style="maxwidth:200px" class="rounded" type="image/jpg">
	      		<img src="<%=request.getContextPath()%>/image/batman" style="maxwidth:200px" class="img-thumbnail">
	    	</object>
		</div>
		<div class="col-md-6">
			<c:choose>
			<c:when test="${empty participant.getPseudo()}"><h1 class="text-center">Nouveau</h1></c:when>
			<c:otherwise><h1 class="text-center">${participant.getPseudo()}</h1></c:otherwise>
			</c:choose>
			
			<form action="" method="POST"	enctype="multipart/form-data">
			<c:choose>
			<c:when test="${empty participant.getPseudo()}"><input type="text" value="nouveau" id="action" name="action" hidden="true"></c:when>
			<c:otherwise><input type="text" value="${participant.getNoParticipant()}" id="action" name="action" hidden="true" ></c:otherwise>
			</c:choose>
				<div class="form-group">
					<label for="pseudo">Pseudo :</label> 
					<input type="text" class="form-control" id="pseudo" name="pseudo" required placeholder="Ex : XxDaRkSaSuKeDu72xX"  value="${participant.getPseudo()}"> 
				</div>
				<div class="form-group">
					<label for="prenom">Prénom :</label> 
					<input type="text" class="form-control" id="prenom" name="prenom" required placeholder="Prénom" value="${participant.getPrenom()}"> 
				</div>
				<div class="form-group">
					<label for="nom">Nom :</label> 
					<input type="text" class="form-control" id="nom" name="nom" required placeholder="Nom" value="${participant.getNom()}"> 
				</div>
				<div class="form-group">
					<label for="telephone">Téléphone :</label> 
					<input type="text" class="form-control" id="telephone" name="telephone"  required placeholder="Numéro de téléphone" maxlength="10" value="${participant.getTelephone()}"> 
				</div>
				<div class="form-group">
					<label for="mail">Mail :</label> 
					<input type="text" class="form-control" id="mail" name="mail" required placeholder="Mail" value="${participant.getMail()}"> 
				</div>
				
				<div class="form-group">
					<label for="motDePasse">Mot de passe :</label> 
					<input type="password" class="form-control" id="motDePasse" name="motDePasse" <c:if test="${empty participant.getPseudo()}">required</c:if> placeholder="Mot de passe" > 
				</div>
				<div class="form-group">
					<label for="confirmationMotDePasse">Confirmation :</label> 
					<input type="password" class="form-control" id="confirmationMotDePasse" name="confirmationMotDePasse" <c:if test="${empty participant.getPseudo()}">required</c:if> placeholder="Confirmation du mot de passe"> 
				</div>
				<div class="form-group">
				    <label for="site">Site de rattachement :</label>
				    <select class="form-control" id="site" name="site">
				    	<c:forEach var="site" items="${listeSite}">
				    		<option value="${site.getNoSite()}" <c:if test="${participant.getSite().getNoSite() == site.getNoSite()}"> selected </c:if>> ${site.getNomSite()}</option>
				    	</c:forEach>
				    </select>
				 </div>
				<div class="form-check">
					<c:choose>
						<c:when test="${participant.getAdministrateur()}">
							<input type="checkbox" class="form-check-input" id="isAdmin" name="isAdmin" value="isAdmin" checked>
						</c:when>    
						<c:otherwise>
							<input type="checkbox" class="form-check-input" id="isAdmin" name="isAdmin" value="isAdmin">
						</c:otherwise>
					</c:choose>
					<label class="form-check-label" for="isAdmin">Administrateur</label>
				</div>
				<div class="form-check">
					<c:choose>
						<c:when test="${participant.getActif()}">
							<input type="checkbox" class="form-check-input" id="isActif" name="isActif" value="isActif" checked>
						</c:when>    
						<c:otherwise>
							<input type="checkbox" class="form-check-input" id="isActif" name="isActif" value="isActif">
						</c:otherwise>
					</c:choose>
					<label class="form-check-label"  for="isActif">Actif</label>
				</div>
				<div class="form-group">
					<label for="photo">Ma photo :</label> <input type="file"
						class="form-control-file" id="photo" name="photo">
				</div>
				<div>
					<a href="<%=request.getContextPath()%>/accueil" class="btn btn-danger float-left">Annuler</a>
					<button type="submit" class="btn btn-primary float-right">Enregistrer</button>
				</div>
			</form>
		</div>
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>