package Test;

import java.util.*;
import Product.*;
import Utility.MissionUtil;

public class ProductHandlingTest {
	
	public static void ProductHandling() {
		ProductHandler productBiz = new ProductHandler();
		productBiz.InitializeProduct();
		
		int menu = 0;
		
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
			
			switch(menu) {
				case 1 :
					productBiz.GetAllProducts();
			}
		}
	}
	
	static void ProductMenu() {
		System.out.println("---------- Pilih Menu ----------");
		System.out.println("1.Tunjukkan Semua Product");

	}
	
}
