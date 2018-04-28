package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CheckOutModel {
	private Connection connection;
	
	CheckOutModel(){
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
    
    public ShippingInfo getShippingInfo(Customer customer) {
		ShippingInfo info = new ShippingInfo();
		try {
			PreparedStatement preparedStatment = null;
			ResultSet rs = null;
			String query = "select * from Shipping where ShippingID=?"; //? is place holders
			preparedStatment = connection.prepareStatement(query);
			preparedStatment.setInt(1, customer.getShippingId());
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
					info.setShippingID(customer.getShippingId());
					info.setAddress(rs.getString("Address"));
					info.setCity(rs.getString("City"));
					info.setState(rs.getString("State"));
					info.setPhoneNum(rs.getString("PhoneNumber"));
			}
			return info;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }
    
    public PaymentMethod getPayMethod(Customer customer) {
		PaymentMethod info = new PaymentMethod();
		try {
			PreparedStatement preparedStatment = null;
			ResultSet rs = null;
			String query = "select * from PaymentMethod where PaymentMethodID=?"; //? is place holders
			preparedStatment = connection.prepareStatement(query);
			preparedStatment.setInt(1, customer.getPaymentId());
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
					info.setPaymentID(customer.getPaymentId());
					info.setCardHolderName(rs.getString("CCCardHolderName"));
					info.setCcNum(rs.getString("CCNum"));
					info.setExpireDate(rs.getString("CCExpireDate"));
					info.setCcvNum(rs.getString("CCCCV"));
			}
			return info;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
    }

}
