package User;

import java.util.*;
import Payment.*;

import java.sql.*;

import Database.DBConnection;

public class History {
	
	//tempat dimana semua data history ada
	private static ArrayList<Payment> histories;
	
	//Membuat history
	public static ArrayList<Payment> addHistory(String userId) {
		 //menginsialisasi arrayList
		histories = new ArrayList<>();
		
		//Untuk mengambil koneksi ke database
        Connection conn = null;
        //Mempersiapkan SQL untuk dieksekusi
        PreparedStatement pstmt = null;
        //Mengambil data SQL yang telah dieksekusi
        ResultSet rs = null;
        
        //Query SQL
        String sql = "SELECT * FROM Payments WHERE userId = ?";
        
        try {
        	//Melakukan koneksi ke database
        	conn = DBConnection.connect();
        	
        	if(conn == null) {
        		System.err.println("Failed to connect to database");
        	}
        	
        	//Mempersiapkan sql 
        	pstmt = conn.prepareStatement(sql);
        	pstmt.setString(1, userId);
        	
        	//Eksekusi sql
        	rs = pstmt.executeQuery();
        	
        	while(rs.next()) {
        		//ambil data pembayaran
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
