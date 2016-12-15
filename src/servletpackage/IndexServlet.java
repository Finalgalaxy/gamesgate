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

import models.Genere;
import models.Produttore;
import models.Videogame;

/**
 * Servlet implementation class IndexServlet
 */
@WebServlet("/Index")
public class IndexServlet extends HttpServlet {
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
				
				ResultSet rs=null;
				rs=db.executeQuery("SELECT NOME_GENERE FROM GENERE;");
				ArrayList<Genere> generi = new ArrayList<Genere>();
				while(rs.next()){
					generi.add(new Genere(rs.getString("NOME_GENERE")));
				}
				
				rs=db.executeQuery("SELECT * FROM PRODUTTORE;");
				ArrayList<Produttore> prod = new ArrayList<Produttore>();
				while(rs.next()){
					prod.add(new Produttore(rs.getString("nome_produttore"), rs.getString("immagine_str"),
							 rs.getInt("anni_exp")));
				}
				
				rs=db.executeQuery("SELECT * FROM VIDEOGAME ORDER BY RAND() LIMIT 0,5");
				ArrayList<Videogame> vids = new ArrayList<Videogame>();
				while(rs.next()){
					vids.add(new Videogame(rs.getString("NOME_VIDEOGAME"), rs.getString("immagine_str"),
							rs.getInt("numero_uscita"), rs.getInt("prezzo"),
							rs.getString("nome_produttore"), rs.getInt("anno_uscita")));
				}
				
				request.setAttribute("lista_generi", generi);
				request.setAttribute("lista_produttori", prod);
				request.setAttribute("lista_videogames", vids);
				
				RequestDispatcher view = request.getRequestDispatcher("pagina_principale.jsp");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
