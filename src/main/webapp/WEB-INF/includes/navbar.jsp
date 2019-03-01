<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<nav class="navbar navbar-expand-lg navbar-dark bg-dark text-white">
  <a class="navbar-brand" href="<%=request.getContextPath()%>/logged/accueil">Sorties ENI</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNav">
    
    <div class="text-right ml-auto">
    <ul class="navbar-nav">
        <c:if test="${ utilisateur.getAdministrateur() && !isMobile }">
	      <li class="nav-item">
	    	  <a class="nav-link" href="<%=request.getContextPath()%>/administration/gestionSites">Sites</a>
	      </li>
	      <li class="nav-item">
	    	  <a class="nav-link" href="<%=request.getContextPath()%>/administration/gestionParticipants">Participants</a>
	      </li>
	    </c:if>
	    <li class="nav-item">
	    	<a class="nav-link" href="<%=request.getContextPath()%>/logged/accueil">Accueil</a>
	    </li>
        <c:if test="${ !isMobile }">
	      <li class="nav-item">
	    	  <a class="nav-link" href="<%=request.getContextPath()%>/logged/gestionCompte">Mon Profil</a>
	      </li>
	    </c:if>
	    <li class="nav-item">
	    	<a class="btn btn-danger" href="<%=request.getContextPath()%>/logout">Déconnexion</a>
	    </li>
    </ul>
    	
    </div>
  </div>
</nav>