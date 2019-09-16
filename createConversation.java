

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import java.io.*;  
import javax.servlet.*;  
import javax.servlet.http.*;  


import java.sql.*;
import java.util.*;
import java.util.Scanner;


/**
 * Servlet implementation class home
 */
@WebServlet("/createConversation")
public class createConversation extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public createConversation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");  
		PrintWriter out = response.getWriter(); 
		HttpSession session=request.getSession(false);  
		String url=(String)session.getAttribute("url");
		String user=(String)session.getAttribute("user");
		String password_u=(String)session.getAttribute("password_u");
		
		
		    try (
				Connection conn = DriverManager.getConnection(url, user, password_u);
				Statement stmt = conn.createStatement(); 
			){
				conn.setAutoCommit(false);
				String name=request.getParameter("userid");
				String password=(String)session.getAttribute("password");
				String name2=(String)session.getAttribute("id");
				String input2 = "select * from users where uid=?";
				String input = "select * from conversations where uid1=? and uid2=?\n"+
								"union\n"+
								"select * from conversations where uid1=? and uid2=?";
				String input1 = "insert into conversations(uid1, uid2) values(?, ?)";
				String input5 = "insert into conversations(uid1, uid2) values(?, ?)";
				String query = "select * from password where ID = ? and password = ?";
            					
				try(PreparedStatement stmt1 = conn.prepareStatement(input);
					PreparedStatement stmt2 = conn.prepareStatement(input1);
					PreparedStatement stmt5 = conn.prepareStatement(input5);
					PreparedStatement stmt3 = conn.prepareStatement(input2);
					PreparedStatement stmtc = conn.prepareStatement(query))
		   		{
					
					stmt1.setString(1, name);
					stmt1.setString(2, name2);
					stmt1.setString(3, name2);
					stmt1.setString(4, name);
					
					stmt2.setString(1, name2);
					stmt2.setString(2, name);
					stmt5.setString(1, name);
					stmt5.setString(2, name2);
					
					stmt3.setString(1, name);
					
					stmtc.setString(1, name2);
					stmtc.setString(2, password);
					
					ResultSet check = stmtc.executeQuery();
	                conn.commit();
	                int size =0;
	                while (check.next())
	                {
                	  size = 1;
               	    }
	                
	                if(size <= 0)
	                {  
	                	response.sendRedirect("Login");
	                }
					ResultSet rsuser = stmt3.executeQuery();
					
					if(!rsuser.next()) {
						out.println("<html><body>Error:User Does Not Exist</body>"); 
						out.println("<form action=\"Home\" method=\"post\">  \n" + 
				    		"<button type=\"submit\" onclick=\"Home\">Home</button>  \n" + 
				    		"</form> </html> ");
					}else {	
						ResultSet rs = stmt1.executeQuery();
						
						if(!rs.next()) {
							if(name2.compareTo(name) < 0) {
								stmt2.executeUpdate();
							}else {
								stmt5.executeUpdate();
							}
							out.println("<html><body>Created New Conersation</body>"); 
							out.println("<form action=\"Home\" method=\"post\">  \n" + 
									"<button type=\"submit\" onclick=\"Home\">Home</button>  \n" + 
									"</form> </html> ");
						
						}else {
							out.println("<html><body>Conversation Already Exists</body>"); 
							out.println("<form action=\"Home\" method=\"post\">  \n" +  
									"<button type=\"submit\" onclick=\"Home\">Home</button>  \n" + 
									"</form> </html> ");
						}
					}
		    			
				}
				catch (Exception ex){
					conn.rollback();
					throw ex;
				} finally {
					conn.setAutoCommit(true);;
				}
			}	
			catch(Exception e) {
				e.printStackTrace();
			} 
		   
		out.close();  
	}   
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}