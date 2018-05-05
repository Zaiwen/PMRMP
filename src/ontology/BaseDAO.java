package ontology;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import databaseaccess.Access;

public abstract class BaseDAO {
	protected Connection conn;
	protected PreparedStatement sta;
	protected ResultSet set;

	public BaseDAO() {
		//this.conn = DBConnector.getConnection();
		this.conn=Access.getConnection();
	}

	public BaseDAO(Connection conn) {
		this.conn = conn;
	}

	public void closeDBConnection() {
		//DBConnector.closeConnection(conn);
		Access.closeConnection(conn);
	}

	public void finalize() throws Throwable {
		super.finalize();
		this.closeDBConnection();
	}
}
