package User;

import java.util.ArrayList;

import Database.DBConnection;
import Test.UserHandlingTest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserManager {
	
	private boolean isLogin = false;
	private static User CurrentUser;
	
	public void FetchUser(int userID) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String SQL = "SELECT * from Users WHERE userId = ?";
		
		try {
			conn = DBConnection.connect();
			
			if(conn == null) {
				System.err.println("Failed to connect to database");
			}
			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, String.valueOf(userID));
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				String id = String.valueOf(userID);
				String username = rs.getString("username");
				String email = rs.getString("email");
				String paymentMethod = rs.getString("payment_method");
				String address = rs.getString("address");
				String role = rs.getString("role");
				
				if(role.equals("User")) {
					CurrentUser = new RegisteredUsers(id,email,username,"",address,paymentMethod);
				}
				else if(role.equals("Admin")) {
					CurrentUser = new Admin(id,email,username,"");
				}
			}
		} catch (SQLException e) {
			System.err.println("Get User Error : " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Get User Error : " + e.getMessage());
		} finally {
			try {if (rs != null) {rs.close();}} catch (SQLException e) {e.printStackTrace();}}
			try {if (conn != null) {conn.close();}} catch (SQLException e) {e.printStackTrace();}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException e) {e.printStackTrace();}
	}
	
	
	public static User getThisSessionUser() {	
		return CurrentUser;
	}


	public boolean register(String email, String username,String password) {	
		boolean isRegistered = false;
		String QuerySQL = "INSERT INTO users (username,email,password,role) VALUES (?,?,?,?)";
		
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
			pstmt.setString(4, "User");
			
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
		
		String sql = "SELECT * FROM users WHERE email = ?";
		
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
					int UserId = rs.getInt("userId");
					FetchUser(UserId);
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
	
	static public boolean updateAddress(String userId, String address) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean isSuccess = false;
		
		String sql = "UPDATE users SET address = ? WHERE userId = ?";
		
		try {
			conn = DBConnection.connect();
			
			if(conn == null) {
				System.err.println("Failed to connect to database");
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, address);
			pstmt.setString(2, userId);
			
			int executePstmt = pstmt.executeUpdate();
			
			if(executePstmt > 0) {
				System.out.println("Berhasil Update");
				isSuccess = true;
			}
		
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Update Address Error : " + e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Update Address Error : " + e.getMessage());
		}
		
		return isSuccess;
	}
	
//	public void seeDetails() {
//		for(RegisteredUsers user: users) {
//			System.out.println(user.toString());
//		}
//	}
	


}
