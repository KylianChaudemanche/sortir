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

/**
 * Servlet implementation class ServletLogin
 */
@WebServlet
(
		name="ServletLogin",
		urlPatterns= {"/login"}
		)
public class ServletLogin extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor. 
	 */
	public ServletLogin() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		if( (session != null)) 
		{
			if((session.getAttribute("participant") != null)) {
				response.sendRedirect("/sortir/accueil");
				return;
			}
		}

		// Check cookies
		Cookie[] cookies = request.getCookies();

		if(cookies!=null){
			for(Cookie cookie:cookies)
			{
				if(cookie.getName().equals("mail")) {
					request.setAttribute("mail", cookie.getValue());
				}
			}
		}

		RequestDispatcher rd = request.getRequestDispatcher("/login.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		String mail = request.getParameter("mail").trim();
		String motDePasse = request.getParameter("motDePasse").trim();

		Participant participant = DaoFactory.getParticipantDao().findParticipantByMail(mail);
		if(participant == null) {
			// TODO : add msg
		}else {
			if(participant.getMotDePasse().equals(motDePasse)) {
				if(request.getParameter("seSouvenir") != null) {
					Cookie cookie = new Cookie("mail", mail);
					cookie.setMaxAge(99999);
					response.addCookie(cookie);
				}else {
					Cookie cookie = new Cookie("mail", "");
					cookie.setMaxAge(0);
					response.addCookie(cookie);
				}
				// redirect
				session.setAttribute("participant", participant);
				response.sendRedirect("/sortir/login");
			}else {
				// login failed
				response.sendRedirect("/sortir/login");
			}
		}
	}

}
