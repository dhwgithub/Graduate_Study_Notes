import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;


public class hbase2 {

	public static Configuration configuration;
	public static Connection connection;
	public static Admin admin;
	
	public static void main(String[] args) throws Exception {
		String tableName = "testTable";
		String[] fields = {"Score:Math", "Score:Computer", "Score:English"};
		String[] values = {"99", "95", "90"};
		addRecord(tableName, "S_Name", fields, values);
	}

	private static void addRecord(String tableName, String row,
					String[] fields, String[] values) throws IOException {
		init();
		
		Table table = connection.getTable(TableName.valueOf(tableName));
		for (int i = 0; i < fields.length; i ++) {
			Put put = new Put(row.getBytes());
			String[] cols = fields[i].split(":");
			if (cols.length == 1) {
				// not extra col limit
				put.addColumn(cols[0].getBytes(), 
						"".getBytes(), 
						values[i].getBytes());
			} else {
				put.addColumn(cols[0].getBytes(), 
						cols[1].getBytes(), 
						values[i].getBytes());
			}
			table.put(put);
		}
		
		System.out.println("add success");
		
		table.close();
		close();
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
