package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AccountManagementController implements Initializable {
	AccountManagementModel accManModel = new AccountManagementModel();
	private static Cart cart;
	private static Customer customer;
	ShippingInfo sInfo = new ShippingInfo();
	PaymentMethod pInfo = new PaymentMethod();
	
	@FXML
	private Button homeButton;
	@FXML
	private TextField addressTF;
	@FXML
	private TextField cityTF;
	@FXML
	private TextField stateTF;
	@FXML
	private TextField phoneNumbTF;
	@FXML
	private TextField ccHolderName;
	@FXML
	private TextField ccNumTF;
	@FXML
	private TextField ccccvNumTF;
	@FXML
	private ComboBox<String> monthCB;
	@FXML
	private ComboBox<String> yearCB;
	@FXML
	private TextField cusFName;
	@FXML
	private TextField cusLName;
	@FXML
	private TextField cusEMail;
	@FXML
	private TextField cusPass;
	@FXML
	private Button saveCusInfo;
	@FXML
	private Button saveShipInfo;
	@FXML
	private Button savePayInfo;
	@FXML
	private Label cusStatus;
	@FXML
	private Label shipStatus;
	@FXML
	private Label payStatus;

	
	
	public static void passInCustomerAndCart(Customer cus, Cart sCart) {
		customer = cus;
		cart = sCart;
	}


	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		fillCusInfo();
		fillShipInfo();
		fillPayInfo();
		setUpComboBox();
	}
	
	private void fillPayInfo() {
		if (customer.getPaymentId() != 0) {
			pInfo = accManModel.getPayInfo(customer.getPaymentId());
			ccHolderName.setText(pInfo.getCardHolderName());
			ccNumTF.setText(pInfo.getCcNum());
			ccccvNumTF.setText(pInfo.getCcvNum());
			String[] result = pInfo.getExpireDate().split(" ");
			monthCB.setValue(result[0]);
			yearCB.setValue(result[1]);
		}
	}


	private void fillShipInfo() {
		if (customer.getShippingId() != 0) {
		sInfo = accManModel.getShippingInfo(customer.getShippingId() );
		addressTF.setText(sInfo.getAddress());
		cityTF.setText(sInfo.getCity());
		stateTF.setText(sInfo.getState());
		phoneNumbTF.setText(sInfo.getPhoneNum());
		}
	}


	private void fillCusInfo() {
		cusFName.setText(customer.getfName());
		cusLName.setText(customer.getlName());
		cusEMail.setText(customer.getEmail());
		cusPass.setText(customer.getPassword());
	}
	
	private void setUpComboBox() {
		monthCB.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12");
		yearCB.getItems().addAll("2018", "2019", "2020", "2021", "2022", "2023", "2024", "2025", "2026", "2027", "2028",
				"2029", "2030", "2031", "2032", "2033", "2034", "2035");
	}
	
	@FXML
	public void updateCustomer() {
		if (callFilledOut() && cusDidntChange()) { 
			Customer cus = getCus();
			if (accManModel.updateCustomer(cus)) {
				cusStatus.setText("Customer Updated");
				customer = cus;
			} else {
				cusStatus.setText("Cus not updated, Make sure email is unique and al values are filled out");
			}
		}
	}


	private Customer getCus() {
		Customer cus = new Customer();
		cus.setEmail(cusEMail.getText());
		cus.setfName(cusFName.getText());
		cus.setlName(cusLName.getText());
		cus.setPassword(cusPass.getText());
		cus.setId(customer.getId());
		return cus;
	}


	private boolean cusDidntChange() {
		if (!(cusFName.getText()).equals(customer.getfName()))
			return true;
		if (!cusLName.getText().equals(customer.getlName()))
			return true;
		if (!cusEMail.getText().equals(customer.getEmail()))
			return true;
		if (!cusPass.getText().equals(customer.getPassword()))
			return true;
		return false;
	}


	private boolean callFilledOut() {
		if (cusFName.getText().equals(""))
			return false;
		if (cusLName.getText().equals(""))
			return false;
		if (cusEMail.getText().equals(""))
			return false;
		if (cusPass.getText().equals(""))
			return false;
		return true;
	}
	
	@FXML
	public void updateShipping() {
		if (sallFilledOut() && shipDidntChange()) { 
			ShippingInfo ship = getShip();
			if (accManModel.updateShipping(ship)) {
				shipStatus.setText("Shipping Updated");
				sInfo = ship;
			} else {
				shipStatus.setText("Shipping not updated");
			}
		}
	}


	private ShippingInfo getShip() {
		ShippingInfo ship = new ShippingInfo();
		ship.setAddress(addressTF.getText());
		ship.setCity(cityTF.getText());
		ship.setPhoneNum(phoneNumbTF.getText());
		ship.setState(stateTF.getText());
		ship.setShippingID(sInfo.getShippingID());
		return ship;
	}


	private boolean shipDidntChange() {
		if (!(addressTF.getText()).equals(sInfo.getAddress()))
			return true;
		if (!cityTF.getText().equals(sInfo.getCity()))
			return true;
		if (!stateTF.getText().equals(sInfo.getState()))
			return true;
		if (!phoneNumbTF.getText().equals(sInfo.getPhoneNum()))
			return true;
		return false;
	}


	private boolean sallFilledOut() {
		if (addressTF.getText().equals(""))
			return false;
		if (cityTF.getText().equals(""))
			return false;
		if (stateTF.getText().equals(""))
			return false;
		if (phoneNumbTF.getText().equals(""))
			return false;
		return true;
	}
	
	@FXML
	public void updatePay() {
		if (pallFilledOut() && payDidntChange()) { 
			PaymentMethod pay = getPay();
			if (accManModel.updatePay(pay)) {
				payStatus.setText("Payment Updated");
				pInfo = pay;
			} else {
				payStatus.setText("Payment not updated");
			}
		}
	}


	private PaymentMethod getPay() {
		PaymentMethod pay = new PaymentMethod();
		pay.setCardHolderName(ccHolderName.getText());
		pay.setCcNum(ccNumTF.getText());
		pay.setCcvNum(ccccvNumTF.getText());
		pay.setExpireDate(monthCB.getValue() +" "+ yearCB.getValue());
		pay.setPaymentID(pInfo.getPaymentID());
		return pay;
	}


	private boolean payDidntChange() {
		if (!(ccHolderName.getText()).equals(pInfo.getCardHolderName()))
			return true;
		if (!ccNumTF.getText().equals(pInfo.getCcNum()))
			return true;
		if (!ccccvNumTF.getText().equals(pInfo.getCcvNum()))
			return true;
		String[] expire = pInfo.getExpireDate().split(" ");
		if (!monthCB.getValue().equals(expire[0]))
			return true;
		if (!yearCB.getValue().equals(expire[1]))
			return true;

		return false;
	}


	private boolean pallFilledOut() {
		if (ccHolderName.getText().equals(""))
			return false;
		if (ccNumTF.getText().equals(""))
			return false;
		if (ccccvNumTF.getText().equals(""))
			return false;
		if (monthCB.getValue().equals("MM"))
			return false;
		if (yearCB.getValue().equals("YYYY"))
			return false;
		return true;
	}


	@FXML
	public void goHome() {
		HomeController.passInCustomerAndCart(customer, cart);
		try {
			Parent homeRoot = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
			Scene homeView = new Scene(homeRoot);
			Stage window = (Stage) addressTF.getScene().getWindow();
			window.setScene(homeView);
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
