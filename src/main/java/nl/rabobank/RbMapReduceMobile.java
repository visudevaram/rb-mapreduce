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

public class RbMapReduceMobile {
    // Mapper class
    public static class E_EMapper extends MapReduceBase implements Mapper<LongWritable, /* Input key Type */
    Text, /* Input value Type */
    Text, /* Output key Type */
    Text> /* Output value Type */
    {
        private static final String SEPERATOR = "\t";

        // Map function
        public void map(LongWritable key, Text value, OutputCollector<Text, Text> output, Reporter reporter)
                throws IOException
        {
            if (value != null) {
                 String line = value.toString();
                 dataFilteration(output, line);
            }

        }

        private void dataFilteration(OutputCollector<Text, Text> output, String line) throws IOException {
            final ArrayList<String> messageContentKeysList = RbMapReduceMobile.getMobileMessageContentKeysList();
            for (String messageContentKey : messageContentKeysList) {
                final String[] parts = line.split(SEPERATOR);
                if(parts.length > 3){
                    if (parts[parts.length -1].toLowerCase().contains(messageContentKey.toLowerCase())) {
                         dataTransformation(output, line);
                        break;
                    }
            }
            }
        }

        private void dataTransformation(OutputCollector<Text, Text> output, String line) throws IOException {
            if(line.toLowerCase().contains("https://")){
                line = line.substring(0, line.indexOf("https://"));
            }
            final String[] parts = line.split(SEPERATOR);
            if(parts.length > 3){
                output.collect(new Text(parts[0]), new Text(parts[1] + SEPERATOR + parts[2] + SEPERATOR + parts[parts.length -1]));
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
        JobConf conf = new JobConf(RbMapReduceMobile.class);
        conf.setJobName("rb_map_reduce_mobile");
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

    private static ArrayList<String> getMobileMessageContentKeysList() {
        ArrayList<String> keyList = new ArrayList<String>();
        keyList.add(" app ");
        keyList.add("-app ");
        keyList.add("Mobiel");
        keyList.add("mobile banking");
        keyList.add("mobile app ");
        keyList.add("bankieren app");
        keyList.add("rabobankieren");
        keyList.add("rabo bankieren");
        keyList.add("bankieren-app");
        keyList.add("mobielbankieren");
        return keyList;
    }
}