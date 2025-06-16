package Payment;

//Data pembayaran melalui COD
public class CashOnDeliveryPayment extends Payment implements Payable {
    private String receiverName;
    private String deliveryAddress;
    private String contactNumber;

    public CashOnDeliveryPayment(String paymentID, String paymentDate, String paymentStatus, int productAmount,
                                 String receiverName, String deliveryAddress, String contactNumber) {
        super(paymentID, paymentDate, paymentStatus, productAmount,"Cash on Delivery");
        this.receiverName = receiverName;
        this.deliveryAddress = deliveryAddress;
        this.contactNumber = contactNumber;
    }

    @Override
    public double calculatePrice(double productPrice) {
        return super.calculatedPrice(productPrice);
    }

    @Override
    public void paymentDetail(String productId, String userId) {
        super.paymentDetail();
        System.out.println("Penerima: " + receiverName);
        System.out.println("Alamat: " + deliveryAddress);
        System.out.println("No telepon: " + contactNumber);
    }
}
