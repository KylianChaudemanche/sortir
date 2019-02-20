package fr.eni.sortir.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.entities.Sortie;

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
		request.getRequestDispatcher("createSortie.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		System.out.println(request.getParameter("sortieCity"));
		
		switch(request.getParameter("action")) {
			case "save":
				saveSortie(request);
				break;
			case "publish":
				break;
			default:
				response.sendError(HttpServletResponse.SC_FORBIDDEN);
		}
	}
	
	private boolean saveSortie(HttpServletRequest request) {
		Sortie sortie = new Sortie();
		
		return true;
	}
}
