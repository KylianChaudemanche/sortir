<nav class="navbar navbar-expand-lg navbar-dark bg-dark text-white">
  <a class="navbar-brand" href="#">Sorties ENI</a>
  <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNav" aria-controls="navbarNav" aria-expanded="false" aria-label="Toggle navigation">
    <span class="navbar-toggler-icon"></span>
  </button>
  <div class="collapse navbar-collapse" id="navbarNav">
    
    <div class="text-right ml-auto">
    <ul class="navbar-nav">
	    <li class="nav-item">
	    	<a class="nav-link" href="<%=request.getContextPath()%>/gestionSites">Sites</a>
	    </li>
	    <li class="nav-item">
	    	<a class="nav-link" href="<%=request.getContextPath()%>/accueil">Accueil</a>
	    </li>
	    <li class="nav-item">
	    	<a class="nav-link" href="<%=request.getContextPath()%>/gestionCompte">Mon Profil</a>
	    </li>
	    <li class="nav-item">
	    	<a class="btn btn-danger" href="<%=request.getContextPath()%>/logout">D�connexion</a>
	    </li>
    </ul>
    	
    </div>
  </div>
</nav>