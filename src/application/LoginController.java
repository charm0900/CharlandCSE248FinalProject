package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	private LoginModel loginModel = new LoginModel();
	private Cart cart = new Cart();
	private Customer customer = new Customer();;
	@FXML 
	private Label logInStatus;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label empPortalLabel;
	
//	public static Scene getScene() {
//		if (scene == null) {
//			Parent root = FXMLLoader.load(getClass().getResource("LoginView.fxml")); //used to tie in ONE view
//			Scene scene = new Scene(root);
//			scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//		
//		}
//		return scene;
//	}
	
	public void login(ActionEvent event)  {
		try {
			if (loginModel.isLogin(usernameField.getText(), passwordField.getText())) {
				System.out.println("login success!");
				customer.setEmail(usernameField.getText());
				customer.setPassword(passwordField.getText());
				loginModel.setUpCart(cart);
				if (loginModel.checkProductCount(cart)) {
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setHeaderText("Changes made in your cart");
					alert.setContentText("More then avaible quantity of an item in your \n"
							+ "cart, items have been removed");
					alert.show();
				}
				logInToHome();
			} else {
				logInStatus.setText("Email or Password Incorrect");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void SignUp(ActionEvent event) throws IOException {
		loginModel.closeConnection();
		Parent signUpRoot = FXMLLoader.load(getClass().getResource("CreateAccountView.fxml"));
			Scene signUpView = new Scene(signUpRoot);
			Stage window =  (Stage) ((Node)(event.getSource())).getScene().getWindow();
			window.setScene(signUpView);
//			window.show();
	}
	
	@FXML
	public void goToEmployeePortal() throws IOException {
		loginModel.closeConnection();
		Parent empRoot = FXMLLoader.load(getClass().getResource("EmployeeLoginView.fxml"));
			Scene empView = new Scene(empRoot);
			Stage window =  (Stage) usernameField.getScene().getWindow();
			window.setScene(empView);
//			window.show();
	}

	// required when implements initializable
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		if (loginModel.isDBConnected()) {
			System.out.println("connected");
		} else {
			System.out.println("not connected");
		}
	}
	
	private void logInToHome() {
		loginModel.closeConnection();
		HomeController.passInCustomerAndCart(customer, cart);
		try {
			Parent signUpRoot = FXMLLoader.load(getClass().getResource("HomeView.fxml"));
			Scene signUpView = new Scene(signUpRoot);
			Stage window =  (Stage) passwordField.getScene().getWindow();
			window.setScene(signUpView);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
