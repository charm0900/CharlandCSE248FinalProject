package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class OrderSummaryModel {

	private Connection connection;
	private int orderID;

	public OrderSummaryModel() {
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

	private void reOpenConn() {
		connection = SQLiteHelperClass.conect();
		if (connection == null) {
			System.exit(1);
		}
	}
	
	public int getOrderID() {
		return orderID;
	}

	public boolean placeOrder(Customer customer, Cart cart, double total, String shippingDate) {
		PreparedStatement preparedStatment = null;
		String query = "INSERT INTO 'Order' (CustomerID, ShippingID, ShippingDate, Status, PaymentMethodID, Total) VALUES (?, ?, ?, ?, ?, ?);"; // ?
																																				// is
																																				// place
																																				// holders

		try {
			preparedStatment = connection.prepareStatement(query);
			preparedStatment.setInt(1, customer.getId());
			preparedStatment.setInt(2, customer.getShippingId());
			preparedStatment.setString(3, shippingDate);
			preparedStatment.setString(4, "Shipped");
			preparedStatment.setInt(5, customer.getPaymentId());
			preparedStatment.setDouble(6, total);
			preparedStatment.executeUpdate();
		} catch (SQLException e1) {
			e1.printStackTrace();
			System.out.println("order placement failed");
			return false;
		}

		preparedStatment = null;
		ResultSet rs = null;
		query = "select * from 'Order' where CustomerID=? and ShippingID=? and ShippingDate=? and Status=? and PaymentMethodID=? and Total=?"; // ?
																																				// is
																																				// place
																																				// holders
		try {
			preparedStatment = connection.prepareStatement(query);
			preparedStatment = connection.prepareStatement(query);
			preparedStatment.setInt(1, customer.getId());
			preparedStatment.setInt(2, customer.getShippingId());
			preparedStatment.setString(3, shippingDate);
			preparedStatment.setString(4, "Shipped");
			preparedStatment.setInt(5, customer.getPaymentId());
			preparedStatment.setDouble(6, total);
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				orderID = rs.getInt("OrdersID");
				System.out.println("order id is " + orderID);
			}
			preparedStatment.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		closeConnection();
		reOpenConn();
		fillOrder(cart);
		return true;
	}

	private void fillOrder(Cart cart) {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		PreparedStatement ps2 = null;
		String query = "INSERT INTO OrderItems (OrdersID, ProductID, Quanity, Price) VALUES (?, ?, ?, ?);"; // ? is
																											// place
																											// holders
		try {
			for (int i = 0; i < cart.getCartSize(); i++) {
				String q = "select * from OrderItems where ProductID=? and OrdersID=?;"; // ? is place holders
				for (int j = 0; j < cart.getCartSize(); j++) {
					ps2 = connection.prepareStatement(q);
					ps2.setInt(1, cart.getCart().get(j).getId());
					ps2.setInt(2, orderID);
					rs = ps2.executeQuery();
					if (!rs.next()) {
						// ps2.close();
						closeConnection();
						reOpenConn();
						preparedStatment = connection.prepareStatement(query);
						System.out.println("order id is : " + orderID);
						preparedStatment.setInt(1, orderID);
						preparedStatment.setInt(2, cart.getCart().get(j).getId());
						preparedStatment.setInt(3, cart.getCart().get(j).getQuanity());
						preparedStatment.setDouble(4, cart.getCart().get(j).getPrice());
						preparedStatment.executeUpdate();
						preparedStatment.close();
					}
				}
			}
		} catch (SQLException e) {
			System.out.println("order Placement failed");
			e.printStackTrace();
		}
	}

	public void clearCart(Customer customer, Cart cart) {
		String sql = "DELETE FROM ShoppingCartItems WHERE CustomerID=? and ProductID=?";
		 
		try {
			int counter = 0;
			int rowsDeleted;
			for (int i = 0; i < cart.getCartSize(); i++) {
				PreparedStatement statement = connection.prepareStatement(sql);
				statement.setInt(1, customer.getId());
				statement.setInt(1, cart.getCart().get(i).getId());
				rowsDeleted = statement.executeUpdate();
				if (rowsDeleted > 0) {
					counter++;
				}
			}
			if (counter > 0) {
			    System.out.println("A product was removed from cart!");
			}
		} catch (SQLException e) {
			System.out.println("nothing in cart in database");
			e.printStackTrace();
		}
	}

}
