package fr.eni.sortir.servlets;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.NoResultException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
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
 * Servlet implementation class ServletCreationComptesCSV
 */
@WebServlet(name = "ServletCreationComptesCSV", urlPatterns = { "/administration/creationComptesCSV" })
public class ServletCreationComptesCSV extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ServletCreationComptesCSV() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/creationComptesCSV.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);
		ArrayList<Participant> listeParticipants = new ArrayList<Participant>();
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// Parse the request
		Boolean CSV_OK = true;
		try {
			List<FileItem> items = upload.parseRequest(request);
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = iter.next();
				if (!item.isFormField()) {
					File file = new File(Constantes.CSV_PATH);
					item.write(file);
					Reader in = new FileReader(file);
					CSVParser records = CSVFormat.DEFAULT.withDelimiter(',').withHeader().parse(in);
					ArrayList<ArrayList<String>> listeRecords = new ArrayList<ArrayList<String>>();
					for (CSVRecord record : records) {
						listeRecords.add(convertCSVRecord(record));
					}
					int nbRecords = listeRecords.size();
					String[][] tabParametreStatus = new String[nbRecords][7];
					int posTable = 0;
					for (List<String> listeChamps : listeRecords) {
					    String pseudo = listeChamps.get(0);
					    Boolean pseudoDispo = false;
					    try {
					    	pseudoDispo = DaoFactory.getParticipantDao().isPseudoDispo(pseudo);
						} catch (NoResultException e) {
							System.out.println(e);
						}
					    if(!pseudoDispo) {
					    tabParametreStatus[posTable][0] = "warning";
					    CSV_OK = false;
					    }
					    else {
					    	tabParametreStatus[posTable][0] = "light";
					    }
					    String nom = listeChamps.get(1);
					    tabParametreStatus[posTable][1] = "light";
					    String prenom = listeChamps.get(2);
					    tabParametreStatus[posTable][2] = "light";
					    String telephone = listeChamps.get(3);
					    tabParametreStatus[posTable][3] = "light";
					    String mail = listeChamps.get(4);
					    Boolean mailDispo = false;
					    try {
					    	mailDispo = DaoFactory.getParticipantDao().isMailDispo(mail);
						} catch (NoResultException e) {
							
						}
					    if(!mailDispo) {
					    	tabParametreStatus[posTable][4] = "warning";
					    	CSV_OK = false;
					    }
					    else {
					    	tabParametreStatus[posTable][4] = "light";
					    }
					    String motDePasse = SaltedMD5.getSecurePassword(listeChamps.get(5));
					    tabParametreStatus[posTable][5] = "light";
					    String noSite = listeChamps.get(6);
					    Site site = DaoFactory.getSiteDao().findSite(Integer.valueOf(noSite));
					    if(site == null)
					    {
					    	tabParametreStatus[posTable][6] = "danger";
					    	CSV_OK = false;
					    }
					    else {
					    	tabParametreStatus[posTable][6] = "light";
					    }
					    
					    Participant participant = new Participant(pseudo, nom, prenom, telephone, mail, motDePasse, false, true, new ArrayList<>(), site, new ArrayList<>());
					    listeParticipants.add(participant);
					    posTable++;
					}
					request.setAttribute("tabParametreStatus", tabParametreStatus);
				}
			}
		} catch (FileUploadException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(CSV_OK)
		{
			for(Participant participant : listeParticipants)
			{
				DaoFactory.getParticipantDao().addParticipant(participant);
			}
			request.setAttribute("CSVOK", CSV_OK);
		}else {
			request.setAttribute("listeParticipants", listeParticipants);
			request.setAttribute("CSVOK", CSV_OK);
		}
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/creationComptesCSV.jsp");
		rd.forward(request, response);
	}
	
	private ArrayList<String> convertCSVRecord(CSVRecord record)
		{
			ArrayList<String> returnValue = new ArrayList<String>();

		    for (String currentValue : record)
		    {
		        returnValue.add(currentValue);
		    }

		    return returnValue;
		}
}
