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
 * Servlet implementation class ServletAccueil
 */
@WebServlet(name = "ServletAffichageSortie", urlPatterns = { "/logged/sortie/*" })
public class ServletAffichageSortie extends ServletParent {

    /**
     * 
     */
    private static final long serialVersionUID = 3336521254958339719L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletAffichageSortie() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
    	Sortie sortie = DaoFactory.getSortieDao().findSortie(Integer.valueOf(request.getPathInfo().replace("/", "")));
    	request.setAttribute("sortie", sortie);
    	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/afficherSortie.jsp");
    	rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	doGet(request, response);
    }

}
