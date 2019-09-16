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
 * Servlet implementation class Login
 */
@WebServlet("/Login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String url = "jdbc:postgresql://localhost:5270/postgres";
    private static final String user = "mayankaa";
    private static final String password = "";
      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Login() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
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
		
		out.println("<HEAD><TITLE> Login Result</TITLE></HEAD>");
        out.println("<BODY>");
        
        if(request.getParameter("id") == null || request.getParameter("pass") == null) 
		{
			//out.println("ID: <input type=\"text\" id = \"id\"> Password: <input type=\"text\" pass = \"pass\"> <input type=\"submit\" value = \"Submit\">");
		      
		      out.println("<form action=\"Login\" method=\"post\">");
		      out.println("ID: <input type=\"text\" name = \"id\">");
		      out.println("Password: <input type=\"password\" name = \"pass\">");
		      out.println("<button onclick=\"Login\" type=\"submit\"> Submit </button>");
		          
		}
		
		else
		{
			String id2 = request.getParameter("id"); 
			String pass2 = request.getParameter("pass"); 
			//out.println(id2);
			//out.println("CHECK");
			HttpSession session = request.getSession(false);
			String url=(String)session.getAttribute("url");
			String user=(String)session.getAttribute("user");
			String password_u=(String)session.getAttribute("password_u");
			
	        try (Connection conn = DriverManager.getConnection(url, user, password_u))
	        {
	            conn.setAutoCommit(false);
	            out.println("connection done");
	            //String query = ;
            	
	            try(PreparedStatement stmt = conn.prepareStatement("select * from password where ID = ? and password = ?")) {
	            	
	            	stmt.setString(1, id2);
	            	stmt.setString(2, pass2);
	            	ResultSet check = stmt.executeQuery();
	                conn.commit();
	                int size =0;
	                while (check.next())
	                {
                	  size = 1;
               	    }
	                
	                if(size <= 0)
	                {
	                	session.setAttribute("id", id2);
	                	session.setAttribute("password", pass2);
	                	//response.sendRedirect("Home");  
	                	response.sendRedirect("Login");
	                }
	                else
	                {
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
