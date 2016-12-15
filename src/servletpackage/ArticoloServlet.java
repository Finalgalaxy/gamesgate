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

import models.*;

/**
 * Servlet implementation class HelloWorldServlets
 */

@WebServlet("/Articolo")
public class ArticoloServlet extends HttpServlet{
	private MySQLDatabaseAccess db;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public void init(){
        db=new MySQLDatabaseAccess();
        // TODO Auto-generated constructor stub
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
				
				String str_query = "SELECT * FROM VIDEOGAME";
				String str_mode = request.getParameter("mode");
				String game_id = request.getParameter("id");
			
				ResultSet rs=null;
				if(str_mode != null && str_mode != ""){
					str_query+=" ORDER BY RAND() LIMIT 1";
					rs=db.executeQuery(str_query);
				}else{
					if(game_id != null && game_id != ""){
						str_query+=" WHERE NOME_VIDEOGAME=?";
						db.initPreparedStatement(str_query);
						db.getPreparedStatement().setString(1, game_id);
						rs=db.getPreparedStatement().executeQuery();
					}
				}
				
				if(rs != null){
					if(rs.next()){
						Videogame vid = new Videogame(rs.getString("NOME_VIDEOGAME"), rs.getString("immagine_str"),
								rs.getInt("numero_uscita"), rs.getInt("prezzo"),
								rs.getString("nome_produttore"), rs.getInt("anno_uscita"));
						request.setAttribute("videogame", vid);
						request.setAttribute("user", user);
						if(user != null){
							rs=db.executeQuery("SELECT * FROM ACQUISTA WHERE EMAIL_ACCOUNT='"+
											session.getAttribute("user")+
											"'");//c'è qualquadra che non cosa
							boolean trovato=false;
							while(rs.next() && !trovato){
								if(vid.getNome().equals(rs.getString("NOME_VIDEOGAME"))) trovato=true;
							}
							request.setAttribute("trovato", trovato);
						}
						RequestDispatcher view = request.getRequestDispatcher("articolo.jsp");
						view.forward(request, response);
					}
				}
			}
		}catch(SQLException e){
			out.print("Errore SQL");
			e.printStackTrace();
		}
		
		
		/*
		try{
			db.initPreparedStatement("SELECT * FROM VIDEOGAME WHERE NOME_VIDEOGAME = '?'");
			db.getPreparedStatement().setString(1, nome);
			ResultSet rs=db.getPreparedStatement().executeQuery();
			if(rs.next()){
				Videogame vid = new Videogame(rs.getString("NOME_VIDEOGAME"), rs.getString("immagine_str"),
												rs.getInt("numero_uscita"), rs.getInt("prezzo"),
												rs.getString("nome_produttore"), rs.getInt("anno_uscita"));
				request.setAttribute("videogame", vid);
				RequestDispatcher view = request.getRequestDispatcher("result.jsp");
				view.forward(request, response);
			}else{
				out.print("Errore: nome del gioco non valido.");
			}
		}catch(SQLException e){
			out.print("Errore SQL");
			e.printStackTrace();
		}*/
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
}


