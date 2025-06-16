package User;

//Class user untuk customer
public class RegisteredUsers extends User{
	
	private String address;
	private String PaymentMethod;
	
	public RegisteredUsers(String userID, String email, String username, String password,  String address, String PaymentMethod) {
		super(userID, email, username, password,"User");
		this.address = address;
		this.PaymentMethod = PaymentMethod;
	}

	public String getAddress() {
		return address;
	}



	public void setAddress(String address) {
		this.address = address;
	}



	public String getPaymentMethod() {
		return PaymentMethod;
	}



	public void setPaymentMethod(String paymentMethod) {
		PaymentMethod = paymentMethod;
	}



	@Override
	public String toString() {
		return super.toString() + "\naddress : " + this.address + "\npayment method :";
	}
	
	
}
