package servletpackage;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Servlet implementation class HelloWorldServlets
 */

@WebServlet("/Acquista")
public class AcquistaServlet extends HttpServlet{
	private MySQLDatabaseAccess db;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public void init(){
        db=new MySQLDatabaseAccess();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out= response.getWriter();
		try{
			HttpSession session = request.getSession();
			synchronized(session){
				ResultSet user=null;
				if(session.getAttribute("user") != null){
					user=db.executeQuery("SELECT * FROM UTENTE NATURAL JOIN ACCOUNT WHERE EMAIL_ACCOUNT = '"+session.getAttribute("user")+"'");
					user.next();
				}
				request.setAttribute("user", user);
				
				int result_code=0;
				ResultSet rs=null;
				String game_id = request.getParameter("id");
				if(game_id != null && game_id != ""){
					db.initPreparedStatement("SELECT NOME_VIDEOGAME, prezzo FROM VIDEOGAME WHERE NOME_VIDEOGAME "
							+ "NOT IN (SELECT NOME_VIDEOGAME FROM ACQUISTA "
							+ "WHERE EMAIL_ACCOUNT= '"+session.getAttribute("user")+"') "
							+ "AND NOME_VIDEOGAME=?;");
					db.getPreparedStatement().setString(1, game_id);
					rs=db.getPreparedStatement().executeQuery();
					if(rs.next()){
						int saldo=Integer.parseInt(user.getString("saldo"));
						int prezzo_gioco=Integer.parseInt(rs.getString("prezzo"));
						if(saldo >= prezzo_gioco){
							db.initPreparedStatement("INSERT INTO negoziovid.ACQUISTA VALUES (?, ?, ?)");
							db.getPreparedStatement().setString(1, user.getString("EMAIL_ACCOUNT"));
							db.getPreparedStatement().setString(2, rs.getString("NOME_VIDEOGAME"));
							db.getPreparedStatement().setDate(3, new java.sql.Date(java.util.Calendar.getInstance().getTime().getTime()));
							int rows_manipulated=db.getPreparedStatement().executeUpdate();
							if(rows_manipulated>0) result_code=1;
							else				   result_code=-1;
							
							db.initPreparedStatement("UPDATE ACCOUNT SET saldo=saldo-"+prezzo_gioco+" WHERE EMAIL_ACCOUNT = ?;");
							db.getPreparedStatement().setString(1, user.getString("EMAIL_ACCOUNT"));
							db.getPreparedStatement().executeUpdate();
						}else{result_code=-2;}
					}
				}else{result_code=-3;}
				
				request.setAttribute("result_code", result_code);
				RequestDispatcher view = request.getRequestDispatcher("acquista.jsp");
				view.forward(request, response);		
			}
		}catch(SQLException e){
			out.print("Errore SQL");
			e.printStackTrace();
		}
	}
}