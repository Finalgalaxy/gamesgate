package servletpackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import models.Achievement;

/**
 * Servlet implementation class AchievementServlet
 */
@WebServlet("/Achievements")
public class AchievementServlet extends HttpServlet {
	private MySQLDatabaseAccess db;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public void init() {
        db=new MySQLDatabaseAccess();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
				
				if(user != null){
					String game_id = request.getParameter("id");
					request.setAttribute("id", game_id);
					if(game_id != null && game_id != ""){
						request.setAttribute("game_id", game_id);
						db.initPreparedStatement("SELECT * FROM CONSEGUIMENTO_ACHIEVEMENT NATURAL JOIN ACHIEVEMENT WHERE ACHIEVEMENT.NOME_VIDEOGAME=? AND CONSEGUIMENTO_ACHIEVEMENT.EMAIL_ACCOUNT=?;");
						db.getPreparedStatement().setString(1, game_id);
						db.getPreparedStatement().setString(2, user.getString("EMAIL_ACCOUNT"));
						ResultSet rs = db.getPreparedStatement().executeQuery();
						ArrayList<Achievement> achievements = new ArrayList<Achievement>();
						while(rs.next()){
							achievements.add(new Achievement(rs.getString("descrizione"), rs.getString("tipo"))); 
						}
						request.setAttribute("lista_achievements", achievements);
					}
				}
				RequestDispatcher view = request.getRequestDispatcher("achievements.jsp");
				view.forward(request, response);
			}
		}catch(SQLException e){
			out.print("Errore SQL");
			e.printStackTrace();
		}
	}


}
