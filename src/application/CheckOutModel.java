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
		String query = "select * from PaymentMethod where PaymentMethodID=?"; //? is place holders
		try (PreparedStatement preparedStatment = connection.prepareStatement(query);){
			ResultSet rs = null;
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

	public void rememberAddress(ShippingInfo sInfo, Customer customer) {
		String query = "INSERT INTO Shipping (Address, City, State, PhoneNumber) VALUES (?, ?, ?, ?);"; // ? is place holders
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)){
			preparedStatment.setString(1, sInfo.getAddress());
			preparedStatment.setString(2, sInfo.getCity());
			preparedStatment.setString(3, sInfo.getState());
			preparedStatment.setString(4, sInfo.getPhoneNum());
			preparedStatment.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		query = "select * from Shipping where Address=? and City=? and State=? and PhoneNumber=?";
		ResultSet rs = null;
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)){
			preparedStatment.setString(1, sInfo.getAddress());
			preparedStatment.setString(2, sInfo.getCity());
			preparedStatment.setString(3, sInfo.getState());
			preparedStatment.setString(4, sInfo.getPhoneNum());
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				int s = rs.getInt("ShippingID");
				System.out.println("shipping id is : " + s);
				sInfo.setShippingID(rs.getInt("ShippingID"));
				customer.setShippingId(sInfo.getShippingID());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		query = "UPDATE Customers SET ShippingID=? WHERE CustomerID=?";
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)){
			preparedStatment.setInt(1, sInfo.getShippingID());
			preparedStatment.setInt(2, customer.getId());
			preparedStatment.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void rememberCard(PaymentMethod pInfo, Customer customer) {
		String query = "INSERT INTO PaymentMethod (CCCardHolderName, CCNum, CCExpireDate, CCCCV) VALUES (?, ?, ?, ?);"; // ? is place holders
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)){
			preparedStatment.setString(1, pInfo.getCardHolderName());
			preparedStatment.setString(2, pInfo.getCcNum());
			preparedStatment.setString(3, pInfo.getExpireDate());
			preparedStatment.setString(4, pInfo.getCcvNum());
			preparedStatment.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		query = "select * from PaymentMethod where CCCardHolderName=? and CCNum=? and CCExpireDate=? and CCCCV=?";
		ResultSet rs = null;
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)){
			preparedStatment.setString(1, pInfo.getCardHolderName());
			preparedStatment.setString(2, pInfo.getCcNum());
			preparedStatment.setString(3, pInfo.getExpireDate());
			preparedStatment.setString(4, pInfo.getCcvNum());
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				pInfo.setPaymentID(rs.getInt("PaymentMethodID"));
				customer.setPaymentId(pInfo.getPaymentID());
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		query = "UPDATE Customers SET PaymentMethodID=? WHERE CustomerID=?";
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)){
			preparedStatment.setInt(1, pInfo.getPaymentID());
			preparedStatment.setInt(2, customer.getId());
			preparedStatment.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
