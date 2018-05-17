package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TrackOrderModel {
	private Connection connection;
	Order order = null;

	public TrackOrderModel() {
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

	public Order getOrder(int orderID) {
		ResultSet rs = null;
		String query = "select * from 'Order' where OrdersID=?";
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)) {
			preparedStatment.setInt(1, orderID);
			rs = preparedStatment.executeQuery();
			if (rs.next()) {
				order = new Order();
				order.setOrderID(orderID);
				String date = rs.getString("ShippingDate");
				String status = rs.getString("Status");
				order.setArrivalDate(date);
				checkStatus(date, status);
				int i = rs.getInt("ShippingID");
				getOrderAddress(i);
				return order;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	private void getOrderAddress(int shippingId) {
		ResultSet rset = null;
		String query = "select * from Shipping where ShippingID=?";
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)) {
			if (shippingId != 0) {
				preparedStatment.setInt(1, shippingId);
				rset = preparedStatment.executeQuery();
				if (rset != null) {
					StringBuilder address = new StringBuilder();
					address.append(rset.getString("Address"));
					address.append(", ");
					address.append(rset.getString("City"));
					address.append(", ");
					address.append(rset.getString("State"));
					order.setAddress(address.toString());
				}
			} else {
				order.setAddress("No address on file");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void checkStatus(String date, String status) {
		Date date1;
		try {
			// System.out.println(date);
			date1 = new SimpleDateFormat("MM/dd/yyyy hh:mm:ss").parse(date);
			SimpleDateFormat fmt = new SimpleDateFormat("MM/dd/yyyy  hh:mm:ss");
			fmt.format(date1);
			// System.out.println(date1);

			Date now = Calendar.getInstance().getTime();

			if (now.after(date1)) {
				order.setStatus("Delivered");
			} else {
				order.setStatus("Shipping");
				long diff = date1.getTime() - now.getTime();
				long daysAppart = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
				daysAppart++;
				System.out.println("days apart " + daysAppart);
				order.setDaysFromCurrentDate(daysAppart);
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
	}
}
