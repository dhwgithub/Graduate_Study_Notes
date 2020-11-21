import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FsUrlStreamHandlerFactory;
import org.apache.hadoop.fs.Path;



public class PrintFiles {

	public static void main(String[] args) {
		try {
			
			
			Path file = new Path("/user/dhw/input/t1.txt");
			
			URL.setURLStreamHandlerFactory(new FsUrlStreamHandlerFactory());
			InputStream in = new URL("hdfs", "localhost", 9000, file.toString()).openStream();
			BufferedReader buff = new BufferedReader(new InputStreamReader(in));
			
			String line = null;
			while ((line = buff.readLine()) != null) {
				System.out.println(line);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
	}

}
