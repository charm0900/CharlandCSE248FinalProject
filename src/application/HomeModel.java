package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class HomeModel {
	private Connection connection;

	public HomeModel() {
		connection = SQLiteHelperClass.conect();
		if (connection == null) {
			System.exit(1);
		}
	}

	public void setUpCustomer(Customer customer) {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		String query = "select * from Customers where Email=? and Password=?;"; // ? is place holders
		try {
			preparedStatment = connection.prepareStatement(query);
			preparedStatment.setString(1, customer.getEmail());
			preparedStatment.setString(2, customer.getPassword());
			rs = preparedStatment.executeQuery();
			if (rs.next()) { // if result comes in with a match it returns true
				customer.setId(rs.getInt("CustomerID"));
				customer.setfName(rs.getString("FirstName"));
				customer.setlName(rs.getString("LastName"));
				customer.setPaymentId(rs.getInt("PaymentMethodID"));
				customer.setShippingId(rs.getInt("ShippingID"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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

	public void logOut(Customer customer, Cart cart) {
		String sql = "DELETE FROM ShoppingCartItems WHERE CustomerID=?;";
		PreparedStatement statement = null;

		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, customer.getId());
			int rowsDeleted = statement.executeUpdate();
			if (rowsDeleted > 0) {
				System.out.println("preapred cart for new items");
			}
			statement = null;
			String query = "INSERT INTO ShoppingCartItems (CustomerID, ProductID, Quantity) VALUES (?, ?, ?);"; // ? is
				for (int j = 0; j < cart.getCartSize(); j++) {
						statement = connection.prepareStatement(query);
						statement.setInt(1, customer.getId());
						statement.setInt(2, cart.getCart().get(j).getId());
						statement.setInt(3, 1);
						statement.executeUpdate();
				}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

}
