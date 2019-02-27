<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="Gestion de compte" name="titre"/>
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->
<c:if test="${CSVOK}">
	<div class="alert alert-success" role="alert">
	  Les participants ont été ajoutés.
	</div>
</c:if>
<c:if test="${!empty(CSVOK) && !CSVOK}">
	<div class="alert alert-danger" role="alert">
	  Des entrées comportent des erreurs. Merci de les corriger.
	</div>
</c:if>
<div class="container">
	<div class="row">
		<div class="col-md-4 mx-auto card shadow m-3 p-3">
			<form class="mx-auto" method="post" enctype="multipart/form-data">
				<div class="form-group">
				    <label for="fichierCSV">Fichier d'intégration :</label>
					<input type="file" class="form-control-file" id="fichierCSV" name="fichierCSV">
			 	</div>
			 	<a href="${urlPrecedente}" class="btn btn-primary m-3">Retour</a>
			 	<input type="submit" class="btn btn-success" value="Valider">
			</form>
		</div>
			<c:if test="${listeParticipants != null }">
			<div class="col-md-4 card shadow m-3 p-3">
			<div>
				<div class="font-weight-bold float-left">Pseudo ou Email non disponible : </div> 
				<div class="bg-warning rounded float-right" style="height:50px;width:50px"></div>
			</div>
				<br>
			<div>
				<div class="font-weight-bold float-left">Site inexistant : </div> 
				<div class="bg-danger rounded float-right" style="height:50px;width:50px"></div>
			</div>
			</div>
			<table class="table table-bordered">
			 		<thead class="thead-dark">
				 		<tr>
				 			<th scope="col">#</th>
				 			<th scope="col">pseudo</th>
				 			<th scope="col">nom</th>
				 			<th scope="col">prenom</th>
				 			<th scope="col">telephone</th>
				 			<th scope="col">mail</th>
				 			<th scope="col">motDePasse</th>
				 			<th scope="col">noSite</th>
				 		</tr>
			 		</thead>
			 		<tbody>
			 			<c:forEach var="participant" items="${listeParticipants}" varStatus="status">
				    		<tr>
				    			<td scope="row">${status.count }</td>
				    			<td class="bg-${tabParametreStatus[status.count-1][0]}">${participant.getPseudo()}</td>
					 			<td class="bg-${tabParametreStatus[status.count-1][1]}">${participant.getNom()}</td>
					 			<td class="bg-${tabParametreStatus[status.count-1][2]}">${participant.getPrenom()}</td>
					 			<td class="bg-${tabParametreStatus[status.count-1][3]}">${participant.getTelephone()}</td>
					 			<td class="bg-${tabParametreStatus[status.count-1][4]}">${participant.getMail()}</td>
					 			<td class="bg-${tabParametreStatus[status.count-1][5]}">${participant.getMotDePasse()}</td>
					 			<td class="bg-${tabParametreStatus[status.count-1][6]}">${participant.getSite().getNoSite()}</td>
				    		</tr>
				    	</c:forEach>
			 		</tbody>
			 	</table>
			 </c:if>
		
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>