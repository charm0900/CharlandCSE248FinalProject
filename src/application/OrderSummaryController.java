package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;


public class OrderSummaryController implements Initializable {
	private OrderSummaryModel orderSummaryModel = new OrderSummaryModel();
	private static Cart cart;
	private static Customer customer;
	private static ShippingInfo sInfo;
	private static PaymentMethod pInfo;
	private static double shippingCost;
	private double total;
	
	@FXML 
	private Label arrivalLabel;
	@FXML
	private Label subTotalLabel;
	@FXML
	private Label shippingCostLabel;
	@FXML
	private Label taxLabel;
	@FXML
	private Label totalLabel;
	@FXML
	private Label cartNumber;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		subTotalLabel.setText(cart.getSubTotal());
		shippingCostLabel.setText(Double.toString(shippingCost));
		getArrivaldate();
		getTax();
		getTotal();
		System.out.println("# of items in cart" + cart.getCart().get(0).getQuanity());
		cartNumber.setText(Integer.toString(cart.getCartSize()));
	}
	
	private void getTotal() {
		total = Double.parseDouble(subTotalLabel.getText()) + 
				Double.parseDouble(taxLabel.getText()) + shippingCost;
		String df = new DecimalFormat("##.##").format(total);
		totalLabel.setText(df);
	}

	private void getTax() {
		double tax = .08454 * Double.parseDouble(subTotalLabel.getText());
		String df = new DecimalFormat("##.##").format(tax);
		taxLabel.setText(df);
	}

	private void getArrivaldate() {
		int shippingTime;
		if (shippingCost == 0.0)
			shippingTime = 14;
		else if (shippingCost == 10.99)
			shippingTime = 7;
		else 
			shippingTime = 4;
			
		Date currentDate = new Date();
		DateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss");
		Calendar current = Calendar.getInstance();
		current.setTime(currentDate);
		current.add(Calendar.DAY_OF_MONTH, shippingTime);
		Date shipDate = current.getTime();
		arrivalLabel.setText(dateFormat.format(shipDate));
	}
	
	@FXML
	public void placeOrder() throws SQLException {
		if (orderSummaryModel.placeOrder(customer, cart, total, arrivalLabel.getText())) {
			clearCart();
			orderSummaryModel.updateProducts();
			orderSummaryModel.closeConnection();
			Alert alert = new Alert(Alert.AlertType.INFORMATION);
			alert.setHeaderText("Order Placed");
			alert.setContentText("Your order ID: " + orderSummaryModel.getOrderID());
			Optional<ButtonType> result = alert.showAndWait();
			if (!result.isPresent()) {
				returnToHome();
			} else if (result.get() == alert.getButtonTypes().get(0)) {
				returnToHome();
			}
		}
	}

	private void clearCart() {
		orderSummaryModel.clearCart(customer);
		cart = new Cart();
	}

	private void returnToHome() {
		orderSummaryModel.closeConnection();
		HomeController.passInCustomerAndCart(customer, cart);
		try {
			Parent homeRoot = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
			Scene homeView = new Scene(homeRoot);
			Stage window =  (Stage) arrivalLabel.getScene().getWindow();
			window.setScene(homeView);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	@FXML
	public void backToCheckOut() {
		orderSummaryModel.closeConnection();
		try {
			Parent checkOutRoot = FXMLLoader.load(getClass().getResource("CheckOutView.fxml"));
			Scene CheckOutView = new Scene(checkOutRoot);
			Stage window = (Stage) arrivalLabel.getScene().getWindow();
			window.setScene(CheckOutView);
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void goHome() {
		orderSummaryModel.closeConnection();
		HomeController.passInCustomerAndCart(customer, cart);
		try {
			Parent homeRoot = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
			Scene homeView = new Scene(homeRoot);
			Stage window = (Stage) arrivalLabel.getScene().getWindow();
			window.setScene(homeView);
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void goToCart() {
		CartController.passInCustomerAndCart(customer, cart);
		try {
			Parent searchRoot = FXMLLoader.load(getClass().getResource("CartView.fxml"));
			Scene searchView = new Scene(searchRoot);
			Stage window = (Stage) arrivalLabel.getScene().getWindow();
			window.setScene(searchView);
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public static void passInCustomerAndCart(Customer cus, Cart sCart) {
		customer = cus;
		cart = sCart;
	}
	
	public static void passInCheckOutInfo(ShippingInfo shipInfo, PaymentMethod payInfo, double cost) {
		sInfo = shipInfo;
		pInfo = payInfo;
		shippingCost = cost;
	}

}
