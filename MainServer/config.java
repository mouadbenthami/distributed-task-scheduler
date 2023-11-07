
package main;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class config 
{
  public static String[] getConfig(String file) throws IOException {
    ArrayList<String> values = new ArrayList<>();

    File f = new File(file);   
    if(!f.exists()) {
        System.out.println("Error config file does not exist");
        throw new FileNotFoundException();
    }
      
    FileReader fr = new FileReader(f);  
    BufferedReader br = new BufferedReader(fr);  
    int c = 0;        

    String current = "";
    while ((c = br.read()) != -1) {         
        char character = (char) c; 
        if (character == ' ') {
            values.add(current);
            current = "";
        } else {
            current += character;
        }  
    }

    // Add the final value after the last space (if any)
    if (!current.isEmpty()) {
        values.add(current);
    }

    // Convert the ArrayList to an array
    String[] result = new String[values.size()];
    values.toArray(result);
 
    return result;
}
}
