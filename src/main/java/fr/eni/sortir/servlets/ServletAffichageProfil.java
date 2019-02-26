package fr.eni.sortir.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.sortir.dao.DaoFactory;

/**
 * Servlet implementation class ServletAfficherProfil
 */
@WebServlet
(
	name="ServletAffichageProfil",
	urlPatterns= {"/logged/profil/*"}
)
public class ServletAffichageProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAffichageProfil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String numeroParticipant = request.getPathInfo().replace("/", "");
		String urlPrecedente = request.getHeader("referer");
		request.setAttribute("participant", DaoFactory.getParticipantDao().findParticipant(Integer.valueOf(numeroParticipant)));
		request.setAttribute("urlPrecedente", urlPrecedente);
		/*Participant participant = (Participant) request.getAttribute("participant");
		request.setAttribute("participant", participant);*/
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/afficherProfil.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}