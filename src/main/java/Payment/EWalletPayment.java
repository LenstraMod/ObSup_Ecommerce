package Payment;

//Pembayaran melalui e wallet
public class EWalletPayment extends Payment implements Payable {
	private String walletId;
    private String walletProvider;
    private String walletNumber;
    private int balance; 

    //Overloading
    //Construction e wallet dengan parentnya. Digunakan untuk pembayaran
    public EWalletPayment( String paymentId, String paymentDate, String paymentStatus, int productAmount,
    		String walletId, String walletProvider, String walletNumber, int balance) {
        super(paymentId, paymentDate, paymentStatus, productAmount,"E-Wallet Payment");
        this.walletId = walletId;
        this.walletProvider = walletProvider;
        this.walletNumber = walletNumber;
        this.balance = balance;
    }
    
   
    //Construction e wallet untuk property e wallet saja. Ini digunakan untuk mengambil data dummy ewallet
    public EWalletPayment(String walletId, String walletProvider, String walletNumber, int balance) {
		super("","","",0,"");
		this.walletId = walletId;
		this.walletProvider = walletProvider;
		this.walletNumber = walletNumber;
		this.balance = balance;
	}
    
    public int deductBalance(double totalPrice) {
    	return (int) (this.balance - totalPrice);
    }

    
    public String getWalletId() {
		return walletId;
	}



	@Override
    public double calculatePrice(double productPrice) {
        double basePrice = super.calculatedPrice(productPrice);
        System.out.println("Menghitung harga untuk EwalletPayment...");
        return basePrice;      
    }
	
    @Override
    public void paymentDetail(String productId, String userId) {
        super.paymentDetail();
        System.out.println("Detail E-wallet:");
        System.out.println("Penyedia Wallet: " + this.walletProvider);
        System.out.println("Nomor Wallet: " + this.walletNumber);
        System.out.println("Saldo Saat Ini: " + this.balance);
    }


    public String getWalletProvider() {
        return walletProvider;
    }

    public void setWalletProvider(String walletProvider) {
        this.walletProvider = walletProvider;
    }

    public String getWalletNumber() {
        return walletNumber;
    }

    public void setWalletNumber(String walletNumber) {
        this.walletNumber = walletNumber;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }
}
