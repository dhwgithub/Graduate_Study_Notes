import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Merger;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;


public class t6_1 {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://localhost:9000");
		conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
				".DistributedFileSystem");
		
		String[] path = new String[] {"input", "output"};
		
		Job job = Job.getInstance(conf, "Merge and duplicate removal");
		job.setJarByClass(Merger.class);  // name
		job.setMapperClass(MyMap.class);  // Map class
		job.setReducerClass(MyReduce.class);  // add Reduce class
		job.setOutputKeyClass(Text.class);  // set output type
		job.setOutputValueClass(Text.class);  // set input type
		
		FileInputFormat.addInputPath(job, new Path(path[0]));  // input file
		FileOutputFormat.setOutputPath(job, new Path(path[1]));  // output file
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	
	public static class MyMap extends Mapper<Object, Text, Text, Text> {
		private static Text text = new Text();
		public void map(Object key, Text value, Context context) 
				throws IOException, InterruptedException {
			this.text = value;
			context.write(text, new Text(""));
		}
	}
	
	public static class MyReduce extends Reducer<Text, Text, Text, Text>{
		public void reduce(Text key, Iterable<Text> values, Context context) 
				throws IOException, InterruptedException {
			context.write(key, new Text(""));
		}
	}

}
