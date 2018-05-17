package application;

public class Order {
	private int orderID;
	private String address;
	private String arrivalDate;
	private String status;
	private Long daysFromCurrentDate;
	
	public Order() {
		super();
	}
	
	public Order(int orderID, String address, String arrivalDate, String status) {
		super();
		this.orderID = orderID;
		this.address = address;
		this.arrivalDate = arrivalDate;
		this.status = status;
	}

	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}

	public String getArrivalDate() {
		return arrivalDate;
	}
	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public Long getDaysFromCurrentDate() {
		return daysFromCurrentDate;
	}

	public void setDaysFromCurrentDate(Long daysFromCurrentDate) {
		this.daysFromCurrentDate = daysFromCurrentDate;
	}

}
