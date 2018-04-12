package application;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CreateAccountContoller {
	private CreateAccountModel createAccountModel = new CreateAccountModel();
	@FXML
	private TextField fNameField;
	@FXML
	private TextField lNameField;
	@FXML
	private TextField emailField;
	@FXML
	private TextField passwordField;
	@FXML
	private Label status;

	public void createAccount(ActionEvent event) throws IOException {
		if (checkName() && checkEmail() && checkPass()) {
			try {
				if (createAccountModel.createAccount(emailField.getText(), passwordField.getText(),
						fNameField.getText(), lNameField.getText())) {
					System.out.println("account created");
					Alert alert = new Alert(Alert.AlertType.INFORMATION);
					alert.setContentText("Account Created");
					Optional<ButtonType> result = alert.showAndWait();
					if (!result.isPresent()) {
						returnToLogin(event);
					} else if (result.get() == alert.getButtonTypes().get(0)) {
						returnToLogin(event);
					}
				} else {
					status.setText("Please enter a vaild, unique  email");
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void returnToLogin(ActionEvent event) throws IOException {
		Parent loginRoot = FXMLLoader.load(getClass().getResource("LoginView.fxml"));
		Scene loingView = new Scene(loginRoot);
		Stage window = (Stage) ((Node) (event.getSource())).getScene().getWindow();
		window.setScene(loingView);
		window.show();
	}

	private int findCaptital(String value) {
		Pattern pat = Pattern.compile("[A-Z][^A-Z]*$");
		Matcher match = pat.matcher((CharSequence) value);
		int IndexOfCapital = -1;
		if (match.find()) {
			IndexOfCapital = match.start();
		}

		return IndexOfCapital;
	}

	private int isEmail() {
		if (emailField.getText().contains("@")) {
			return 1;
		} else {
			return -1;
		}

	}

	private boolean checkEmail() {

		if (emailField.getText().length() > 1 && isEmail() > -1) {
			return true;
		} else {
			status.setText("Please Make sure your email is unique, longer than 2 characters, and contains @");
		}
		return false;

	}

	private boolean checkPass() {
		if (passwordField.getText().length() > 1 && findCaptital(passwordField.getText()) > -1) {
			return true;
		} else {
			status.setText("Please Make sure your password is longer than 2 characters, contains a capital letter");
		}
		return false;
	}
	
	private boolean checkName() {
		if (fNameField.getText().equals("") || lNameField.getText().equals("")) {
			status.setText("Please Enter a First and Last Name");
			return false;
		}
		return true;
	}
}
