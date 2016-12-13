echo "Creating jar files"

javac -classpath Downloads/*.jar -d rb_complete Downloads/RbMapReduceComplete.java
jar -cvf rb_complete.jar -C rb_complete/ .

javac -classpath Downloads/*.jar -d rb_cards Downloads/RbMapReduceCards.java
jar -cvf rb_cards.jar -C rb_cards/ .

javac -classpath Downloads/*.jar -d rb_internet Downloads/RbMapReduceInternet.java
jar -cvf rb_internet.jar -C rb_internet/ .

javac -classpath Downloads/*.jar -d rb_mobile Downloads/RbMapReduceMobile.java
jar -cvf rb_mobile.jar -C rb_mobile/ .

javac -classpath Downloads/*.jar -d rb_savings Downloads/RbMapReduceSavings.java
jar -cvf rb_savings.jar -C rb_savings/ .
