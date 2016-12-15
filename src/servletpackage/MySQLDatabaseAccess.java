package servletpackage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MySQLDatabaseAccess{
	private Connection connect = null;
	private Statement statement = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
  
	public MySQLDatabaseAccess(){
		try{
			Class.forName("com.mysql.jdbc.Driver");
			connect = DriverManager.getConnection("jdbc:mysql://localhost/negoziovid?user=root&password=unisatesting");
		}catch(Exception e){
			System.out.println("Errore in MySQLDatabaseAccess: "+e.getMessage());
			e.printStackTrace();
		}
	}
	
 	public ResultSet executeQuery(String query) throws SQLException{
 		statement = connect.createStatement();
		if(statement!=null){
			final ResultSet rs=statement.executeQuery(query);
			return rs;
		}else return null;
 	}
 	
 	public void initPreparedStatement(String query) throws SQLException{
 		preparedStatement = connect.prepareStatement(query);
 	}
 	
 	public PreparedStatement getPreparedStatement(){
 		return preparedStatement;
 	}

 	// chiusura del resultSet
 	public void close(){
	    try{
	      if(resultSet != null)	resultSet.close();
	      if(statement != null)	statement.close();
	      if(connect != null) 	connect.close();
	    }catch(Exception e){System.out.println("MySQLDatabaseAccess error: "+e.getMessage());}
 	}

} 