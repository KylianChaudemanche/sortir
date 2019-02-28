package fr.eni.sortir.servlets;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
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
@WebServlet(name = "ServletInscription", urlPatterns = { "/logged/inscription/*" })
public class ServletInscription extends ServletParent {

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
		super.doGet(request, response);
    	Sortie sortie = DaoFactory.getSortieDao().findSortie(Integer.valueOf(request.getPathInfo().replace("/", "")));
    	
    	if (!sortie.getEtat().getLibelle().equals(State.OPENED.toString())) {
    		request.setAttribute("typeMessage", "warning");
    	    request.setAttribute("message", "Vous ne pouvez vous inscrire qu'aux sorties ayant le statut Ouvert.");
    	    RequestDispatcher rd = request.getRequestDispatcher("/logged/accueil");
    		rd.forward(request, response);
    		return;
    	}
    	
    	Participant participant = (Participant) request.getSession().getAttribute("participant");
    	if(sortie.getNbInscriptionsMax() >= sortie.getInscriptions().size()) {
    	sortie.addPartcipant(participant);
    	DaoFactory.getSortieDao().updateSortie(sortie);
    	request.setAttribute("typeMessage", "success");
	    request.setAttribute("message", "Votre inscription a été enregistrée.");
    	}
    	else{
    		request.setAttribute("typeMessage", "warning");
    		request.setAttribute("message", "La sortie est pleine.");
    	}
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
