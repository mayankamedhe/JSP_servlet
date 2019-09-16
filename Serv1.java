import java.sql.*;
import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class Serv1
 */
@WebServlet("/Serv1")
public class Serv1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String url = "jdbc:postgresql://localhost:5270/postgres";
    private static final String user = "mayanka";
    private static final String password = "";
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Serv1() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
//		response.getWriter().append("Served at: ").append(request.getContextPath());
	     PrintWriter out = response.getWriter();
	     //out.println("<html><body>Hello world</body></html>");
//	     String name = request.getParameter("name"); 
//	     out.println(name);
	     
	     //request.getParameter(null);
	     //out.println("ID: <input type=\"text\" id = \"id\"> Password: <input type=\"text\" pass = \"pass\"> <input type=\"submit\" value = \"Submit\">");
	     doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		out.println("<HEAD><TITLE> Query Result</TITLE></HEAD>");
        out.println("<BODY>");
		
//		out.println(id2);
//		out.println(pass2);
		if(request.getParameter("id") == null || request.getParameter("pass") == null) 
		{
			//out.println("ID: <input type=\"text\" id = \"id\"> Password: <input type=\"text\" pass = \"pass\"> <input type=\"submit\" value = \"Submit\">");
		      
		      out.println("<form action=\"Serv1\" method=\"post\">");
		      out.println("ID: <input type=\"text\" name = \"id\">");
		      out.println("Password: <input type=\"password\" name = \"pass\">");
		      out.println("<button onclick=\"Serv1\" type=\"submit\"> Submit </button>");
		          
		}
		
		else
		{
			String id2 = request.getParameter("id"); 
			String pass2 = request.getParameter("pass"); 
			//out.println(id2);
			//System.out.println("CHECK");
	        try (Connection conn = DriverManager.getConnection(url, user, password))
	        {
	            conn.setAutoCommit(false);

	            try(Statement stmt = conn.createStatement()) {
	            	
	            	String query = "select * from password where ID = '" + id2 + "' and password = '" + pass2 + "'";
	            	ResultSet check = stmt.executeQuery(query);
	                conn.commit();
	                int size =0;
	                while (check.next())
	                {
                	  size = check.getInt(1);
               	    }
	                
	                if(size <= 0)
	                {
	                	response.sendRedirect("Serv1");
	                }
	                else
	                {
	                	//out.println("Error!!");
	                	HttpSession session = request.getSession();
	                	session.setAttribute("id", id2);
	                	session.setAttribute("password", pass2);
	                	response.sendRedirect("Home");  
	                }

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
		
		out.println("</BODY>");
		
	}
	
}
