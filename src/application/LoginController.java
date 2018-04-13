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
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController implements Initializable {
	private static Scene scene;
	private LoginModel loginModel = new LoginModel();
	@FXML // visable to scene builder
	private Label logInStatus;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	
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
				logInStatus.setText("login success!");
			} else {
				logInStatus.setText("Email or Password Incorrect");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public void SignUp(ActionEvent event) throws IOException {
		Parent signUpRoot = FXMLLoader.load(getClass().getResource("CreateAccountView.fxml"));
			Scene signUpView = new Scene(signUpRoot);
			Stage window =  (Stage) ((Node)(event.getSource())).getScene().getWindow();
			window.setScene(signUpView);
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

}
