package fr.eni.sortir.servlets;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
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
import fr.eni.sortir.utils.SaltedMD5;

/**
 * Servlet implementation class ServletGestionCompte
 */
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class ServletGestionCompte extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletGestionCompte() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.setAttribute("participant", DaoFactory.getParticipantDao().findParticipant(7));
		Participant participant = (Participant) session.getAttribute("participant");
		request.setAttribute("participant", participant);
		Collection<Site> listeSite = DaoFactory.getSiteDao().getAllSite();
		request.setAttribute("listeSite", listeSite);
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/gestionCompte.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			        
			        switch(name) {
			        case "pseudo":
			        	participant.setPseudo(value);
			        	break;
			        case "prenom":
			        	participant.setPrenom(value);
			        	break;
			        case "nom":
			        	participant.setNom(value);
			        	break;
			        case "telephone":
			        	participant.setTelephone(value);
			        	break;
			        case "mail":
			        	participant.setMail(value);
			        	break;
			        case "motDePasse":
			        	motDePasse = value;
			        	break;
			        case "confirmationMotDePasse":
		        		String passwordToHash = value;
		        	    byte[] salt = SaltedMD5.getSalt();
		        	     
		        	    String securePassword = SaltedMD5.getSecurePassword(passwordToHash, salt);
		        		participant.setMotDePasse(securePassword);
			        	
			        	break;
			        case "site":
			        	participant.setSite(DaoFactory.getSiteDao().findSite(Integer.valueOf(value)));
			        	break;
			        }
			        
			    } else {
			    	
			    }
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchProviderException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*participant.setPseudo(request.getParameter("pseudo"));
		participant.setNom(request.getParameter("nom"));
		participant.setPrenom(request.getParameter("prenom"));
		participant.setTelephone(request.getParameter("telephone"));
		participant.setMail(request.getParameter("mail"));
		System.out.println(request.getParameter("site"));
		participant.setSite(DaoFactory.getSiteDao().findSite(Integer.valueOf(request.getParameter("site"))));*/
		
		
		
		
		/*if(request.getParameter("photo").toString() != "")
		System.out.println(request.getParameter("photo").toString());*/
		DaoFactory.getParticipantDao().updateParticipant(participant);
		doGet(request, response);
	}
	
}
