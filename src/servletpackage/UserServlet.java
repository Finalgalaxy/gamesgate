package servletpackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/User")
public class UserServlet extends HttpServlet {
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
		// TODO Auto-generated method stub
		
		response.setContentType("text/html");
		HttpSession session = request.getSession();
		RequestDispatcher view=null;
		
		String str_mode = request.getParameter("mode");
		if(str_mode != null && str_mode != ""){
			if(str_mode.equals("login")){
				view = request.getRequestDispatcher("accedi.jsp");
				request.setAttribute("result_code", 0);
			}else if(str_mode.equals("register")){
				view = request.getRequestDispatcher("registrati.jsp");
			}
		}else{
			String str_action = request.getParameter("action");
			if(str_action != null){
				if(str_action.equals("exit")){
					session.removeAttribute("user");
					view = request.getRequestDispatcher("esci.jsp");
				}
			}else{
				PrintWriter out= response.getWriter();
				try{
					ResultSet user=null;
					if(session.getAttribute("user") != null){
						user=db.executeQuery("SELECT * FROM UTENTE NATURAL JOIN ACCOUNT WHERE EMAIL_ACCOUNT = '"+session.getAttribute("user")+"'");
						user.next();
					}
					request.setAttribute("user", user);
					view = request.getRequestDispatcher("accedi.jsp");
				}catch(SQLException e){
					out.print("Errore SQL");
					e.printStackTrace();
				}
			}
			request.setAttribute("result_code", 0);
		}
		view.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
			
			String str_action = request.getParameter("action");
			if(str_action != null){
				if(str_action.equals("accesso")){
					String username = request.getParameter("username");
					String password = request.getParameter("password");
					int result_code=0;
					if(username != null && username != "" && password != null && password != ""){
						db.initPreparedStatement("SELECT * FROM ACCOUNT WHERE EMAIL_ACCOUNT = ? AND password = ?");
						db.getPreparedStatement().setString(1, username);
						db.getPreparedStatement().setString(2, password);
						ResultSet rs=db.getPreparedStatement().executeQuery();
						if(rs.next()){
							result_code=1;
							session.setAttribute("user",rs.getString("EMAIL_ACCOUNT"));
						}else{result_code=-1;}
					}else{result_code=0;}
					
					request.setAttribute("result_code", result_code);
					RequestDispatcher view = request.getRequestDispatcher("accedi.jsp");
					view.forward(request, response);
				}else if(str_action.equals("registrazione")){
					String CF_utente = request.getParameter("CF_utente");
					String nome = request.getParameter("nome");
					String cognome = request.getParameter("cognome");
					String via = request.getParameter("via");
					String cap = request.getParameter("cap");
					String n_civico = request.getParameter("n_civico");
					String username = request.getParameter("username");
					String password = request.getParameter("password");
					
					db.initPreparedStatement("INSERT INTO UTENTE VALUES (?, ?, ?, ?, ?, ?, 0)");
					db.getPreparedStatement().setString(1, CF_utente);
					db.getPreparedStatement().setString(2, nome);
					db.getPreparedStatement().setString(3, cognome);
					db.getPreparedStatement().setString(4, via);
					db.getPreparedStatement().setInt(5, Integer.parseInt(cap));
					db.getPreparedStatement().setInt(6, Integer.parseInt(n_civico));
					int n_rows=db.getPreparedStatement().executeUpdate();
					if(n_rows > 0){
						db.initPreparedStatement("INSERT INTO ACCOUNT VALUES (?, ?, ?, 0)");
						db.getPreparedStatement().setString(1, username);
						db.getPreparedStatement().setString(2, CF_utente);
						db.getPreparedStatement().setString(3, password);
						n_rows=db.getPreparedStatement().executeUpdate();
						if(n_rows > 0){
							System.out.println("Fatto");
							response.sendRedirect("./User?mode=login");
						}
						else 			System.out.println("Fail");
					}else{
						System.out.println("Fail");
					}
					
				}else{
					out.print("<h2>Azione non consentita.</h2>");
				}
			}
		}catch(SQLException e){
			out.print("Errore SQL");
			e.printStackTrace();
		}
	}

}
