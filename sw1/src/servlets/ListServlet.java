package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@SuppressWarnings("serial")
@WebServlet("/attend/list")
public class ListServlet extends HttpServlet{

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	//데이터베이스 관련 객체의 참조 변수 선언
	Connection conn=null;
	Statement stmt=null;
	ResultSet rs=null;
	request.setCharacterEncoding("UTF-8");
	try{
		
		ServletContext sc = this.getServletContext();
		Class.forName(sc.getInitParameter("driver"));
		conn = DriverManager.getConnection(
					sc.getInitParameter("url"),
					sc.getInitParameter("username"),
					sc.getInitParameter("password")); 
		
		stmt = conn.createStatement();
		rs = stmt.executeQuery(
						"select SNO,SNAME,SAGE,SMAJOR" +
						" from MEMBERS" +
						" order by SNO ASC");
		
		response.setContentType("text/html; charset=UTF-8");
		
		PrintWriter out = response.getWriter();
		out.println("<html><head><title>학생목록</title></head>");
		out.println("<body><center><h1>학생목록</h1>");
		out.println("<h3>학번 &emsp;&emsp; 이름 &emsp;&emsp; 소속 &emsp;&emsp; 나이 &emsp; &emsp;&emsp;&emsp;</h3>");
		while(rs.next()){
			out.println( 
					rs.getInt("SNO")+"  |  "+rs.getString("SNAME") + "  |  "+rs.getString("SMAJOR") + "  |  "+rs.getInt("SAGE") + "  |  "+
					"<a href='update?no=" + rs.getInt("SNO") + "'>" + "[조회]"+"</a>"+
					"<a href='delete?no=" + rs.getInt("SNO") +"'>"+"[삭제]"+"</a><br>"
					+"<form action='check' method='post'><input type='hidden' name='no' value='"+ rs.getInt("SNO") +"'   출석<input type='radio' name='check' value='attd' checked>"+
														"출석<input type='radio' name='check' value='attd'>"+
														"지각<input type='radio' name='check' value='late'>"+
														"결석<input type='radio' name='check' value='absent'>"+
														"<input type='submit' value='저장'>"+"</form>");
		}
		out.println("<p><a href='add'>학생 등록</a></p>");
		out.println("</center></body></html>");
		}catch (Exception e){
			throw new ServletException(e);
		}finally{
			try {if (rs !=null) rs.close(); } catch (Exception e) {}
			try {if (stmt !=null) stmt.close(); } catch (Exception e) {}
			try {if (conn !=null) conn.close(); } catch (Exception e) {}
		}
	}
}
