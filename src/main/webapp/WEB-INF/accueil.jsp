<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="Accueil" name="titre" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
 
<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->
<div class="container card mt-4 p-3">
	<form method="POST">
		<div class="row">
			
			<div class="col-md-6">
				<div class="form-group row">
					<label for="site" class="col-sm-5 col-form-label col-form-label-sm">Site: </label>
					 <div class="col-sm-7">
					 	<select name="site" id="site" class="form-control form-control-sm">
					 		<c:forEach items="${ listeSites }" var="site">
						     <c:choose>
							  <c:when test="${ site.getNoSite() == siteSelected }">
							    <option value="${ site.getNoSite() }" selected>${ site.getNomSite() }</option>
							  </c:when>
							  <c:otherwise>
								<option value="${ site.getNoSite() }">${ site.getNomSite() }</option>
							  </c:otherwise>
							</c:choose>
							</c:forEach>
						</select>
					 </div>
				</div>
				<div class="form-group row">
					<label for="recherche" class="col-sm-5 col-form-label col-form-label-sm">Le nom de la sortie contient: </label>
					<div class="col-sm-7">
						<input type="text" class="form-control form-control-lg" id="recherche" name="recherche" placeholder="rechercher...">
					</div>
				</div>
			</div>
				
			<div class="col-lg-1"></div>
			
			<div class="col-md-4">
				<div class="custom-control custom-checkbox">
					<c:choose>
					  <c:when test="${ listeCheckbox[0] == true}">
						<input class="custom-control-input" type="checkbox" value="on" id="sortieOrganisateur" name="sortieOrganisateur" checked>
					  </c:when>
					  <c:otherwise>
						<input class="custom-control-input" type="checkbox" value="on" id="sortieOrganisateur" name="sortieOrganisateur">
					  </c:otherwise>
					</c:choose>
					<label class="custom-control-label" for="sortieOrganisateur">
						Sorties dont je suis l'organisateur/trice
					</label>
				</div>
				<div class="custom-control custom-checkbox">
					<c:choose>
					  <c:when test="${ listeCheckbox[1] == true}">
					<input class="custom-control-input" type="checkbox" value="on" id="sortieInscrit" name="sortieInscrit" checked>
					  </c:when>
					  <c:otherwise>
					<input class="custom-control-input" type="checkbox" value="on" id="sortieInscrit" name="sortieInscrit">
					  </c:otherwise>
					</c:choose>
					<label class="custom-control-label" for="sortieInscrit">
						Sorties auxquelles je suis inscrit/e
					</label>
				</div>
				<div class="custom-control custom-checkbox">
					<c:choose>
					  <c:when test="${ listeCheckbox[2] == true}">
					<input class="custom-control-input" type="checkbox" value="on" id="sortiePasInscrit" name="sortiePasInscrit" checked>
					  </c:when>
					  <c:otherwise>
					<input class="custom-control-input" type="checkbox" value="on" id="sortiePasInscrit" name="sortiePasInscrit">
					  </c:otherwise>
					</c:choose>
					<label class="custom-control-label" for="sortiePasInscrit">
						Sorties auxquelles je ne suis pas inscrit/e
					</label>
				</div>
				<div class="custom-control custom-checkbox">
					<c:choose>
					  <c:when test="${ listeCheckbox[3] == true}">
					<input class="custom-control-input" type="checkbox" value="on" id="sortiePassee" name="sortiePassee" checked>
					  </c:when>
					  <c:otherwise>
					<input class="custom-control-input" type="checkbox" value="on" id="sortiePassee" name="sortiePassee">
					  </c:otherwise>
					</c:choose>
					<label class="custom-control-label" for="sortiePassee">
						Sorties passées
					</label>
				</div>
			</div>
				
		</div> <!-- </row> -->
		<hr>
		
		<div class="row mt-4">
				<div class="col-md-8">
					<div class="form-group row">
					<div class="col-md-6 row">
						<label for="dateDebut" class="col-sm-3 col-form-label col-form-label-sm">Entre le: </label>
						<div class="col-sm-9">
						 <c:choose>
							  <c:when test="${ dateDebut != null }">
							<input type="date" class="form-control form-control-lg" id="dateDebut" name="dateDebut" value="${ dateDebut }">
							  </c:when>
							  <c:otherwise>
							<input type="date" class="form-control form-control-lg" id="dateDebut" name="dateDebut">
							  </c:otherwise>
						  </c:choose>
						</div>
					</div>
						<div class="col-md-6 row">
							<label for="dateFin" class="col-sm-3 col-form-label col-form-label-sm">Et le: </label>
							<div class="col-sm-9">
							<c:choose>
							  <c:when test="${ dateFin != null }">
								<input type="date" class="form-control form-control-lg" id="dateFin" name="dateFin" value="${ dateFin }">
							  </c:when>
							  <c:otherwise>
								<input type="date" class="form-control form-control-lg" id="dateFin" name="dateFin">
							  </c:otherwise>
						  </c:choose>
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<input type="submit" class="btn btn-lg btn-success" value="Rechercher">
					</div>
				</div>
		</div>
    </form>
</div>

<div class="container mt-5">
	<div class="row">
		<div class="col-md-12">
			<table class="table table-striped table-bordered text-center">
			  <thead class="thead-dark">
			    <tr>
			      <th scope="col">Nom</th>
			      <th scope="col">Date</th>
			      <th scope="col">Clôture</th>
			      <th scope="col">Inscrits/Places</th>
			      <th scope="col">État</th>
			      <th scope="col">Inscrit</th>
			      <th scope="col">Organisateur</th>
			      <th scope="col">Détail</th>
			    </tr>
			  </thead>
			  <tbody>
				<c:forEach items="${ listeSorties }" var="sortie">
			    <tr>
			      <td> ${ sortie.getNom() }</td>
			      <td>
			      	<fmt:formatDate value="${ sortie.getDateDebut() }" pattern="dd/MM/yyyy HH'h'mm" />
			      </td>
			      <td>
			      	<fmt:formatDate value="${ sortie.getDateCloture() }" pattern="dd/MM/yyyy" />
			      </td>
			      <td>${fn:length(sortie.getInscriptions())}<b>/</b>${ sortie.getNbInscriptionsMax() }</td>
			      <td>${ sortie.getEtat().getLibelle() }</td>
			      <td>
			      	<div class="form-check">
					  <input class="form-check-input" type="checkbox" value="on" id="inscrit" name="inscrit">
					</div>
			      </td>
			      <td>${ sortie.getOrganisateur().getPrenom() } ${ sortie.getOrganisateur().getNom() }</td>
			      <td>
			      	<a href="<%=request.getContextPath()%>/sortie/${sortie.getNoSortie()}">
			      		<i class="grow text-dark" data-feather="external-link"></i>
			      	</a>
			      </td>
			    </tr>
				</c:forEach>
			   
			  </tbody>
			</table>
		</div>
		<div class="col-md-12">
			<a href="" class="btn btn-warning">Créer une sortie</a>
		</div>
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>