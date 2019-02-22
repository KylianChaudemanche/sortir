$(function() {

});

function initSelectize(cities) {
	$("#sortieCity").selectize({
		options: cities,
		valueField: "id",
		labelField: "name",
		searchField: "name"
	});		
	
	$("#sortiePlace").selectize({
		valueField: "id",
		labelField: "name",
		searchField: "name"
	});
}

function handleOnChangeCity(cities, places) {
	$("#sortieCity").on("change", function() {
		var cityId = $("#sortieCity").find(":selected").val();
		var city = cities.filter(city => city.id == cityId)[0];
		if (city) {
			$("#sortieCodePostal").val(city.codePostal);
			$("#sortieCityOrganizing").val(city.name)
			var placeFiltred = places.filter(place => place.cityId == cityId);
			handleOnChangePlace(placeFiltred);
		}
	});
}

function handleOnChangePlace(placeFiltred) {
	var selectize = $('#sortiePlace').selectize()[0].selectize;
	selectize.clear();
	selectize.clearOptions();
	selectize.load(function(callback) {
		callback(placeFiltred);
	})
	
	$("#sortiePlace").on("change", function() {
		var placeId = $("#sortiePlace").find(":selected").val();
		var place = placeFiltred.filter(place => place.id == placeId)[0];
		if (place) {
			$("#sortieAddress").val(place.address);
			$("#sortieLongitude").val(place.longitude);
			$("#sortieLatitude").val(place.latitude);
		}
	});
}