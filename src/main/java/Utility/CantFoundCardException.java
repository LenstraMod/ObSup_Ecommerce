package Utility;

public class CantFoundCardException extends Exception{

	public CantFoundCardException(String message) {
		super(message);
	}
	
	public CantFoundCardException() {
		System.out.println("Karu tidak ditemukan, Cek lagi informasi kartu anda");
	}
}
