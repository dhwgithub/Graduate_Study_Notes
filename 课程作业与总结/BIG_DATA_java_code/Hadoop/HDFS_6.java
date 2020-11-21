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


public class HDFS_6 {

	public static void main(String[] args) {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://localhost:9000");
			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
					".DistributedFileSystem");
			FileSystem fs = FileSystem.get(conf);
			
			Path path = new Path("input/m6");
			Path file = new Path("input/m6/test.txt");
			
			// main code
			if (fs.exists(path)) {
				System.out.println("select : 1.create 2.delete");
				int i = new Scanner(System.in).nextInt();
				switch (i) {
					case 1:
						fs.create(file);
						System.out.println("create success");
						break;
					case 2:
						fs.delete(file, true);
						System.out.println("delete success");
						break;
				}
			} else {
				fs.mkdirs(path);
				System.out.println("create success");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
