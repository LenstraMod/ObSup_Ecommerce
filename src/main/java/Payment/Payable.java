package Payment;

public interface Payable {
  public double calculatePrice(double ProductPrice);
  public void paymentDetail(String ProductId, String UserId);
  
}
