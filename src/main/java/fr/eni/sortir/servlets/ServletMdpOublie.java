package fr.eni.sortir.servlets;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Token;

/**
 * Servlet implementation class ServletMdpOublie
 */
@WebServlet(name = "ServletMdpOublie", urlPatterns = { "/mdpOublie" })
public class ServletMdpOublie extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static final long ONE_MINUTE_IN_MILLIS=60000;//millisecs

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletMdpOublie() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/demandeReinitialisationMdp.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String mail = request.getParameter("mail").trim();
		Participant participant = DaoFactory.getParticipantDao().findParticipantByMail(mail);
		Token token = null;
		Calendar date = Calendar.getInstance();
		long time= date.getTimeInMillis();
		Date expirationDate=new Date(time + (20 * ONE_MINUTE_IN_MILLIS));
		
		if(participant != null) {
			String uuid = UUID.randomUUID().toString().replace("-", "").substring(0, 16);
			token = new Token(mail, uuid, expirationDate);
			DaoFactory.getTokenDao().addToken(token);
			request.setAttribute("noParticipant", participant.getNoParticipant());
			request.setAttribute("token", token);
		}else {
			request.setAttribute("token", token);
			request.setAttribute("noParticipant", 0);
		}
		
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/demandeReinitialisationMdp.jsp");
		rd.forward(request, response);

	}
}
