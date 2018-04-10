package application;

import java.sql.Connection;
import java.sql.DriverManager;

public class SQLiteHelperClass {
	public static Connection conect() { //good to use verbs for methods, static means you dont need an instance of class to call method
		try {
			Class.forName("org.sqlite.JDBC");
			Connection conect = DriverManager.getConnection("jdbc:sqlite:HumanityDB.db"); //this is possible if database is in project folder
			return conect;
		} catch (Exception e) {
			return null;
		}
	}

}
