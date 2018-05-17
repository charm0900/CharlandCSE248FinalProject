package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountManagementModel {
	private Connection connection;
	
	public AccountManagementModel() {
		connection = SQLiteHelperClass.conect();
		if (connection == null) {
			System.exit(1);
		}
	}

	public ShippingInfo getShippingInfo(int shippingId) {
		String query = "select * from Shipping where ShippingID=?;";
		ResultSet rs = null;
		try (PreparedStatement preparedStatment = connection.prepareStatement(query);){
			preparedStatment.setInt(1, shippingId);
			rs = preparedStatment.executeQuery();
			if (rs.next()) { // if result comes in with a match it returns true
				ShippingInfo sInfo = new ShippingInfo();
				sInfo.setAddress(rs.getString("Address"));
				sInfo.setCity(rs.getString("City"));
				sInfo.setPhoneNum(rs.getString("PhoneNumber"));
				sInfo.setState(rs.getString("State"));
				sInfo.setShippingID(shippingId);
				return sInfo;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public PaymentMethod getPayInfo(int paymentId) {
		String query = "select * from PaymentMethod where PaymentMethodID=?;";
		ResultSet rs = null;
		try (PreparedStatement preparedStatment = connection.prepareStatement(query);){
			preparedStatment.setInt(1, paymentId);
			rs = preparedStatment.executeQuery();
			if (rs.next()) { // if result comes in with a match it returns true
				PaymentMethod pInfo = new PaymentMethod();
				pInfo.setCardHolderName(rs.getString("CCCardHolderName"));
				pInfo.setCcNum(rs.getString("CCNum"));
				pInfo.setExpireDate(rs.getString("CCExpireDate"));
				pInfo.setCcvNum(rs.getString("CCCCV"));
				pInfo.setPaymentID(paymentId);
				return pInfo;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public boolean updateCustomer(Customer customer) {
		String query = "UPDATE Customers SET FirstName=?, LastName=?, Email=?, Password=? WHERE CustomerID=?";
		int rs = 0;
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)){
			preparedStatment.setString(1, customer.getfName());
			preparedStatment.setString(2, customer.getlName());
			preparedStatment.setString(3, customer.getEmail());
			preparedStatment.setString(4, customer.getPassword());
			preparedStatment.setInt(5, customer.getId());
			rs = preparedStatment.executeUpdate();
			if (rs > 0) { // if result comes in with a match it returns true
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;	
	}

	public boolean updateShipping(ShippingInfo ship) {
		String query = "UPDATE Shipping SET Address=?, City=?, State=?, PhoneNumber=? WHERE ShippingID=?";
		int rs = 0;
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)){
			preparedStatment.setString(1, ship.getAddress());
			preparedStatment.setString(2, ship.getCity());
			preparedStatment.setString(3, ship.getState());
			preparedStatment.setString(4, ship.getPhoneNum());
			preparedStatment.setInt(5, ship.getShippingID());
			rs = preparedStatment.executeUpdate();
			if (rs > 0) { // if result comes in with a match it returns true
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;	
	}

	public boolean updatePay(PaymentMethod pay) {
		String query = "UPDATE PaymentMethod SET CCCardHolderName=?, CCNum=?, CCExpireDate=?, CCCCV=? WHERE PaymentMethodID=?";
		int rs = 0;
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)){
			preparedStatment.setString(1, pay.getCardHolderName());
			preparedStatment.setString(2, pay.getCcNum());
			preparedStatment.setString(3, pay.getExpireDate());
			preparedStatment.setString(4, pay.getCcvNum());
			preparedStatment.setInt(5, pay.getPaymentID());
			rs = preparedStatment.executeUpdate();
			if (rs > 0) { // if result comes in with a match it returns true
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;	
	}

}
