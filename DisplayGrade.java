

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DisplayGrade
 */
@WebServlet("/DisplayGrade")
public class DisplayGrade extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String url = "jdbc:postgresql://localhost:5270/postgres";
    private static final String user = "mayanka";
    private static final String password = "";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DisplayGrade() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String id = (String) request.getSession(false).getAttribute("id");
	
		try (Connection conn = DriverManager.getConnection(url, user, password))
	    {
	        conn.setAutoCommit(false);

	        try(Statement stmt = conn.createStatement()) 
	        {
	        	//out.println("connedtion made"+ id);
	        	// display the course_id, title, section id, semester, year, and the grade obtained
	        	String query = "select takes.course_id, title, sec_id, semester, year, grade from takes, course where ID = '" + id + "' and takes.course_id = course.course_id";
	        	ResultSet table = stmt.executeQuery(query);
	        	
	        	out.println("<table><tr>");
	        	out.println("<th>Course ID</th>");
	        	out.println("<th>Title</th>");
	        	out.println("<th>Section ID</th>");
	        	out.println("<th>Semester</th>");
	        	out.println("<th>Year</th>");
	        	out.println("<th>Grade</th></tr>");
	        	while(table.next())
	        	{
//		        	String name = table.getString(1);
//		        	String dept_name = table.getString(2);
		        	
		        	out.println("<tr>");
		        	out.println("<th>" + table.getString(1) +"</th>");
		        	out.println("<th>" + table.getString(2) +"</th>");
		        	out.println("<th>" + table.getString(3) +"</th>");
		        	out.println("<th>" + table.getString(4) +"</th>");
		        	out.println("<th>" + table.getString(5) +"</th>");
		        	out.println("<th>" + table.getString(6) +"</th></tr>");
		        	
		        }
	        	out.println("</table>");
	        	conn.commit();
	        	//out.println("dbg3");
	        }
	        catch(Exception ex)
	        {
	            conn.rollback();
	            throw ex;
	        }
	        finally
	        {
	            conn.setAutoCommit(true);
	        }
	    } catch (Exception e) 
	    {
	        e.printStackTrace();
	    }

		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
