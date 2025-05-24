package Payment;

public interface Payable {
  public double calculatePrice(double ProductPrice);
  public void payment detail(String ProductId, String UserId);
  
}
