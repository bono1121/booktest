package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CustomerDAO;
import dao.impl.CustomerDAOImpl;
import model.customer.Customer;

@WebServlet(name = "LogInOutController", urlPatterns = { "/login_input", "/login", "/login_ajax", "/logout" })
public class LogInOutController extends HttpServlet {

	private static final long serialVersionUID = 1579L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		process(request, response);
	}

	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		process(request, response);
	}

	private void process(HttpServletRequest request, HttpServletResponse response)
			throws IOException, ServletException {

		String uri = request.getRequestURI();

		int lastIndex = uri.lastIndexOf("/");
		String action = uri.substring(lastIndex + 1);

		// 로직
		if (action.equals("login_input")) {
			// 할일 없음
		} else if (action.equals("login")) {

			// 객체생성
			Customer inputCustomer = new Customer();

			// 속성 추출하여 객체에 속성 set
			request.setCharacterEncoding("utf-8");

			inputCustomer.setId(request.getParameter("id"));
			inputCustomer.setPassword(request.getParameter("pwd"));

			// dao
			CustomerDAO customerDAO = new CustomerDAOImpl();
			// 입력한 id를 가진 고객명수 검색 (아이디는 중복이 안되니 무조건 0 아니면 1)
			int custoemrCount = customerDAO.selectCntById(inputCustomer.getId());

			String resultMessage = null;

			// 1.id 존재하는 경우
			if (custoemrCount > 0) {
				// 해당하는 id를 가진 고객정보 검색
				Customer selectCustomer = customerDAO.selectById(inputCustomer.getId());

				// 1)패스워드 일치
				if (inputCustomer.getPassword().equals(selectCustomer.getPassword())) {

					HttpSession session = request.getSession();
					session.setAttribute("member", selectCustomer);

					resultMessage = selectCustomer.getId() + "님 안녕하세요";

					// 원래 액션으로 돌아가기 코드
					if (session.getAttribute("action") != null) {
						String originalAction = (String) session.getAttribute("action");
						session.removeAttribute("action");
						response.sendRedirect(originalAction);
						return;
					}

					// 2)패스워드 불일치
				} else {
					request.setAttribute("inputCustomer", inputCustomer);
					resultMessage = "비밀번호가 일치하지 않습니다.";
				}

				// 2.id 존재하지 않는 경우
			} else {
				request.setAttribute("inputCustomer", inputCustomer);
				resultMessage = "존재하지 않는 아이디 입니다.";
			}

			// 객체를 저장하여 뷰로 보내기
			request.setAttribute("resultMessage", resultMessage);

		} else if (action.equals("login_ajax")) {

			// 객체생성
			Customer inputCustomer = new Customer();

			// 속성 추출하여 객체에 속성 set
			request.setCharacterEncoding("utf-8");

			inputCustomer.setId(request.getParameter("id"));
			inputCustomer.setPassword(request.getParameter("pwd"));

			// dao
			CustomerDAO customerDAO = new CustomerDAOImpl();
			// 입력한 id를 가진 고객명수 검색 (아이디는 중복이 안되니 무조건 0 아니면 1)
			int custoemrCount = customerDAO.selectCntById(inputCustomer.getId());
			
			boolean result = false;
			String resultMessage = null;

			// 1.id 존재하는 경우
			if (custoemrCount > 0) {
				// 해당하는 id를 가진 고객정보 검색
				Customer selectCustomer = customerDAO.selectById(inputCustomer.getId());

				// 1)패스워드 일치
				if (inputCustomer.getPassword().equals(selectCustomer.getPassword())) {

					HttpSession session = request.getSession();
					session.setAttribute("member", selectCustomer);

					resultMessage = selectCustomer.getId() + "님 안녕하세요";
					result = true;

					// 원래 액션으로 돌아가기 코드
					if (session.getAttribute("action") != null) {
						String originalAction = (String) session.getAttribute("action");
						session.removeAttribute("action");
						response.sendRedirect(originalAction);
						return;
					}

				// 2)패스워드 불일치
				} else {
					request.setAttribute("inputCustomer", inputCustomer);
					resultMessage = "비밀번호가 일치하지 않습니다.";
				}

				// 2.id 존재하지 않는 경우
			} else {
				request.setAttribute("inputCustomer", inputCustomer);
				resultMessage = "존재하지 않는 아이디 입니다.";
			}

			// 객체를 저장하여 뷰로 보내기
			request.setAttribute("result", result);
			request.setAttribute("resultMessage", resultMessage);


		} else if (action.equals("logout")) {

			HttpSession session = request.getSession();
			session.removeAttribute("member");
		}

		String dispatchUrl = null;

		if (action.equals("login_input")) {

			dispatchUrl = "/jsp/damember/login.jsp";

		} else if (action.equals("login")) {
			HttpSession session = request.getSession();

			if (session.getAttribute("member") != null) {
				dispatchUrl = "/index.jsp";
			} else {
				dispatchUrl = "/jsp/damember/login.jsp";
			}			
		} else if (action.equals("login_ajax")) {

			dispatchUrl = "/ajax/login.jsp";
			
		} else if (action.equals("logout")) {

			dispatchUrl = "/index.jsp";
		}

		RequestDispatcher rd = request.getRequestDispatcher(dispatchUrl);
		rd.forward(request, response);
		 
	}
}
