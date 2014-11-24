package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/attend/check")
public class CheckServlet extends HttpServlet{
	
	@Override
	protected void doPost(
				HttpServletRequest request, HttpServletResponse response	)	
				throws ServletException, IOException {
				
		Connection conn =null;
		Statement stmt = null;
		
		request.setCharacterEncoding("UTF-8");
		
		try {
			ServletContext sc=this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			
			conn = DriverManager.getConnection(
						sc.getInitParameter("url"),
						sc.getInitParameter("username"),
						sc.getInitParameter("password"));
			stmt = conn.createStatement();
			String Param = request.getParameter("check");			
			
			if(Param.equals("attd")){
			stmt.executeUpdate(
					"UPDATE MEMBERS SET ATTD=ATTD+1 " +" WHERE SNO="+request.getParameter("no"));				
			}
			if(Param.equals("late")){
				stmt.executeUpdate(
						"UPDATE MEMBERS SET LATE=LATE+1 " +" WHERE SNO="+request.getParameter("no"));				
				}
			if(Param.equals("absent")){
				stmt.executeUpdate(
						"UPDATE MEMBERS SET ABSENT=ABSENT+1 " +" WHERE SNO="+request.getParameter("no"));				
				}
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			out.println("<html><head><title>출석저장결과</title></head>");
			out.println("<meta http-equiv='Refresh content='1; url=list'>");
			out.println("<body>");
			out.println("<p>출석정보가 저장되었습니다!</p>");
			out.println("</body></html>");
			response.addHeader("Refresh" , "1;url=list");
			
		}catch (Exception e){
				throw new ServletException(e);
		}finally{
				try {if (stmt !=null) stmt.close(); } catch (Exception e) {}
				try {if (conn !=null) conn.close(); } catch (Exception e) {}
		}
	}

}