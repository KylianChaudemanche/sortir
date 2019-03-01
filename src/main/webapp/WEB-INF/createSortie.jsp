<!-- ####### HEADER ######## -->
<%@include file="includes/header.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->

<c:if test = "${error != null}">
    <div class="alert alert-danger text-center font-weight-bold" role="alert">
  	<c:out value = "${error}"/>
	</div>
</c:if>


<div class="container">
	<h1 class="text-center mb-4 mt-4">Créer une sortie</h1>
	
	<form action="createSortie" method="POST">
		<div class="row">
			<div class="col-sm-6 float-left">
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Nom de la sortie</label>
					<div class="col-sm-8">
						<input class="form-control" type="text" name="sortieName" value="${sortieName}" required>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Date et heure de la sortie</label>
					<div class="col-sm-8">
						<input class="form-control" type="datetime-local" name="sortieBeginDate" value="${sortieBeginDate}" required>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Date limite d'inscription</label>
					<div class="col-sm-8">
						<input class="form-control" type="date" name="sortieCloseInscriptionDate" value="${sortieCloseInscriptionDate}" required>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Nombre de place</label>
					<div class="col-sm-8">
						<input class="form-control" type="number" name="sortieNbMaxPlace" value="${sortieNbMaxPlace}" required>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Durée</label>
					<div class="col-sm-8">
						<input class="form-control col-sm-3 d-inline" name="sortieDuration" value="${sortieDuration}" type="number" min="1" max="999" step="1" required>
						<p class="d-inline ml-3">minutes</p>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Description et infos</label>
					<div class="col-sm-8">
						<textarea class="form-control" name="sortieDesc" rows="3" required>${sortieDesc}</textarea>
					</div>
				</div>
			</div>
			
			<div class="col-sm-6 float-right">
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Ville organisatrice</label>
					<div class="col-sm-8">
						<input id="sortieCityOrganizing" class="form-control" type="text" disabled>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Ville</label>
					<div class="col-sm-8">
						<input type='text'
					       class='form-control'
					       data-min-length='3'
					       data-search-in='name'
					       data-selection-required='true'
					       data-value-property='id'
					       id="sortieCity"
					       name='sortieCity'>
			        </div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Lieu</label>
					<div class="col-sm-6">
						<input type='text'
					       class='flexdatalist form-control'
					       data-min-length='2'
					       data-search-in='name'
					       data-selection-required='true'
					       data-value-property='id'
					       id="sortiePlace"
					       name='sortiePlace'>
					</div>
					<div class="col-sm-2">
						<button type="button" id="toggleModal" class="btn btn-primary" data-toggle="modal" data-target="#ajouterLieuModal">
						  +
						</button>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Adresse</label>
					<div class="col-sm-8">
						<input id="sortieAddress" class="form-control" type="text" disabled>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Code postal</label>
					<div class="col-sm-8">
						<input id="sortieCodePostal" class="form-control" type="text" disabled>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Latitude</label>
					<div class="col-sm-8">
						<input id="sortieLatitude" class="form-control"type="text" disabled>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Longitude</label>
					<div class="col-sm-8">
						<input id="sortieLongitude" class="form-control"type="text" disabled>
					</div>
				</div>
			</div>
		</div>
		
		<div class="form-group row">
			<div class="mx-auto mt-2">
		      	<button type="submit" name="action" class="btn btn-secondary btn-align" value="save">Enregistrer</button>
		      	<button type="submit" name="action" class="btn btn-secondary btn-align" value="publish">Publier la sortie</button>
		      	<a href="#"><button class="btn btn-secondary btn-align">Annuler</button></a>
	      	</div>
   		</div>
	</form>
</div>

