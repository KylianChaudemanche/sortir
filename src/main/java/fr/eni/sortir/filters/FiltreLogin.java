package fr.eni.sortir.filters;

import java.io.IOException;

import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import fr.eni.sortir.entities.Participant;

/**
 * Servlet implementation class FiltreLogin
 */
@WebFilter(dispatcherTypes = {
		DispatcherType.REQUEST, 
		DispatcherType.FORWARD, 
		DispatcherType.INCLUDE, 
		DispatcherType.ERROR
}, 
urlPatterns = { "/logged/*" })
public class FiltreLogin implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)throws IOException, ServletException {
		HttpSession session = ((HttpServletRequest) request).getSession();
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		Participant participant = null;
		
		if ((session != null)) {
			if ((session.getAttribute("participant") != null)) {
				// on laisse passer la requete
		        chain.doFilter(request, response);
		        return;
			}
		}

		//Renvoyons une 403 à l'utilisateur
		httpResponse.sendRedirect("/sortir/login");
		return;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}
       
}
