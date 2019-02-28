<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="Gestion Participants" name="titre" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->

<div class="container">
	<div class="row">
		<div class="col-md-6 mx-auto">
			<h1 class="">Gérer les participants</h1>
			<input type="text" class="w-100 form-control my-3" id="rechercheParticipants">
			<form class="card shadow" method="POST" action=gestionParticipants>
				<table class="table ">
					<thead>
						<tr>
							<th scope="col">#</th>
							<th scope="col">Pseudo</th>
							<th scope="col">Action</th>
						</tr>
					</thead>
					<tbody id="maTableParticipants">
						<c:forEach var="participant" items="${listeParticipants}">
							<tr>
								<th scope="row">${participant.getNoParticipant()}</th>
								<td><p id="${participant.getPseudo()}" >${participant.getPseudo()}</p></td>
								<td><button type="submit" class="btn btn-primary btn-sm" name="modifierParticipant" value="${participant.getNoParticipant()}">Modifier</button> - <button type="submit" class="btn btn-danger btn-sm" name="supprimerParticipant" value="${participant.getNoParticipant()}">Supprimer</button></td>
							</tr>
						</c:forEach>
					</tbody>
						<tr class="my-auto">
							<td><button type="submit" class="btn btn-success mx-auto" name="ajouterParticipant">Ajouter</button></td>
						</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>

<script>
$(document).ready(function(){
	  $("#rechercheParticipants").on("keyup", function() {
	    var value = $(this).val().toLowerCase();
	    $("#maTableParticipants tr").filter(function() {
	      $(this).toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	});
</script>