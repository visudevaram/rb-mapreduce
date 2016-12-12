echo "Cleaning up HDFS"

hadoop fs -rm output_dir/*
hadoop fs -rmdir output_dir
hadoop fs -rm input_dir/completeTweets.txt

echo "Transfer input file to HDFS"

hadoop fs -put Downloads/uniqueRabobankTweets.txt input_dir

echo "Run the map reduce program"

hadoop jar rb_complete.jar nl.rabobank.RbMapReduceComplete input_dir output_dir

echo "Transfer the output file to the home directory"

hadoop fs -get output_dir/part-00000 .

echo "Renaming the file to Rabobank_Complete"

mv part-00000 Rabobank_Complete.txt

echo "Cleaning up HDFS"

hadoop fs -rm output_dir/*
hadoop fs -rmdir output_dir
hadoop fs -rm input_dir/uniqueRabobankTweets.txt

echo "Transfer input file to HDFS"

hadoop fs -put Downloads/uniqueAbnAmroTweets.txt input_dir

echo "Run the map reduce program"

hadoop jar rb_complete.jar nl.rabobank.RbMapReduceComplete input_dir output_dir

echo "Transfer the output file to the home directory"

hadoop fs -get output_dir/part-00000 .

echo "Renaming the file to AbnAmro_Complete"

mv part-00000 AbnAmro_Complete.txt

echo "Cleaning up HDFS"

hadoop fs -rm output_dir/*
hadoop fs -rmdir output_dir
hadoop fs -rm input_dir/uniqueAbnAmroTweets.txt

echo "Transfer input file to HDFS"

hadoop fs -put Downloads/uniqueINGTweets.txt input_dir

echo "Run the map reduce program"

hadoop jar rb_complete.jar nl.rabobank.RbMapReduceComplete input_dir output_dir

echo "Transfer the output file to the home directory"

hadoop fs -get output_dir/part-00000 .

echo "Renaming the file to ING_Complete"

mv part-00000 ING_Complete.txt