<div class="modal" id="ajouterLieuModal" tabindex="-1" role="dialog">
	<div class="modal-dialog" role="document">
		<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Ajouter un lieu</h5>
					<button type="button" class="close" data-dismiss="modal" aria-label="Close">
						<span aria-hidden="true">&times;</span>
					</button>
				</div>
				<div class="modal-body">
					<div class="form-group">
						<label for="modalNomLieu">Nom : </label> 
						<input type="text" class="form-control" id="modalNomLieu" name="modalNomLieu" placeholder="Nom" required>
					</div>
					<div class="form-group">
						<label for="modalAdresse">Adresse : </label> 
						<input type="text" class="form-control" id="modalAdresse" name="modalAdresse" placeholder="Adresse" required>
					</div>
					<div class="form-group">
						<label for="modalLatitude">Latitude : </label> 
						<input type="text" class="form-control" id="modalLatitude" name="modalLatitude" placeholder="Latitude" required>
					</div>
					<div class="form-group">
						<label for="modalLongitude">Longitude : </label> 
						<input type="text" class="form-control" id="modalLongitude" name="modalLongitude" placeholder="Longitude" required>
					</div>
					<input type="text" id="modalNoVille" name="modalNoVille" hidden="true" required>

				</div>
				<div class="modal-footer">
					<button class="btn btn-secondary" data-dismiss="modal">Annuler</button>
					<button class="btn btn-primary" id="ajouterLieu" data-toggle="modal" data-target="#ajouterLieuModal">Ajouter</button>
				</div>
		</div>
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>

<script>
	$(function() {
		var cities = [
			<c:forEach items="${cities}" var="city">
				{
					name: "${city.nomVille}",
					id: "${city.noVille}",
					codePostal: "${city.codePostal}"
				},
			</c:forEach> 
		];

		var places = [
			<c:forEach items="${places}" var="place">
				{
					name: "${place.nomLieu}",
					id: "${place.noLieu}",
					address: "${place.adresse}",
					latitude: "${place.latitude}",
					longitude: "${place.longitude}",
					cityId: "${place.ville.noVille}"
				},
			</c:forEach> 
		];
		
		$('#sortieCity').flexdatalist({
		     minLength: 3,
		     searchIn: 'name',
		     data: cities,
		     valueProperty: 'id',
		     selectionRequired: true
		});		
		
		$('#sortieCity').on("change", function() {
			var cityId = $('#sortieCity').val();
			var city = cities.filter(city => city.id == cityId)[0];
			if (city) {
				$("#sortieCodePostal").val(city.codePostal);
				$("#sortieCityOrganizing").val(city.name)
				var placeFiltred = places.filter(place => place.cityId == cityId)
				$('#sortiePlace').flexdatalist({
				     minLength: 2,
				     searchIn: 'name',
				     data: placeFiltred,
				     valueProperty: 'id',
				     selectionRequired: true
				});
				$('#sortiePlace').on("change", function() {
					var placeId = $('#sortiePlace').val();
					var place = places.filter(place => place.id == placeId)[0];
					$("#sortieAddress").val(place.address);
					$("#sortieLongitude").val(place.longitude);
					$("#sortieLatitude").val(place.latitude);
				});
			}
		});
		$("#toggleModal").click(function(){
			$("#modalNoVille").val($("#sortieCity").val());
		});
		
		
		$("#ajouterLieu").click(function(){
            $.ajax({
                type: 'POST',
                url: 'creationLieu',
                data : {
                	modalNomLieu : $('#modalNomLieu').val(),
                	modalAdresse : $('#modalAdresse').val(),
                	modalLatitude : $('#modalLatitude').val(),
                	modalLongitude : $('#modalLongitude').val(),
                	modalNoVille : $('#modalNoVille').val()
                	},
                	success: function (data) {
                        if (!data) {
                            alert('Please check Code');
                        } else {
                        	places.push(
                        	{
            					name: data.nomLieu,
            					id: data.noLieu,
            					address: data.adresse,
            					latitude: data.latitude,
            					longitude: data.longitude,
            					cityId: data.ville.noVille
            				});
                        	$('#sortieCity').change();
                        }
                    },
                    error: function ()
                    { alert('there is some error to get Rate'); }
            });
		});
	});
</script>