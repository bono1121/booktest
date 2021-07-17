package filter;

import java.io.IOException;

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

@WebFilter(filterName = "/MemberCheckFilter", urlPatterns = { "/*" })
public class LoginCheckFilter implements Filter {

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession(false);

		String uri = req.getRequestURI();

		int lastIndex = uri.lastIndexOf("/");
		String action = uri.substring(lastIndex + 1);

		// 요청주소에 LOGCK 문자열이 포함되어 있으면
		// session에 member속성이 존재하는지 확인하고
		// member속성이 있는 경우는 그대로 요청진행로직을 타고
		// member속성이 없는 경우로그인 로직을 탄다.
		if (action.contains("LOGCK")) {
			if (session != null) {

				if (session.getAttribute("member") != null) {
					chain.doFilter(request, response);
				} else {
					session.setAttribute("action", action);
					((HttpServletResponse) response).sendRedirect("login_input");
				}
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

}
