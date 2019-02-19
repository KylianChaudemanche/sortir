<!-- ####### HEADER ######## -->
<%@include file="includes/header.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->

<div class="container">
	<h1 class="title-create-sortie mb-4 mt-4">Créer une sortie</h1>
	
	<form>
		<div class="row">
			<div class="col-sm-6 float-left">
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Nom de la sortie</label>
					<div class="col-sm-8">
						<input class="form-control" type="text" name="sortieName">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Date de la sortie</label>
					<div class="col-sm-8">
						<input class="form-control" type="date" name="sortieBeginDate">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Date limite d'inscription</label>
					<div class="col-sm-8">
						<input class="form-control" type="date" name="sortieCloseInscriptionDate">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Nombre de place</label>
					<div class="col-sm-8">
						<input class="form-control" type="number" name="sortieNbMaxPlace">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Durée</label>
					<div class="col-sm-8">
						<input class="form-control col-sm-2 d-inline" type="number" min="1" max="100" step="1">
						<p class="d-inline ml-3">minutes</p>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Description et infos</label>
					<div class="col-sm-8">
						<textarea class="form-control" name="sortieDesc" rows="3"></textarea>
					</div>
				</div>
			</div>
			
			<div class="col-sm-6 float-right">
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Ville organisatrice</label>
					<div class="col-sm-8">
						<input class="form-control" type="text">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Ville</label>
					<div class="col-sm-8">
						<select class="form-control">
							<option></option>
						</select>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Lieu</label>
					<div class="col-sm-8">
						<select class="form-control">
							<option></option>
						</select>
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Rue</label>
					<div class="col-sm-8">
						<input class="form-control" type="text">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Code postal</label>
					<div class="col-sm-8">
						<input class="form-control" type="text">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Latitude</label>
					<div class="col-sm-8">
						<input class="form-control" type="text">
					</div>
				</div>
				<div class="form-group row">
					<label class="col-sm-4 col-form-label">Longitude</label>
					<div class="col-sm-8">
						<input class="form-control" type="text">
					</div>
				</div>
			</div>
		</div>
		
		<div class="form-group row">
			<div class="btn-wrapper mt-2">
		      	<button type="submit" class="btn btn-secondary btn-align">Créer</button>
		      	<button type="submit" class="btn btn-secondary btn-align">Publier la sortie</button>
		      	<a href=""><button class="btn btn-secondary btn-align">Annuler</button></a>
	      	</div>
   		</div>
	</form>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>