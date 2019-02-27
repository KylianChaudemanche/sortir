package fr.eni.sortir.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.entities.Site;

/**
 * Servlet implementation class ServletGestionSites
 */
@WebServlet(name = "ServletGestionSites", urlPatterns = { "/administration/gestionSites" })
public class ServletGestionSites extends ServletParent {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletGestionSites() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		request.setAttribute("listeSites", DaoFactory.getSiteDao().getAllSite());
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/gestionSites.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		if(request.getParameter("modifierSite")!= null){
			Site site = DaoFactory.getSiteDao().findSite(Integer.valueOf(request.getParameter("modifierSite")));
			site.setNomSite(request.getParameter(request.getParameter("modifierSite")));
			DaoFactory.getSiteDao().updateSite(site);
		}else if(request.getParameter("supprimerSite")!= null){
			DaoFactory.getSiteDao().removeSite(Integer.valueOf(request.getParameter("supprimerSite")));
		}else if(request.getParameter("ajouterSite")!= null){
			DaoFactory.getSiteDao().addSite(new Site(request.getParameter("nouveauSite"), new ArrayList<>()));
		}
		doGet(request,response);
	}

}
