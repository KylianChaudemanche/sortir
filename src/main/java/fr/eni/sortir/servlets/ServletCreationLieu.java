package fr.eni.sortir.servlets;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.ObjectWriter;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.entities.Lieu;
import fr.eni.sortir.entities.Ville;

/**
 * Servlet implementation class ServletCreationLieu
 */
@WebServlet(name = "ServletCreationLieu", urlPatterns = { "/logged/creationLieu" })
public class ServletCreationLieu extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletCreationLieu() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String nomLieu = request.getParameter("modalNomLieu");
		String adresse = request.getParameter("modalAdresse");
		Float latitude = Float.valueOf(request.getParameter("modalLatitude"));
		Float longitude = Float.valueOf(request.getParameter("modalLongitude"));
		Ville ville = DaoFactory.getVilleDao().findVille(Integer.valueOf(request.getParameter("modalNoVille")));
		Lieu lieu = new Lieu(nomLieu, adresse, latitude, longitude, ville, new ArrayList<>());
		DaoFactory.getLieuDao().addLieu(lieu);
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(lieu);
	    response.setContentType("application/json;charset=UTF-8");
	    response.getWriter().print(json);
	}

}
