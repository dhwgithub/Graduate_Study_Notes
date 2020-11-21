import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;


public class HDFS_2 {

	public static void main(String[] args) {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://localhost:9000");
			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
					".DistributedFileSystem");
			FileSystem fs = FileSystem.get(conf);
			
			Path srcPath = new Path("/home/dhw/myfile/t1.txt");
			Path hdfsPath = new Path("input/t1.txt");
			
			// main code
			if (fs.exists(hdfsPath)) {
				// download file
				if (new File("/home/dhw/myfile/t1.txt").exists()) {
					fs.copyToLocalFile(hdfsPath, new Path("/home/dhw/myfile/te.txt"));
					System.out.println("success 1");
				}
				else {
					fs.copyToLocalFile(hdfsPath, srcPath);
					System.out.println("success 2");
				}
			}
			else {
				System.out.println("file not exists");
			}
			
			System.out.println("finish");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
