<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="Accueil" name="titre" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
					 		<option>Rennes</option>
							<option>Nantes</option>
						</select>
					 </div>
				</div>
				<div class="form-group row">
					<label for="recherche" class="col-sm-5 col-form-label col-form-label-sm">Le nom du site contient: </label>
					<div class="col-sm-7">
						<input type="text" class="form-control form-control-lg" id="recherche" name="recherche" placeholder="rechercher...">
					</div>
				</div>
			</div>
				
			<div class="col-lg-1"></div>
			
			<div class="col-md-4">
				<div class="custom-control custom-checkbox">
					<input class="custom-control-input" type="checkbox" value="on" id="sortieOrganisateur" name="sortieOrganisateur">
					<label class="custom-control-label" for="sortieOrganisateur">
						Sorties dont je suis l'organisateur/trice
					</label>
				</div>
				<div class="custom-control custom-checkbox">
					<input class="custom-control-input" type="checkbox" value="on" id="sortieInscrit" name="sortieInscrit">
					<label class="custom-control-label" for="sortieInscrit">
						Sorties auxquelles je suis inscrit/e
					</label>
				</div>
				<div class="custom-control custom-checkbox">
					<input class="custom-control-input" type="checkbox" value="on" id="sortiePasInscrit" name="sortiePasInscrit">
					<label class="custom-control-label" for="sortiePasInscrit">
						Sorties auxquelles je ne suis pas inscrit/e
					</label>
				</div>
				<div class="custom-control custom-checkbox">
					<input class="custom-control-input" type="checkbox" value="on" id="sortiePassee" name="sortiePassee">
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
							<input type="date" class="form-control form-control-lg" id="dateDebut" name="dateDebut">
						</div>
					</div>
						<div class="col-md-6 row">
							<label for="dateFin" class="col-sm-3 col-form-label col-form-label-sm">Et le: </label>
							<div class="col-sm-9">
								<input type="date" class="form-control form-control-lg" id="dateFin" name="dateFin">
							</div>
						</div>
					</div>
				</div>
				<div class="col-md-4">
					<div class="form-group">
						<div class="btn btn-lg btn-success">Rechercher</div>
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
			    <tr>
			      <td>Beuverie</td>
			      <td>22/02/2019 20h45</td>
			      <td>22/02/2019</td>
			      <td>7<b>/</b>12</td>
			      <td>Ouvert</td>
			      <td>
			      	<div class="form-check">
					  <input class="form-check-input" type="checkbox" value="on" id="inscrit" name="inscrit">
					</div>
			      </td>
			      <td>Maxence Millot</td>
			      <td>
			      	<a href="">
			      		<i class="grow text-dark" data-feather="external-link"></i>
			      	</a>
			      </td>
			    </tr>
			     <tr>
			      <td>Beuverie</td>
			      <td>22/02/2019 20h45</td>
			      <td>22/02/2019</td>
			      <td>7/12</td>
			      <td>Ouvert</td>
			      <td>
			      	<div class="form-check">
					  <input class="form-check-input" type="checkbox" value="on" id="inscrit" name="inscrit">
					</div>
			      </td>
			      <td>Maxence Millot</td>
			      <td>
			      	<a href="">
			      		<i class="grow text-dark" data-feather="external-link"></i>
			      	</a>
			      </td>
			    </tr>
			    
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