package Payment;

import java.util.*;

import Database.DBConnection;
import Utility.CantFoundCardException;

import java.sql.*;

public class PaymentManager {
	
	static ArrayList<CardPayment> cardDatas;
	
	public static boolean cardPaymentProcess(String cardNumber, String cardExpiryDate, String cvv, String  userId, String productId, double price) {
		initializeCardData();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean isSuccess = false;
		boolean isCardFound = false;
		
		String sql = "INSERT INTO payments (paymentStatus,paymentAmount,userId,ProductId,paymentMethod) values (?,?,?,?,?)";
		
		try {
			conn = DBConnection.connect();
			
			if(conn == null) {
				System.err.println("Failed to connect to database");
			}
			
			for(CardPayment cp: cardDatas) {
				if(cp.getCardNumber().equals(cardNumber)){
					if(cp.getCardExpiryDate().equals(cardExpiryDate)) {
						if(cp.getCvv().equals(cvv)) {						
							pstmt = conn.prepareStatement(sql);
							pstmt.setString(1, "Success");
							pstmt.setDouble(2, price);
							pstmt.setString(3, userId);
							pstmt.setString(4, productId);
							pstmt.setString(5, "Card");
							
							int executePstmt = pstmt.executeUpdate();
							
							if(executePstmt > 0) {
								System.out.println("Check out berhasil");
								isSuccess = true;
							}
							else {
								System.err.println("Checkout Gagal");
								isSuccess = false;
							}
							
							isCardFound = true;
							break;
						} 
					}
				}
			}
			
			if(!isCardFound) {
				throw new CantFoundCardException();
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.err.println("Card Payment Error : " + e.getMessage());
		} catch(CantFoundCardException e) {
			e.printStackTrace();
			System.err.println("Card Payment Error : " + e.getMessage());
		}catch(Exception e) {
			e.printStackTrace();
			System.err.println("Card Payment Error : " + e.getMessage());
		}
		return isSuccess;
	}
	
	public static boolean cashOnDeliveryProcess(String userId, String productId, String phoneNumber, double totalPrice) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		boolean isSuccess = false;
		
		String sql = "INSERT INTO payments (paymentStatus,paymentAmount,userId,ProductId,paymentMethod) values (?,?,?,?,?)";
		
		try {
			conn = DBConnection.connect();
			
			if(conn == null) {
				System.err.println("Failed to connect to database");
			}
			
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "Success");
			pstmt.setDouble(2, totalPrice);
			pstmt.setString(3, userId);
			pstmt.setString(4, productId);
			pstmt.setString(5, "Cash on Delivery");
			
			int pstmtExecute = pstmt.executeUpdate();
			
			if(pstmtExecute > 0) {
				System.out.println("Checkout Berhasil");
				isSuccess = true;
			}
			else {
				System.out.println("Checkout Gagal");
			}
		} catch(SQLException e) {
			e.printStackTrace();
			System.out.println("COD Error : " + e.getMessage());
		} catch(Exception e) {
			System.out.println("COD Error : " + e.getMessage());
		}
		
		return isSuccess;
		
	}
	
	public static void initializeCardData() {
		
		cardDatas = new ArrayList<>();
		
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		String sql = "SELECT * FROM cardsdatadummy";
		
		try {
			conn = DBConnection.connect();
			
			if(conn == null) {
				System.err.println("Failed to connect to database");
			}
			
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String cardNumber = rs.getString("card_number");
				String cardName = rs.getString("card_name");
				String cardExpiredDate = rs.getString("card_expired_date");
				String cvv = rs.getString("card_cvv");
				
				cardDatas.add(new CardPayment(cardNumber,cardName,cardExpiredDate,cvv));
			}
			
		} catch(SQLException e) {
			e.printStackTrace();
			System.err.println("Failed when fetching data");
		} finally {
			try { if(conn != null) {conn.close();} } catch (SQLException e) {e.printStackTrace();}
			try { if(pstmt != null) {pstmt.close();} } catch (SQLException e) {e.printStackTrace();}
			try { if(rs != null) {rs.close();} } catch (SQLException e) {e.printStackTrace();}
		}
	}
	
	

}
