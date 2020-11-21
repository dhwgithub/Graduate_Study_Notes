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
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;


public class Test {

	public static Configuration configuration;
	public static Connection connection;
	public static Admin admin;
	
	public static void main(String[] args) throws IOException {
		
		
		createTable("t2",new String[]{"cf1", "cf2"});
		
		insertRow("t2", "rw1", "cf1", "q1", "val1");
		
		getData("t2", "rw1", "cf1", "q1");
		
//		deleteTable("t2");
	}

	// delete table
	private static void deleteTable(String tableName) throws IOException {
        init();
        
        TableName tn = TableName.valueOf(tableName);
        if (admin.tableExists(tn)) {
            admin.disableTable(tn);
            admin.deleteTable(tn);
        }
        
        close();
	}

	// get datas
	private static void getData(String tableName, 
			String rowKey, String colFamily, String col) throws IOException{
        init();
        
        Table table = connection.getTable(TableName.valueOf(tableName));
        
        Get get = new Get(rowKey.getBytes());
        get.addColumn(colFamily.getBytes(),col.getBytes());
        
        Result result = table.get(get);
        showCell(result);
        
        table.close();
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

	// put rows
	private static void insertRow(String tableName, 
			String rowKey, String colFamily, 
			String col,String val) throws IOException {
        init();
        
        Table table = connection.getTable(TableName.valueOf(tableName));
        
        Put put = new Put(rowKey.getBytes());
        put.addColumn(colFamily.getBytes(), col.getBytes(), val.getBytes());
        
        table.put(put);
        
        table.close();
        close();
	}

	// create table
	private static void createTable(String myTableName,String[] colFamily) 
			throws IOException {
		init();
		
		TableName tableName = TableName.valueOf(myTableName); 
        if(admin.tableExists(tableName)){
            System.out.println("talbe is exists!");
        }else {
            HTableDescriptor hTableDescriptor = new HTableDescriptor(tableName);
            for (String colstr : colFamily) {
            	HColumnDescriptor hColumnDescriptor = 
            			new HColumnDescriptor(colstr);
                hTableDescriptor.addFamily(hColumnDescriptor);
            }
            admin.createTable(hTableDescriptor);
        }
        
        close();
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

}
