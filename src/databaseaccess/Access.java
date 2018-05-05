package databaseaccess;
import java.sql.*;
public class Access{
    private Connection conn=null;
    private Statement stmt=null;
    private ResultSet rs=null;
    
    private static String DB_DRIVER = "org.postgresql.Driver";

	private static String DB_URL = "jdbc:postgresql://localhost:5432/ontology";

	private static String DB_USER="postgres";

	private static String DB_PASSWD = "123456";

	private  String type;
	
    
	/*the following is the function that should be called when logging in*/
	public void connDB(String type) throws Exception{
		this.type=type;
    	String url="jdbc:mysql://localhost:3306/"+type+"?useUnicode=true&characterEncoding=utf-8";
    	Class.forName("com.mysql.jdbc.Driver");
    	conn=DriverManager.getConnection(url,"root","MAcri");

    	//System.out.println("connect SQL db successfully! ");
    }

	public Connection getCon() {
		try {
			if (conn==null||conn.isClosed()) {
				String url = "jdbc:mysql://localhost:3306/" + type + "?useUnicode=true&characterEncoding=utf-8";
				conn = DriverManager.getConnection(url, "root", "MAcri");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static  Connection getConnection() {
		Connection connection = null;
		try {
			Class.forName(DB_DRIVER);
			connection = DriverManager
					.getConnection(DB_URL, DB_USER, DB_PASSWD);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		//System.out.println("postgre is ok...");
		return connection;
	}
    public void closeDB() throws SQLException{
    	if(rs!=null){
    		rs.close();
    		rs=null;
    		//System.out.println("释放RS");
    	
    	}
    	if(conn!=null){
    		conn.close();
    		conn=null;
    		//System.out.println("释放con");
    	}
    }
	public Connection getconn(){
    	return this.conn;
    }
    public ResultSet executeSelectSql(String sql)throws SQLException{
    	if(sql==null||sql.equals("")) return null;
    	sql=sql.trim();
    	stmt=conn.createStatement();
    	rs=stmt.executeQuery(sql);
    	
    	//System.out.println("sql execute successfully! ");
    	return rs;
    	
    	
    }
    public void executeUpdateSql(String sql) throws SQLException{
    	stmt=conn.createStatement();
    	stmt.executeUpdate(sql);
    }
    public int executeUpdateSql2(String sql) throws SQLException{
    	stmt=conn.createStatement();
    	stmt.executeUpdate(sql,Statement.RETURN_GENERATED_KEYS);
    	ResultSet rs = stmt.getGeneratedKeys();
    	int keyValue = -1;
    	if(rs.next()){
    		keyValue = rs.getInt(1);
    	}
    	return keyValue;
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
