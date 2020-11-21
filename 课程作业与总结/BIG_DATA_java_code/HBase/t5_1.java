import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Table;



public class t5_1 {

	public static Configuration configuration;
	public static Connection connection;
	public static Admin admin;

	public static void main(String[] args) throws IOException {
		String tableName = "Student";
		String[] fields = {"score:English", "score:Math", "score:Computer"};
		String[] values = {"45", "89", "100"};
		String row = "scofield";
		
		init();
		Table table = connection.getTable(TableName.valueOf(tableName));
		for (int i = 0; i < fields.length; i ++) {
			Put put = new Put(row.getBytes());
			String[] cols = fields[i].split(":");
			if (cols.length == 1) {
				// no limit col
				put.addColumn(cols[0].getBytes(), "".getBytes(), values[i].getBytes());
			} else {
				put.addColumn(cols[0].getBytes(), cols[1].getBytes(), values[i].getBytes());
			}
			table.put(put);
		}
		System.out.println("add success");
		
		table.close();
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
