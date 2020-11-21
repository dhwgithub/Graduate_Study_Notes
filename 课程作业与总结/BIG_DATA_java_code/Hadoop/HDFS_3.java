import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


public class HDFS_3 {

	public static void main(String[] args) {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://localhost:9000");
			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
					".DistributedFileSystem");
			FileSystem fs = FileSystem.get(conf);
			
			Path hdfsPath = new Path("input");
			
			// main code
			FSDataInputStream fis = fs.open(hdfsPath);
			BufferedReader br = new BufferedReader(new InputStreamReader(fis));
			String string;
			while ((string = br.readLine()) != null) {
				System.out.println(string);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
