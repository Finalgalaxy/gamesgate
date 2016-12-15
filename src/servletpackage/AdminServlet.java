package servletpackage;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

/**
 * Servlet implementation class AdminServlet
 */
@WebServlet("/Admin")
@MultipartConfig(
	fileSizeThreshold = 1024*1024*2,
	maxFileSize = 1024*1024*10,
	maxRequestSize = 1024*1024*50
)
public class AdminServlet extends HttpServlet {
	private MySQLDatabaseAccess db;
	
	private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public void init() {
        // TODO Auto-generated constructor stub
        db=new MySQLDatabaseAccess();
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
			synchronized(session){
				ResultSet user=null;
				if(session.getAttribute("user") != null){
					user=db.executeQuery("SELECT * FROM UTENTE NATURAL JOIN ACCOUNT WHERE EMAIL_ACCOUNT = '"+session.getAttribute("user")+"'");
					user.next();
				}
				request.setAttribute("user", user);
				
				if(user != null){
					if(user.getBoolean("is_admin") == true){
						String str_action = request.getParameter("action");
						if(str_action.equals("delete_videogame")){
							String id = request.getParameter("id");
							
							String str_query = "SELECT * FROM VIDEOGAME WHERE NOME_VIDEOGAME=?";
							db.initPreparedStatement(str_query);
							db.getPreparedStatement().setString(1, id);
							ResultSet rs=db.getPreparedStatement().executeQuery();
							
							if(rs != null){
								if(rs.next()){
									db.executeQuery("SET FOREIGN_KEY_CHECKS=0;");
									
									db.initPreparedStatement("UPDATE ACCOUNT "+
										"SET saldo=saldo+"+rs.getInt("prezzo")+" "+
										"WHERE EMAIL_ACCOUNT IN "+
											"(SELECT ACQUISTA.EMAIL_ACCOUNT "+
											"FROM ACQUISTA "+
											"WHERE ACQUISTA.NOME_VIDEOGAME=?);");
									
									db.getPreparedStatement().setString(1, id);
									db.getPreparedStatement().executeUpdate();
									
									db.initPreparedStatement("DELETE FROM ACQUISTA WHERE NOME_VIDEOGAME = ?");
									db.getPreparedStatement().setString(1, id);
									db.getPreparedStatement().executeUpdate();
									
									db.initPreparedStatement("DELETE a,c FROM ACHIEVEMENT AS a "+
											"INNER JOIN CONSEGUIMENTO_ACHIEVEMENT AS c ON a.COD_ACHIEVEMENT = c.COD_ACHIEVEMENT "+
											"WHERE a.NOME_VIDEOGAME = ?;");
									db.getPreparedStatement().setString(1, id);
									db.getPreparedStatement().executeUpdate();
									
									db.initPreparedStatement("DELETE FROM VIDEOGAME WHERE NOME_VIDEOGAME = ?");
									db.getPreparedStatement().setString(1, id);
									db.getPreparedStatement().executeUpdate();
									
									db.executeQuery("SET FOREIGN_KEY_CHECKS=1;");
								}
							}
							
							db.executeQuery("SET FOREIGN_KEY_CHECKS=0;");
							
							db.initPreparedStatement("DELETE FROM ACQUISTA WHERE NOME_VIDEOGAME = ?");
							db.getPreparedStatement().setString(1, id);
							db.getPreparedStatement().executeUpdate();
							
							db.initPreparedStatement("DELETE a,c FROM ACHIEVEMENT AS a "+
									"INNER JOIN CONSEGUIMENTO_ACHIEVEMENT AS c ON a.COD_ACHIEVEMENT = c.COD_ACHIEVEMENT "+
									"WHERE a.NOME_VIDEOGAME = ?;");
							db.getPreparedStatement().setString(1, id);
							db.getPreparedStatement().executeUpdate();
							
							db.initPreparedStatement("DELETE FROM VIDEOGAME WHERE NOME_VIDEOGAME = ?");
							db.getPreparedStatement().setString(1, id);
							db.getPreparedStatement().executeUpdate();
							
							db.executeQuery("SET FOREIGN_KEY_CHECKS=1;");
						}else if(str_action.equals("delete_user")){
							String id = request.getParameter("CF_utente");
							db.executeQuery("SET FOREIGN_KEY_CHECKS=0;");
							db.initPreparedStatement("DELETE a,b,c,d,e "+
										"FROM UTENTE AS a INNER JOIN "+
											"TELEFONO AS b INNER JOIN "+
											"ACCOUNT AS c INNER JOIN "+
											"CARICAMENTO AS d INNER JOIN "+
											"CONSEGUIMENTO_ACHIEVEMENT AS e "+
										"ON "+
											"a.CF_UTENTE=b.CF_UTENTE AND "+
											"b.CF_UTENTE=c.CF_UTENTE AND "+
											"c.EMAIL_ACCOUNT=d.EMAIL_ACCOUNT AND "+
											"d.EMAIL_ACCOUNT=e.EMAIL_ACCOUNT "+
										"WHERE "+
										"a.CF_UTENTE = ?;");
							db.getPreparedStatement().setString(1, id);
							db.getPreparedStatement().executeUpdate();
							db.executeQuery("SET FOREIGN_KEY_CHECKS=1;");
						}else if(str_action.equals("insert_videogame")){
							String nome_videogame = request.getParameter("nome_videogame");
							String n_uscita = request.getParameter("n_uscita");
							String prezzo = request.getParameter("prezzo");
							String nome_produttore = request.getParameter("nome_produttore");
							String anno_uscita = request.getParameter("anno_uscita");
							
							if(nome_videogame != null && !nome_videogame.equals("") && 
								n_uscita != null && !n_uscita.equals("") && 
								prezzo != null && !prezzo.equals("") && 
								nome_produttore != null && !nome_produttore.equals("") && 
								anno_uscita != null && !anno_uscita.equals(""))
							{
								String realpath = getServletContext().getRealPath("");
								String subpath = realpath.substring(0, realpath.indexOf("\\."));
								String path_tot = subpath + "\\ProgettoPW\\WebContent\\images\\";
								String immagine_str=null;
								Part file_write=null;
								for(Part part : request.getParts()){
									String content_header = part.getHeader("content-disposition");
									int index_filename_word = content_header.indexOf("filename=\"");
									if(index_filename_word > -1){
										immagine_str = content_header.substring(index_filename_word + (("filename=\"").length()), content_header.length()-1);
										file_write=part;
									}
								}
								db.initPreparedStatement("INSERT INTO VIDEOGAME VALUES (?, ?, ?, ?, ?, ?)");
								db.getPreparedStatement().setString(1, nome_videogame);
								db.getPreparedStatement().setString(2, immagine_str);
								db.getPreparedStatement().setInt(3, Integer.parseInt(n_uscita));
								db.getPreparedStatement().setInt(4, Integer.parseInt(prezzo));
								db.getPreparedStatement().setString(5, nome_produttore);
								db.getPreparedStatement().setInt(6, Integer.parseInt(anno_uscita));
								
								
								int n_rows=db.getPreparedStatement().executeUpdate();
								if(n_rows > 0){
									if(file_write!=null){
										file_write.write(path_tot + File.separator + "cover\\" + immagine_str);
										file_write.write(path_tot + File.separator + "grid\\" + immagine_str);
										file_write.write(path_tot + File.separator + "label\\" + immagine_str);
									}
									System.out.println("Fatto");
								}else{
									System.out.println("Fail");
								}
							}
						}else if(str_action.equals("insert_produttore")){
							String nome_produttore = request.getParameter("nome_produttore");
							String descrizione = request.getParameter("descrizione");
							String anni_exp = request.getParameter("anni_exp");
							
							if(nome_produttore != null && !nome_produttore.equals("") && 
								descrizione != null && !descrizione.equals("") && 
								anni_exp != null && !anni_exp.equals(""))
							{
								String realpath = getServletContext().getRealPath("");
								String subpath = realpath.substring(0, realpath.indexOf("\\."));
								String path_tot = subpath + "\\ProgettoPW\\WebContent\\images\\";
								String immagine_str=null;
								Part file_write=null;
								for(Part part : request.getParts()){
									String content_header = part.getHeader("content-disposition");
									int index_filename_word = content_header.indexOf("filename=\"");
									if(index_filename_word > -1){
										immagine_str = content_header.substring(index_filename_word + (("filename=\"").length()), content_header.length()-1);
										file_write=part;
									}
								}
								db.initPreparedStatement("INSERT INTO PRODUTTORE VALUES (?, ?, ?, ?)");
								db.getPreparedStatement().setString(1, nome_produttore);
								db.getPreparedStatement().setString(2, immagine_str);
								db.getPreparedStatement().setString(3, descrizione);
								db.getPreparedStatement().setInt(4, Integer.parseInt(anni_exp));
								int n_rows=db.getPreparedStatement().executeUpdate();
								if(n_rows > 0){
									if(file_write!=null){
										file_write.write(path_tot + File.separator + "banner\\" + immagine_str);
									}
									System.out.println("Fatto");
								}else{
									System.out.println("Fail");
								}
							}
						}
					}else{
						response.sendRedirect("./");
					}
				}else{
					response.sendRedirect("./");
				}
				
				response.sendRedirect(request.getHeader("referer"));	
			}
		}catch(SQLException e){
			out.print("Errore SQL");
			e.printStackTrace();
		}
	}

}
