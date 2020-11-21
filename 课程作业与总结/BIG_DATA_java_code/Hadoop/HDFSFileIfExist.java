
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

public class HDFSFileIfExist {
	public static void main(String[] args) {
		try {
			String fileName = "eclipse_new_file";
			
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", "hdfs://localhost:9000");
			conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
					".DistributedFileSystem");
			
			FileSystem fs = FileSystem.get(conf);
			if (fs.exists(new Path(fileName))) {
				System.out.println("the file is exists");
			} else {
				System.out.println("the file not exists");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
