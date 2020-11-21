import java.io.IOException;
import java.util.Scanner;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.ZooKeeperConnectionException;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;


public class t4 {

	public static Configuration configuration;
	public static Connection connection;
	public static Admin admin;
	
	public static void main(String[] args) throws Exception {
		clearTable("t2");
	}

	private static void clearTable(String tableName) throws Exception {
		init();
		
		// table copy
		HBaseAdmin admin2 = new HBaseAdmin(configuration);
		HTableDescriptor tableDescriptor = 
				admin2.getTableDescriptor(Bytes.toBytes(tableName));
		
		TableName tableName2 = TableName.valueOf(tableName);
		
		// delete ori table
		admin.disableTable(tableName2);
		admin.deleteTable(tableName2);
		
		// create ori table
		admin.createTable(tableDescriptor);
		
		close();
	}

	private static void deleteRow(String tableName, String rowKey,
			String colFamily, String col) throws IOException {
		init();
		
		Table table = connection.getTable(TableName.valueOf(tableName));
		Delete delete = new Delete(rowKey.getBytes());
		
		System.out.println("1.delete colFamily info or 2.delete col info");
		switch (new Scanner(System.in).next()) {
			case "1":
				// delete colFamily
				delete.addFamily(colFamily.getBytes());
				table.delete(delete);
				break;
			case "2":
				// delete col
				delete.addColumn(colFamily.getBytes(), col.getBytes());
				table.delete(delete);
				break;
			default:
				System.out.println("error");
		}
		
		System.out.println("ok");
		table.close();
		close();
	}

	// put rows
	private static void insertRow(String tableName, 
			String rowKey, String colFamily, 
			String col, String val) throws IOException {
        init();
        
        Table table = connection.getTable(TableName.valueOf(tableName));
        
        Put put = new Put(rowKey.getBytes());
        put.addColumn(colFamily.getBytes(), col.getBytes(), val.getBytes());
        
        table.put(put);
        
        table.close();
        close();
	}
	
	private static void getTableByName(String tableName) throws IOException {
		init();
		
		Table table = connection.getTable(TableName.valueOf(tableName));
		Scan scan = new Scan();
		ResultScanner scanner = table.getScanner(scan);
		for (Result result : scanner) {
			showCell(result);
		}
		
		close();
	}
	
	// show cells
	private static void showCell(Result result){
        Cell[] cells = result.rawCells();
        for (Cell cell : cells){
            System.out.println("RowName:" + 
            					new String(CellUtil.cloneRow(cell)) + " ");
            System.out.println("Timetamp:" + cell.getTimestamp() + " ");
            System.out.println("column Family:" + 
            					new String(CellUtil.cloneFamily(cell)) + " ");
            System.out.println("row Name:" + 
            					new String(CellUtil.cloneQualifier(cell)) + " ");
            System.out.println("value:" + 
            					new String(CellUtil.cloneValue(cell)) + " ");
        }
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
