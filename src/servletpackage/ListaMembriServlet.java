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

import models.Utente;

/**
 * Servlet implementation class ListaMembriServlet
 */
@WebServlet("/ListaMembri")
public class ListaMembriServlet extends HttpServlet {
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
			synchronized(session){
				ResultSet user=null;
				if(session.getAttribute("user") != null){
					user=db.executeQuery("SELECT * FROM UTENTE NATURAL JOIN ACCOUNT WHERE EMAIL_ACCOUNT = '"+session.getAttribute("user")+"'");
					user.next();
				}
				request.setAttribute("user", user);
				
				ArrayList<Utente> utenti= new ArrayList<Utente>();
				ResultSet rs=db.executeQuery("SELECT * FROM UTENTE ORDER BY cognome ASC;");
				while(rs.next()){
					utenti.add(new Utente(rs.getString("CF_UTENTE"), rs.getString("nome"),rs.getString("cognome")));
				}
				request.setAttribute("lista_utenti", utenti);
				RequestDispatcher view = request.getRequestDispatcher("lista_membri.jsp");
				view.forward(request, response);
			}
		}catch(SQLException e){
			out.print("Errore SQL");
			e.printStackTrace();
		}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	}	

}
