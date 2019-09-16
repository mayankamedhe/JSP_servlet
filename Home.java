

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
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
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
				String name=(String)session.getAttribute("id");
				String password=(String)session.getAttribute("password");
				
				String conversation = "with res as\n" + 
						"(select thread_id,max(timestamp) from posts\n" + 
						"group by thread_id),\n" + 
						"result as\n" + 
						"(select res.thread_id,text,res.max from res,posts\n" + 
						"where res.thread_id=posts.thread_id and res.max=posts.timestamp)\n" + 
						"\n" + 
						"select uid1,uid2,conversations.thread_id,result.max,result.text from result right outer join conversations\n" + 
						"on result.thread_id=conversations.thread_id where uid1=? or uid2=?\n";
						
				String input = "select * from users";
				String query = "select * from password where ID = ? and password = ?";
            	
				try(PreparedStatement stmt1 = conn.prepareStatement(conversation);
					PreparedStatement stmt2 = conn.prepareStatement(input);
					PreparedStatement stmtc = conn.prepareStatement(query);
					PreparedStatement stmt4 = conn.prepareStatement("select name from users where uid = ?"))
		   		{
					stmt1.setString(1, name);
					stmt1.setString(2, name);
					stmtc.setString(1, name);
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
	                
	                out.println("<html><body></body>"); 
					out.println("<form action=\"createConversation\" method=\"post\">  \n" + 
			    		"ID:<input type=\"text\" name=\"userid\"/><br/><br/>  \n" +  
			    		"<button type=\"submit\" onclick=\"createConversation\">create conversation</button>  \n" + 
			    		"</form> </html> "); 

	                
		    		ResultSet rs = stmt1.executeQuery();
		    		ResultSet rs2 = stmt2.executeQuery();
		    			out.println("<html><body><table>\n");
			        	ResultSetMetaData rsmd = rs.getMetaData();
			        	out.println("<tr>");
	        			out.println("<th>");
	        			out.println("name");
	        			out.println("</th>");
		        		for (int i = 3; i <rsmd.getColumnCount()+1; i++) {
		        			out.println("<th>");
		        			out.println(rsmd.getColumnName(i));
		        			out.println("</th>");
		        		}
		        		out.println("<th>");
		        		out.println("Details");
		        		out.println("</th>");
		        		out.println("</tr>\n");
			        	while(rs.next()) {
			        		out.println("<tr>");
			        		int i;
			        		String idn;
			        		if(rs.getString(1).equals(name)) {
			        				stmt4.setString(1,rs.getString(2));
			        				ResultSet rr = stmt4.executeQuery();
			        				while(rr.next()) {
			        					out.println("<td>");
				        				out.println(rr.getString(1));
				        				out.println("</td>");
			        				}

			        				
			        		}else {
			        			stmt4.setString(1,rs.getString(2));
	        					ResultSet rr = stmt4.executeQuery();
	        					while(rr.next()) {
			        					out.println("<td>");
				        				out.println(rr.getString(1));
				        				out.println("</td>");
	        					}
			        		}
			        		for (i = 3; i <rsmd.getColumnCount()+1; i++) {
			        			out.println("<td>");
			        			out.println(rs.getString(i));
			        			out.println("</td>");
			        		}
			        		out.println("<td>");
			        		out.println("<a href=ConversationDetails?thread_id="+rs.getString(3)+">Thread</a>  \n");
			        		out.println("</td>");
			        		out.println("</tr>\n");
			        	}
			        	out.println("</table>");
			        	out.println("</body></html>");
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