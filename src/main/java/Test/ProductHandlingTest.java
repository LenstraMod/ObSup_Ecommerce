package Test;

import java.sql.SQLException;
import java.util.*;
import Product.*;
import Utility.MissionUtil;

public class ProductHandlingTest {
	
	public static void ProductHandling() {
		ProductHandler productBiz = new ProductHandler();
		productBiz.GetAllProducts();
		
		int menu = 0;
		int menuProduct= 0;
		
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
						
						ActionMenuProductControl(actionMenuProduct);
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
	
	static void ActionMenuProductControl(int menuNumber) {
		switch(menuNumber) {
		case 1:
			System.out.println();
			System.out.println("Pilih Metode Pembayaran");
			System.out.println("1.Kartu Debit/Kredit");
			System.out.println("2.E-Wallet");
			System.out.println("3.Cash on Delivery");
			
			int paymentMethodMenu = MissionUtil.getIntInput("Pilih metode pembayaran : ");
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
