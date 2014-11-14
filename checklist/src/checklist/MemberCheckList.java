package checklist;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class MemberCheckList extends HttpServlet{

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
					throws ServletException, IOException{
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try{
			ServletContext sc = this.getServletContext();
			Class.forName(sc.getInitParameter("driver"));
			conn = DriverManager.getConnection(
					sc.getInitParameter("url"), 
					sc.getInitParameter("user"),
					sc.getInitParameter("password")
			);
			
			stmt = conn.prepareStatement("SELECT MNO,MNAME,EMAIL,AGE" + 
										 " FROM MEMBER" + 
										 " ORDER BY MNO ASC");
			rs = stmt.executeQuery();
			
			response.setContentType("text/html; charset=UTF-8");
			PrintWriter out = response.getWriter();
			
			out.println("<html><head><title>출석관리부</title></head>");
			out.println("<body>");
			while(rs.next()){
				out.println(
						rs.getInt("MNO") + "," +
						rs.getString("MNAME") + "," +
						rs.getString("EMAIL") + "," +
						rs.getString("AGE") + "<br>");
			}
			out.println("</body></html>");
			
		}catch(Exception e){
			throw new ServletException(e);
		}finally{
			try{if(rs!=null)rs.close();} catch(Exception e){}
			try{if(stmt!=null)stmt.close();} catch(Exception e){}
			try{if(conn!=null)conn.close();} catch(Exception e){}
		}
	}

}
