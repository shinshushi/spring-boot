package com.laptrinhjavaweb.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class EntityManagerFactory {
	
	static ResourceBundle resourceBundle = ResourceBundle.getBundle("db");
	public static Connection getConnection() {
		try {
			//Class.forName("com.mysql.cj.jdbc.Driver");
			//String url = "jdbc:mysql://localhost:3306/jspservlet122019?serverTimezone=UTC";
			//String user = "root";
			//String password = "1234";
			
			Class.forName(resourceBundle.getString("jdbc.datasource.driver"));
			String url = resourceBundle.getString("jdbc.datasource.url");
			String user = resourceBundle.getString("jdbc.datasource.username");
			String password = resourceBundle.getString("jdbc.datasource.password");
			return DriverManager.getConnection(url, user, password);
		} catch (ClassNotFoundException | SQLException e) {
			return null;
		}
	}

}
