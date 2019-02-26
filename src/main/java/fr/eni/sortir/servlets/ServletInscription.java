package fr.eni.sortir.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Sortie;
import fr.eni.sortir.utils.State;

/**
 * Servlet implementation class ServletInscription
 */
@WebServlet(name = "ServletInscription", urlPatterns = { "/inscription/*" })
public class ServletInscription extends HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = 3564556019227754709L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletInscription() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
    	Sortie sortie = DaoFactory.getSortieDao().findSortie(Integer.valueOf(request.getPathInfo().replace("/", "")));
    	
    	if (!sortie.getEtat().getLibelle().equals(State.OPENED.toString())) {
    	    response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
    	    return;
    	}
    	
    	Participant participant = (Participant) request.getSession().getAttribute("participant");
    	sortie.addPartcipant(participant);
    	DaoFactory.getSortieDao().updateSortie(sortie);
    	response.sendRedirect("/sortir/accueil");
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
