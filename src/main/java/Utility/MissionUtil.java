package Utility;

import java.util.InputMismatchException;
import java.util.Scanner;

public final class MissionUtil {
	
	static Scanner scn = new Scanner(System.in);
	
	private MissionUtil() {};
	
	//Scanner untuk String
	public static String getStringInput() throws InputEmptyException {
		String input = scn.nextLine();
		
		if(input.trim().isEmpty()) {
			throw new InputEmptyException("Tidak bole kosong");
		}
		return input;
	}
	
	//Scanner untuk Integer
	public static int getIntInput(String prompt) {
		while(true) {		
			System.out.print(prompt + "");
			try {
				int value = scn.nextInt();
				scn.nextLine();
				return value;
			} catch (InputMismatchException e) {
				System.out.println("Bukan angka");
				scn.nextLine();
			}
		}
		
	}

}
