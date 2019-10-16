package fr.mds.travelagencyproject.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionManager {
	// CONSTANTS
	private static final String URL = "jdbc:mysql://localhost/mds.java.jdbc?zeroDateTimeBehavior=CONVERT_TO_NULL&serverTimezone=UTC" ; 
	private static final String USERNAME = "root" ; 
	private static final String PSD = "" ; 
	
	
	// singleton
	private static Connection connection = null ;  // c'est par défaut (contrairement aux primitifs ;) ) 
	
	public static Connection getConnection() {
		if (connection == null ) {
			loadDriver();
			//instantiate connection
			try {
				connection = DriverManager.getConnection(URL , USERNAME , PSD) ;
				connection.setAutoCommit(false);
			} catch (SQLException e) {
				System.err.println("The connexion to the Database didn't worked!");
				e.printStackTrace();
			}
		}
		return connection ; 
	}
		
	private static void loadDriver() {
		try {
			Class.forName(com.mysql.cj.jdbc.Driver.class.getName());
		} catch (ClassNotFoundException e) { e.printStackTrace(); }
	}
	
	public static void closeConnection() {
		try {
			if (connection != null && !connection.isClosed() ) {
				connection.close(); 
			}
		} catch (SQLException e) { e.printStackTrace(); }
	}
	
	

}
