import java.io.IOException;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Merger;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.MergeSort;


public class t6_2 {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://localhost:9000");
		conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
				".DistributedFileSystem");
		
		String[] path = new String[] {"input2", "output2"};
		String[] other = new GenericOptionsParser(conf, path).getRemainingArgs(); 
		
		Job job = Job.getInstance(conf, "mergesort");
		job.setJarByClass(MergeSort.class);  // name
		job.setMapperClass(MyMap.class);  // Map class
		job.setReducerClass(MyReduce.class);  // add Reduce class
		job.setOutputKeyClass(IntWritable.class);  // set output type
		job.setOutputValueClass(IntWritable.class);  // set input type
		
		FileInputFormat.addInputPath(job, new Path(other[0]));  // input file
		FileOutputFormat.setOutputPath(job, new Path(other[1]));  // output file
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	
	public static class MyMap extends Mapper<Object, Text, IntWritable, IntWritable> {
		private static IntWritable data = new IntWritable();
		
		public void map(Object key, Text value, Context context) 
				throws IOException, InterruptedException {
			String line = value.toString();
			this.data.set(Integer.parseInt(line));
			context.write(data, new IntWritable(1));
		}
	}
	
	public static class MyReduce extends Reducer<IntWritable, IntWritable, 
												IntWritable, IntWritable>{
		private static IntWritable linenum = new IntWritable(1);
		
		public void reduce(IntWritable key, Iterable<IntWritable> values, Context context) 
				throws IOException, InterruptedException {
			for (IntWritable num : values) {
				context.write(linenum, key);
				linenum = new IntWritable(linenum.get() + 1);
			}
		}
	}

}
