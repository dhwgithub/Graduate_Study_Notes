import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Scanner;


public class HDFS_4 {

	public static void main(String[] args) {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://localhost:9000");
			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
					".DistributedFileSystem");
			FileSystem fs = FileSystem.get(conf);
			
			Path file = new Path("input/t1.txt");
			
			// main code
			FileStatus[] statue = fs.listStatus(file);
			for (FileStatus s : statue) {
				System.out.println(file + " is:");
				System.out.println(s.getPermission());
				System.out.println(s.getBlockSize());
				System.out.println(s.getAccessTime());
				System.out.println(s.getPath());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
