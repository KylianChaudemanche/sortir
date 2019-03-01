<!-- ####### HEADER ######## -->
<%@include file="includes/header.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->

<div class="container">
	<div class="row">
		<div class="col-md-3 py-5">
		<c:choose>
			<c:when test="${nouveau == true}">
	    		<img src="/sortir/logged/image/batman" style="maxwidth:200px" class="img-thumbnail">
	    	</c:when>
			<c:otherwise>
				<object data="/sortir/logged/image/${participant.noParticipant}" style="maxwidth:200px" class="img-thumbnail" type="image/jpg">
	      		<img src="/sortir/logged/image/batman" style="maxwidth:200px" class="img-thumbnail">
	    		</object>
			</c:otherwise>
		</c:choose>
		</div>
		<div class="col-md-6">
			<c:choose>
			<c:when test="${nouveau == true}"><h1 class="text-center">Nouveau</h1></c:when>
			<c:otherwise><h1 class="text-center">${participant.getPseudo()}</h1></c:otherwise>
			</c:choose>
			
			<form action="" method="POST"	enctype="multipart/form-data">
			<c:choose>
			<c:when test="${nouveau == true}">
				<input type="text" value="nouveau" id="action" name="action" hidden="true">
				<div class="form-group">
					<label for="pseudo">Pseudo :</label> 
					<input type="text" class="form-control" id="pseudo" name="pseudo" required placeholder="Pseudo"  > 
				</div>
				<div class="form-group">
					<label for="prenom">Pr�nom :</label> 
					<input type="text" class="form-control" id="prenom" name="prenom" required placeholder="Pr�nom" > 
				</div>
				<div class="form-group">
					<label for="nom">Nom :</label> 
					<input type="text" class="form-control" id="nom" name="nom" required placeholder="Nom" > 
				</div>
				<div class="form-group">
					<label for="telephone">T�l�phone :</label> 
					<input type="text" class="form-control" id="telephone" name="telephone"  required placeholder="Num�ro de t�l�phone" maxlength="10" > 
				</div>
				<div class="form-group">
					<label for="mail">Mail :</label> 
					<input type="text" class="form-control" id="mail" name="mail" required placeholder="Mail" > 
				</div>
				
				<div class="form-group">
					<label for="motDePasse">Mot de passe :</label> 
					<input type="password" class="form-control" id="motDePasse" name="motDePasse" required placeholder="Mot de passe" > 
				</div>
				<div class="form-group">
					<label for="confirmationMotDePasse">Confirmation :</label> 
					<input type="password" class="form-control" id="confirmationMotDePasse" name="confirmationMotDePasse" required placeholder="Confirmation du mot de passe"> 
				</div>
				<div class="form-group">
				    <label for="site">Site de rattachement :</label>
				    <select class="form-control" id="site" name="site">
				    	<c:forEach var="site" items="${listeSite}">
				    		<option value="${site.getNoSite()}" >${site.getNomSite()}</option>
				    	</c:forEach>
				    </select>
				 </div>
				<div class="form-check">
					<input type="checkbox" class="form-check-input" id="isAdmin" name="isAdmin" value="isAdmin">
					<label class="form-check-label" for="isAdmin">Administrateur</label>
				</div>
				<div class="form-check">
					<input type="checkbox" class="form-check-input" id="isActif" name="isActif" value="isActif">
					<label class="form-check-label"  for="isActif">Actif</label>
				</div>
			</c:when>
			<c:otherwise>
				<input type="text" value="${participant.getNoParticipant()}" id="action" name="action" hidden="true" >
				<div class="form-group">
					<label for="pseudo">Pseudo :</label> 
					<input type="text" class="form-control" id="pseudo" name="pseudo" required placeholder="Pseudo"  value="${participant.getPseudo()}"> 
				</div>
				<div class="form-group">
					<label for="prenom">Pr�nom :</label> 
					<input type="text" class="form-control" id="prenom" name="prenom" required placeholder="Pr�nom" value="${participant.getPrenom()}"> 
				</div>
				<div class="form-group">
					<label for="nom">Nom :</label> 
					<input type="text" class="form-control" id="nom" name="nom" required placeholder="Nom" value="${participant.getNom()}"> 
				</div>
				<div class="form-group">
					<label for="telephone">T�l�phone :</label> 
					<input type="text" class="form-control" id="telephone" name="telephone"  required placeholder="Num�ro de t�l�phone" maxlength="10" value="${participant.getTelephone()}"> 
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
			</c:otherwise>
			</c:choose>
				<div class="form-group">
					<label for="photo">Ma photo :</label> <input type="file"
						class="form-control-file" id="photo" name="photo">
				</div>
				<div>
					<a href="/sortir/logged/accueil" class="btn btn-danger float-left">Annuler</a>
					<button type="submit" class="btn btn-primary float-right">Enregistrer</button>
				</div>
			</form>
		</div>
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>