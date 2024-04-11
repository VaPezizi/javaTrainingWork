package com.harjtyo;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;    

import java.util.Random;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class dataGenerator {
    
    public static void kirjoitaTiedostoon(JSONArray jArray){
        try{
            FileWriter file = new FileWriter("./javaharjtyo/src/main/java/com/harjtyo/data.json");
            file.write(jArray.toString());
            file.flush();
            file.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }

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
        //Json objekti dataa varten
        JSONArray data = new JSONArray();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
        
        //Datan luonnille for loop. Luo 100 JSONObjektia
        for(int i = 0;i<100;i++){
            //Lampotila datan luonto
            //System.out.println(i);

            Random randomi = new Random();
            double lampotila = 10 * Math.cos(i/15) + (randomi.nextDouble() * 2 - 1);
            //String lampotilaForm = String.format(String.valueOf(lampotila));
            //System.out.println(lampotilaForm);

            //Paineen datan generointi
            double paine = 10 * Math.sin(i/10) + (randomi.nextDouble() * 2 - 1);
            //String paineForm = String.format(String.valueOf(paine));
            //System.out.println(paineForm);

            //kosteus datan generointi
            double kosteus = Math.sin(i / 20) + (randomi.nextDouble() * 4 - 2);
            //String kosteusForm = String.format(String.valueOf(kosteus));
            //System.out.println(kosteusForm);

            //Luodaa JSONObjekti ja lisätään siihen mittaukset ja mittausten aika
            JSONObject jobj = new JSONObject();
            jobj.put("Lampotila", lampotila);
            jobj.put("Paine", paine);
            jobj.put("Kosteus", kosteus);
            LocalDateTime now = LocalDateTime.now();
            //Muokataan aikaa lisäämän sekuntin jokaisen mittauksen väliin
            jobj.put("Aika", dtf.format(now.plusSeconds(i)));

            //Lisätään JSONObjekti JSONArrayyn
            data.put(jobj);
        }
        
        return data;
    }
    
    
    public static void main(String[] args) {
        luoTiedosto();
        kirjoitaTiedostoon(luoData());
        

    }

}
