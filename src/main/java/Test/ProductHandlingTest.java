package Test;

import java.sql.SQLException;
import java.util.*;

import Payment.PaymentManager;
import Product.*;
import User.UserManager;
import Utility.MissionUtil;

public class ProductHandlingTest {
	
	static ProductHandler productBiz = new ProductHandler();
	
	public static void ProductHandling() {
		
		productBiz.GetAllProducts();
		
		int menu = 0;
		int menuProduct= 0;
		String thisUserId = UserManager.getThisSessionUser().getUserID();
		
		while(menu != 9) {
			ProductMenu();
			
			String menuPrompt = "Masukkan menu : ";
			
			try {
				menu = MissionUtil.getIntInput(menuPrompt);
				
				if(menu < 0) {
					throw new Exception("Tidak bole angka negatif");
				} 
			}catch (Exception e) {
					System.out.println(e.getMessage());
			}
			
			try {
				switch(menu) {
				case 1 :
					productBiz.ShowProduct();
					continue;
					
				case 2:
					System.out.println(productBiz.ShowFormalProduct());
					continue;
					
				case 3:
					System.out.println(productBiz.ShowNonFormalProduct());
					continue;
					
				case 4:
					menuProduct = MissionUtil.getIntInput("Masukkan ID product : ");
					
					String productIdSearch = String.valueOf(menuProduct);
					
					Product findProduct = productBiz.ShowProductDetail(productIdSearch);
					
					if(findProduct != null) {
						System.out.println(productBiz.ShowProductDetail(productIdSearch).toString());
						
						System.out.println();
						System.out.println("Mau Ngapain Nih ?");
						System.out.println("1.Beli Product");
						System.out.println("2.Rating Product");
						System.out.println("3.Comment Product");	
						
						int actionMenuProduct = MissionUtil.getIntInput("Pilih Menu : ");
						
						ActionMenuProductControl(actionMenuProduct,thisUserId,productIdSearch);
					}
					else {
						System.err.println("ID tidak ada");
						continue;
					}
				
			}
			} catch(Exception e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
			}
		}
	}
	
	static void ActionMenuProductControl(int menuNumber, String userId, String productId) {
		switch(menuNumber) {
		case 1:
			int getProductAmount = MissionUtil.getIntInput("Masukkan jumlah produk yang ingin dibeli : ");	
			Product getProduct = productBiz.ShowProductDetail(productId);
			double totalPrice = getProduct.getPrice() * getProductAmount;
			
			System.out.println();
			System.out.println("Total Harga : " + totalPrice );
			System.out.println("Lanjut beli ?");
			System.out.println("1.Iya");
			System.out.println("2.Tidak");
			
			int continueBuyMenu = MissionUtil.getIntInput("Pilih Menu : ");
			
			switch(continueBuyMenu) {
			case 1:
				
				System.out.println("Pilih Metode Pembayaran");
				System.out.println("1.Kartu Debit/Kredit");
				System.out.println("2.E-Wallet");
				System.out.println("3.Cash on Delivery");
				
				int paymentMethodMenu = MissionUtil.getIntInput("Pilih metode pembayaran : ");
				
				try {
					switch(paymentMethodMenu) {
					case 1:
						System.out.println("Isi Data Kartu Dibawah Ini : ");
						System.out.println();
						System.out.print("Masukkan nomor kartu : ");
						String cardNumber = MissionUtil.getStringInput();
						System.out.print("Masukkan expired date kartu : ");
						String expiredDate = MissionUtil.getStringInput();
						System.out.print("Masukkan cvv : ");
						String cvv = MissionUtil.getStringInput();
						
						boolean getPaymentStatus = PaymentManager.cardPaymentProcess(cardNumber, expiredDate, cvv, userId, productId,totalPrice);
						
						if(getPaymentStatus) {
							int productAmountNow = getProduct.getStock() - getProductAmount;
							productBiz.updateStock(productId, productAmountNow);
							System.out.println("Checkout Success");
						} else {
							System.out.println("Checkout Failed");
						}
						break;
						
					case 2:
						break;
					}
				} catch(Exception e) {
					e.printStackTrace();
					System.err.println(e.getMessage());
				} 
				
			}
			
			
	}
}
	
	static void ProductMenu() {
		System.out.println("---------- Pilih Menu ----------");
		System.out.println("1.Tunjukkan Semua Product");
		System.out.println("2.Tunjukkan Semua Product Formal");
		System.out.println("3.Tunjukkan Semua Product Non Formal");
		System.out.println("4.Check Product");

	}
	
}
