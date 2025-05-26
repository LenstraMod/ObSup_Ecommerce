package Payment;

public class CashOnDeliveryPayment extends Payment implements Payable {
    private String receiverName;
    private String deliveryAddress;
    private String contactNumber;

    public CashOnDeliveryPayment(String paymentID, Date paymentDate, String paymentStatus, int productAmount,
                                 String receiverName, String deliveryAddress, String contactNumber) {
        super(paymentID, paymentDate, paymentStatus, productAmount);
        this.receiverName = receiverName;
        this.deliveryAddress = deliveryAddress;
        this.contactNumber = contactNumber;
    }

    public void confirmDelivery() {
        System.out.println("Delivery dikonfirmasi untuk: " + receiverName + " Di " + deliveryAddress);
    }

    public boolean collectCash() {
        System.out.println("Uang diterima dari: " + receiverName);
        return true;
    }

    @Override
    public double calculatedPrice(double productPrice) {
        return super.calculatedPrice(productPrice);
    }

    @Override
    public void paymentDetail(String productId, String userId) {
        super.paymentDetail(productId, userId);
        System.out.println("Receiver: " + receiverName);
        System.out.println("Delivery Address: " + deliveryAddress);
        System.out.println("Contact Number: " + contactNumber);
    }
}
