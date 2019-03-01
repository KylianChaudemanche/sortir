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
import fr.eni.sortir.messages.MsgReader;
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
	private static final String JSP_CREATE_SORTIE = "/WEB-INF/createSortie.jsp";
	private static final String URL_SHOW_SORTIE = "/sortir/logged/sortie/";
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
	private static final String ERROR = "error";

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
		String error = checkParameter(request);
		if (error != null) {
			request.setAttribute(ERROR, error);
			request.setAttribute(SORTIE_NAME, request.getParameter(SORTIE_NAME));
			request.setAttribute(SORTIE_BEGIN_DATE, request.getParameter(SORTIE_BEGIN_DATE));
			request.setAttribute(SORTIE_CLOSE_INSCRIPTION_DATE, request.getParameter(SORTIE_CLOSE_INSCRIPTION_DATE));
			request.setAttribute(SORTIE_MAX_SUBSCRIPTION, request.getParameter(SORTIE_MAX_SUBSCRIPTION));
			request.setAttribute(SORTIE_DURATION, request.getParameter(SORTIE_DURATION));
			request.setAttribute(SORTIE_DESCRIPTION, request.getParameter(SORTIE_DESCRIPTION));
			request.setAttribute(SORTIE_PLACE, request.getParameter(SORTIE_PLACE));
			request.setAttribute(SORTIE_CITY, request.getParameter(SORTIE_CITY));
			doGet(request, response);
			return;
		}

		Lieu lieu = DaoFactory.getLieuDao().findLieu(Integer.valueOf(request.getParameter(SORTIE_PLACE)));
		Participant organisateur = (Participant) request.getSession().getAttribute("participant");
		SimpleDateFormat sdfDateTime = new SimpleDateFormat(FORMAT_DATETIME);
		SimpleDateFormat sdfDate = new SimpleDateFormat(FORMAT_DATE);
		Date dateBegin = null;
		Date dateCloseInscription = null;
		try {
			dateBegin = sdfDateTime.parse(request.getParameter(SORTIE_BEGIN_DATE));
			dateCloseInscription = sdfDate.parse(request.getParameter(SORTIE_CLOSE_INSCRIPTION_DATE));
		} catch (ParseException e) {
			e.printStackTrace();
		}

		Sortie sortie = new Sortie(request.getParameter(SORTIE_NAME), dateBegin,
				Integer.valueOf(request.getParameter(SORTIE_DURATION)), dateCloseInscription,
				Integer.valueOf(request.getParameter(SORTIE_MAX_SUBSCRIPTION)),
				request.getParameter(SORTIE_DESCRIPTION), null, null, lieu, new ArrayList<>(), organisateur);
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

	private String checkParameter(HttpServletRequest request) {
		String error = null;
		error = checkSortieName(request.getParameter(SORTIE_NAME));
		if (error != null) {
			return error;
		}
		error = checkBeginDate(request.getParameter(SORTIE_BEGIN_DATE));
		if (error != null) {
			return error;
		}
		error = checkCloseInscriptionDate(request.getParameter(SORTIE_CLOSE_INSCRIPTION_DATE));
		if (error != null) {
			return error;
		}
		error = checkBeginIsAfterCloseInscription(request.getParameter(SORTIE_BEGIN_DATE),
				request.getParameter(SORTIE_CLOSE_INSCRIPTION_DATE));
		if (error != null) {
			return error;
		}
		error = checkMaxSubscription(request.getParameter(SORTIE_DURATION));
		if (error != null) {
			return error;
		}
		error = checkDuration(request.getParameter(SORTIE_MAX_SUBSCRIPTION));
		if (error != null) {
			return error;
		}
		error = checkDescription(request.getParameter(SORTIE_DESCRIPTION));
		if (error != null) {
			return error;
		}
		error = checkCity(request.getParameter(SORTIE_CITY));
		if (error != null) {
			return error;
		}
		error = checkLieu(request.getParameter(SORTIE_PLACE));
		if (error != null) {
			return error;
		}
		if (request.getSession().getAttribute("participant") == null) {
			return MsgReader.getMessage("create.user.not_connected");
		}
		return error;
	}

	private String checkCity(String strNoCity) {
		if (strNoCity != null) {
			Integer noSortie = null;
			try {
				noSortie = Integer.valueOf(strNoCity);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			if (noSortie == null) {
				return MsgReader.getMessage("create.city.nan");
			}
			Ville ville = DaoFactory.getVilleDao().findVille(noSortie);
			if (ville == null) {
				return MsgReader.getMessage("create.city.not_found");
			}
		} else {
			return MsgReader.getMessage("create.city.is_empty");
		}
		return null;
	}

	private String checkLieu(String strNoLieu) {
		if (strNoLieu != null) {
			Integer noLieu = null;
			try {
				noLieu = Integer.valueOf(strNoLieu);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			if (noLieu == null) {
				return MsgReader.getMessage("create.place.nan");
			}
			Lieu lieu = DaoFactory.getLieuDao().findLieu(noLieu);
			if (lieu == null) {
				return MsgReader.getMessage("create.place.not_found");
			}
		} else {
			return MsgReader.getMessage("create.place.is_empty");
		}
		return null;
	}

	private String checkDescription(String desc) {
		if (desc != null) {
			if (desc.length() > 500) {
				return MsgReader.getMessage("create.desc.max_length");
			}
		} else {
			return MsgReader.getMessage("create.desc.is_empty");
		}
		return null;
	}

	private String checkMaxSubscription(String strN) {
		if (strN != null) {
			Integer n = null;
			try {
				n = Integer.valueOf(strN);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			if (n == null) {
				return MsgReader.getMessage("create.max_subscription.nan");
			}
			if (n <= 0) {
				return MsgReader.getMessage("create.max_subscription.bad_value");
			}
		} else {
			return MsgReader.getMessage("create.max_subscription.is_empty");
		}
		return null;
	}

	private String checkDuration(String strN) {
		if (strN != null) {
			Integer n = null;
			try {
				n = Integer.valueOf(strN);
			} catch (NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			if (n == null) {
				return MsgReader.getMessage("create.duration.nan");
			}
			if (n <= 0) {
				return MsgReader.getMessage("create.duration.bad_value");
			}
		} else {
			return MsgReader.getMessage("create.duration.is_empty");
		}
		return null;
	}

	private String checkSortieName(String name) {
		if (name != null) {
			if (name.length() > 30) {
				return MsgReader.getMessage("create.name.max_length");
			}
		} else {
			return MsgReader.getMessage("create.name.is_empty");
		}
		return null;
	}

	private String checkBeginIsAfterCloseInscription(String strBeginDate, String strCloseInscriptionDate) {
		SimpleDateFormat sdfDateTime = new SimpleDateFormat(FORMAT_DATETIME);
		SimpleDateFormat sdfDate = new SimpleDateFormat(FORMAT_DATE);
		Date beginDate = null;
		Date closeInscriptionDate = null;
		try {
			beginDate = sdfDateTime.parse(strBeginDate);
			closeInscriptionDate = sdfDate.parse(strCloseInscriptionDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (beginDate.before(closeInscriptionDate)) {
			return MsgReader.getMessage("create.subscription.over_begin_date");
		}
		return null;
	}

	private String checkBeginDate(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATETIME);
		if (strDate == null) {
			return MsgReader.getMessage("create.begin_date.is_empty");
		}
		Date today = new Date();
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			if (date.before(today)) {
				return MsgReader.getMessage("create.begin_date.in_past");
			}
		} else {
			return MsgReader.getMessage("create.begin_date.bad_format");
		}
		return null;
	}

	private String checkCloseInscriptionDate(String strDate) {
		SimpleDateFormat sdf = new SimpleDateFormat(FORMAT_DATE);
		if (strDate == null) {
			return MsgReader.getMessage("create.close_inscription_date.is_empty");
		}
		Date today = new Date();
		Date date = null;
		try {
			date = sdf.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date != null) {
			if (date.before(today)) {
				return MsgReader.getMessage("create.close_inscription_date.in_past");
			}
		} else {
			return MsgReader.getMessage("create.close_inscription_date.bad_format");
		}
		return null;
	}

	private Sortie saveSortie(Sortie sortie, State state) {
		sortie.setEtat(DaoFactory.getEtatDao().findEtatByName(state.toString()));
		return DaoFactory.getSortieDao().addSortie(sortie);
	}
}
