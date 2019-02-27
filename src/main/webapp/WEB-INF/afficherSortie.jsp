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
			<h1 class="mx-auto text-center mt-4 mb-3">Détail sortie</h1>
				<div class="col-md-5 float-left bg-light rounded shadow p-3 mobile-mb-3">
					<div class="row mb-2">
						<div class="col-6 float-left font-weight-bold">Nom : </div>
						<div class="col-6 float-right">${sortie.getNom()}</div>
					</div>
					<div class="row mb-2">
						<div class="col-6 float-left font-weight-bold">Date et heure : </div>
						<div class="col-6 float-right">${sortie.getDateDebut()}</div>
					</div>
					<div class="row mb-2">
						<div class="col-6 float-left font-weight-bold">Nombre de places : </div>
						<div class="col-6 float-right mb-2">${sortie.getNbInscriptionsMax()}</div>
					</div>
					<div class="row mb-2">
						<div class="col-6 float-left font-weight-bold">Durée : </div>
						<div class="col-6 float-right mb-2">${sortie.getDuree()} minutes</div>
					</div>
					<div class="row mb-2">
						<div class="col-6 float-left font-weight-bold">Description et infos : </div>
						<div class="col-12 overflow-auto ml-2" style="max-height: 300px !important">${sortie.getDescriptionInfos()}</div>
					</div>
				</div>
				<div class="col-md-6 float-right bg-light rounded shadow p-3">
					<div class="row mb-2">
						<div class="col-6 float-left font-weight-bold">Ville organisatrice : </div>
						<div class="col-6 float-right mb-2">${sortie.getLieu().getVille().getNomVille() }</div>
					</div>
					<div class="row mb-2">
						<div class="col-6 float-left font-weight-bold">Lieu : </div>
						<div class="col-6 float-right mb-2">${sortie.getLieu().getNomLieu()}</div>
					</div>
					<div class="row mb-2">
						<div class="col-6 float-left font-weight-bold">Adresse : </div>
						<div class="col-6 float-right mb-2">${sortie.getLieu().getAdresse()}</div>
					</div>
					<div class="row mb-2">
						<div class="col-6 float-left font-weight-bold">Code postal : </div>
						<div class="col-6 float-right mb-2">${sortie.getLieu().getVille().getCodePostal()}</div>
					</div>
					<div class="row mb-2">
						<div class="col-6 float-left font-weight-bold">Latitude : </div>
						<div class="col-6 float-right mb-2">${sortie.getLieu().getLatitude()}</div>
					</div>
					<div class="row mb-2">
						<div class="col-6 float-left font-weight-bold">Longitude : </div>
						<div class="col-6 float-right mb-2">${sortie.getLieu().getLongitude()}</div>
					</div>
					<div class="row mb-2">
						<div class="col-12 float-left font-weight-bold">Liste des participants inscrits : </div>
					<div class="col-12">
						<table class="table bg-white viewTable mt-4">
							<thead class="d-block theadData mx-auto">
								<tr class="trData">
									<th class="thData" scope="col">Pseudo</th>
									<th class="thData" scope="col">Nom</th>
								</tr>
							</thead>
							<tbody class="overflow-auto d-block tbodyData" style="height:200px !important">
								<c:forEach var="inscription" items="${sortie.getInscriptions()}">
								<tr class="trData">
									<td class="tdData"><a href="<%=request.getContextPath()%>/logged/profil/${inscription.getParticipant().getNoParticipant()}">${inscription.getParticipant().getPseudo()}</a></td>
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