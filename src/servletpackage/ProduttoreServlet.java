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

@WebServlet("/Produttore")
public class ProduttoreServlet extends HttpServlet{
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
				
				String producer_name = request.getParameter("name");
				if(producer_name != null && producer_name != ""){
					db.initPreparedStatement("SELECT * FROM PRODUTTORE WHERE NOME_PRODUTTORE=?");
					db.getPreparedStatement().setString(1, producer_name);
					ResultSet rs=db.getPreparedStatement().executeQuery();
					Produttore produttore = null;
					if(rs.next()) produttore = new Produttore(rs.getString("NOME_PRODUTTORE"),
																rs.getString("immagine_str"),
																rs.getInt("anni_exp")
																);
					request.setAttribute("produttore", produttore);
					if(produttore != null){
						rs=db.executeQuery("SELECT * FROM VIDEOGAME WHERE nome_produttore='"+produttore.getNome()+"' ORDER BY NOME_VIDEOGAME ASC;");
						ArrayList<Videogame> vids = new ArrayList<Videogame>();
						while(rs.next()){
							vids.add(new Videogame(rs.getString("NOME_VIDEOGAME"), rs.getString("immagine_str"),
									rs.getInt("numero_uscita"), rs.getInt("prezzo"),
									produttore.getNome(), rs.getInt("anno_uscita")));
						}
						request.setAttribute("lista_videogames", vids);
						RequestDispatcher view = request.getRequestDispatcher("produttore.jsp");
						view.forward(request, response);
					}
				}
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


