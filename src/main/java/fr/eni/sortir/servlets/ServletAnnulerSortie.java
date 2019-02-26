package fr.eni.sortir.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.entities.Sortie;

/**
 * Servlet implementation class ServletAnnulerSortie
 */
@WebServlet(name = "ServletAnnulerSortie", urlPatterns = { "/annulerSortie/*" })
public class ServletAnnulerSortie extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAnnulerSortie() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			Sortie sortie = DaoFactory.getSortieDao().findSortie(Integer.valueOf(request.getPathInfo().replace("/","")));
			request.setAttribute("sortie", sortie);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/annulerSortie.jsp");
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
