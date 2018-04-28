package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class CheckOutController implements Initializable {
	private CheckOutModel checkOutModel = new CheckOutModel();
	private static Cart cart;
	private static Customer customer;
	private ToggleGroup shippingGroup = new ToggleGroup();
	private double shippingCost = 0.0;
	
	@FXML
	private TextField addressTF;
	@FXML
	private TextField cityTF;
	@FXML
	private TextField stateTF;
	@FXML
	private TextField phoneNumbTF;
	@FXML
	private RadioButton economyShipRB;
	@FXML
	private RadioButton standardShipRB;
	@FXML
	private RadioButton expeditedShipRB;
	@FXML
	private TextField ccHolderFnameTF;
	@FXML
	private TextField ccHolderLnameTF;
	@FXML
	private TextField ccNumTF;
	@FXML
	private TextField ccccvNumTF;
	@FXML
	private ComboBox<String> monthCB;
	@FXML
	private ComboBox<String> yearCB;
	@FXML
	private Label cartNumber;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		autoFillInfo();
		cartNumber.setText(Integer.toString(cart.getCartSize()));
		setUpRadioButtons();
		setUpComboBox();
	}
	
	private void setUpComboBox() {
		monthCB.getItems().addAll("01", "02", "03", "04", "05", "06", "07", "08",
				"09", "10", "11", "12");
		yearCB.getItems().addAll("2018", "2019", "2020", "2021", "2022", "2023", "2024",
				"2025", "2026", "2027", "2028", "2029", "2030", "2031", "2032", "2033",
				"2034", "2035");
	}

	private void setUpRadioButtons() {
			economyShipRB.setToggleGroup(shippingGroup);
			standardShipRB.setToggleGroup(shippingGroup);
			expeditedShipRB.setToggleGroup(shippingGroup);
			shippingGroup.selectToggle(economyShipRB);
	}
	
	@FXML
	public void radioButtonChanged() {
		if (economyShipRB.isSelected())
			shippingCost = 0.0;
		if (standardShipRB.isSelected())
			shippingCost = 10.99;
		if (expeditedShipRB.isSelected())
			shippingCost = 20.99;
	}

	private void autoFillInfo() {
		if (customer.getShippingId() != 0) {
			ShippingInfo sInfo = checkOutModel.getShippingInfo(customer);
			autoFillShipping(sInfo);
		}
		if (customer.getPaymentId() != 0) {
			PaymentMethod pInfo = checkOutModel.getPayMethod(customer);
			autoFillPaymentMethod(pInfo);
		}
	}

	private void autoFillPaymentMethod(PaymentMethod pInfo) {
		String[] fThenLName = pInfo.getCardHolderName().split(" ");
		ccHolderFnameTF.setText(fThenLName[0]);
		ccHolderLnameTF.setText(fThenLName[1]);
		ccNumTF.setText(pInfo.getCcNum());
		ccccvNumTF.setText(pInfo.getCcvNum());
		String[] monthThenYear = pInfo.getExpireDate().split(" ");
		monthCB.setValue(monthThenYear[0]);
		yearCB.setValue(monthThenYear[1]);
	}

	private void autoFillShipping(ShippingInfo sInfo) {
		addressTF.setText(sInfo.getAddress());
		cityTF.setText(sInfo.getCity());
		stateTF.setText(sInfo.getState());
		phoneNumbTF.setText(sInfo.getPhoneNum());
	}

	public static void passInCustomerAndCart(Customer cus, Cart sCart) {
		customer = cus;
		cart = sCart;
	}

}
