package Payment;

public class EwalletPayment extends Payment implements Payable {
    private String walletProvider;
    private String walletNumber;
    private int balance; 

    public EwalletPayment(String paymentId, String paymentDate, String paymentStatus, int productAmount,
                          String walletProvider, String walletNumber, int balance) {
        super(paymentId, paymentDate, paymentStatus, productAmount,"E-Wallet Payment");
        this.walletProvider = walletProvider;
        this.walletNumber = walletNumber;
        this.balance = balance;
    }

    public boolean checkBalance() {
        System.out.println("Memeriksa saldo e-wallet...");
        return this.balance > 0; 
    }
    
    public void deductBalance() {
      
    }

    @Override
    public double calculatePrice(double productPrice) {
        double basePrice = super.calculatedPrice(productPrice);
        System.out.println("Menghitung harga untuk EwalletPayment...");
        return basePrice; 
        
    }
    @Override
    public void paymentDetail(String productId, String userId) {
        super.paymentDetail(productId, userId);
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
