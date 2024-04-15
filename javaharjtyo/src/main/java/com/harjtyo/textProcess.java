package com.harjtyo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.text.DecimalFormat;

import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;

public class textProcess {
    private JSONArray jArr;
    private ArrayList<JSONObject> JArrList;

    public textProcess(JSONArray jArr){
        this.jArr = jArr;
        this.JArrList = JsonArrayToList();
    }
    public Text jArrToText(){
        //Converts JSONArray to Text for the GUI
        Text teksti = new Text();
        try{
            for(int i = 0; i < this.jArr.length();i++){      
                //jArray.get for some reason returns an Object and not an JSONObject, so casting i to JSONObj here
                JSONObject jObj = ((JSONObject) jArr.get(i));
                DecimalFormat formatter = new DecimalFormat("#0.0000000");
                teksti.setText(teksti.getText() + jObj.getString("Aika") + "\t" + formatter.format(jObj.getDouble("Kosteus")) + "\t" + formatter.format(jObj.getDouble("Lampotila")) + "\t" + formatter.format(jObj.getDouble("Paine")) + "\t" +  "\n");
            }
        }catch (Exception e){
            return teksti;
        }
        return teksti;
    }

    public Text searchData(String aika, String aika2){
        /*for (JSONObject jsonObject : JArrList) {
            if(jsonObject.getString("Aika").equals(aika)){
                NumberFormat formatter = new DecimalFormat("#0.00");
                String str = "Aika : " + jsonObject.getString("Aika") + " Kosteus: " + String.valueOf(formatter.format(jsonObject.getDouble("Kosteus"))) + " Lampotila: " + String.valueOf(formatter.format(jsonObject.getDouble("Lampotila"))) + " Paine:  " + String.valueOf(formatter.format(jsonObject.getDouble("Paine")));
                return str;
            }
        }*/
        Text teksti = new Text();
        Long aikaLong = Long.valueOf(aika.replaceAll("[^0-9]", ""));
        Long aika2Long = Long.valueOf(aika2.replaceAll("[^0-9]", "")) + 1;
        DecimalFormat formatter = new DecimalFormat("#0.0000000");
        for(JSONObject jsonObject:JArrList){
            Long jAika = Long.valueOf(jsonObject.getString("Aika").replaceAll("[^0-9]", ""));
            if(aikaLong <= jAika && (aika2Long - jAika > 0)){
                teksti.setText(teksti.getText() + jsonObject.getString("Aika") + "\t" + formatter.format(jsonObject.getDouble("Kosteus")) + "\t" + formatter.format(jsonObject.getDouble("Lampotila")) + "\t" + formatter.format(jsonObject.getDouble("Paine")) + "\t" +  "\n");
            }
        }
        return teksti;
    }

