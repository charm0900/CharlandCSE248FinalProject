package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreateAccountModel {
	private Connection connection;

	public CreateAccountModel() {
		connection = SQLiteHelperClass.conect();
		if (connection == null) {
			System.exit(1);
		}
	}

	public boolean createAccount(String email, String password, String fName, String lName) throws SQLException {
		PreparedStatement preparedStatment = null;
		String query = "INSERT INTO Customers (Email, Password, FirstName, LastName) VALUES (?, ?, ?, ?);"; // ? is place holders
		try {
			preparedStatment = connection.prepareStatement(query);
			preparedStatment.setString(1, email);
			preparedStatment.setString(2, password);
			preparedStatment.setString(3, fName);
			preparedStatment.setString(4, lName);
			preparedStatment.executeUpdate();
		} catch (SQLException e) {
			System.out.println("Email already in System");
			return false;
		} finally {
			preparedStatment.close();
		}
		return true;
	}
	


}
