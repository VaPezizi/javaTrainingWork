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

public abstract class dataProcessor {
    /*
     * Function to read JSON data from a specified path. Returns the data as an JSONArray
     * !!REQUIRES THE FILE TO CONTAIN CORRECTLY FORMATTED JSON DATA!!
     * On Ba
     */
    public static JSONArray lueData(String filePath){
        try{
            File file = new File(filePath);
            Scanner lukija = new Scanner(file);
            String stringi = lukija.nextLine();
            JSONArray jArray = new JSONArray(stringi);
            lukija.close();
            //This try catch for checking if the file is empty
            try{
                jArray.get(0);
            }
            catch(Exception e){  
                return errorData();
            }
            return jArray;
        }catch(FileNotFoundException e){
            luoTiedosto();
            e.printStackTrace();
            return errorData();
        }catch(JSONException e){
            e.printStackTrace();
            return errorData();
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
            //Lämpötila datan generointi
            Random randomi = new Random();
            double lampotila = 10 * Math.cos(i/15) + (randomi.nextDouble() * 2 - 1);
            //String lampotilaForm = String.format(String.valueOf(lampotila));
            //System.out.println(lampotilaForm);

            //Paine datan generointi
            double paine = 10 * Math.sin(i/10) + (randomi.nextDouble() * 2);
            //String paineForm = String.format(String.valueOf(paine));
            //System.out.println(paineForm);

            //kosteus datan generointi
            double kosteus = Math.sin(i / 20) + (randomi.nextDouble() * 4) + 1;


            double sahko = (5 * Math.sin(2 * Math.PI * i / 10) + (randomi.nextInt(100))) * 5 + 50;
            //Creating the JSONObject and adding the data to it.
            JSONObject jobj = new JSONObject();

            jobj.put("Lampotila", lampotila);
            jobj.put("Paine", paine);
            jobj.put("Kosteus", kosteus);
            LocalDateTime now = LocalDateTime.now();
            //Modifying the time to make the data a bit more realistic
            jobj.put("Aika", dtf.format(now.plusMinutes(i)));
            jobj.put("Sahko", sahko);

            //Lisätään JSONObjekti JSONArrayyn
            data.put(jobj);
        }
        
        return data;
    }
    //Luo tyhjän datan, joka palautetaan ohjelmalle jos oikeaa dataa ei ole, tai se on esimerkiksi virheellistä
    public static JSONArray errorData(){
        JSONArray virheArr = new JSONArray();
        JSONObject virheObj = new JSONObject();
        virheObj.put("Aika", "0");
        virheObj.put("Kosteus", "0");
        virheObj.put("Lampotila", "0");
        virheObj.put("Paine", "0");
        virheArr.put(virheObj);
        return virheArr;
    }
    public static void main(String[] args) {
        //luoTiedosto();
        kirjoitaTiedostoon(luoData());
        //File myObj = new File("./javaharjtyo/src/main/java/com/harjtyo/data.json");
        //System.out.println(lueData(myObj).getClass());

        

    }
    
}
