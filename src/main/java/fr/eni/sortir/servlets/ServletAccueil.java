package fr.eni.sortir.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Site;
import fr.eni.sortir.entities.Sortie;

/**
 * Servlet implementation class ServletAccueil
 */
@WebServlet
(
		name="ServletAccueil",
		urlPatterns= {"/accueil"}
		)
public class ServletAccueil extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletAccueil() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		if( (session != null) && (session.getAttribute("participant") != null) ){
			System.out.println("session participant: "+session.getAttribute("participant"));
			Participant participant = (Participant) session.getAttribute("participant");
			System.out.println(participant.getNom());
		}else {
			System.out.println("not logged in");
		}


		//TODO : remove test below
		Participant participant = DaoFactory.getParticipantDao().findParticipant(7);

		Collection<Sortie> listeSorties = (Collection<Sortie>) DaoFactory.getSortieDao().getAllSortie();
		request.setAttribute("listeSorties", listeSorties);

		Collection<Site> listeSites = (Collection<Site>) DaoFactory.getSiteDao().getAllSite();
		request.setAttribute("listeSites", listeSites);

		request.setAttribute("siteSelected", participant.getSite().getNoSite());

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		//Participant participant = (Participant) session.getAttribute("participant");
		//TODO : remove test below
		Participant participant = DaoFactory.getParticipantDao().findParticipant(7);
		// TODO : place in constant
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		Collection<Sortie> listeSorties = null;

		// Récupération des champs du formulaire
		int siteId = Integer.parseInt(request.getParameter("site"));
		Site site = DaoFactory.getSiteDao().findSite(siteId);

		// TODO : vérifier que boolPassee = true ET avoir un date définie après
		Boolean sortieOrganisateurBool = ( (request.getParameter("sortieOrganisateur")!=null)? true : false);
		Boolean sortieInscritBool = ( (request.getParameter("sortieInscrit")!=null)? true : false);
		Boolean sortiePasInscritBool = ( (request.getParameter("sortiePasInscrit")!=null)? true : false);
		Boolean sortiePasseeBool = ( (request.getParameter("sortiePassee")!=null)? true : false);
		Boolean sortieDebutBool = ( (!"".equals(request.getParameter("dateDebut")))? true : false);
		Boolean sortieFinBool = ( (!"".equals(request.getParameter("dateFin")))? true : false);

		Date dateDebut = null;
		try {
			dateDebut = sortieDebutBool ? new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateDebut")) : null;
		} catch (ParseException e) {
			System.out.println("failed to parse dateDebut");
			e.printStackTrace();
		}
		Date dateFin = null;
		try {
			dateFin = sortieFinBool ? new SimpleDateFormat("yyyy-MM-dd").parse(request.getParameter("dateFin")) : null;
		} catch (ParseException e) {
			System.out.println("failed to parse dateFin");
			e.printStackTrace();
		}
		
		listeSorties = (Collection<Sortie>) DaoFactory.getSortieDao().getAllSortieFiltre(site, sortieOrganisateurBool, 
				sortieInscritBool, sortiePasInscritBool, 
				sortiePasseeBool, dateDebut, dateFin, 
				participant);

		Collection<Site> listeSites = (Collection<Site>) DaoFactory.getSiteDao().getAllSite();
		
		// sending back filters
		ArrayList<Boolean> listeCheckbox = new ArrayList<Boolean>();
		listeCheckbox.add(sortieOrganisateurBool);
		listeCheckbox.add(sortieInscritBool);
		listeCheckbox.add(sortiePasInscritBool);
		listeCheckbox.add(sortiePasseeBool);
		
		request.setAttribute("listeSites", listeSites);
		request.setAttribute("listeSorties", listeSorties);
		request.setAttribute("siteSelected", site.getNoSite());
		request.setAttribute("listeCheckbox", listeCheckbox);
		if(sortieDebutBool) {
			request.setAttribute("dateDebut", dateFormat.format(dateDebut));
		}
		if(sortieFinBool) {
			request.setAttribute("dateFin", dateFormat.format(dateFin));
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/accueil.jsp");
		rd.forward(request, response);
	}

}
