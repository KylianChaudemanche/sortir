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
@WebServlet(name = "ServletCreateSortie", urlPatterns = { "/logged/createSortie" })

public class ServletCreateSortie extends ServletParent {

    /**
     * 
     */
    private static final long serialVersionUID = -6076039526658978701L;

    private static final String CITIES = "cities";
    private static final String PLACES = "places";
    private static final String ACTION = "action";
    private static final String SAVE = "save";
    private static final String PUBLISH = "publish";
    private static final int CITY = 0;
    private static final int PLACE = 1;
    private static final int MAX_SUBSCRIPTION = 2;
    private static final int DURATION = 3;
    private static final String JSP_CREATE_SORTIE = "/WEB-INF/createSortie.jsp";
    private static final String URL_SHOW_SORTIE = "/sortir/sortie/";
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
    public ServletCreateSortie() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	super.doGet(request, response);
	request.setAttribute(CITIES, DaoFactory.getVilleDao().getAllVille());
	request.setAttribute(PLACES, DaoFactory.getLieuDao().getAllLieu());
	request.getRequestDispatcher(JSP_CREATE_SORTIE).forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	int[] parameters = checkParameter(request);
	if (parameters == null) {
	    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
	    return;
	}

	Ville ville = DaoFactory.getVilleDao().findVille(parameters[CITY]);
	Lieu lieu = DaoFactory.getLieuDao().findLieu(parameters[PLACE]);
	if (ville == null || lieu == null) {
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
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
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    return;
	}
	if (dateBegin.compareTo(dateCloseInscription) < 0) {
	    response.sendError(HttpServletResponse.SC_BAD_REQUEST);
	    return;
	}

	Participant organisateur = (Participant) request.getSession().getAttribute("participant");
	
	if (organisateur == null) {
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    return;
	}
	
	Sortie sortie = new Sortie(request.getParameter(SORTIE_NAME), dateBegin, parameters[DURATION],
		dateCloseInscription, parameters[MAX_SUBSCRIPTION], request.getParameter(SORTIE_DESCRIPTION), null,
		null, lieu, new ArrayList<>(), organisateur);
	switch (request.getParameter(ACTION)) {
	case SAVE:
	    sortie = saveSortie(sortie, State.CREATED);
	    break;
	case PUBLISH:
	    sortie = saveSortie(sortie, State.OPENED);
	    break;
	default:
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	    return;
	}
	
	if (sortie != null) {
	    response.sendRedirect(URL_SHOW_SORTIE + sortie.getNoSortie());
	} else {
	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
    }

    private int[] checkParameter(HttpServletRequest request) {
		for (String name : request.getParameterMap().keySet()) {
		    if ("".equals(request.getParameter(name))) {
			return null;
		    }
		}
		int[] parameters = new int[4];
		try {
		    parameters[CITY] = Integer.parseInt(request.getParameter(SORTIE_CITY));
		    parameters[PLACE] = Integer.parseInt(request.getParameter(SORTIE_PLACE));
		    parameters[MAX_SUBSCRIPTION] = Integer.parseInt(request.getParameter(SORTIE_MAX_SUBSCRIPTION));
		    parameters[DURATION] = Integer.parseInt(request.getParameter(SORTIE_DURATION));
		} catch (NumberFormatException nfe) {
		    nfe.printStackTrace();
		    return null;
		}
		return parameters;
    }

    private Sortie saveSortie(Sortie sortie, State state) {
	sortie.setEtat(DaoFactory.getEtatDao().findEtatByName(state.toString()));
	return DaoFactory.getSortieDao().addSortie(sortie);
    }
}
