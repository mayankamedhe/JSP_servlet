

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

/**
 * Servlet implementation class ConversationDetails
 */
@WebServlet("/ConversationDetails")
public class ConversationDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConversationDetails() {
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
		HttpSession session=request.getSession(false);  
		String name=request.getParameter("id");
		String password=(String)session.getAttribute("password");
		String name_sess=(String)session.getAttribute("id");
		String url=(String)session.getAttribute("url");
		String user=(String)session.getAttribute("user");
		String password_u=(String)session.getAttribute("password_u");
		
		
		
	    try (
			Connection conn = DriverManager.getConnection(url, user, password_u);
			Statement stmt = conn.createStatement(); 
		){
			conn.setAutoCommit(false);
			String threadd = request.getParameter("thread_id");
			int thread = Integer.valueOf(threadd);
			String input = "select text,timestamp,thread_id,post_id,uid from posts where thread_id=?\n"+
						   "order by timestamp desc";
			String query = "select * from password where ID = ? and password = ?";
        	
			try(PreparedStatement stmt1 = conn.prepareStatement(input);
				PreparedStatement stmtc = conn.prepareStatement(query);
				PreparedStatement stmt4 = conn.prepareStatement("select name from users where uid = ?"))
	   		{
				stmt1.setInt(1, thread);
				
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
				
				out.println("<html><body></body>"); 
				out.println("<form action=\"NewMessage\" method=\"get\">  \n" + 
		    		"Message:<input type=\"text\" name=\"message_content\"/><br/><br/>  \n" + 
					"<input type=\"hidden\" name=\"thread_id\" value="+thread+">"+
		    		"<button type=\"submit\" onclick=\"NewMessage\">send new message</button>  \n" + 
		    		"</form> </html> ");
	    		ResultSet rs = stmt1.executeQuery();
	    		out.println("<html><body><table>\n");
	        	ResultSetMetaData rsmd = rs.getMetaData();
	        	out.println("<tr>");
        		for (int i = 1; i <rsmd.getColumnCount(); i++) {
        			out.println("<th>");
        			out.println(rsmd.getColumnName(i));
        			out.println("</th>");
        		}
				out.println("<td>");
				out.println("name");
				out.println("</td>");
        		out.println("</tr>\n");
	        	while(rs.next()) {
	        		out.println("<tr>");
	        		int i;
	        		for (i = 1; i <rsmd.getColumnCount(); i++) {
	        			out.println("<td>");
	        			out.println(rs.getString(i));
	        			out.println("</td>");
	        		}
	        		stmt4.setString(1,rs.getString(i++));
    				ResultSet rr = stmt4.executeQuery();
    				while(rr.next()) {
    					out.println("<td>");
    					out.println(rr.getString(1));
    					out.println("</td>");
    				}
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
