package fr.eni.sortir.servlets;

import java.io.IOException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Token;
import fr.eni.sortir.utils.Constantes;
import fr.eni.sortir.utils.SaltedMD5;

/**
 * Servlet implementation class ServletReinitialisationMdp
 */
@WebServlet(name = "ServletReinitialisationMdp", urlPatterns = { "/reinitialisationMdp" })
public class ServletReinitialisationMdp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String REGEXP_MOT_DE_PASSE = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[a-z])[0-9A-Za-z]{8,50}$";
	private static Pattern pattern;
	private static Matcher matcher;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletReinitialisationMdp(){
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String tokenClient = request.getParameter("token");
		request.setAttribute("token", tokenClient);
		int noParticipant = ( (!"".equals(request.getParameter("id"))) ? Integer.valueOf(request.getParameter("id")):null);
		Participant participant = DaoFactory.getParticipantDao().findParticipant(noParticipant);
		if(participant == null) {
			request.setAttribute(Constantes.TYPE_MESSAGE, Constantes.MSG_TYPE_DANGER);
			request.setAttribute(Constantes.MESSAGE, Constantes.MSG_SESSION_INEXISTANTE);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ServletMdpOublie");
			rd.forward(request, response);
			return;
		}
		Token token = DaoFactory.getTokenDao().findTokenByMailAndToken(participant.getMail(), tokenClient);
		Date now = new Date();

		if(token != null) {
			if( tokenClient.equals(token.getToken()) ) {
				if(now.compareTo(token.getDateExpiration()) < 0 ) {
					request.setAttribute("participant", participant);
					request.setAttribute("token", tokenClient);
					RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/reinitialisationMdp.jsp");
					rd.forward(request, response);
					return;
				}else {
					request.setAttribute(Constantes.TYPE_MESSAGE, Constantes.MSG_TYPE_DANGER);
					request.setAttribute(Constantes.MESSAGE, Constantes.MSG_SESSION_EXPIRE);
				}
			}else {
				request.setAttribute(Constantes.TYPE_MESSAGE, Constantes.MSG_TYPE_DANGER);
				request.setAttribute(Constantes.MESSAGE, Constantes.MSG_SESSION_INVALIDE);
			}
		}else {
			request.setAttribute(Constantes.TYPE_MESSAGE, Constantes.MSG_TYPE_DANGER);
			request.setAttribute(Constantes.MESSAGE, Constantes.MSG_SESSION_INEXISTANTE);
		}

		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ServletMdpOublie");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String motDePasse = request.getParameter("motDePasse").trim();
		String confirmerMotDePasse = request.getParameter("confirmerMotDePasse").trim();
		int noParticipant = ( !"".equals(request.getParameter("noParticipant") ) ? (Integer.valueOf((String) request.getParameter("noParticipant"))):null); 
		Participant participant = DaoFactory.getParticipantDao().findParticipant(noParticipant);
		if(participant == null) {
			request.setAttribute(Constantes.TYPE_MESSAGE, Constantes.MSG_TYPE_DANGER);
			request.setAttribute(Constantes.MESSAGE, Constantes.MSG_SESSION_INEXISTANTE);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ServletMdpOublie");
			rd.forward(request, response);
			return;
		}
		String tokenClient = request.getParameter("token");
		Token token = DaoFactory.getTokenDao().findTokenByMailAndToken(participant.getMail(), tokenClient);
		Date now = new Date();
		Boolean validateForm = true;
		Boolean validateToken = true;

		if(token != null) {
			if( tokenClient.equals(token.getToken()) ) {
				if(now.compareTo(token.getDateExpiration()) < 0 ) {
					validateToken = true;
				}else {
					request.setAttribute(Constantes.TYPE_MESSAGE, Constantes.MSG_TYPE_DANGER);
					request.setAttribute(Constantes.MESSAGE, Constantes.MSG_SESSION_EXPIRE);
					validateToken = false;
				}
			}else {
				request.setAttribute(Constantes.TYPE_MESSAGE, Constantes.MSG_TYPE_DANGER);
				request.setAttribute(Constantes.MESSAGE, Constantes.MSG_SESSION_INVALIDE);
				validateToken = false;
			}
		}else {
			request.setAttribute(Constantes.TYPE_MESSAGE, Constantes.MSG_TYPE_DANGER);
			request.setAttribute(Constantes.MESSAGE, Constantes.MSG_SESSION_INEXISTANTE);
			validateToken = false;
		}

		if(!validateToken) {
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/ServletMdpOublie");
			rd.forward(request, response);
			return;
		}

		// Validation formulaire
		if( !motDePasse.equals(confirmerMotDePasse) ){
			request.setAttribute(Constantes.TYPE_MESSAGE, Constantes.MSG_TYPE_DANGER);
			request.setAttribute(Constantes.MESSAGE, Constantes.MSG_DEUX_MDP_IDENTIQUES);
			validateForm = false;
		}

		pattern = Pattern.compile(REGEXP_MOT_DE_PASSE);
		matcher = pattern.matcher(motDePasse);
		if(!matcher.matches()) {
			request.setAttribute(Constantes.TYPE_MESSAGE, Constantes.MSG_TYPE_DANGER);
			request.setAttribute(Constantes.MESSAGE, Constantes.MSG_MDP_INVALIDE);
			validateForm = false;
		}
		
		if(validateForm) {
			// Encoder MDP
			String securePassword = SaltedMD5.getSecurePassword(motDePasse);
			participant.setMotDePasse(securePassword);
			// Update participant
			DaoFactory.getParticipantDao().updateParticipant(participant);
			// Retour au login + message succès
			request.setAttribute(Constantes.TYPE_MESSAGE, Constantes.MSG_TYPE_SUCCESS);
			request.setAttribute(Constantes.MESSAGE, Constantes.MSG_MDP_REINITIALISE);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/mdpReinitialise.jsp");
			rd.forward(request, response);
			return;
		}else {
			request.setAttribute("participant", participant);
			request.setAttribute("token", tokenClient);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/reinitialisationMdp.jsp");
			rd.forward(request, response);
		}

	}

}
