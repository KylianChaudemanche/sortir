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
 * Servlet implementation class ServletCreationCompte
 */

@WebServlet(name = "ServletGestionCompteAdmin", urlPatterns = { "/administration/gestionComptes/*" })
public class ServletGestionCompteAdmin extends ServletParent {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletGestionCompteAdmin() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		super.doGet(request, response);
		Collection<Site> listeSite = DaoFactory.getSiteDao().getAllSite();
		request.setAttribute("listeSite", listeSite);
		if("/nouveau".equals(request.getPathInfo())){
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/gestionCompteAdmin.jsp");
			rd.forward(request, response);
		}else {
			try {
			Participant participant = DaoFactory.getParticipantDao().findParticipant(Integer.valueOf(request.getPathInfo().replace("/","")));
			request.setAttribute("participant", participant);
			RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/gestionCompteAdmin.jsp");
			rd.forward(request, response);
			}catch(NumberFormatException e) {
			}
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Participant participant = null;
		Boolean nouveau = false;
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
			boolean isAdmin = false;
			boolean isActif = false;
			while (iter.hasNext()) {
				FileItem item = iter.next();
				if (item.isFormField()) {
					String name = item.getFieldName();
					String value = item.getString();
					switch (name) {
					case "action":
						if("nouveau".equals(value))
						{
							participant = new Participant();
							nouveau = true;
						}else {
							participant = DaoFactory.getParticipantDao().findParticipant(Integer.valueOf(value));
							nouveau = false;
						}
						break;
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
							if (value.equals(motDePasse))
							{
								String passwordToHash = value;
								String securePassword = SaltedMD5.getSecurePassword(passwordToHash);
								participant.setMotDePasse(securePassword);
							}
						}
						break;
					case "isAdmin":
						if (value.equals("isAdmin")) {
							isAdmin = true;
							}
						break;
					case "isActif":
						if (value.equals("isActif")) {
							isActif = true;
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
						try
						{
							String uploadName = participant.getNoParticipant().toString() + ".jpg";
							File writeFile = new File(Constantes.DATA_PATH + uploadName);
							item.write(writeFile);

						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}
			}
			participant.setAdministrateur(isAdmin);
			participant.setActif(isActif);
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(nouveau) {
			DaoFactory.getParticipantDao().addParticipant(participant);
		}else {
			DaoFactory.getParticipantDao().updateParticipant(participant);
		}
		
		response.sendRedirect("../gestionCompteAdmin/"+participant.getNoParticipant());
	}

}
