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


public class HDFS_5 {

	public static void main(String[] args) {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://localhost:9000");
			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
					".DistributedFileSystem");
			FileSystem fs = FileSystem.get(conf);
			
			Path path = new Path("input/t1.txt");
			
			// main code
			RemoteIterator<LocatedFileStatus> iterator = fs.listFiles(path, true);
			while (iterator.hasNext()) {
				FileStatus status = iterator.next();
				System.out.println(status.getPath());
				System.out.println(status.getPermission());
				System.out.println(status.getLen());
				System.out.println(status.getModificationTime());
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
