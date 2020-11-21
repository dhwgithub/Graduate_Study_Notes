import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.LocatedFileStatus;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.fs.RemoteIterator;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


public class HDFS_9 {

	public static void main(String[] args) {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://localhost:9000");
			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
					".DistributedFileSystem");
			FileSystem fs = FileSystem.get(conf);
			
			Path des = new Path("input/t1_temp.txt");
			
			// main code
			if (!fs.exists(des)) {
				System.out.println("file not exists");
				return ;
			}
			
			if (fs.delete(des, true)) {
				System.out.println("delete success");
			} 
			else {
				System.out.println("delete failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
