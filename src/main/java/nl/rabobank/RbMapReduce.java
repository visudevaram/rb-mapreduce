package nl.rabobank;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.FileInputFormat;
import org.apache.hadoop.mapred.FileOutputFormat;
import org.apache.hadoop.mapred.JobClient;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapred.MapReduceBase;
import org.apache.hadoop.mapred.Mapper;
import org.apache.hadoop.mapred.OutputCollector;
import org.apache.hadoop.mapred.Reducer;
import org.apache.hadoop.mapred.Reporter;
import org.apache.hadoop.mapred.TextInputFormat;
import org.apache.hadoop.mapred.TextOutputFormat;

public class RbMapReduce {
    // Mapper class
    public static class E_EMapper extends MapReduceBase implements Mapper<LongWritable, /* Input key Type */
    Text, /* Input value Type */
    Text, /* Output key Type */
    Text> /* Output value Type */
    {
        // Map function
        public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)
                throws IOException
        {
            if (value != null) {
                final String line = value.toString();
                final ArrayList<String> messageContentKeysList = RbMapReduce.getMessageContentKeysList();
                for (String messageContentKey : messageContentKeysList) {
                    if (line.toLowerCase().contains(messageContentKey.toLowerCase())) {
                        output.collect(new Text(messageContentKey), new Text(line));
                        break;
                    }
                }
            }

        }
    }

    // Reducer class
    public static class E_EReduce extends MapReduceBase implements Reducer<Text, Text, Text, Text> {

        // Reduce function
        public void reduce(Text key, Iterator<Text> values, OutputCollector<Text, Text> output, Reporter reporter)
                throws IOException
        {
            final String inputSearchKey = key.toString();
            while (values.hasNext()) {
                final Text messageText = values.next();
                final String messageValue = messageText.toString();
                output.collect(new Text(inputSearchKey), new Text(messageValue));
            }
        }
    }

    // Main function
    public static void main(String args[]) throws Exception {
        JobConf conf = new JobConf(RbMapReduce.class);
        conf.setJobName("rb_map_reduce");
        conf.setOutputKeyClass(Text.class);
        conf.setOutputValueClass(Text.class);
        conf.setMapperClass(E_EMapper.class);
        conf.setCombinerClass(E_EReduce.class);
        conf.setReducerClass(E_EReduce.class);
        conf.setInputFormat(TextInputFormat.class);
        conf.setOutputFormat(TextOutputFormat.class);

        FileInputFormat.setInputPaths(conf, new Path(args[0]));
        FileOutputFormat.setOutputPath(conf, new Path(args[1]));

        JobClient.runJob(conf);
    }

    private static ArrayList<String> getMessageContentKeysList() {
        ArrayList<String> keyList = new ArrayList<String>();
        keyList.add("card ");
        keyList.add("bankpas  ");
        keyList.add(" kaarten ");
        return keyList;
    }

}