package org.sklse.ontologyRegister.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public abstract class BaseDAO {
	protected Connection mycon;
	protected PreparedStatement ps;
	protected ResultSet set;

	public BaseDAO() {
		this.mycon = DBConnector.getConnection();
	}

	public BaseDAO(Connection conn) {
		this.mycon = conn;
	}

	public void closeDBConnection() {
		DBConnector.closeConnection(mycon);
	}

}
