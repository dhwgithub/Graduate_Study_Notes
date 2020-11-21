import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.management.relation.Relation;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.Merger;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.Mapper.Context;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;
import org.apache.hadoop.util.MergeSort;


public class t6_3 {

	public static void main(String[] args) throws Exception {
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", "hdfs://localhost:9000");
		conf.set("fs.hdfs.impl", "org.apache.hadoop.hdfs" +
				".DistributedFileSystem");
		
		String[] path = new String[] {"input3", "output3"};
		
		Job job = Job.getInstance(conf, "Single table join");
		job.setJarByClass(t6_3.class);  // name
		job.setMapperClass(MyMap.class);  // Map class
		job.setReducerClass(MyReduce.class);  // add Reduce class
		job.setOutputKeyClass(Text.class);  // set output type
		job.setOutputValueClass(Text.class);  // set input type
		
		FileInputFormat.addInputPath(job, new Path(path[0]));  // input file
		FileOutputFormat.setOutputPath(job, new Path(path[1]));  // output file
		
		System.exit(job.waitForCompletion(true) ? 0 : 1);
	}
	
	public static int time = 0;
	
	public static class MyMap extends Mapper<Object, Text, Text, Text> {
        @Override
        public void map(Object key, Text value, Context context) 
        		throws IOException, InterruptedException {

            String line = value.toString();
            String[] childAndParent = line.split(" ");
            List<String> list = new ArrayList<String>(2);

            for (String childOrParent : childAndParent) {
                if (! "".equals(childOrParent)) {
                    list.add(childOrParent);
                }

            }

            if (!"child".equals(list.get(0))) {
                String childName = list.get(0);
                String parentName = list.get(1);
                
                String relationType = "1";
                context.write(new Text(parentName), new Text(relationType + "+"
                        + childName + "+" + parentName));

                relationType = "2";
                context.write(new Text(childName), new Text(relationType + "+"
                        + childName + "+" + parentName));
            }
        }
    }
	
	public static class MyReduce extends Reducer<Text, Text, Text, Text> {

        @Override
        public void reduce(Text key, Iterable<Text> values, Context context) 
        		throws IOException, InterruptedException {

            if (time == 0) {
                context.write(new Text("grand_child"), new Text("grand_parent"));
                time ++;

            }

            List<String> grandChild = new ArrayList<String>();
            List<String> grandParent = new ArrayList<String>();

            for (Text text : values) {
                String s = text.toString();
                String[] relation = s.split("\\+");
                String relationType = relation[0];
                String childName = relation[1];
                String parentName = relation[2];

                if ("1".equals(relationType)) {
                    grandChild.add(childName);
                } else {
                    grandParent.add(parentName);
                }

            }

            int grandParentNum = grandParent.size();
            int grandChildNum = grandChild.size();

            if (grandParentNum != 0 && grandChildNum != 0) {
                for (int m = 0; m < grandChildNum; m++) {
                    for (int n = 0; n < grandParentNum; n++) {
                        context.write(new Text(grandChild.get(m)), 
                        		new Text(grandParent.get(n)));
                    }
                }
            }
        }
    }
}
