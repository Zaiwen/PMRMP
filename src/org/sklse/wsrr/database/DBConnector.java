package org.sklse.wsrr.database;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * 数据库连接器
 * 
 * @author sklse
 * 
 */
public class DBConnector {
	// 配置参数
	private static String PROP_FILE = "DBconnection.properties";

	private static String DB_DRIVER = "org.postgresql.Driver";

	private static String DB_URL = "jdbc:postgresql://127.0.0.1/wsrr?autoReconnect=true";

	private static String DB_USER = "postgres";

	private static String DB_PASSWD = "123456";

	private static String DB = "PostgreSQL";

	// 数据库类型
	private static int DB_TYPE = 0;

	static {
		// 加载配置文件
		loadProperties();
	}

	// 获得数据库连接
	public static Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(DB_DRIVER);
			connection = DriverManager
					.getConnection(DB_URL, DB_USER, DB_PASSWD);
			//log("获取数据库连接成功，在：" + new java.util.Date());
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

	// 关闭数据库连接
	public static void closeConnection(Connection conn) {
		try {
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// 加载数据库配置文件
	private static void loadProperties() {
		Properties prop = new Properties();
		try {
			prop.load(DBConnector.class.getResourceAsStream("/" + PROP_FILE));
			log("加载数据库配置成功，在：" + new java.util.Date());
		} catch (FileNotFoundException e) {
			log("找不到配置文件，将以默认配置加载数据库连接参数。");
			e.printStackTrace();
			return;
		} catch (IOException e) {
			log("加载配置文件失败，将以默认配置加载数据库连接参数。");
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

	// 获得数据库名称
	public static String getDBName() {
		return DB;
	}

	public static int getDBType() {
		return DB_TYPE;
	}

	// 日志记录
	private static void log(String msg) {
		System.out.println(msg);
	}

	// 数据库具体配置参数
	public static String getParams() {
		return DBConnector.DB + "\n" + DBConnector.DB_DRIVER + "\n"
				+ DBConnector.DB_URL + "\n" + DBConnector.DB_USER + "\n"
				+ DBConnector.DB_PASSWD + "\n";
	}

}
