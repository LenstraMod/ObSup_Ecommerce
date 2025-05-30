package Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	
	private static final String URL = "jdbc:mysql://localhost:3306/obsessedsupply";
	private static final String USER = "root";
	private static final String PASSWORD = "";
	private static Connection con;
	
	public static Connection connect() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			con = DriverManager.getConnection(URL,USER,PASSWORD);
		} catch (ClassNotFoundException | SQLException e) {
			System.out.println("Failed to connect to the database");
			e.printStackTrace();
		}
		
		return con;
	}
	
	public static void main(String[] args) {
		Connection conn = DBConnection.connect();
	}

}
