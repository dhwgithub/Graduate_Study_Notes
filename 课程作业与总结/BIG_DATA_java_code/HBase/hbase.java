import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;


public class hbase {

	public static Configuration configuration;
	public static Connection connection;
	public static Admin admin;
	
	public static void main(String[] args) throws Exception {
		String tableName = "testTable";
		String[] fileds = {"Id", "Score"};
		createTable(tableName, fileds);
	}

	private static void createTable(String talbeName,
									String[] fields) throws Exception {
		init();
		
		TableName tableName = TableName.valueOf(talbeName);
		if (admin.tableExists(tableName)) {
			// first delete table
			// then create new table
			System.out.println("the table already exists");
			admin.disableTable(tableName);
			admin.deleteTable(tableName);
		}
		
		HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
		for (String str : fields) {
			HColumnDescriptor hColumnDescriptor = new HColumnDescriptor(str);
			hTableDescriptor.addFamily(hColumnDescriptor);
		}
		
		admin.createTable(hTableDescriptor);
		System.out.println("create new table success");
		
		close();
	}
	
	// create connection
	private static void init() {
		configuration  = HBaseConfiguration.create();
        configuration.set("hbase.rootdir","hdfs://localhost:9000/hbase");
        try{
            connection = ConnectionFactory.createConnection(configuration);
            admin = connection.getAdmin();
        }catch (IOException e){
            e.printStackTrace();
        }
	}
	
	// close conntion
    private static void close(){
        try{
            if(admin != null){
                admin.close();
            }
            if(null != connection){
                connection.close();
            }
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
