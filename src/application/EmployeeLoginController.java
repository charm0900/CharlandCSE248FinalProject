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
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class EmployeeLoginController implements Initializable{
	private EmployeeLoginModel empolyeeLoginModel = new EmployeeLoginModel();
	private Employee employee;
	
	@FXML
	private TextField employeeIDTF;
	@FXML
	private TextField passwordTF;
	@FXML
	private Button signInBtn;
	@FXML
	private Label status;
	@FXML
	private Hyperlink backHP;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
	}
	
	@FXML
	public void login() {
		employee = empolyeeLoginModel.isLogin(employeeIDTF.getText(), passwordTF.getText());
		if (employee != null) {
			System.out.println("Login success");
			loginToAddProducts();
		} else {
			status.setText("ID or password incorrect");
		}
	}
	
	@FXML
	public void toCustomerLogin() {
//		EmployeeLoginModel
		empolyeeLoginModel.closeConnection();
		try {
			Parent loginRoot = FXMLLoader.load(getClass().getResource("loginView.fxml"));
			Scene loginView = new Scene(loginRoot);
			Stage window = (Stage)  (employeeIDTF.getScene().getWindow());
			window.setScene(loginView);
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	private void loginToAddProducts() {
		AddProductsController.passInEmployee(employee);
		empolyeeLoginModel.closeConnection();
		try {
			Parent addRoot = FXMLLoader.load(getClass().getResource("AddProductsView.fxml"));
			Scene addView = new Scene(addRoot);
			Stage window = (Stage)  (employeeIDTF.getScene().getWindow());
			window.setScene(addView);
			window.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	
}
