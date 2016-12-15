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
 * Servlet implementation class CaricaSoldiServlet
 */
@WebServlet("/CaricaSoldi")
public class CaricaSoldiServlet extends HttpServlet {
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
				if(user != null){
					String str_money = request.getParameter("amount");
					if(str_money != null && str_money != ""){
						if(Integer.parseInt(str_money) > 0){
							db.initPreparedStatement("UPDATE ACCOUNT SET saldo=saldo+"+str_money+" WHERE EMAIL_ACCOUNT='"+user.getString("EMAIL_ACCOUNT")+"' AND CF_UTENTE='"+user.getString("CF_UTENTE")+"';");
							int rows_manipulated=db.getPreparedStatement().executeUpdate();
							if(rows_manipulated>0) 	result_code=1;
							else					result_code=-1;
						}else{
							result_code=-2;
						}
					}else{
						result_code=0;
					}
				}
				request.setAttribute("result_code", result_code);
				RequestDispatcher view = request.getRequestDispatcher("carica_soldi.jsp");
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
