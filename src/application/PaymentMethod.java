package application;

public class PaymentMethod {
	private Integer paymentID = null;
	private String cardHolderName = null;
	private String ccNum = null;
	private String expireDate = null;
	private String ccvNum = null;
	
	public PaymentMethod() {

	}

	public int getPaymentID() {
		return paymentID;
	}

	public void setPaymentID(int paymentID) {
		this.paymentID = paymentID;
	}

	public String getCardHolderName() {
		return cardHolderName;
	}

	public void setCardHolderName(String cardHolderName) {
		this.cardHolderName = cardHolderName;
	}

	public String getCcNum() {
		return ccNum;
	}

	public void setCcNum(String ccNum) {
		this.ccNum = ccNum;
	}

	public String getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}

	public String getCcvNum() {
		return ccvNum;
	}

	public void setCcvNum(String ccvNum) {
		this.ccvNum = ccvNum;
	}
	
	

}
