package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class AddProductsModel {
	private Connection connection;

	
	public AddProductsModel() {
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

	public boolean addProduct(Product product) {
		String query = "INSERT INTO Product (ProductName, Price, Quanity, Description, Department, DateAdded, Height, Length, Width) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?);"; // ? is place holders
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)){
			preparedStatment.setString(1, product.getName());
			preparedStatment.setDouble(2, product.getPrice());
			preparedStatment.setInt(3, product.getQuanity());
			preparedStatment.setString(4, product.getDescription());
			preparedStatment.setString(5, product.getDepartment());
			preparedStatment.setString(6, product.getDateAdded());
			preparedStatment.setDouble(7, product.getHeight());
			preparedStatment.setDouble(8, product.getLength());
			preparedStatment.setDouble(9, product.getWidth());
			preparedStatment.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} 
		return true;
	}
    
}
