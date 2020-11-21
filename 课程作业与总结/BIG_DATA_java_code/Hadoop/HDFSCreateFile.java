import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class HDFSCreateFile {

	public static void main(String[] args) {
		try{
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://localhost:9000");
			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
					".DistributedFileSystem");
			
			FileSystem fs = FileSystem.get(conf);
			byte[] buff = "Create File By HDFS".getBytes();
			String fileName = "test";
			
			FSDataOutputStream os =fs.create(new Path(fileName));
			os.write(buff, 0, buff.length);
			
			System.out.println("Create:"+fileName);
			
			os.close();
			fs.close();
		} catch (Exception e){
			e.printStackTrace();
		} 
	}

}
