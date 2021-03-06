import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class HDFSReadFile {

	public static void main(String[] args) {
		try{
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", 
					"hdfs://localhost:9000");
			conf.set("fs.hdfs.impl", 
					"org.apache.hadoop.hdfs" +
					".DistributedFileSystem");
			
			FileSystem fs = FileSystem.get(conf);
			
			Path file = new Path("test");
			FSDataInputStream in = fs.open(file);
			BufferedReader d = new BufferedReader(
					new InputStreamReader(in));
			
			String content = d.readLine();
			
			System.out.println(content);
			
			d.close();			
			fs.close();			
		}catch (Exception e){
			e.printStackTrace();
		}
	}

}
