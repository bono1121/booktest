package controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

//@WebServlet(name = "HomeController", urlPatterns = { "/" })
public class HomeController extends HttpServlet {

	private static final long serialVersionUID = 1579L;

	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

		RequestDispatcher rd = request.getRequestDispatcher("/jsp/mainMenu.jsp");
		rd.forward(request, response);
	}
}
