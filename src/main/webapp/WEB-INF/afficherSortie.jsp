<!-- ####### HEADER ######## -->
<jsp:include page="includes/header.jsp">
	<jsp:param value="${sortie.getNom()}" name="titre" />
</jsp:include>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!-- ####### NAVBAR ######## -->
<%@include file="includes/navbar.jsp"%>


<!-- ####### CONTENT ######## -->
<div class="container">
	<div class="row">
		<div class="col-md-12">
			<h1 class="mx-auto text-center my-3 ">Afficher une sortie</h1>
				<div class="col-md-5 float-left bg-light rounded shadow p-3">
					<div class="col-md-6 float-left">Nom : </div>
					<div class="col-md-6 float-right font-weight-bold">${sortie.getNom()}</div>
					<div class="col-md-12">&nbsp</div>
					<div class="col-md-6 float-left">Date et heure : </div>
					<div class="col-md-6 float-right font-weight-bold">${sortie.getDateDebut()}</div>
					<div class="col-md-12">&nbsp</div>
					<div class="col-md-6 float-left">Nombre de places : </div>
					<div class="col-md-6 float-right font-weight-bold">${sortie.getNbInscriptionsMax()}</div>
					<div class="col-md-12">&nbsp</div>
					<div class="col-md-6 float-left">Durée : </div>
					<div class="col-md-6 float-right font-weight-bold">${sortie.getDuree()} minutes</div>
					<div class="col-md-12">&nbsp</div>
					<div class="col-md-6 float-left">Description et infos : </div>
					<div class="col-md-12 font-weight-bold overflow-auto" style="max-height: 300px !important">${sortie.getDescriptionInfos()}</div>
				</div>
				<div class="col-md-6 float-right bg-light rounded shadow p-3">
					<div class="col-md-6 float-left">Ville organisatrice : </div>
					<div class="col-md-6 float-right font-weight-bold">${sortie.getLieu().getVille().getNomVille() }</div>
					<div class="col-md-12">&nbsp</div>
					<div class="col-md-6 float-left">Lieu : </div>
					<div class="col-md-6 float-right font-weight-bold">${sortie.getLieu().getNomLieu()}</div>
					<div class="col-md-12">&nbsp</div>
					<div class="col-md-6 float-left">Adresse : </div>
					<div class="col-md-6 float-right font-weight-bold">${sortie.getLieu().getAdresse()}</div>
					<div class="col-md-12">&nbsp</div>
					<div class="col-md-6 float-left">Code postal : </div>
					<div class="col-md-6 float-right font-weight-bold">${sortie.getLieu().getVille().getCodePostal()}</div>
					<div class="col-md-12">&nbsp</div>
					<div class="col-md-6 float-left">Latitude : </div>
					<div class="col-md-6 float-right font-weight-bold">${sortie.getLieu().getLatitude()}</div>
					<div class="col-md-12">&nbsp</div>
					<div class="col-md-6 float-left">Longitude : </div>
					<div class="col-md-6 float-right font-weight-bold">${sortie.getLieu().getLongitude()}</div>
					<div class="col-md-12">&nbsp</div>
					<div class="col-md-6 float-left">Liste des participants inscrits : </div>
					<div class="col-md-12">
						<table class="table bg-light rounded shadow viewTable mt-4">
							<thead class="d-block theadData mx-auto">
								<tr class="trData">
									<th class="thData" scope="col">Pseudo</th>
									<th class="thData" scope="col">Nom</th>
								</tr>
							</thead>
							<tbody class="overflow-auto d-block tbodyData" style="height:200px !important">
								<c:forEach var="inscription" items="${sortie.getInscriptions()}">
								<tr class="trData">
									<td class="tdData"><a href="../profil/${inscription.getParticipant().getNoParticipant()}">${inscription.getParticipant().getPseudo()}</a></td>
									<td class="tdData">${inscription.getParticipant().getPrenom()} ${inscription.getParticipant().getNom()}</td>
								</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<!-- ####### </>CONTENT ######## -->

<!-- ####### FOOTER ######## -->
<%@include file="includes/footer.jsp"%>