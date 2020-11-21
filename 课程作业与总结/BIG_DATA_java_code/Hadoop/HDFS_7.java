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


public class HDFS_7 {

	public static void main(String[] args) {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://localhost:9000");
			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
					".DistributedFileSystem");
			FileSystem fs = FileSystem.get(conf);
			
			Path path = new Path("input/m7");
			
			// main code
			if (!fs.exists(path)) {
				fs.mkdirs(path);
				System.out.println("create success");
				return ;
			}
			else {
				System.out.println("Do you want delete this dir ? (y / n)");
				RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(path, true);
				
				if (new Scanner(System.in).next().equals("y")) {
					FileStatus[] it = fs.listStatus(path);
					if (it.length > 0) {
						System.out.println("sure to delete all ? (y / n)");
						if (new Scanner(System.in).next().equals("y")) {
							if (fs.delete(path, true)) {
								System.out.println("delete success");
								return ;
							}
						}
					}
					if (fs.delete(path, true)) {
						System.out.println("delete success");
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
