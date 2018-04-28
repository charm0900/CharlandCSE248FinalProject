package application;

public class Customer {
	private Integer id;
	private String fName;
	private String lName;
	private String email;
	private String password;
	private Integer shippingId;
	private Integer paymentId;
	
	public Customer() {
		super();
		this.id = null;
		this.fName = null;
		this.lName = null;
		this.email = null;
		this.password = null;
		this.shippingId = null;
		this.paymentId = null;
	}
	
	public Customer(int id, String fName, String lName, String email, String password, int shippingId, int paymentId) {
		super();
		this.id = id;
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.password = password;
		this.shippingId = shippingId;
		this.paymentId = paymentId;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getShippingId() {
		return shippingId;
	}

	public void setShippingId(int shippingId) {
		this.shippingId = shippingId;
	}

	public int getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(int paymentId) {
		this.paymentId = paymentId;
	}

	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	@Override
	public String toString() {
		return "name=" + fName + ", " + lName + " shippingId=" + shippingId + " paymentID=" + paymentId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setShippingId(Integer shippingId) {
		this.shippingId = shippingId;
	}

	public void setPaymentId(Integer paymentId) {
		this.paymentId = paymentId;
	}
	
	

}
