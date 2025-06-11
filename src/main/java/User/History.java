package User;

import java.util.*;
import Payment.*;

import java.sql.*;

import Database.DBConnection;

public class History {
	
	private static ArrayList<Payment> histories;
	
	public static ArrayList<Payment> addHistory(String userId) {
		
		histories = new ArrayList<>();
		
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        String sql = "SELECT * FROM Payments WHERE userId = ?";
        
        try {
        	conn = DBConnection.connect();
        	
        	if(conn == null) {
        		System.err.println("Failed to connect to database");
        	}
        	
        	pstmt = conn.prepareStatement(sql);
        	pstmt.setString(1, userId);
        	rs = pstmt.executeQuery();
        	
        	while(rs.next()) {
        		String paymentId = rs.getString("paymentId");
        		String paymentDate = rs.getString("paymentDate");
        		String paymentStatus = rs.getString("paymentStatus");
        		int paymentPrice = rs.getInt("paymentAmount");
        		String paymentMethod = rs.getString("paymentMethod");
        		
        		histories.add(new Payment(paymentId,paymentDate,paymentStatus,paymentPrice,paymentMethod));

        	}
        } catch (SQLException e) {
        	e.printStackTrace();
        	System.out.println("History Fetching error : " + e.getMessage());
        } catch (Exception e) {
        	e.printStackTrace();
        	System.out.println("History Fetching error : " + e.getMessage());
        }
        
    	return histories;
	}
}
