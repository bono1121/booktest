package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import dao.BookCommentDAO;
import dao.impl.BookCommentDAOImpl;
import model.comment.BookComment;
import model.comment.BookCommentDeleteResult;
import model.comment.BookCommentListResult;
import model.comment.BookCommentResult;
import model.customer.Customer;

@WebServlet(name = "BookCommentController", urlPatterns = { "/comment_search", "/comment_save", "/comment_delete" })
@MultipartConfig
public class BookCommentController extends HttpServlet {

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

		if (action.equals("comment_search")) {
			
			
			BookCommentDAO bookCommentDAO = new BookCommentDAOImpl();

			int bookid = Integer.parseInt(request.getParameter("bookid"));

			List<BookComment> comments = bookCommentDAO.selectAll(bookid);

			BookCommentListResult bookCommentListResult = new BookCommentListResult();
			
			System.out.println("comments.size():"+comments.size());
			
			if (comments.size() > 0) {
				bookCommentListResult.setResult(true);
				bookCommentListResult.setMessage("검색된 댓글 수 : " + comments.size());
				bookCommentListResult.setCommentItem(comments);
			} else {
				bookCommentListResult.setResult(false);
				bookCommentListResult.setMessage("검색된 댓글이 존재하지 않습니다.");
				bookCommentListResult.setCommentItem(null);
			}

			String json = new Gson().toJson(bookCommentListResult);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			return;


		} else if (action.equals("comment_save")) {

			// 속성 추출하여 객체에 속성 set
			request.setCharacterEncoding("utf-8");

			// 객체생성
			BookComment comment = new BookComment();

			HttpSession session = request.getSession();
			Customer customer = (Customer) session.getAttribute("member");

			comment.setWriter(customer.getId());
			comment.setContent(request.getParameter("content"));
			comment.setBookid(Integer.parseInt(request.getParameter("bookid")));

			// dao 실행
			BookCommentDAO bookCommentDAO = new BookCommentDAOImpl();
			// 댓글내용 추가
			int commentid = bookCommentDAO.insert(comment);

			BookCommentResult bookCommentResult = new BookCommentResult();

			if (commentid != 0) {

				BookComment selectBookCommentResult = bookCommentDAO.selectByCommentid(commentid);

				bookCommentResult.setResult(true);
				bookCommentResult.setMessage("댓글이 등록되었습니다.");
				bookCommentResult.setComment(selectBookCommentResult);

			} else {

				bookCommentResult.setResult(false);
				bookCommentResult.setMessage("댓글 등록에 실패했습니다. 잠시 후에 다시 시도해 주세요.");
				bookCommentResult.setComment(null);
			}

			String json = new Gson().toJson(bookCommentResult);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			return;

		} else if (action.equals("comment_delete")) {
			System.out.println(Integer.parseInt(request.getParameter("commentid")));
			// 속성 추출하여 객체에 속성 set
			request.setCharacterEncoding("utf-8");

			BookCommentDAO bookCommentDAO = new BookCommentDAOImpl();
			boolean result = bookCommentDAO.deleteByCommentid(Integer.parseInt(request.getParameter("commentid")));

			
			BookCommentDeleteResult bookCommentDeleteResult = new BookCommentDeleteResult();
			
			if(result){
				bookCommentDeleteResult.setResult(true);
				bookCommentDeleteResult.setMessage("댓글이 삭제되었습니다.");
			}else{
				bookCommentDeleteResult.setResult(false);
				bookCommentDeleteResult.setMessage("댓글 삭제에 실패했습니다.");				
			}
			
			String json = new Gson().toJson(bookCommentDeleteResult);

			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(json);

			return;			
		}
	}
	
}
