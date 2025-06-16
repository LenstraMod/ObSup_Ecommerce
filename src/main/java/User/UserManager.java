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
	
	private static ArrayList<Comment> comments;
	private static ArrayList<User> users;
	private static ArrayList<Rating> ratings;
	
	//Ambil user untuk membuat session login (Siapa yg login saat ini)
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
				
				String id = rs.getString("userId");
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
	
	//Mengambil data user untuk pembayaran
	public User getUser(String userId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		User user = null;
		
		String SQL = "SELECT * from Users WHERE userId = ?";
		
		try {
			conn = DBConnection.connect();
			
			if(conn == null) {
				System.err.println("Failed to connect to database");
			}
			
			pstmt = conn.prepareStatement(SQL);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				
				String id = rs.getString("userId");
				String username = rs.getString("username");
				String email = rs.getString("email");
				String role = rs.getString("role");
				
				user = new User(id,email,username,"",role);	
			}
		} catch (SQLException e) {
			System.err.println("Get User Error : " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Get User Error : " + e.getMessage());
		} finally {
			try {if (rs != null) {rs.close();}} catch (SQLException e) {e.printStackTrace();}}
			try {if (conn != null) {conn.close();}} catch (SQLException e) {e.printStackTrace();}
			try {if (pstmt != null) {pstmt.close();}} catch (SQLException e) {e.printStackTrace();
	
	
			
		}
			return user;		
	}
	
	//mengirim data user secara keseluruhan untuk sesi saat ini
	public static User getThisSessionUser() {	
		return CurrentUser;
	}
	

	//Membuat data user yg baru
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
	
	//Untuk user masuk ke dalam aplikasi
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
	
	//Mengambil status login user
	public boolean getIsLogin() {
		return isLogin;
	}
	
	//Melakukan update address user
	public static boolean updateAddress(String userId, String address) {
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
	
	//Mengambil seluruh comment yang ada dari database
	public static void getComment() {
		comments = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * from comments";
		

		try {
			conn = DBConnection.connect();
			
			if(conn == null) {
				System.err.println("Failed to connect to database");
			}
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String commentId = rs.getString("commentId");
				String commentText = rs.getString("commentText");
				String commentDate = rs.getString("commentDate");
				String userId = rs.getString("userId");
				String productId = rs.getString("productId");
				
				comments.add(new Comment(commentId,commentText,commentDate,userId,productId));
			}
		
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Comment Error : " + e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Comment Error : " + e.getMessage());
		}
	}


	//Mengirim comment
	public static ArrayList<Comment> getComments() {
		return comments;
	}
	
	//Menambah comment
	public static boolean addComment(String commentText, String userId, String productId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean isSuccess = false;
		
		String sql = "INSERT INTO comments (commentText,userId,productId) VALUES (?,?,?)";
		
		try {
			conn = DBConnection.connect();
			
			if(conn == null) {
				System.err.println("Failed to connect to database");
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, commentText);
			pstmt.setString(2, userId);
			pstmt.setString(3, productId);
			
			int executePstmt = pstmt.executeUpdate();
			
			if(executePstmt > 0) {
				System.out.println("Berhasil Comment");
				isSuccess = true;
			}
		
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Comment Error : " + e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Comment Error : " + e.getMessage());
		}
		
		return isSuccess;
	}
	
	//Menambah Rating
	public static boolean addRating(int ratingValue, String productId) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean isSuccess = false;
		
		String sql = "INSERT INTO ratings (ratingValue,productId) VALUES (?,?)";
		
		try {
			conn = DBConnection.connect();
			
			if(conn == null) {
				System.err.println("Failed to connect to database");
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, ratingValue);
			pstmt.setString(2, productId);
			
			int executePstmt = pstmt.executeUpdate();
			
			if(executePstmt > 0) {
				System.out.println("Berhasil Rating");
				isSuccess = true;
			}
		
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Rating Error : " + e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Rating Error : " + e.getMessage());
		}
		
		return isSuccess;
	}
	
	//Mengambil rating
	public static void getRating() {
		ratings = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * from ratings";
		

		try {
			conn = DBConnection.connect();
			
			if(conn == null) {
				System.err.println("Failed to connect to database");
			}
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String ratingId = rs.getString("ratingId");
				String commentDate = rs.getString("ratingDate");
				int ratingValue = rs.getInt("ratingValue");
				String productId = rs.getString("productId");
				
				ratings.add(new Rating(ratingId,commentDate,ratingValue,productId));
			}
		
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("Comment Error : " + e.getMessage());
		} catch(Exception e) {
			e.printStackTrace();
			System.out.println("Comment Error : " + e.getMessage());
		}
	}
	
	//menghitung rating
	public static double ratingCalculation(String productId) {
		getRating();
		
		double ratingTotal = 0;
		double ratingIndex = 0;
		double result = 0;
		
		for(Rating rt : ratings) {
			if(rt.getProductId().equals(productId)) {
				ratingTotal += rt.getRatingValue();
				ratingIndex++;
			}
		}
		
		if(ratings.isEmpty()) {
			System.out.println("Belum ada rating untuk produk ini");
			result = 0.0;
		}
		else if(ratingIndex == 0) {
			System.out.println("belum ada rating untuk produk ini");
			result = 0.0;
		}
		else {
			result = ratingTotal/ratingIndex;
		}
		
		return result;
	}
	public void logout() {
		UserManager.CurrentUser = null;
		System.out.println("Logout Success | Terima Kasih Telah Datang");
		System.out.println();
	}
	
	
	
//	public void seeDetails() {
//		for(RegisteredUsers user: users) {
//			System.out.println(user.toString());
//		}
//	}
	


}
