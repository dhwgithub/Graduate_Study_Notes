import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import com.mysql.cj.exceptions.RSAException;

public class t2 {

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
		String sql = "select Name, English from Student where Name='scofield'";
		ResultSet rs = stat.executeQuery(sql);
		System.out.println("Name" + "\t\t" + "English");
		while (rs.next()) {
			System.out.println(rs.getString(1) + "\t\t");
			System.out.println(rs.getInt(2));
		}
	}

}
