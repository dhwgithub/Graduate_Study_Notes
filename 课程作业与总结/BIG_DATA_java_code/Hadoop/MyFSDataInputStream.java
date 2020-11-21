import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;


public class MyFSDataInputStream extends FSDataInputStream{

	public MyFSDataInputStream(InputStream in) {
		super(in);
	}

	public static void main(String[] args) {
		try {
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://localhost:9000");
			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
					".DistributedFileSystem");
			FileSystem fs = FileSystem.get(conf);
			
			Path file = new Path("input/t1.txt");
			FSDataInputStream in = fs.open(file);
			
			System.out.println("read files ...");
			readLine(in);
			
			fs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String readLine(FSDataInputStream in) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String c = br.readLine();
			while (c != null) {
				System.out.println(c);
				c = br.readLine();
			}
			
			br.close();
			in.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

}
