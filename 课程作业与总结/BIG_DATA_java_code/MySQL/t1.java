import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class t1 {

	static final String DRIVER = "com.mysql.jdbc.Driver";
	static final String DB = "jdbc:mysql://localhost/test?useUnicode=true&characterEncoding=utf-8&useSSL=false";
	static final String USER = "root";
	static final String PASSWD = "123456";
	
	public static void main(String[] args) throws Exception{
		Connection conn = null;
		Statement stat = null;
		
		Class.forName(DRIVER);
		System.out.println("Connecting to database...");
		
		// con mysql
		conn = DriverManager.getConnection(DB, USER, PASSWD);
		stat = conn.createStatement();
		
		// insert data
		String sql = "insert into Student values('scofield', 45, 89, 100)";
		stat.execute(sql);
		System.out.println("insert success");
			
	}

}
