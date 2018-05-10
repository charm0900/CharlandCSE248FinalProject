package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {
	private Connection connection;
	private int customerID = 0;
	
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
				customerID = resultSet.getInt("CustomerID");
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
	
	public void setUpCart(Cart cart) {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		String query = "select * from ShoppingCartItems where CustomerID=?;"; // ? is place holders
		try {
			preparedStatment = connection.prepareStatement(query);
			preparedStatment.setInt(1, customerID);
			rs = preparedStatment.executeQuery();
			while (rs.next()) { // if result comes in with a match it returns true
				Product product = getProduct(rs.getInt("ProductID"));
				if (product != null) {
					cart.addToCart(product);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public boolean checkProductCount(Cart cart) {
		boolean result = false;
		for (int i = 0; i < cart.getCartSize(); i++) {
			int counter = 0;
			for (int j = 0; j < cart.getCartSize(); j++) {
				if (cart.getCart().get(i).getId() == cart.getCart().get(j).getId()) {
					counter++;
				}
			}
			Product product = getProduct(cart.getCart().get(i).getId());
			int k = (product.getQuanity() - counter);
			if (k < 0) {
				while (k < 0) {
					cart.removeByID(product.getId());
					k++;
				}
				result = true;
			}
		}
		return result;
	}

	private Product getProduct(int productId) {
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		String query = "select * from Product where ProductID=?;"; // ? is place holders
		try {
			preparedStatment = connection.prepareStatement(query);
			preparedStatment.setInt(1, productId);
			rs = preparedStatment.executeQuery();
			if (rs.next()) { // if result comes in with a match it returns true
				Product product = new Product(productId, rs.getString("ProductName"), rs.getDouble("Price"),
						rs.getInt("Quanity"), rs.getString("Department"), rs.getString("Description"),
						rs.getString("DateAdded"), rs.getDouble("Height"), rs.getDouble("Length"),
						rs.getDouble("Width"));
				return product;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	


}
