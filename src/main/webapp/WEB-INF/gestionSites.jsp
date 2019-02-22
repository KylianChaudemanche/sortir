<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="Gestion Sites" name="titre" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->

<div class="container">
	<div class="row">
		<div class="col-md-6 mx-auto">
			<h1 class="">Gérer les sites</h1>
			<input class="w-100 form-control my-3" type="text" id="rechercheSite" name="rechercheSite" placeholder="Rechercher un site">
			<form class="card shadow" method="POST" action=gestionSites>
				<table class="table ">
					<thead>
						<tr>
							<th scope="col">#</th>
							<th scope="col">Ville</th>
							<th scope="col">Action</th>
						</tr>
					</thead>
					<tbody id="maTableSite">
						<c:forEach var="site" items="${listeSites}">
							<tr>
								<th scope="row">${site.noSite}</th>
								<td><input class="form-control" id="${site.noSite}" name="${site.noSite}" value="${site.nomSite}"></td>
								<td><button type="submit" class="btn btn-primary btn-sm" name="modifierSite" value="${site.noSite}">Modifier</button> - <button type="submit" class="btn btn-danger btn-sm" name="supprimerSite" value="${site.noSite}">Supprimer</button></td>
							</tr>
						</c:forEach>
					</tbody>
						<tr class="my-auto">
							<th scope="row"></th>
							<td><input class="form-control" type="text" id="nouveauSite" name="nouveauSite"></td>
							<td><button type="submit" class="btn btn-success mx-auto" name="ajouterSite">Ajouter</button></td>
						</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>