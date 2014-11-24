package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/attend/add")
public class AddServlet extends HttpServlet {

	@Override
	protected void doGet(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>학생 등록</title></head>");
		out.println("<body><h1>학생 등록</h1>");
		out.println("<form action='add' method='post'>");
		out.println("이름: <input type='text' name='name'><br>");
		out.println("학번: <input type='text' name='no'><br>");
		out.println("소속: <input type='text' name='major'><br>");
		out.println("나이: <input type='text' name='age'><br>");
		out.println("<input type='submit' value='추가'>");
		out.println("<input type='reset' value='취소'>");
		out.println("</form>");
		out.println("</body></html>");
	}
	
	@Override
	protected void doPost(
			HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Connection conn = null;
		PreparedStatement stmt = null;

		request.setCharacterEncoding("UTF-8");
		try {
			ServletContext sc=this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url") ,
					sc.getInitParameter("username") ,
					sc.getInitParameter("password"));

			stmt = conn.prepareStatement(
					"INSERT INTO MEMBERS(SNO,SNAME,SMAJOR,SAGE,ATTD,LATE,ABSENT)"
					+ " VALUES (?,?,?,?,0,0,0)");
			stmt.setString(1, request.getParameter("no"));
			stmt.setString(2, request.getParameter("name"));
			stmt.setString(3, request.getParameter("major"));
			stmt.setString(4, request.getParameter("age"));
			stmt.executeUpdate();
			
			response.sendRedirect("list");
			response.setContentType("text/html; charset=UTF-8");

		} catch (Exception e) {
			throw new ServletException(e);
			
		} finally {
			try {if (stmt != null) stmt.close();} catch(Exception e) {}
			try {if (conn != null) conn.close();} catch(Exception e) {}
		}

	}
}
