package fr.eni.sortir.servlets;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.eni.sortir.utils.Constantes;

/**
 * Servlet implementation class ServletImage
 */
@WebServlet(name = "ServletImage", urlPatterns = { "/logged/image/*" })

public class ServletImage extends ServletParent {

    /**
     * 
     */
    private static final long serialVersionUID = 8464668936127232776L;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServletImage() {
	super();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
	    throws ServletException, IOException {
	super.doGet(request, response);
	File file = new File(Constantes.DATA_PATH + request.getPathInfo() + ".jpg");
	BufferedImage image = ImageIO.read(file);
	ImageIO.write(image, "JPG", response.getOutputStream());
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
