package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class SearchModel {
	private Connection connection;
	private String department;
	
	public SearchModel() {
		openConn();
	}
	
	private void openConn() {
		connection = SQLiteHelperClass.conect();
		if (connection == null) {
			System.exit(1);
		}
	}
	
	
	public ObservableList<Product> searchByDepartment() {
		openConn();
		ObservableList<Product> items = FXCollections.observableArrayList();
		PreparedStatement preparedStatment = null;
		ResultSet rs = null;
		String query = "select * from Product where Department=?"; //? is place holders
		try {
			preparedStatment = connection.prepareStatement(query);
			preparedStatment.setString(1, department);
			rs = preparedStatment.executeQuery();
			while (rs.next()) {
				// Create DateFormat to convert Date object information to prefered format
				//SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				// Create Date object at the current time
				//Date date = Calendar.getInstance().getTime();
				// Format information of date object based on DateFormat object
				//String s = dateFormat.format(date);
				

				// To convert string back to Date object
				//Date date = new SimpleDateFormat("yyyy-MM-dd").parse(rs.getString("DateAdded"));
				items.add(new Product(rs.getInt("ProductID"), rs.getString("ProductName"),
						rs.getDouble("Price"), rs.getInt("Quanity"), department, rs.getString("Description"),
						rs.getString("DateAdded"), rs.getDouble("Height"), rs.getDouble("Length"), rs.getDouble("Width")));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} //catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		closeConnection();
		return items;
	}

	public void setDepartment(String department) {
		this.department = department;
	}
	
	public String getDepartment() {
		return department;
	}
//	
//    private  java.sql.Date convertUtilToSql(java.util.Date uDate) {
//        java.sql.Date sDate = new java.sql.Date(uDate.getTime());
//        return sDate;
//    }
	
	public void close(Cart cart, Customer customer) {
		
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
