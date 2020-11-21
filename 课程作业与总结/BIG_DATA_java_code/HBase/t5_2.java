import java.io.IOException;
import java.sql.ResultSet;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;



public class t5_2 {

	public static Configuration configuration;
	public static Connection connection;
	public static Admin admin;

	public static void main(String[] args) throws IOException {
		String tableName = "Student";
		String row = "scofield";
		String column = "score:English";
		
		init();
		Table table = connection.getTable(TableName.valueOf(tableName));
		Get get = new Get(row.getBytes());
		String[] cols = column.split(":");
		if (cols.length == 1) {
			// no limit col
			get.addFamily(Bytes.toBytes(column));
		} else {
			get.addColumn(Bytes.toBytes(cols[0]), Bytes.toBytes(cols[1]));
		}
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
