package fr.eni.sortir.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.utils.Constantes;
import fr.eni.sortir.utils.SaltedMD5;

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet(name = "ServletLogin", urlPatterns = { "/login" })
public class ServletLogin extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7926787715554177925L;
	private final String LOGIN_INVALIDE = "Informations de connexion invalides";
	private final String COMPTE_NON_ACTIF  = "Votre compte a été désactivé";
	private final String COMPTE_INEXISTANT  = "Aucun compte n'éxiste pour cet identifiant";
	/**
	 * Default constructor.
	 */
	public ServletLogin() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("GET");
		HttpSession session = request.getSession();
		if ((session != null)) {
			if ((session.getAttribute("participant") != null)) {
				response.sendRedirect("/sortir/logged/accueil");
				return;
			}
		}	

		// Check cookies
		Cookie[] cookies = request.getCookies();

		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("identifiant")) {
					request.setAttribute("identifiant", cookie.getValue());
				}
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Participant participant = null;

		String identifiant = request.getParameter("identifiant").trim();
		String motDePasse = request.getParameter("motDePasse").trim();
		String mediaWidth = request.getParameter("media-width");
		Boolean isMobile = null;
		if(!"".equals(mediaWidth)) {
			isMobile = (Integer.parseInt(mediaWidth) <=425) ? true : false;
		}

		participant = DaoFactory.getParticipantDao().findParticipantByMail(identifiant);
		if (participant == null) {
			participant = DaoFactory.getParticipantDao().findParticipantByPseudo(identifiant);
			if (participant == null ) {
				// compte  inexistant
				request.setAttribute(Constantes.TYPE_MESSAGE, Constantes.MSG_TYPE_DANGER);
				request.setAttribute(Constantes.MESSAGE, COMPTE_INEXISTANT);
				doGet(request, response);
				return;
			}
		} 

		if (participant.getMotDePasse().equals(SaltedMD5.getSecurePassword(motDePasse))) {
			if(participant.getActif()) {

				if (request.getParameter("seSouvenir") != null) {
					Cookie cookie = new Cookie("identifiant", identifiant);
					cookie.setHttpOnly(true);
					cookie.setMaxAge(99999);
					response.addCookie(cookie);
				} else {
					Cookie cookie = new Cookie("identifiant", "");
					cookie.setHttpOnly(true);
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}

				// add participant in session
				session.setAttribute("participant", participant);
				// add isMobile
				session.setAttribute("isMobile", isMobile);
				// redirect
				response.sendRedirect("/sortir/logged/accueil");
			}else {
				// compte  non actif
				request.setAttribute(Constantes.TYPE_MESSAGE, Constantes.MSG_TYPE_DANGER);
				request.setAttribute(Constantes.MESSAGE, COMPTE_NON_ACTIF);
				doGet(request, response);
			}
		} else {
			// login failed
			request.setAttribute(Constantes.TYPE_MESSAGE, Constantes.MSG_TYPE_DANGER);
			request.setAttribute(Constantes.TYPE_MESSAGE, LOGIN_INVALIDE);
			doGet(request, response);
		}
	}
}
