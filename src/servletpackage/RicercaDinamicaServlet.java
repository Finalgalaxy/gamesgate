package servletpackage;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/RicercaDinamicaServlet")
public class RicercaDinamicaServlet extends HttpServlet {
	private MySQLDatabaseAccess db;
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public void init(){
        db=new MySQLDatabaseAccess();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/xml");
		PrintWriter out= response.getWriter();
		try{
			StringBuffer output_xml = new StringBuffer("");
			output_xml.append("<catalogo>");
			
			String str_key = request.getParameter("str_key");
			if(str_key != ""){
				db.initPreparedStatement("SELECT * FROM VIDEOGAME WHERE NOME_VIDEOGAME LIKE ? ORDER BY NOME_VIDEOGAME ASC;");
				db.getPreparedStatement().setString(1, "%"+str_key+"%");
				ResultSet rs=db.getPreparedStatement().executeQuery();
				while(rs.next()){
					output_xml.append("<videogame>");
						output_xml.append("<nome>"+rs.getString("NOME_VIDEOGAME")+"</nome>");
						output_xml.append("<img>"+rs.getString("immagine_str")+"</img>");
						output_xml.append("<numero>"+rs.getInt("numero_uscita")+"</numero>");
						output_xml.append("<prezzo>"+rs.getInt("prezzo")+"</prezzo>");
						output_xml.append("<produttore>"+rs.getString("nome_produttore")+"</produttore>");
						output_xml.append("<anno>"+rs.getInt("anno_uscita")+"</anno>");
					output_xml.append("</videogame>");
				}
			}
			
			output_xml.append("</catalogo>");
			
			try {
				Thread.sleep(200);
			} catch(InterruptedException e) {}
			
			out.write(output_xml.toString());
		}catch(SQLException e){
			out.print("Errore SQL");
			e.printStackTrace();
		}
	}
}