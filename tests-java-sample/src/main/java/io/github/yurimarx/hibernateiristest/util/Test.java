package io.github.yurimarx.hibernateiristest.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Test {

	public static void main(String[] args) throws SQLException {
		Connection conn = null;
	    Properties connectionProps = new Properties();
	    connectionProps.put("user", "_SYSTEM");
	    connectionProps.put("password", "SYS");

	    conn = DriverManager.getConnection("jdbc:IRIS://localhost:1972/USER", connectionProps);
	    
	    String query = "select {fn cot(1.57)}";
	    
	    try (Statement stmt = conn.createStatement()) {
	      
	    	ResultSet rs = stmt.executeQuery(query);
	      
	    	while (rs.next()) {
	        
	    		Double value = rs.getDouble(1);
	    		System.out.println("Value: " + value);
	    	}
	    	
	    }
	}

}
