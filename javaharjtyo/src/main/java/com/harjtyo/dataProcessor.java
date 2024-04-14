package com.harjtyo;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    


import java.util.Scanner;
import java.util.Random;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;

/*
 * Static class for reading, writing and generating data
 */

final public class dataProcessor {
    /*
     * Function to read JSON data from a specified path. Returns the data as an JSONArray
     * !!REQUIRES THE FILE TO CONTAIN CORRECTLY FORMATTED JSON DATA!!
     */
    public static JSONArray lueData(String filePath){
        try{
            File file = new File(filePath);
            Scanner lukija = new Scanner(file);
            String stringi = lukija.nextLine();
            JSONArray jArray = new JSONArray(stringi);
            lukija.close();
            return jArray;
        }catch(FileNotFoundException e){
            e.printStackTrace();
            return new JSONArray();
        }catch(JSONException e){
            e.printStackTrace();
            return new JSONArray();
        }
    }
    
    public static void kirjoitaTiedostoon(JSONArray jArray){
        /*
        * Function to write a JSONArray to the data.json file
        */
        try{
            FileWriter file = new FileWriter("./javaharjtyo/src/main/java/com/harjtyo/data.json");
            file.write(jArray.toString());
            file.flush();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    /*
     * Function to create the data.json file, if it doesn't exist already.
     */
    public static void luoTiedosto(){
        try {
            File data = new File("./javaharjtyo/src/main/java/com/harjtyo/data.json");
            if (data.createNewFile()) {
                System.out.println("File created: " + data.getName());
            }else {
                System.out.println("File already exists.");
            }
        }catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        
    }
    public static JSONArray luoData(){
        //JSON object for data
        JSONArray data = new JSONArray();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        
        //Loop for creating a 100 JSONObjects
        for(int i = 0;i<1000;i++){
            
            //System.out.println(i);
            //Temperature data creation
            Random randomi = new Random();
            double lampotila = 10 * Math.cos(i/15) + (randomi.nextDouble() * 2 - 1);
            //String lampotilaForm = String.format(String.valueOf(lampotila));
            //System.out.println(lampotilaForm);

            //Pressure data generation
            double paine = 10 * Math.sin(i/10) + (randomi.nextDouble() * 2);
            //String paineForm = String.format(String.valueOf(paine));
            //System.out.println(paineForm);

            //kosteus datan generointi
            double kosteus = Math.sin(i / 20) + (randomi.nextDouble() * 4) + 1;
            //String kosteusForm = String.format(String.valueOf(kosteus));
            //System.out.println(kosteusForm);

            //Creating the JSONObject and adding the data to it.
            JSONObject jobj = new JSONObject();
            jobj.put("Lampotila", lampotila);
            jobj.put("Paine", paine);
            jobj.put("Kosteus", kosteus);
            LocalDateTime now = LocalDateTime.now();
            //Modifying the time to make the data a bit more realistic
            jobj.put("Aika", dtf.format(now.plusMinutes(i)));

            //Lisätään JSONObjekti JSONArrayyn
            data.put(jobj);
        }
        
        return data;
    }
    
    
    /*public static void main(String[] args) {
        //luoTiedosto();
        //kirjoitaTiedostoon(luoData());
        //File myObj = new File("./javaharjtyo/src/main/java/com/harjtyo/data.json");
        //System.out.println(lueData(myObj).getClass());
        kirjoitaTiedostoon(luoData());
        

    }*/
    
}
