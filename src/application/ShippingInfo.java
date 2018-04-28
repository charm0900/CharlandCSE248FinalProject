package application;

public class ShippingInfo {
	private Integer shippingID = null;
	private String address = null;
	private String city = null;
	private String state = null;
	private String phoneNum = null;
	
	public ShippingInfo() {

	}

	public int getShippingID() {
		return shippingID;
	}

	public void setShippingID(int shippingID) {
		this.shippingID = shippingID;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
	
	
}
