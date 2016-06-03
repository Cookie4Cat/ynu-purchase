package edu.ynu.dao.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.filter.OncePerRequestFilter;

import edu.ynu.util.TokenUtil;

public class SessionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	        throws ServletException, IOException {

		String[] NoFilter = new String[] { "login" };

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
			TokenUtil tokenUtil = new TokenUtil();
			String userFormToken = tokenUtil.getUserFormToken(request.getParameter("token"));
			if (userFormToken == null) {
				response.sendRedirect("/login.html");
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
