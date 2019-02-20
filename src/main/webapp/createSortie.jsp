<!-- ####### HEADER ######## -->
<%@include file="includes/header.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->

<div class="container">
	<h1 class="title-create-sortie mb-4 mt-4">Cr�er une sortie</h1>
	
	<form action="createSortie" method="POST">
		<div class="row">
			<div class="col-sm-6 float-left">
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Nom de la sortie</label>
					<div class="col-sm-8">
						<input class="form-control" type="text" name="sortieName" required>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Date et heure de la sortie</label>
					<div class="col-sm-8">
						<input class="form-control" type="datetime-local" name="sortieBeginDate" required>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Date limite d'inscription</label>
					<div class="col-sm-8">
						<input class="form-control" type="date" name="sortieCloseInscriptionDate" required>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Nombre de place</label>
					<div class="col-sm-8">
						<input class="form-control" type="number" name="sortieNbMaxPlace" required>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Dur�e</label>
					<div class="col-sm-8">
						<input class="form-control col-sm-2 d-inline" name="sortieDuration" type="number" min="1" max="100" step="1" required>
						<p class="d-inline ml-3">minutes</p>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Description et infos</label>
					<div class="col-sm-8">
						<textarea class="form-control" name="sortieDesc" rows="3" required></textarea>
					</div>
				</div>
			</div>
			
			<div class="col-sm-6 float-right">
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Site organisateur</label>
					<div class="col-sm-8">
						<input class="form-control" type="text" disabled>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Ville</label>
					<div class="col-sm-8">
						<select id="sortieCity" name="sortieCity">
						</select>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Lieu</label>
					<div class="col-sm-8">
						<select id="sortiePlace" name="sortiePlace" require>
						</select>
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
			<div class="btn-wrapper mt-2">
		      	<button type="submit" name="action" class="btn btn-secondary btn-align" value="save">Enregistrer</button>
		      	<button type="submit" name="action" class="btn btn-secondary btn-align" value="publish">Publier la sortie</button>
		      	<a href="#"><button class="btn btn-secondary btn-align">Annuler</button></a>
	      	</div>
   		</div>
	</form>
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
		
		initSelectize(cities);
		handleOnChangeCity(cities, places);
	});
	
	function initSelectize(cities) {
		$("#sortieCity").selectize({
			options: cities,
			valueField: "id",
			labelField: "name",
			searchField: "name"
		});		
		
		$("#sortiePlace").selectize();
	}
	
	function handleOnChangeCity(cities, places) {
		$("#sortieCity").on("change", function() {
			var cityId = $("#sortieCity").find(":selected").val();
			$("#sortieCodePostal").val(cities.filter(city => city.id == cityId)[0].codePostal);

			var placeFiltred = places.filter(place => place.cityId == cityId);
			handleOnChangePlace(placeFiltred);
		});
	}
	
	function handleOnChangePlace(placeFiltred) {
		$('#sortiePlace').selectize()[0].selectize.destroy();
		$("#sortiePlace").selectize({
			options: placeFiltred,
			valueField: "id",
			labelField: "name",
			searchField: "name"
		});
		
		$("#sortiePlace").on("change", function() {
			var placeId = $("#sortiePlace").find(":selected").val();
			var place = placeFiltred.filter(place => place.id == placeId)[0];
			$("#sortieAddress").val(place.address);
			$("#sortieLongitude").val(place.longitude);
			$("#sortieLatitude").val(place.latitude);
		});
	}
</script>