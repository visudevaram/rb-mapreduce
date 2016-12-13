echo "Cleaning up HDFS"

hadoop fs -rm output_dir/*
hadoop fs -rmdir output_dir

echo "Run the map reduce program"

hadoop jar rb_internet.jar nl.rabobank.RbMapReduceInternet input_dir output_dir

echo "Transfer the output file to the home directory"

hadoop fs -get output_dir/part-00000 .

echo "Renaming the file to Internet"

mv part-00000 Internet.txt
