package nl.rabobank;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    private static Properties props = new Properties();
    
    private static final String SEPERATOR = "\t";

    public static void main(String[] args) {
        try {
            InputStream in = Main.class.getClass().getResourceAsStream("/props/Keys.properties");
            props.load(in);
            // filterDataWithkey(props.getProperty("keys"));
             //removeDuplicatesAndFormat();
            //cleanText();
            //mapReduceLogic();
             sortFileBasedOnDate();
            in.close();
        } catch (IOException ioException) {
        }
    }

    private static void filterDataWithkey(final String key) {
        final URL url = Main.class.getClass().getResource("/tweets.txt");
        FileWriter f = null;
        String[] keys = key.split(",");
        try {
            Scanner input = new Scanner(new File(url.getFile())).useDelimiter("[|\\n]");
            f = new FileWriter("C://temp//filteredTweets.txt");
            while (input.hasNext()) {
                String currentLine = input.next();
                for (String k : keys) {
                    if (currentLine.contains(k)) {
                        f.write(currentLine);
                    }
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (f != null) {
                    f.flush();
                    f.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void removeDuplicatesAndFormat() {
        final URL url = Main.class.getClass().getResource("/tweets.txt");
        FileWriter f = null;
        String feed;
        List<String> timeStamp = new ArrayList<String>();
        final DateFormat format = new SimpleDateFormat("EEE");
        boolean isDate = true;
        
        //String bankName = "Rabobank";
        //String toBeFilteredName = "rabo";
        
        String bankName = "ING";
        String toBeFilteredName = "ING Nederland";
        
         //String bankName = "Abn Amro";
        // String toBeFilteredName = "ABN AMRO";
       
        
        try {
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                               new FileInputStream(new File(url.getFile())), "UTF8"));

                 String str;
                 f = new FileWriter("C://temp//unique"+bankName+"Tweets.txt");
                 while ((str = in.readLine()) != null) {
                   //  System.out.println(str);
              //   }
                 
                 
          //  Scanner input = new Scanner(new File(url.getFile())).useDelimiter("[|\\n]");
           
          //  while (input.hasNext()) {
                String currentLine = str;
                System.out.println(currentLine);
               
                String[] parts = currentLine.split("\t");
               
                if (parts.length > 2 && parts[1].toLowerCase().contains(toBeFilteredName.toLowerCase())) {
                    continue; // if the tweet is by bank employee, then skip it.
                }
                try {
                    format.parse(parts[0]);
                    isDate = true;
                } catch (Exception e) {
                    isDate = false;
                }
                if (isDate && parts.length > 2) {
                    
                   // if (!parts[parts.length - 1].toLowerCase().contains(bankName.toLowerCase())) {
                     //   continue; // if the tweet does not have bank name, then skip it.
                   // }
                    
                    
                    if (!timeStamp.contains(parts[0] + "\t" + parts[1])) {
                        f.write(bankName + "\t" + parts[0] + "\t" + parts[1] + "\t" + parts[parts.length - 1]);
                       f.write(System.lineSeparator());
                    } else {
                        System.out.println(currentLine);
                    }
                    timeStamp.add(parts[0] + "\t" + parts[1]);
                }
            }
                 in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (f != null) {
                    f.flush();
                    f.close();
                    
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private static void cleanText() {
        String s = "Woningprijzen in Nederland blijven stijgen, verkopen naar hoogtepunt https://t.co/Yv7dtexhaC https://t.co/Z22TlFLBJ8";
        System.out.println(s);
        if (s.contains("https://")) {
            System.out.println(s.substring(0, s.indexOf("https://")));
        }
    }
    
    private static void sortFileBasedOnDate(){
        
        final URL url = Main.class.getClass().getResource("/tweets.txt");
        FileWriter f = null;
        final DateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss Z yyyy", Locale.ENGLISH);

        try {
            
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                               new FileInputStream(new File(url.getFile())), "UTF8"));

                 String str;
                 List<Feed> feeds = new ArrayList<Feed>();
                 f = new FileWriter("C://temp//Files_Input_For_MapReduce//27Nov//Sorted//uniqueINGTweets.txt");
                 while ((str = in.readLine()) != null) {
                     
                     
              //   }
          //  Scanner input = new Scanner(new File(url.getFile())).useDelimiter("[|\\n]");
          
           

           // while (input.hasNext()) {
                String currentLine = str;
                System.out.println(currentLine);
                String[] parts = currentLine.split("\t");

                try {
                    Date feedDate = format.parse(parts[1]);
                    feeds.add(new Feed(parts[0], feedDate, parts[2], parts[3]));
                } catch (Exception e) {
                    e.printStackTrace();
                    System.out.println("Parsing error for the feed date.");
                }

            }
            Collections.sort(feeds, new DateComparator());
            for(Feed feed : feeds) {
                f.write(feed.getBankName() + "\t" + feed.getFeetDate() + "\t" + feed.getFeedBy() + "\t" +  "\t" +  feed.getFeed());
                f.write(System.lineSeparator());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (f != null) {
                    f.flush();
                    f.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }
    
    private static void mapReduceLogic(){
        final URL url = Main.class.getClass().getResource("/uniqueRabobankTweets.txt");
        FileWriter f = null;
        try {
            Scanner input = new Scanner(new File(url.getFile())).useDelimiter("[|\\r]");
            f = new FileWriter("C://temp//mapReduce.txt");
            while (input.hasNext()) {
                String line = input.next();
                if(line.toLowerCase().contains("https://")){
                    line = line.substring(0, line.indexOf("https://"));
                }
                final String[] parts = line.split(SEPERATOR);
               // System.out.println(parts[0] + SEPERATOR + parts[1] + SEPERATOR + parts[2] + SEPERATOR + parts[3]);
                if(parts.length > 3){
                    f.write(parts[0] + SEPERATOR + parts[1] + SEPERATOR + parts[2] + SEPERATOR + parts[3]);
                    f.write(System.lineSeparator());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (f != null) {
                    f.flush();
                    f.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
