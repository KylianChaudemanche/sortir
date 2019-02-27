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
<div class="container">
		<div class="row mt-3 ml-2 table-responsive">
			<h2>Mes Sorties:</h2>
						<table class="table table-striped table-bordered text-center">
			  <thead class="thead-dark">
			    <tr>
			      <th scope="col">Nom</th>
			      <th scope="col">Date</th>
			      <th scope="col">Lieux</th>
			    </tr>
			  </thead>
			  <tbody id="table-sorties">
				<c:if test="${fn:length(listeSorties) == 0}">
					<tr>
						<td colspan="8">
							<p class="font-weight-bold">Vous n'êtes inscrit à aucune sortie</p>
						</td>
					</tr>
				</c:if>
				<c:forEach items="${ listeSorties }" var="sortie">
			    <tr>
			      <td>
			      	<a href="<%=request.getContextPath()%>/logged/sortie/${sortie.getNoSortie()}">${ sortie.getNom() }</a>
			       </td>
			      <td>
			      	<fmt:formatDate value="${ sortie.getDateDebut() }" pattern="dd/MM/yyyy HH'h'mm" />
			      </td>
			      <td> ${ sortie.getLieu().getNomLieu() }</td>
			    </tr>
				</c:forEach>
			  </tbody>
			</table>
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