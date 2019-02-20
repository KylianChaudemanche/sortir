package fr.eni.sortir.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.entities.Lieu;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Sortie;
import fr.eni.sortir.entities.Ville;
import fr.eni.sortir.utils.State;

/**
 * Servlet implementation class ServletCreateSortie
 */
@WebServlet(name = "ServletCreateSortie", urlPatterns = { "/createSortie" })
public class ServletCreateSortie extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletCreateSortie() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setAttribute("cities", DaoFactory.getVilleDao().getAllVille());
		request.setAttribute("places", DaoFactory.getLieuDao().getAllLieu());
		request.getRequestDispatcher("createSortie.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("sortieCity").equals("") 
				|| request.getParameter("sortiePlace").equals("")
				|| request.getParameter("sortieName").equals("")
				|| request.getParameter("sortieBeginDate").equals("")
				|| request.getParameter("sortieCloseInscriptionDate").equals("")
				|| request.getParameter("sortieNbMaxPlace").equals("")
				|| request.getParameter("sortieDuration").equals("")
				|| request.getParameter("sortieDesc").equals("")
				) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
		try {
			Integer.parseInt(request.getParameter("sortieCity"));
			Integer.parseInt(request.getParameter("sortiePlace"));
			Integer.parseInt(request.getParameter("sortieNbMaxPlace"));
			Integer.parseInt(request.getParameter("sortieDuration"));
		} catch(NumberFormatException nfe) {
			nfe.printStackTrace();
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
		Ville ville = DaoFactory.getVilleDao().findVille(Integer.valueOf(request.getParameter("sortieCity")));
		Lieu lieu = DaoFactory.getLieuDao().findLieu(Integer.valueOf(request.getParameter("sortiePlace")));
		if (ville == null || lieu == null) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-ddThh:mm:ss");
		SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd");
		Date dateBegin = null;
		Date dateCloseInscription = null;
		try {
			dateBegin = sdfDateTime.parse(request.getParameter("sortieBeginDate"));
			dateCloseInscription = sdfDate.parse(request.getParameter("sortieCloseInscriptionDate"));
		} catch (ParseException e) {
			e.printStackTrace();
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
		if (dateBegin.compareTo(dateCloseInscription) > 0) {
			response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
		//TODO replace with the value of user connected
		Participant organisateur = DaoFactory.getParticipantDao().findParticipant(2);
		
		Sortie sortie = new Sortie(
				request.getParameter("sortieName"),
				dateBegin,
				Integer.valueOf(request.getParameter("sortieDuration")),
				dateCloseInscription,
				Integer.valueOf(request.getParameter("sortieNbMaxPlace")),
				request.getParameter("sortieDesc"),
				null,
				null,
				lieu,
				new ArrayList<>(),
				organisateur);
		
		switch(request.getParameter("action")) {
			case "save":
				sortie = saveSortie(sortie, State.CREATED);
				break;
			case "publish":
				sortie = saveSortie(sortie, State.OPENED);
				break;
			default:
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
		
		//TODO replace with correct page redirection
		if (sortie != null) {
			
		} else {
			
		}
	}
	
	private Sortie saveSortie(Sortie sortie, State state) {
		sortie.setEtat(DaoFactory.getEtatDao().findEtatByName(state.toString()));	
		return DaoFactory.getSortieDao().addSortie(sortie);
	}
}
