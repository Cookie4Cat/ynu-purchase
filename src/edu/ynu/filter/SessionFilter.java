package edu.ynu.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import edu.ynu.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.filter.OncePerRequestFilter;

public class SessionFilter extends OncePerRequestFilter {

	private TokenService tokenService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {
		ServletContext sc = request.getSession().getServletContext();
		XmlWebApplicationContext cxt = (XmlWebApplicationContext) WebApplicationContextUtils
		        .getWebApplicationContext(sc);
		if (cxt != null && cxt.getBean("tokenService") != null && tokenService == null) {
			tokenService = (TokenService) cxt.getBean("tokenService");
		}

		String[] NoFilter = new String[] { "login", "public" };

		String uri = request.getRequestURI();
		// uri中包含NoFilter时不进行过滤，其他情况默认过滤
		boolean doFilter = true;
		for (String s : NoFilter) {
			if (uri.indexOf(s) != -1) {
				// 如果uri中包含不过滤的uri，则不进行过滤
				doFilter = false;
				break;
			}
		}

		if (doFilter) {
			System.out.println(request.getParameter("token"));
			System.out.println(tokenService);
			String userFormToken = tokenService.getUserFormToken(request.getParameter("token"));

			if (userFormToken == null) {
				//response.sendRedirect("/login.html");
				response.sendError(401);
				return;
			}
			request.setAttribute("userId", userFormToken);
			filterChain.doFilter(request, response);
			return;

		} else {
			filterChain.doFilter(request, response);
			return;
		}
	}
}
