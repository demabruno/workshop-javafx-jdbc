package db;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DB {
	private static Connection conn = null;
	
	public static Connection getConnection() {
		try{
			if (conn == null) {
				Properties prop = loadProperties();
				String url = prop.getProperty("dburl");
				conn = DriverManager.getConnection(url, prop);
			}
			return conn;
		}
		catch(SQLException e)
		{
			throw new DBException(e.getMessage());
		}
	}
	
	public static void closeConnection()
	{
		try {
			if (conn != null) {
				conn.close();
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static void closeResultSet(ResultSet rs)
	{
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static void closeStatement(Statement st)
	{
		try {
			if (st != null) {
				st.close();
			}
		} catch (SQLException e) {
			throw new DBException(e.getMessage());
		}
	}
	
	public static Properties loadProperties() {
		try(FileInputStream fs = new FileInputStream("db.properties")){
			Properties prop = new Properties();
			prop.load(fs);
			return prop;
			}
		catch (IOException e) {
			throw new DBException(e.getMessage());
		}
	}
}

