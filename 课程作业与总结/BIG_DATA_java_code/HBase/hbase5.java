import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Delete;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;


public class hbase5 {

	public static Configuration configuration;
	public static Connection connection;
	public static Admin admin;
	
	public static void main(String[] args) throws Exception {
		String tableName = "testTable";
		deleteRow(tableName, "S_Name");
	}
	
	private static void deleteRow(String tableName, String row) 
			throws Exception {
		init();
		
		Table table = connection.getTable(TableName.valueOf(tableName));
		Delete delete = new Delete(row.getBytes());
		table.delete(delete);
		
		System.out.println("delete success");
		
		table.close();
		close();
	}
	
	private static void modifyData(String tableName, String row,
			String colum, String value) throws Exception {
		init();
		
		Table table = connection.getTable(TableName.valueOf(tableName));
		Put put = new Put(row.getBytes());
		String[] cols = colum.split(":");
		if (cols.length == 1) {
			// not col limit
			put.addColumn(colum.getBytes(), "".getBytes(), value.getBytes());
		}
		else {
			put.addColumn(cols[0].getBytes(), cols[1].getBytes(), value.getBytes());
		}
		table.put(put);
		System.out.println("modify success");
		
		table.close();
		close();
	}
	
	private static void scanColum(String tableName, String colum) 
			throws Exception {
		init();
		
		Table table = connection.getTable(TableName.valueOf(tableName));
		Scan scan = new Scan();
		String[] cols = colum.split(":");
		if (cols.length == 1) {
			// not col limit
			scan.addFamily(Bytes.toBytes(colum));
		}
		else {
			scan.addColumn(Bytes.toBytes(cols[0]), Bytes.toBytes(cols[1]));
		}
		
		ResultScanner scanner = table.getScanner(scan);
		Result result = scanner.next();
		while (result != null) {
			showCell(result);
			result = scanner.next();
		}
		
		table.close();
		close();
	}
	
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
