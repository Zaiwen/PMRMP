package org.sklse.wsrr.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * ���ݿ�������
 * 
 * @author sklse
 * 
 */
public class DBConnector {
	// ���ò���
	private static String PROP_FILE = "DBconnection.properties";

	private static String DB_DRIVER = "org.postgresql.Driver";

	private static String DB_URL = "jdbc:postgresql://127.0.0.1/wsrr?autoReconnect=true";

	private static String DB_USER = "postgres";

	private static String DB_PASSWD = "123456";

	private static String DB = "PostgreSQL";

	// ���ݿ�����
	private static int DB_TYPE = 0;

	static {
		// ���������ļ�
		loadProperties();
	}

	// ������ݿ�����
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(DB_DRIVER);
			connection = DriverManager
					.getConnection(DB_URL, DB_USER, DB_PASSWD);
			//log("��ȡ���ݿ����ӳɹ����ڣ�" + new java.util.Date());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			try {
				connection.close();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		return connection;
	}

	// �ر����ݿ�����
	public static void closeConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// �������ݿ������ļ�
	private static void loadProperties() {
		Properties prop = new Properties();
		try {
			prop.load(DBConnector.class.getResourceAsStream("/" + PROP_FILE));
			log("�������ݿ����óɹ����ڣ�" + new java.util.Date());
		} catch (FileNotFoundException e) {
			log("�Ҳ��������ļ�������Ĭ�����ü������ݿ����Ӳ�����");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			log("���������ļ�ʧ�ܣ�����Ĭ�����ü������ݿ����Ӳ�����");
			e.printStackTrace();
			return;
		}
		DBConnector.DB = (String) prop.get("DB");
		DBConnector.DB_DRIVER = (String) prop.get("DB_DRIVER");
		DBConnector.DB_URL = (String) prop.get("DB_URL");
		DBConnector.DB_USER = (String) prop.get("DB_USER");
		DBConnector.DB_PASSWD = (String) prop.get("DB_PASSWD");
		DBConnector.DB_TYPE = Integer.parseInt((String) prop.get("DB_TYPE"));

	}

	// ������ݿ�����
	public static String getDBName() {
		return DB;
	}

	public static int getDBType() {
		return DB_TYPE;
	}

	// ��־��¼
	private static void log(String msg) {
		System.out.println(msg);
	}

	// ���ݿ�������ò���
	public static String getParams() {
		return DBConnector.DB + "\n" + DBConnector.DB_DRIVER + "\n"
				+ DBConnector.DB_URL + "\n" + DBConnector.DB_USER + "\n"
				+ DBConnector.DB_PASSWD + "\n";
	}

}
