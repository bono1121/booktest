package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.BookDAO;
import dao.OrderItemDAO;
import dao.OrdersDAO;
import dao.impl.BookDAOImpl;
import dao.impl.OrderItemDAOImpl;
import dao.impl.OrdersDAOImpl;
import model.book.Book;
import model.customer.Customer;
import model.order.Order;
import model.order.OrderInfo;
import model.order.OrderItem;
import model.order.ShoppingItem;
import service.FileManager;

@WebServlet(name = "OrdersController", urlPatterns = { 
		"/addBook_toCart", "/cartView_LOGCK", "/orderBook", "/payBook", "/order_list", "/order_detail" })
public class OrdersController extends HttpServlet {

	private static final long serialVersionUID = 1579L;
	private static final String CART_ATTRIBUTE = "cart";
	private static final String BUY_ATTRIBUTE = "buy";

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

		// detail_member 에서 장바구니 버튼 눌러 장바구니에 책을 집어 넣는다.
		if (action.equals("addBook_toCart")) {

			BookDAO bookDAO = new BookDAOImpl();

			Book book = bookDAO.selectByBookId(Integer.parseInt(request.getParameter("bookid")));
			book.setImagepath(FileManager.getFileServerPath() + book.getImagepath());

			int quantity = Integer.parseInt(request.getParameter("quantity"));

			if (book != null && quantity >= 0) {

				ShoppingItem shoppingItem = new ShoppingItem(book, quantity);

				HttpSession session = request.getSession();
				List<ShoppingItem> cart = (List<ShoppingItem>) session.getAttribute(CART_ATTRIBUTE);

				if (cart == null) {
					cart = new ArrayList<ShoppingItem>();
					session.setAttribute(CART_ATTRIBUTE, cart);
				}
				cart.add(shoppingItem);
			}
			// 장바구니에 들어 있는 것을 볼때
		} else if (action.equals("cartView_LOGCK")) {

			int totalPrice = 0;

			HttpSession session = request.getSession();
			List<ShoppingItem> cart = (List<ShoppingItem>) session.getAttribute(CART_ATTRIBUTE);

			// System.out.println(cart.size());

			if (cart != null) {
				for (ShoppingItem shoppingItem : cart) {
					System.out.println(shoppingItem);
					totalPrice += shoppingItem.getBook().getPrice() * shoppingItem.getQuantity();
				}
			}
			request.setAttribute("totalPrice", totalPrice);

			// 장바구니에서 구매하기 누룰때
		} else if (action.equals("orderBook")) {

			int totalPrice = 0;

			HttpSession session = request.getSession();
			List<ShoppingItem> cart = (List<ShoppingItem>) session.getAttribute(CART_ATTRIBUTE);
			
			List<ShoppingItem> buylist = new ArrayList<ShoppingItem>();
			String[] choiceShoppingItemInedx = request.getParameterValues("shoppingItemInedx");

			//System.out.println("orderBook의 cart.size() :" + cart.size());

			//System.out.println("orderBook의 choiceBook.length :" + choiceBook.length);

			for (ShoppingItem shoppingItem : cart) {
				for (String shoppingItemInedx : choiceShoppingItemInedx) {
					if (shoppingItem.getShoppingItemInedx()== Integer.parseInt(shoppingItemInedx)) {
						buylist.add(shoppingItem);
					}
				}
			}
			
			session.setAttribute(BUY_ATTRIBUTE, buylist);
			
			if (cart != null) {
				for (ShoppingItem shoppingItem : buylist) {
					totalPrice += shoppingItem.getBook().getPrice() * shoppingItem.getQuantity();
				}
			}
			
			//주문확정된 상품들의 총합계액
			request.setAttribute("totalPrice", totalPrice);

			// System.out.println("orderBook 요청
			// buylist.size():"+buylist.size());
			// 주문 결제할때
		} else if (action.equals("payBook")) {

			HttpSession session = request.getSession();
			List<ShoppingItem> cart = (List<ShoppingItem>) session.getAttribute(CART_ATTRIBUTE);
			List<ShoppingItem> buylist = (List<ShoppingItem>) session.getAttribute(BUY_ATTRIBUTE);

			Order order = new Order();

			order.setAmount(Integer.parseInt(request.getParameter("amount")));
			order.setPayoption(Integer.parseInt(request.getParameter("payoption")));
			order.setCustomerid(((Customer) session.getAttribute("member")).getCustomerid());

			// System.out.println("Order:"+order.toString());

			OrdersDAO ordersDAO = new OrdersDAOImpl();
			int orderid = ordersDAO.insert(order);

			OrderItemDAO orderItemDAO = new OrderItemDAOImpl();

			for (ShoppingItem shoppingItem : buylist) {
				OrderItem orderItem = new OrderItem();

				orderItem.setBookid(shoppingItem.getBook().getBookid());
				orderItem.setQuantity(shoppingItem.getQuantity());
				orderItem.setOrderid(orderid);

				orderItemDAO.insert(orderItem);
			}

			for (ShoppingItem shoppingItem : buylist) {
				cart.remove(shoppingItem);
			}

			buylist.clear();
			
		} else if (action.equals("order_list")) {
			HttpSession session = request.getSession();	
			Customer customer =(Customer)session.getAttribute("member");
			
			OrdersDAO ordersDAO = new OrdersDAOImpl();
			List<Order> orders = ordersDAO.selectAByCustomerid(customer.getCustomerid());
			
			request.setAttribute("orders", orders);
			
		} else if (action.equals("order_detail")) {
			int amount=0;
			
			OrderItemDAO orderItemDAO = new OrderItemDAOImpl();
			List<OrderInfo> orderInfos = orderItemDAO.selectByOrderid(Integer.parseInt(request.getParameter("orderid")));
			
			for (OrderInfo orderInfo : orderInfos) {
				amount+= orderInfo.getPrice()*orderInfo.getQuantity();
				orderInfo.setImagepath(FileManager.getFileServerPath() + orderInfo.getImagepath());
			}

			request.setAttribute("amount", amount);
			request.setAttribute("orderInfos", orderInfos);
			request.setAttribute("orderdate", request.getParameter("orderdate"));
		}

		// 뷰로 포워드
		String dispatchUrl = null;

		if (action.equals("addBook_toCart")) {
			dispatchUrl = "book_detail?bookid=" + Integer.parseInt(request.getParameter("bookid")) + "&div=" + 2;
		} else if (action.equals("cartView_LOGCK")) {
			dispatchUrl = "/jsp/daorder/cart.jsp";
		} else if (action.equals("orderBook")) {
			dispatchUrl = "/jsp/daorder/payment.jsp";
		} else if (action.equals("payBook")) {
			dispatchUrl = "/jsp/daorder/message.jsp";
		} else if (action.equals("order_list")) {
			dispatchUrl = "/jsp/daorder/orderlist.jsp";
		} else if (action.equals("order_detail")) {
			dispatchUrl = "/jsp/daorder/orderdetail.jsp";
		}

		RequestDispatcher rd = request.getRequestDispatcher(dispatchUrl);
		rd.forward(request, response);
	}
}