    public void addData(String kosteus, String lampo, String paine){
        JSONObject data = new JSONObject();
        LocalDateTime aika = LocalDateTime.now();
        data.put("Aika", DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss").format(aika));
        data.put("Kosteus", kosteus);
        data.put("Lampotila", lampo);
        data.put("Paine", paine);
        
        this.JArrList.add(data);
        this.jArr.put(data);
        dataProcessor.kirjoitaTiedostoon(this.jArr);
    }
    /*
     * Removes a specified item from the data file and 
     */
    public String removeData(String aika){
        for (JSONObject jsonObject : JArrList) {
            //System.out.println(jsonObject.getString("Aika"));
            if(jsonObject.getString("Aika").equals(aika)){
                //System.out.println(aika);
                JArrList.remove(jsonObject);
                this.jArr = new JSONArray(JArrList);
                dataProcessor.kirjoitaTiedostoon(jArr);
                return "Succesfully removed";
            }
        }
        return "Removal failed, time: " + aika + " not found.";
    }
    /*
     * Returns a JSONObject ArrayList from the JSONArray
     */
    public ArrayList<JSONObject> JsonArrayToList(){
        ArrayList<JSONObject> lista = new ArrayList<JSONObject>();
        for (int i = 0; i<this.jArr.length();i++) {
            JSONObject jObj = ((JSONObject) jArr.get(i));
            lista.add(jObj);
        }
        return lista;
    }
    public ArrayList<JSONObject> getArrayList(){
        return this.JArrList;
    }
    public JSONArray getJsonArray(){
        return this.jArr;
    }
    /*public JSONArray ArrListToJsnArr(ArrayList<JSONObject> ArrList){
       JSONArray jarr = new JSONArray(ArrList); 
        //for (JSONObject jsonObject : ArrList) { 
        //    jarr.put(jsonObject);
        //}
        return jarr;
    }*/
    

    //I realised way after doing this whole sorting thing, that i had a way better way of doing this,
    //which should be faster, but im too lazy, as i would need to do this whole method completely differently.
    public Text textSort(String nappi){
        //Taking the current JSONArray object and making an ArrayList copy of it
        this.JArrList = JsonArrayToList();
        switch (nappi) {
            case "Aika":
                Collections.sort(this.JArrList, new AikaCompare());
                break;
            case "AikaReverse":
                //Collections.sort(this.JArrList, new AikaCompare());
                Collections.reverse(this.JArrList);
                break;
            case "Kosteus":
                Collections.sort(this.JArrList, new KosteusCompare());
                break;
            case "KosteusReverse":
                //Collections.sort(this.JArrList, new KosteusCompare());
                Collections.reverse(this.JArrList);
                break;
            case "Lampotila":
                Collections.sort(this.JArrList, new LampoCompare());
                break;
            case "LampotilaReverse":
                //Collections.sort(this.JArrList, new LampoCompare());
                Collections.reverse(this.JArrList);
                break;
            case "Paine":
                Collections.sort(this.JArrList, new PaineCompare());
                break;
            case "PaineReverse":
                //Collections.sort(this.JArrList, new LampoCompare());
                Collections.reverse(this.JArrList);
                break;
            default:
                break;
        }

        //System.out.println(this.JArrList);
        //Updating the current JSONArray to match the JSONObject ArrayList
        this.jArr = new JSONArray(JArrList);
        return this.jArrToText();
    }
}
class AikaCompare implements Comparator<JSONObject>{
    @Override
    public int compare(JSONObject lhs, JSONObject rhs){
        //Yeah yeah i know it looks bad but, but works ;D Edit: used to look much worse but still bad
        Long lhs2 = Long.valueOf(lhs.getString("Aika").replaceAll("[^0-9]", ""));
        Long rhs2 = Long.valueOf(rhs.getString("Aika").replaceAll("[^0-9]", ""));

        return lhs2 > rhs2 ? -1 : lhs2 < rhs2 ? 1 : 0;
        //System.out.println(i);
    }
}
class KosteusCompare implements Comparator<JSONObject>{
    @Override
    public int compare(JSONObject lhs, JSONObject rhs){
        Double lhs2 = lhs.getDouble("Kosteus");
        Double rhs2 = rhs.getDouble("Kosteus");
        return lhs2 > rhs2 ? -1 : lhs2 < rhs2 ? 1 : 0;
    }
}
/*This is such a stupid way to do this, i just can't figure out
How to get any extra arguments there, would save a bunch, if i just
could get the JSON Key as an argument*/
class LampoCompare implements Comparator<JSONObject>{
    @Override
    public int compare(JSONObject lhs, JSONObject rhs){
        Double lhs2 = lhs.getDouble("Lampotila");
        Double rhs2 = rhs.getDouble("Lampotila");
        return lhs2 > rhs2 ? -1 : lhs2 < rhs2 ? 1 : 0;
    }
}
class PaineCompare implements Comparator<JSONObject>{
    @Override
    public int compare(JSONObject lhs, JSONObject rhs){
        Double lhs2 = lhs.getDouble("Paine");
        Double rhs2 = rhs.getDouble("Paine");
        return lhs2 > rhs2 ? -1 : lhs2 < rhs2 ? 1 : 0;
    }
}