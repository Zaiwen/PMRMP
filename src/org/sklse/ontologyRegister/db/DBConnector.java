package org.sklse.ontologyRegister.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnector {

	public static Connection getConnection() {
		Connection conn = org.sklse.wsrr.database.DBConnector.getConnection();
		// try{
		// Class.forName(IDBInfoConstant.DRIVER_CLASS);
		// conn = DriverManager.getConnection(IDBInfoConstant.URL,
		// IDBInfoConstant.USER, IDBInfoConstant.PASSWORD);
		// // Context ctx = new InitialContext();
		// // DataSource ds = (DataSource)
		// ctx.lookup(IDBInfoConstant.DATASOURCE);
		// // conn = ds.getConnection();
		// }catch(Exception e){
		// e.printStackTrace();
		// }
		return conn;
	}

	public static void closeConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void closeStatement(Statement sta) {
		try {
			if (sta != null)
				sta.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
