package fr.eni.sortir.servlets;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

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
@WebServlet(name = "ServletUpdateSortie", urlPatterns = { "/logged/updateSortie", "/logged/updateSortie/*" })
public class ServletUpdateSortie extends HttpServlet {
    private static final Logger LOGGER = Logger.getLogger(ServletUpdateSortie.class.getName());

    /**
     * 
     */
    private static final long serialVersionUID = -6076039526658978701L;

    private static final String CITIES = "cities";
    private static final String PLACES = "places";
    private static final String SORTIE = "sortie";
    private static final String ACTION = "action";
    private static final String SAVE = "save";
    private static final String PUBLISH = "publish";
    private static final String DELETE = "delete";
    private static final int CITY = 0;
    private static final int PLACE = 1;
    private static final int MAX_SUBSCRIPTION = 2;
    private static final int DURATION = 3;
    private static final int NO_SORTIE = 4;
    private static final String JSP_UPDATE_SORTIE = "/WEB-INF/updateSortie.jsp";
    private static final String PARAM_NO_SORTIE = "noSortie";
    private static final String SORTIE_NO_SORTIE = "sortieNoSortie";
    private static final String SORTIE_CITY = "sortieCity";
    private static final String SORTIE_PLACE = "sortiePlace";
    private static final String SORTIE_NAME = "sortieName";
    private static final String SORTIE_BEGIN_DATE = "sortieBeginDate";
    private static final String SORTIE_CLOSE_INSCRIPTION_DATE = "sortieCloseInscriptionDate";
    private static final String SORTIE_MAX_SUBSCRIPTION = "sortieNbMaxPlace";
    private static final String SORTIE_DURATION = "sortieDuration";
    private static final String SORTIE_DESCRIPTION = "sortieDesc";
    private static final String FORMAT_DATE = "yyyy-MM-dd";
    private static final String FORMAT_DATETIME = "yyyy-MM-dd'T'hh:mm";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletUpdateSortie() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	Integer noSortie = null;
	try {
	    noSortie = Integer.parseInt(request.getParameter(PARAM_NO_SORTIE));
	} catch(NumberFormatException nfe) {
	    LOGGER.log(Level.SEVERE, nfe.getMessage(), nfe);
	}
	if (noSortie == null) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}
	Sortie sortie = DaoFactory.getSortieDao().findSortie(noSortie);
	if (sortie == null) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}
	request.setAttribute(SORTIE, sortie);
	request.setAttribute(CITIES, DaoFactory.getVilleDao().getAllVille());
	request.setAttribute(PLACES, DaoFactory.getLieuDao().getAllLieu());
	request.getRequestDispatcher(JSP_UPDATE_SORTIE).forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	int[] parameters = checkParameter(request);
	if (parameters == null) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
	
	Ville ville = DaoFactory.getVilleDao().findVille(parameters[CITY]);
	Lieu lieu = DaoFactory.getLieuDao().findLieu(parameters[PLACE]);
	if (ville == null || lieu == null) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}

	SimpleDateFormat sdfDateTime = new SimpleDateFormat(FORMAT_DATETIME);
	SimpleDateFormat sdfDate = new SimpleDateFormat(FORMAT_DATE);
	Date dateBegin = null;
	Date dateCloseInscription = null;
	try {
	    dateBegin = sdfDateTime.parse(request.getParameter(SORTIE_BEGIN_DATE));
	    dateCloseInscription = sdfDate.parse(request.getParameter(SORTIE_CLOSE_INSCRIPTION_DATE));
	} catch (ParseException e) {
	    e.printStackTrace();
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}
	if (dateBegin.compareTo(dateCloseInscription) < 0) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}

	// TODO replace with the value of user connected
	Participant organisateur = DaoFactory.getParticipantDao().findParticipant(2);
	Sortie sortie = new Sortie(request.getParameter(SORTIE_NAME), dateBegin, parameters[DURATION],
		dateCloseInscription, parameters[MAX_SUBSCRIPTION], request.getParameter(SORTIE_DESCRIPTION), null,
		null, lieu, new ArrayList<>(), organisateur);
	
	if(DaoFactory.getSortieDao().findSortie(parameters[NO_SORTIE]) == null) {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}
	
	sortie.setNoSortie(parameters[NO_SORTIE]);
	
	switch (request.getParameter(ACTION)) {
	case SAVE:
	    sortie = updateSortie(sortie, State.CREATED);
	    break;
	case PUBLISH:
	    sortie = updateSortie(sortie, State.OPENED);
	    break;
	case DELETE:
	    if(!deleteSortie(sortie.getNoSortie())) {
		sortie = null;
	    }
	    break;
	default:
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	    return;
	}
	// TODO replace with correct page redirection
	if (sortie != null) {
	    response.sendRedirect("/sortir/updateSortie?noSortie=" + String.valueOf(parameters[NO_SORTIE]));
	} else {
	    response.sendError(HttpServletResponse.SC_FORBIDDEN);
	}
    }

    private int[] checkParameter(HttpServletRequest request) {
	for (String name : request.getParameterMap().keySet()) {
	    if ("".equals(request.getParameter(name))) {
		return null;
	    }
	}
	int[] parameters = new int[5];
	System.out.println(request.getParameter(SORTIE_NO_SORTIE));
	try {
	    parameters[CITY] = Integer.parseInt(request.getParameter(SORTIE_CITY));
	    parameters[PLACE] = Integer.parseInt(request.getParameter(SORTIE_PLACE));
	    parameters[MAX_SUBSCRIPTION] = Integer.parseInt(request.getParameter(SORTIE_MAX_SUBSCRIPTION));
	    parameters[DURATION] = Integer.parseInt(request.getParameter(SORTIE_DURATION));
	    parameters[NO_SORTIE] = Integer.parseInt(request.getParameter(SORTIE_NO_SORTIE));
	} catch (NumberFormatException nfe) {
	    nfe.printStackTrace();
	    return null;
	}
	return parameters;
    }
    
    private boolean deleteSortie(int noSortie) {
	return DaoFactory.getSortieDao().removeSortie(noSortie);
    }

    private Sortie updateSortie(Sortie sortie, State state) {
	sortie.setEtat(DaoFactory.getEtatDao().findEtatByName(state.toString()));
	return DaoFactory.getSortieDao().updateSortie(sortie);
    }
}
