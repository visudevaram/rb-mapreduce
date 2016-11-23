package nl.rabobank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    private static Properties props = new Properties();

    public static void main(String[] args) {
        try {
            InputStream in = Main.class.getClass().getResourceAsStream("/props/Keys.properties");
            props.load(in);
            // filterDataWithkey(props.getProperty("keys"));
             removeDuplicatesAndFormat();
            //cleanText();
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
       // String toBeFilteredName = "rabo";
        
        String bankName = "ING";
        String toBeFilteredName = "ING Nederland";
        
        // String bankName = "Abn Amro";
       //  String toBeFilteredName = "ABN AMRO";
       
        
        try {
            Scanner input = new Scanner(new File(url.getFile())).useDelimiter("[|\\n]");
            f = new FileWriter("C://temp//unique"+bankName+"Tweets.txt");
            while (input.hasNext()) {
                String currentLine = input.next();
                String[] parts = currentLine.split("\t");
               
                if (parts.length > 2 && parts[1].toLowerCase().contains(toBeFilteredName.toLowerCase())) {
                    continue;
                }
                try {
                    format.parse(parts[0]);
                    isDate = true;
                } catch (Exception e) {
                    isDate = false;
                }
                if (isDate && parts.length > 2) {
                    if (!timeStamp.contains(parts[0] + "\t" + parts[1])) {
                        f.write(bankName + "\t" + parts[0] + "\t" + parts[1] + "\t" + parts[parts.length - 1]);
                    } else {
                        System.out.println(currentLine);
                    }
                    timeStamp.add(parts[0] + "\t" + parts[1]);
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

    private static void cleanText() {
        String s = "Woningprijzen in Nederland blijven stijgen, verkopen naar hoogtepunt https://t.co/Yv7dtexhaC https://t.co/Z22TlFLBJ8";
        System.out.println(s);
        if (s.contains("https://")) {
            System.out.println(s.substring(0, s.indexOf("https://")));
        }
    }

}
