package application;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController implements Initializable {
	private LoginModel loginModel = new LoginModel();
	@FXML // visable to scene builder
	private Label logInStatus;
	@FXML
	private TextField usernameField;
	@FXML
	private PasswordField passwordField;
	
	public void login(ActionEvent event)  {
		try {
			if (loginModel.isLogin(usernameField.getText(), passwordField.getText())) {
				logInStatus.setText("login success!");
			} else {
				logInStatus.setText("Login failure!");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
