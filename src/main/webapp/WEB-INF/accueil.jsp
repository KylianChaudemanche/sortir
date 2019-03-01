<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="Accueil" name="titre" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ page import="fr.eni.sortir.utils.State" %>
 
<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>

<!-- ####### CONTENT ######## -->
<div class="container card mt-4 p-3">
	<form id="form" method="POST">
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

<div class="container mt-5 desktop-only">
	<div class="row">
		<div class="col-md-12 table-responsive">
			<div class="col-12 alert alert-${ typeMessage } text-center" role="alert">
			  <b>${ message }</b>
			</div>
			<div class="col-md-12 mb-3">
				<a href="<%=request.getContextPath()%>/logged/createSortie" class="btn btn-warning">Créer une sortie</a>
			</div>
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
			      <th scope="col">Actions</th>
			    </tr>
			  </thead>
			  <tbody id="table-sorties">
				<c:if test="${fn:length(listeSorties) == 0}">
					<tr>
						<td colspan="8">
							<p class="font-weight-bold">Aucun résultat</p>
						</td>
					</tr>
				</c:if>
				<c:forEach items="${ listeSorties }" var="sortie">
			    <tr>
			      <td class="col-nom"> ${ sortie.getNom() }</td>
			      <td>
			      	<fmt:formatDate value="${ sortie.getDateDebut() }" pattern="dd/MM/yyyy HH'h'mm" />
			      </td>
			      <td>
			      	<fmt:formatDate value="${ sortie.getDateCloture() }" pattern="dd/MM/yyyy" />
			      </td>
			      <td>${fn:length(sortie.getInscriptions())}<b>/</b>${ sortie.getNbInscriptionsMax() }</td>
			      <td>${ sortie.getEtat().getLibelle() }</td>
			      <td>
	     			<c:set var="isInscrit" value="false" />
				      <c:forEach items="${ sortie.getInscriptions() }" var="inscription">
				      	  <c:if test="${ inscription.participant.getNoParticipant() ==  participant.getNoParticipant()}">
	     				  	<c:set var="isInscrit" value="true" />
				      	  </c:if>
				      </c:forEach>
					  <c:if test="${ isInscrit == true }"> 
						<i class="font-weight-bold text-success" data-feather="check"></i>
					  </c:if>
			      </td>
			      <td>${ sortie.getOrganisateur().getPrenom() } ${ sortie.getOrganisateur().getNom() }</td>
			      <td>		      	
			      	<c:choose>
				    	<c:when test="${ sessionScope.participant != null}">
							<c:choose>
						    	<c:when test="${ participant.getNoParticipant() == sortie.getOrganisateur().getNoParticipant() and (sortie.getEtat().getLibelle() eq State.OPENED.toString() or sortie.getEtat().getLibelle() eq State.CREATED.toString())}">
									<a href="<%=request.getContextPath()%>/logged/updateSortie/${sortie.getNoSortie()}">
							      		Modifier
							      	</a>
						    	</c:when>    
						    	<c:otherwise>
									<a href="<%=request.getContextPath()%>/logged/sortie/${sortie.getNoSortie()}">
					      				Afficher
					      			</a>
						   	 	</c:otherwise>
							</c:choose>
							
							<c:choose>
								<c:when test="${sortie.getEtat().getLibelle() eq State.CREATED.toString() and participant.getNoParticipant() == sortie.getOrganisateur().getNoParticipant() }">
									-
									<a href="<%=request.getContextPath()%>/logged/publish/${sortie.getNoSortie()}">
							      		Publier
							      	</a>
						    	</c:when> 
						    	<c:when test="${sortie.getEtat().getLibelle() eq State.OPENED.toString() }">
									<c:choose>
										<c:when test="${ participant.getNoParticipant() == sortie.getOrganisateur().getNoParticipant() }">
											-
											<a href="<%=request.getContextPath()%>/logged/annulerSortie/${sortie.getNoSortie()}">
									      		Annuler
									      	</a>
										</c:when>
										<c:when test="${ participant.getAdministrateur() }">
											-
											<a href="<%=request.getContextPath()%>/logged/annulerSortie/${sortie.getNoSortie()}">
									      		Annuler
									      	</a>
										</c:when>
									</c:choose>
									<c:choose>
										<c:when test="${ isInscrit == true and  sortie.getEtat().getLibelle() eq State.OPENED.toString()}">
											-
											<a href="<%=request.getContextPath()%>/logged/desinscription/${sortie.getNoSortie()}">
								      			Se désister
								      		</a>
							    		</c:when>    
							    		<c:when test="${ isInscrit == false and sortie.getEtat().getLibelle() eq State.OPENED.toString() }">
											-
											<a href="<%=request.getContextPath()%>/logged/inscription/${sortie.getNoSortie()}">
								      			S'inscrire
								      		</a>
							   	 		</c:when>
									</c:choose>
						    	</c:when>
							</c:choose>
				    	</c:when>    
				    	<c:otherwise>
							<a href="<%=request.getContextPath()%>/logged/sortie/${sortie.getNoSortie()}">
			      				Afficher
			      			</a>
				   	 	</c:otherwise>
					</c:choose>
			      </td>
			    </tr>
				</c:forEach>
			  </tbody>
			</table>
		</div>
		
	</div>
</div>
<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>


<!-- ####### SCRIPTS ######## -->
<script>
$(document).ready(function(){	
	  $("#recherche").on("keyup", function() {
	    var value = $(this).val().toLowerCase();
	    $("#table-sorties tr .col-nom").filter(function() {
	      $(this).parent().toggle($(this).text().toLowerCase().indexOf(value) > -1)
	    });
	  });
	  
	  $('#recherche').on('keyup keypress', function(e) {
		  var keyCode = e.keyCode || e.which;
		  if (keyCode === 13) { 
		    e.preventDefault();
		    return false;
		  }
		});
	});
</script>