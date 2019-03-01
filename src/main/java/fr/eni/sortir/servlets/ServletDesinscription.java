package fr.eni.sortir.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.entities.Inscription;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Sortie;
import fr.eni.sortir.utils.State;

/**
 * Servlet implementation class ServletInscription
 */
@WebServlet(name = "ServletDesinscription", urlPatterns = { "/logged/desinscription/*" })
public class ServletDesinscription extends ServletParent {

    /**
     * 
     */
    private static final long serialVersionUID = 3564556019227754709L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletDesinscription() {
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
    	Participant participant = (Participant) request.getSession().getAttribute("participant");

    	if (!sortie.getEtat().getLibelle().equals(State.OPENED.toString())) {
    		request.setAttribute("typeMessage", "warning");
    	    request.setAttribute("message", "Vous ne pouvez vous désinscrire qu'aux sorties ayant le statut Ouvert.");
    	    RequestDispatcher rd = request.getRequestDispatcher("/logged/accueil");
    		rd.forward(request, response);
    		return;
    	}
    	
    	Inscription inscription = DaoFactory.getInscriptionDao().findInscription(participant, sortie);
    	DaoFactory.getInscriptionDao().removeInscription(inscription);
    	request.setAttribute("typeMessage", "success");
	    request.setAttribute("message", "Vous avez été desinscrit");
    	response.sendRedirect("/sortir/logged/accueil");
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
