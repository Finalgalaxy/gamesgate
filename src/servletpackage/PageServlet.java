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

/**
 * Servlet implementation class CaricaSoldiServlet
 */
@WebServlet("/Page")
public class PageServlet extends HttpServlet {
	private MySQLDatabaseAccess db;
	private static final long serialVersionUID = 1L;
    private ArrayList<String> list;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public void init() {
        db=new MySQLDatabaseAccess();
        list=new ArrayList<String>();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		list.add("inserisci_produttore.jsp");
		list.add("inserisci_videogame.jsp");
		
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
				
				String str_pagina = request.getParameter("name");
				if(str_pagina.endsWith(".jsp")){
					boolean posso=false;
					if(list.contains(str_pagina)){
						if(user!=null){
							if(user.getBoolean("is_admin"))	posso=true;
							else							posso=false;
						}else{
							posso=false;
						}
					}else{
						posso=true;
					}
					if(posso){
						RequestDispatcher view = request.getRequestDispatcher(str_pagina);
						view.forward(request, response);
					}else{
						response.sendError(403);
					}
				}else{
					response.sendRedirect("./");
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
