package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CustomerDAO;
import dao.impl.CustomerDAOImpl;
import model.customer.Customer;

@WebServlet(name = "CustomerController", urlPatterns = { "/customer_input", "/idcheck", "/customer_save"  })
public class CustomerController extends HttpServlet {

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

		//로직
		if (action.equals("customer_input")) {
			// 할일없음
			
		} else if (action.equals("idcheck")) {
			
			CustomerDAO customerDAO = new CustomerDAOImpl();
			int resultCount = customerDAO.selectCntById(request.getParameter("id"));
			
			if(resultCount>0){
				//아이디가 존재하는 경우 사용 못하게 false
				request.setAttribute("useTF", false);
			}else{
				//아이디가 존재하지 않는 경우 사용 할 수 있게 true
				request.setAttribute("useTF", true);
			}
			
		} else if (action.equals("customer_save")) {

			//객체생성
			Customer customer = new Customer();

			//속성 추출하여 객체에 속성 set
			request.setCharacterEncoding("utf-8");
			
			customer.setId(request.getParameter("id"));
			customer.setName(request.getParameter("name"));
			customer.setPassword(request.getParameter("pwd"));
			customer.setPostcode(request.getParameter("postcode"));
			customer.setAddress(request.getParameter("address"));
			customer.setAddress2(request.getParameter("address2"));
			customer.setPhone(request.getParameter("phone"));
			customer.setEmail(request.getParameter("email"));

			// dao 실행
			CustomerDAO customerDAO = new CustomerDAOImpl();
			customerDAO.insert(customer);

			//객체를 저장하여 뷰로 보내기
			request.setAttribute("customer", customer);
		}
		
		
		//뷰로 포워드
		String dispatchUrl=null;
		
		if (action.equals("customer_input")) {
			dispatchUrl="/jsp/damember/insert.jsp";
		} else if (action.equals("idcheck")) {	
			dispatchUrl="/ajax/idcheck.jsp";
		} else if (action.equals("customer_save")) {		
			dispatchUrl="/index.jsp"; 
		}

		RequestDispatcher rd = request.getRequestDispatcher(dispatchUrl);
		rd.forward(request, response);
	}
}
