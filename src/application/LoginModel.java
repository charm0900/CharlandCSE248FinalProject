package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
	private Connection connection;
	
	public LoginModel() {
		connection = SQLiteHelperClass.conect();
		if (connection == null) {
			System.exit(1);
		}
	}
	
    public void closeConnection() {
    	try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
	
	public boolean isDBConnected() {
		try {
			return !connection.isClosed();
		} catch (Exception e) {
			System.out.println("Error connecting to the DB");
			return false;
		}
	}
	
	public boolean isLogin(String email, String password) throws SQLException { 
		PreparedStatement preparedStatment = null;
		ResultSet resultSet = null;
		String query = "select * from Customers where Email=? and Password=?;"; //? is place holders
		try {
			preparedStatment = connection.prepareStatement(query);
			preparedStatment.setString(1, email);
			preparedStatment.setString(2, password);
			resultSet = preparedStatment.executeQuery();
			if (resultSet.next()) { // if result comes in with a match it returns true
				return true;
			} else {
				return false;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			preparedStatment.close();
			resultSet.close();
		}
		return false;
	}
	
	


}
