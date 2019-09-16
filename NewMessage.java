

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Timestamp;
import java.util.Date;
/**
 * Servlet implementation class NewMessage
 */
@WebServlet("/NewMessage")
public class NewMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewMessage() {
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
		HttpSession session3=request.getSession(false);  
		String uid=(String)session3.getAttribute("uname2");
		String name=request.getParameter("id");
		String password=(String)session3.getAttribute("password");
		String name_sess=(String)session3.getAttribute("id");
		String url=(String)session3.getAttribute("url");
		String user=(String)session3.getAttribute("user");
		String password_u=(String)session3.getAttribute("password_u");
		

		try (
				Connection conn = DriverManager.getConnection(url, user, password_u);
				Statement stmt = conn.createStatement(); 
			){
				conn.setAutoCommit(false);
				//HttpSession session=request.getSession(false);  
				//String message_content=(String)session.getAttribute("message_content");
				String thread_id_s=request.getParameter("thread_id");
				int thread = Integer.valueOf(thread_id_s);
				
				String msg=request.getParameter("message_content");

				java.util.Date date= new java.util.Date();
				Timestamp time_h = new Timestamp(date.getTime());
				
				String input = "insert into posts(thread_id, uid, timestamp, text) values (?, ?, ?, ?)" ;
				String query = "select * from password where ID = ? and password = ?";
	        	
				try(PreparedStatement stmt1 = conn.prepareStatement(input);
					PreparedStatement stmtc = conn.prepareStatement(query))
		   		{
					
					stmt1.setInt(1, thread);
					stmt1.setTimestamp(3, time_h);
					
					stmtc.setString(1, name_sess);
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
					
					stmt1.setString(2, uid);
					stmt1.setString(4, msg);
		    		stmt1.executeUpdate();
		    		response.sendRedirect("ConversationDetails?thread_id="+thread_id_s);		    			
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
