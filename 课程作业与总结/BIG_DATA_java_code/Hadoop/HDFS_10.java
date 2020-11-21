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


public class HDFS_10 {

	public static void main(String[] args) {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://localhost:9000");
			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
					".DistributedFileSystem");
			FileSystem fs = FileSystem.get(conf);
			
			Path src = new Path("input/temp/mv_test1.txt");
			Path des = new Path("input/test1.txt");
			
			// main code
			if (!fs.exists(src)) {
				System.out.println("file not exists");
				return ;
			}
			
			if (fs.rename(src, des)) {
				System.out.println("mv success");
			} 
			else {
				System.out.println("mv failed");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
