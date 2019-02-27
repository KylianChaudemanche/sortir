package fr.eni.sortir.servlets;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import fr.eni.sortir.dao.DaoFactory;
import fr.eni.sortir.entities.Participant;
import fr.eni.sortir.entities.Site;
import fr.eni.sortir.utils.Constantes;
import fr.eni.sortir.utils.SaltedMD5;

/**
 * Servlet implementation class ServletGestionCompte
 */
@WebServlet(name = "ServletGestionCompte", urlPatterns = { "/logged/gestionCompte" })
public class ServletGestionCompte extends HttpServlet {
    private static final String ATTR_PARTICIPANT = "participant";
    private static final String ATTR_LIST_SITE = "listeSite";

    /**
     * 
     */
    private static final long serialVersionUID = 2978411438742435566L;


    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletGestionCompte() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	HttpSession session = request.getSession();
	session.setAttribute(ATTR_PARTICIPANT, DaoFactory.getParticipantDao().findParticipant(7));
	Participant participant = (Participant) session.getAttribute(ATTR_PARTICIPANT);
	request.setAttribute(ATTR_PARTICIPANT, participant);
	Collection<Site> listeSite = DaoFactory.getSiteDao().getAllSite();
	request.setAttribute(ATTR_LIST_SITE, listeSite);
	RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/gestionCompte.jsp");
	rd.forward(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
	HttpSession session = request.getSession();
	Participant participant = (Participant) session.getAttribute("participant");
	DiskFileItemFactory factory = new DiskFileItemFactory();
	// Configure a repository (to ensure a secure temp location is used)
	ServletContext servletContext = this.getServletConfig().getServletContext();
	File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
	factory.setRepository(repository);

	// Create a new file upload handler
	ServletFileUpload upload = new ServletFileUpload(factory);

	// Parse the request
	try {
	    List<FileItem> items = upload.parseRequest(request);
	    Iterator<FileItem> iter = items.iterator();
	    String motDePasse = null;
	    while (iter.hasNext()) {
		FileItem item = iter.next();
		if (item.isFormField()) {
		    String name = item.getFieldName();
		    String value = item.getString();

		    switch (name) {
		    case "pseudo":
			if (!value.equals("")) {
			    participant.setPseudo(value);
			}
			break;
		    case "prenom":
			if (!value.equals("")) {
			    participant.setPrenom(value);
			}
			break;
		    case "nom":
			if (!value.equals("")) {
			    participant.setNom(value);
			}
			break;
		    case "telephone":
			if (!value.equals("")) {
			    participant.setTelephone(value);
			}
			break;
		    case "mail":
			if (!value.equals("")) {
			    participant.setMail(value);
			}
			break;
		    case "motDePasse":
			if (!value.equals("")) {
			    motDePasse = value;
			}
			break;
		    case "confirmationMotDePasse":
			if (!value.equals("")) {
			    if (value.equals(motDePasse)) {
				String passwordToHash = value;
				String securePassword = SaltedMD5.getSecurePassword(passwordToHash);
				participant.setMotDePasse(securePassword);
			    }
			}
			break;
		    case "site":
			if (!value.equals("")) {
			    participant.setSite(DaoFactory.getSiteDao().findSite(Integer.valueOf(value)));
			}
			break;
		    }
		} else {
		    if (item.getName() != null && item.getSize() != 0) {
			try {
			    String uploadName = participant.getNoParticipant().toString() + ".jpg";
			    File writeFile = new File(Constantes.DATA_PATH + uploadName);
			    item.write(writeFile);

			} catch (Exception e) {
			    e.printStackTrace();
			    System.out.println(e.toString());
			}
		    }
		}
	    }
	} catch (FileUploadException e) {
	    System.out.println(e.toString());
	} catch (Exception e) {
	    System.out.println(e.toString());
	}
	DaoFactory.getParticipantDao().updateParticipant(participant);
	doGet(request, response);
    }

}
