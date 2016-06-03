package edu.ynu.dao.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.filter.OncePerRequestFilter;

public class SessionFilter extends OncePerRequestFilter {

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		String[] Filter = new String[] { "qqq", "checkLogin", "upLoad", "checkDownloadPointsEnough", "download",
				"insertComment", "logout", "userId.action" };

		String uri = request.getRequestURI();
		// uri中包含Filter时才进行过滤
		boolean doFilter = false;
		for (String s : Filter) {
			if (uri.indexOf(s) != -1) {
				// 如果uri中包含不过滤的uri，则进行过滤
				doFilter = true;
				break;
			}
		}

		if (doFilter) {
			// 执行过滤
			// 从session中获取登录者实体
			Object obj = request.getSession().getAttribute("id");
			if (null == obj) {
				Cookie[] cookies = null;
				// 获取cookies的数据,是一个数组
				cookies = request.getCookies();
				if (cookies != null) {
					for (int i = 0; i < cookies.length; i++) {
						if (cookies[i].getName().equals("rsId")) {
							HttpSession session = request.getSession();
							session.setAttribute("id", Integer.parseInt(cookies[i].getValue()));
							filterChain.doFilter(request, response);
							return;
						}
					}

				}
			} else {
				filterChain.doFilter(request, response);
				return;
			}
		} else {
			filterChain.doFilter(request, response);
			return;
		}

		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		String jsonStr = "{\"name\":\"\"}";
		PrintWriter out = null;
		out = response.getWriter();
		out.write(jsonStr);
		out.close();
	}
}
