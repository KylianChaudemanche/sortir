$(document).ready(function(){
  $("#rechercheSite").on("keyup", function() {
    var value = $(this).val().toLowerCase();
    $("#maTableSite tr td input").filter(function() {
      $(this).parent().parent().toggle($(this).val().toLowerCase().indexOf(value) > -1)
    });
  });
});
