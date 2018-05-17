package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class AccountingModel {
	private Connection connection;

	public AccountingModel() {
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

	public double[] getSalesForPastWeek() {
		double[] array = new double[7];
		Date now = Calendar.getInstance().getTime();
		final Calendar cal = Calendar.getInstance();

		ResultSet rs = null;
		String query = "select * from 'Order' where ShippingDate like ? "; // ? is place holders
		for (int i = 0; i < array.length; i++) {
			try (PreparedStatement preparedStatment = connection.prepareStatement(query)) {
				SimpleDateFormat sdf = new SimpleDateFormat("MM/dd/yyyy");
				String sDate = sdf.format(now);
				preparedStatment.setString(1, sDate + "%");
				rs = preparedStatment.executeQuery();
				while (rs.next()) { // if result comes in with a match it returns true
					array[i] += rs.getDouble("Total");
				}
				cal.add(Calendar.DATE, -i);
				now.setTime(cal.getTimeInMillis());
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return array;
	}

	public int getOrdersPlaced() {
		// SELECT COUNT(column_name)
		// FROM table_name
		ResultSet rs = null;
		String query = "SELECT COUNT(*) from 'Order'"; // ? is place holders
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)) {
			rs = preparedStatment.executeQuery();
			if (rs.next()) { // if result comes in with a match it returns true
				return rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}

	public int getProdcutsSold() {
		// SELECT COUNT(column_name)
		// FROM table_name
		int counter = 0;
		ResultSet rs = null;
		String query = "SELECT * from 'OrderItems' "; // ? is place holders
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)) {
			rs = preparedStatment.executeQuery();
			while (rs.next()) { // if result comes in with a match it returns true
				counter++;
			}
			return counter;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;

	}
	
	public String getTotalSales() {
		// SELECT COUNT(column_name)
		// FROM table_name
		double sum = 0;
		ResultSet rs = null;
		String query = "SELECT * from 'Order' "; // ? is place holders
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)) {
			rs = preparedStatment.executeQuery();
			while (rs.next()) { // if result comes in with a match it returns true
				sum+= rs.getDouble("Total");
			}
			DecimalFormat df = new DecimalFormat("##.##");
			String end = df.format(sum);
			return end;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;

	}

}
