echo "Compiling java file"

javac -classpath Downloads/*.jar -d rb Downloads/RbMapReduce.java

echo "Creating jar file"

jar -cvf rb.jar -C rb/ .

echo "Cleaning up HDFS"

hadoop fs -rm output_dir/*
hadoop fs -rmdir output_dir
hadoop fs -rm input_dir/completeTweets.txt

echo "Transfer input file to HDFS"

hadoop fs -put Downloads/completeTweets.txt input_dir

echo "Run the map reduce program"

hadoop jar rb.jar nl.rabobank.RbMapReduce input_dir output_dir

echo "Transfer the output file to the home directory"

hadoop fs -get output_dir/part-00000 .

echo "Renaming the file to Cards"

mv part-00000 Cards.txt
