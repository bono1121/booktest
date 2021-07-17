package controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import dao.BookDAO;
import dao.impl.BookDAOImpl;
import form.BookForm;
import formError.BookError;
import model.book.Book;
import model.page.PageGroupResult;
import service.FileManager;
import service.PageManager;
import sql.CountSQL;
import validator.BookValidator;

@WebServlet(name = "BookController", urlPatterns = { "/book_input", "/book_save", "/book_search", "/book_detail",
		"/book_search_name", "/book_update" })
@MultipartConfig
public class BookController extends HttpServlet {

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
		if (action.equals("book_input")) {

			// 할일없음

		} else if (action.equals("book_save")) {// 수정
			
			// 속성 추출하여 객체에 속성 set
			request.setCharacterEncoding("utf-8");

			//유효성 검사
			BookForm bookForm = new BookForm();
			
			bookForm.setBookname(request.getParameter("bookname"));
			bookForm.setPublisher(request.getParameter("publisher"));
			bookForm.setPrice(request.getParameter("price"));
			bookForm.setDescription(request.getParameter("description"));		
						
			BookValidator bookValidator = new BookValidator();
			//List<String> errors = bookValidator.validate(bookForm);//v1
			BookError bookError = bookValidator.validate(bookForm);//v2
			
			//if(errors.isEmpty()){	//v1
			if(!bookError.isResult()){//v2	
				// 객체생성
				Book book = new Book();
				
				//유효성 적용전
/*				book.setBookname(request.getParameter("bookname"));
				book.setPublisher(request.getParameter("publisher"));
				book.setPrice(Integer.parseInt(request.getParameter("price")));
				book.setDescription(request.getParameter("description"));*/
				
				//유효성 적용후
				book.setBookname(bookForm.getBookname());
				book.setPublisher(bookForm.getPublisher());
				book.setPrice(Integer.parseInt(bookForm.getPrice()));
				book.setDescription(bookForm.getDescription());

				Part part = request.getPart("filename");
				String fileName = FileManager.saveImageToFileServer(part);
				book.setImagepath(fileName);

				// dao 실행
				BookDAO bookDAO = new BookDAOImpl();
				bookDAO.insert(book);	
				
			}else{	
	
				//request.setAttribute("errors", errors);//v1
				request.setAttribute("bookError", bookError);//v2
				request.setAttribute("bookForm", bookForm);
			}


		} else if (action.equals("book_search")) {

			BookDAO bookDAO = new BookDAOImpl();
			int requestPage = Integer.parseInt(request.getParameter("reqPage"));			
			
			List<Book> books = bookDAO.selectAll(requestPage);

			PageManager pm = new PageManager(requestPage);
			PageGroupResult pageGroupResult = pm.getPageGroupResult(CountSQL.BOOK_SELECT_ALL_COUNT_SQL);		
			
			
			// 객체를 저장하여 뷰로 보내기
			request.setAttribute("books", books);
			request.setAttribute("pageGroupResult", pageGroupResult);

		} else if (action.equals("book_detail")) {

			BookDAO bookDAO = new BookDAOImpl();
			Book book = bookDAO.selectByBookId(Integer.parseInt(request.getParameter("bookid")));

			book.setImagepath(FileManager.getFileServerPath() + book.getImagepath());

			// 객체를 저장하여 뷰로 보내기
			request.setAttribute("book", book);

		} else if (action.equals("book_search_name")) {

			BookDAO bookDAO = new BookDAOImpl();
			List<Book> books = bookDAO.selectByBookname(request.getParameter("bookname"));

			// 객체를 저장하여 뷰로 보내기
			request.setAttribute("books", books);
			
		} else if (action.equals("book_update")) {

			// 속성 추출하여 객체에 속성 set
			request.setCharacterEncoding("utf-8");

			//수정버튼 클릭시
			if (request.getParameter("update") != null) {
				// 객체생성
				Book book = new Book();

				book.setBookid(Integer.parseInt(request.getParameter("bookid")));
				book.setBookname(request.getParameter("bookname"));
				book.setPublisher(request.getParameter("publisher"));
				book.setPrice(Integer.parseInt(request.getParameter("price")));
				book.setDescription(request.getParameter("description"));

				Part part = request.getPart("filename");
				String fileName = FileManager.saveImageToFileServer(part);

				BookDAO bookDAO = new BookDAOImpl();

				if (fileName != null) {// 이미지 수정이 된경우(filename != null)

					// 1.그 전 이미지 파일 서버에서 삭제
					Book currBook = bookDAO.selectByBookId(book.getBookid());
					FileManager.deleteFile(currBook.getImagepath());

					// 2.이미지를 파일 서버에 새로 올리고 imagepath 값 셋팅
					book.setImagepath(fileName);

					// 3.imagepath 포함 수정쿼리 실행
					bookDAO.update(book);

				} else {// 이미지 수정이 안된경우(filename == null) : imagepath 수정하지 않는다.
						// -imagepath 불포함 수정 쿼리 실행
					bookDAO.updateEXImagepath(book);
				}
				
			//삭제 버튼 클릭시
			} else if (request.getParameter("delete") != null) {

				BookDAO bookDAO = new BookDAOImpl();
				bookDAO.deleteByBookId(Integer.parseInt(request.getParameter("bookid")));
			}
		}
		
		// 뷰로 포워드
		String dispatchUrl = null;

		if (action.equals("book_input")) {
			dispatchUrl = "/jsp/dabook/insert.jsp";// 수정
		} else if (action.equals("book_save")) {
			dispatchUrl = "/jsp/dabook/insert.jsp";
		} else if (action.equals("book_search")) {
			dispatchUrl = "/jsp/dabook/list.jsp";
		} else if (action.equals("book_detail")) {		
			int div = Integer.parseInt(request.getParameter("div"));
			
			if(div==1){
				dispatchUrl = "/jsp/dabook/detail_manager.jsp";
			}else if(div==2){
				dispatchUrl = "/jsp/dabook/detail_member.jsp";
			}
		} else if (action.equals("book_search_name")) {
			dispatchUrl = "/jsp/dabook/listbybookname.jsp";
		} else if (action.equals("book_update")) {
			response.sendRedirect("book_search?reqPage=1");
			//더 이상 아래 코드 실행되지 않게 하기 위해서 return; 코드 넣음
			return;
		}
		
		RequestDispatcher rd = request.getRequestDispatcher(dispatchUrl);
		rd.forward(request, response);
	}
}
