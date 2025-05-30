package User;

import java.util.ArrayList;

import Database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
	
	private boolean isLogin = false;

	
	public boolean register(String email, String username,String password) {	
		boolean isRegistered = false;
		String QuerySQL = "INSERT INTO users (username,email,password) VALUES (?,?,?)";
		
		try (Connection conn = DBConnection.connect(); PreparedStatement pstmt = (conn != null) ? conn.prepareStatement(QuerySQL) : null){
			if (conn == null) {
				System.err.println("Failed to regist, Cannot connect to the database");
				return false;
			}
			
			if(pstmt == null) {
				System.err.println("Failed to regist, Cannont make the SQL Statement");
				return false;
			}
			
			pstmt.setString(1, username);
			pstmt.setString(2, email);
			pstmt.setString(3, password);
			
			int executePstmt = pstmt.executeUpdate();
			
			if(executePstmt > 0) {
				isRegistered = true;
				System.out.println("Register berhasil");
			}
			else {
				System.out.println("Register gagal");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
			
			if(e.getMessage().toLowerCase().contains("duplicate entry")) {
				System.err.println("Kemungkinan sudah terdaftar");
			}
		}
		
		
		return isRegistered;
	}
	
	public boolean login(String email, String password) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT password FROM users WHERE email = ?";
		
		try {
			conn = DBConnection.connect();
			
			if(conn == null) {
				System.err.println("Failed to login, cannot login to the database");
				return false;
			}
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, email);
			
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				String storedPassword = rs.getString("password");
				
				if(password.equals(storedPassword)) {
					 System.out.print("Login Succes");
					 isLogin = true;
					 return true;	 
				}
				else {
					System.out.println("Incorrect Password");
					return false;
				}
			} else {
				System.out.println("Email not found");
				return false;
			}
		} catch(SQLException e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
			return false;
		} finally{
			try {
				if(rs != null) rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				if(pstmt != null) pstmt.close(); 
			} catch(SQLException e) {
				e.printStackTrace();
			}
			try {
				if(conn != null) conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

	}
	
	public boolean getIsLogin() {
		return isLogin;
	}
	
//	public void seeDetails() {
//		for(RegisteredUsers user: users) {
//			System.out.println(user.toString());
//		}
//	}
	


}
