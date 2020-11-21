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


public class HDFS_8 {

	public static void main(String[] args) {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://localhost:9000");
			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
					".DistributedFileSystem");
			FileSystem fs = FileSystem.get(conf);
			
			Path des = new Path("input/test1.txt");
			Path temp = new Path("/home/dhw/myfile/t1_temp.txt");
			Path local = new Path("/home/dhw/myfile/t1.txt");
			
			// main code
			if (!fs.exists(des)) {
				System.out.println("file not exists");
				return ;
			}
			
			System.out.println("append file: 1.head 2.tail");
			switch (new Scanner(System.in).nextInt()) {
				case 1:
					// use mid file
					fs.moveToLocalFile(des, temp);
					FSDataOutputStream fos = fs.create(des);
					FileInputStream fis = new FileInputStream(local.toString());
					FileInputStream fis2 = new FileInputStream(temp.toString());
					
					// append words
					byte[] b = new byte[1024];
					int read = -1;
					while ((read = fis.read(b)) > 0) {
						fos.write(b, 0, read);
					}
					
					// transform
					while ((read = fis2.read(b)) > 0) {
						fos.write(b, 0, read);
					}
					
					fos.close();
					fis.close();
					fis2.close();
					System.out.println("ok");
					break;
	
				case 2:
					FileInputStream inputStream = new FileInputStream(local.toString());
					FSDataOutputStream outputStream = fs.append(des);
					
					byte[] by = new byte[1024];
					int reads = -1;
					while ((reads = inputStream.read(by)) > 0) {
						outputStream.write(by, 0, reads);
					}
					
					System.out.println("ok");
					outputStream.close();
					inputStream.close();
					break;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
