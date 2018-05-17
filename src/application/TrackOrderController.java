package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class TrackOrderController implements Initializable {
	TrackOrderModel trackOrderModel = new TrackOrderModel();
	private static Cart cart;
	private static Customer customer;
	private int orderID;
	private Order order;

	@FXML
	private Button homeBtn;
	@FXML
	private TextField orderTF;
	@FXML
	private Label addressLabel;
	@FXML
	private Label arrivalLabel;
	@FXML
	private Label statusLabel;
	@FXML
	private ProgressIndicator progress;
	@FXML
	private Label status;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	@FXML
	public void track() {
		if (orderTF.getText() != "") {
			try {
				orderID = Integer.parseInt(orderTF.getText());
				order = trackOrderModel.getOrder(orderID);
				if (order != null) {
					arrivalLabel.setText(order.getArrivalDate());
					statusLabel.setText(order.getStatus());
					double days = order.getDaysFromCurrentDate();
					days = 14 - days;
					days = days / 14;
					progress.setProgress(days);
					addressLabel.setText(order.getAddress());
					status.setText("");
				} else {
					orderTF.getText();
					status.setText("Order ID Does not exist");
				}
			} catch (NumberFormatException e) {
				status.setText("please enter a correct order id");
			}
		} else {
			status.setText("Enter an oder ID");
		}
	}

	public void goHome(ActionEvent event) {
		HomeController.passInCustomerAndCart(customer, cart);
		trackOrderModel.closeConnection();
		try {
			Parent homeRoot = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
			Scene homeView = new Scene(homeRoot);
			Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();
			window.setScene(homeView);
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
}
