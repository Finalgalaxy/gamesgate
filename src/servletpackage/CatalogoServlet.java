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
import java.util.ArrayList;

import models.*;

/**
 * Servlet implementation class HelloWorldServlets
 */

@WebServlet("/Catalogo")
public class CatalogoServlet extends HttpServlet{
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
				
				String str_q = FunzioniStatiche.filter(request.getParameter("q"));
				String str_genere=null;
				request.setAttribute("q", str_q);
				
				if(str_q != null && str_q != ""){	
					out.print("<h1>Risultati della ricerca:</h1>\n");
				}else{
					out.print("<h1>Lista dei giochi:</h1>\n");
					str_genere = request.getParameter("genere");
				}
			
				//ResultSet rs = db.executeQuery("SELECT * FROM VIDEOGAME "+str_q+"ORDER BY NOME_VIDEOGAME ASC;");
				ResultSet rs=null;
				if(str_q != null && str_q != ""){
					db.initPreparedStatement("SELECT * FROM VIDEOGAME WHERE NOME_VIDEOGAME LIKE ? ORDER BY NOME_VIDEOGAME ASC;");
					db.getPreparedStatement().setString(1, "%"+str_q+"%");
					rs=db.getPreparedStatement().executeQuery();
				}else if(str_genere != null && str_genere != ""){
					db.initPreparedStatement("SELECT * FROM VIDEOGAME NATURAL JOIN CLASSIFICATO WHERE NOME_GENERE=? ORDER BY NOME_VIDEOGAME ASC;");
					db.getPreparedStatement().setString(1, str_genere);
					rs=db.getPreparedStatement().executeQuery();
				}else{
					rs=db.executeQuery("SELECT * FROM VIDEOGAME ORDER BY NOME_VIDEOGAME ASC;");
				}
				ArrayList<Videogame> vids = new ArrayList<Videogame>();
				while(rs.next()){
					vids.add(new Videogame(rs.getString("NOME_VIDEOGAME"), rs.getString("immagine_str"),
							rs.getInt("numero_uscita"), rs.getInt("prezzo"),
							rs.getString("nome_produttore"), rs.getInt("anno_uscita")));
				}
				request.setAttribute("lista_videogames", vids);
				RequestDispatcher view = request.getRequestDispatcher("catalogo.jsp");
				view.forward(request, response);
			}
		}catch(SQLException e){
			out.print("Errore SQL");
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	
}


