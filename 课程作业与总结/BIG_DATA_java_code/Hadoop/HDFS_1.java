import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.FileInputStream;
import java.util.Scanner;


public class HDFS_1 {

	public static void main(String[] args) {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://localhost:9000");
			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
					".DistributedFileSystem");
			FileSystem fs = FileSystem.get(conf);
			
			Path srcPath = new Path("/home/dhw/myfile/test1.txt");
			Path hdfsPath = new Path("input/t1.txt");
			
			// main code
			if (fs.exists(hdfsPath)) {
				System.out.println("Do you want to overwrite the existed ? (y / n)");
				if (new Scanner(System.in).equals("y")) { // overwrite
					fs.copyFromLocalFile(false, true, srcPath, hdfsPath);
					System.out.println("overwrite success");
				} 
				else { // add
					FileInputStream fis = new FileInputStream(srcPath.toString());
					FSDataOutputStream fos = fs.append(hdfsPath);
					
					byte[] b = new byte[1024];
					int read = -1;
					while ((read = fis.read(b)) > 0) {
						fos.write(b, 0, read);
					}
					
					fis.close();
					fos.close();
					System.out.println("append success");
				}
			}
			else {
				// add file
				fs.copyFromLocalFile(srcPath, hdfsPath);
				System.out.println("create success");
			}
			
			System.out.println("finish");
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
