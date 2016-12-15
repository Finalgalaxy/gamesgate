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

import models.Videogame;

/**
 * Servlet implementation class ProfiloServlet
 */
@WebServlet("/Profilo")
public class ProfiloServlet extends HttpServlet {
	private MySQLDatabaseAccess db;
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public void init() {
        db= new MySQLDatabaseAccess();
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
			ResultSet user=null;
			if(session.getAttribute("user") != null){
				user=db.executeQuery("SELECT * FROM UTENTE NATURAL JOIN ACCOUNT WHERE EMAIL_ACCOUNT = '"+session.getAttribute("user")+"'");
				user.next();
			}
			request.setAttribute("user", user);
			
			if(user != null){
				ArrayList<Videogame> vids=new ArrayList<Videogame>();
				ResultSet rs=db.executeQuery("SELECT * FROM VIDEOGAME NATURAL JOIN ACQUISTA WHERE ACQUISTA.EMAIL_ACCOUNT='"+session.getAttribute("user")+"';" );
				while(rs.next()){
					vids.add(new Videogame(rs.getString("nome_videogame"),rs.getString("immagine_str"),rs.getInt("numero_uscita"),rs.getInt("prezzo"),rs.getString("nome_produttore"),rs.getInt("anno_uscita")));
				}
				
				request.setAttribute("lista_videogames", vids);
			}
			RequestDispatcher view = request.getRequestDispatcher("profilo.jsp");
			view.forward(request, response);
	
		}catch(SQLException e){
			out.print("Errore SQL");
			e.printStackTrace();
		}
			
	
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	

}
