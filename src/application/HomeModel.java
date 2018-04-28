package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
		String query = "select * from Customers where Email=? and Password=?;"; //? is place holders
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
	
	public void setUpCart(Customer customer, Cart cart) {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		String query = "select * from ShoppingCartItems where CustomerID=?;"; //? is place holders
		try {
			preparedStatment = connection.prepareStatement(query);
			preparedStatment.setInt(1, customer.getId());
			rs = preparedStatment.executeQuery();
			while (rs.next()) { // if result comes in with a match it returns true
				Product product = getProduct(rs.getInt("ProductID"));
				if (product != null) {
					cart.addToCart(product);
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	private Product getProduct(int productId) {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		String query = "select * from Product where ProductID=?;"; //? is place holders
		try {
			preparedStatment = connection.prepareStatement(query);
			preparedStatment.setInt(1, productId);
			rs = preparedStatment.executeQuery();
			if (rs.next()) { // if result comes in with a match it returns true
				Product product = new Product(productId, rs.getString("ProductName"),
						rs.getDouble("Price"), rs.getInt("Quanity"), rs.getString("Department"), 
						rs.getString("Description"), rs.getString("DateAdded"), 
						rs.getDouble("Height"), rs.getDouble("Length"),
						rs.getDouble("Width"));
				return product;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		return null;
	}
	
    public void closeConnection() {
    	try {
			connection.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

}
