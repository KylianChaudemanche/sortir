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
import fr.eni.sortir.utils.State;

/**
 * Servlet implementation class ServletInscription
 */
@WebServlet(name = "ServletPublishSortie", urlPatterns = { "/logged/publish/*" })
public class ServletPublishSortie extends ServletParent {

    /**
     * 
     */
    private static final long serialVersionUID = 3564556019227754709L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletPublishSortie() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	super.doGet(request, response);
	Sortie sortie = DaoFactory.getSortieDao().findSortie(Integer.valueOf(request.getPathInfo().replace("/", "")));
	sortie.setEtat(DaoFactory.getEtatDao().findEtatByName(State.OPENED.toString()));
	DaoFactory.getSortieDao().updateSortie(sortie);
	RequestDispatcher rd = request.getRequestDispatcher("/logged/accueil");
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
