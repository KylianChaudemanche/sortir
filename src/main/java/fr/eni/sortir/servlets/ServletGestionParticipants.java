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
 * Servlet implementation class ServletGestionParticipants
 */
@WebServlet(name = "ServletGestionParticipants", urlPatterns = { "/administration/gestionParticipants" })

public class ServletGestionParticipants extends ServletParent {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletGestionParticipants() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		request.setAttribute("listeParticipants", DaoFactory.getParticipantDao().getAllParticipant());
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/gestionParticipants.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("modifierParticipant")!= null){
			response.sendRedirect("/sortir/administration/gestionComptes/"+request.getParameter("modifierParticipant"));
		}else if(request.getParameter("supprimerParticipant")!= null){
			System.out.println(request.getParameter("supprimerParticipant"));
			DaoFactory.getParticipantDao().removeParticipant(Integer.valueOf(request.getParameter("supprimerParticipant")));
			doGet(request, response);
		}else if(request.getParameter("ajouterParticipant")!= null){
			response.sendRedirect("/sortir/administration/gestionComptes/nouveau");
		}
	}

}
