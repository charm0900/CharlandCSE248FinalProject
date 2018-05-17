package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EmployeeLoginModel {
	private Connection connection;
	
	public EmployeeLoginModel() {
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
    
	public Employee isLogin(String id, String password)  { 
		ResultSet rs = null;
		String query = "select * from Employees where IDNumber=? and Password=?;"; //? is place holders
		try (PreparedStatement preparedStatment = connection.prepareStatement(query)){
			preparedStatment.setString(1, id);
			preparedStatment.setString(2, password);
			rs = preparedStatment.executeQuery();
			if (rs.next()) { // if result comes in with a match it returns true
				 Employee emp = new Employee(rs.getString("IDNumber"), rs.getString("Password"), 
						 rs.getString("Name"));
				 return emp;
			} else {
				return null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
