package nl.rabobank;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import java.util.Scanner;

public class Main {

    private static Properties props = new Properties();

    public static void main(String[] args) {
        try {
            InputStream in = Main.class.getClass().getResourceAsStream("/props/Keys.properties");
            props.load(in);
            filterDataWithkey(props.getProperty("keys"));
            in.close();
        } catch (IOException ioException) {
        }
    }

    private static void filterDataWithkey(final String key) {
        final URL url =  Main.class.getClass().getResource("/tweets.txt");
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
                if(f!=null){
                    f.flush();
                    f.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
