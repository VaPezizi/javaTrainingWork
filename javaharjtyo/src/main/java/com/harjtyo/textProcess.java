package com.harjtyo;

import java.text.DecimalFormat;
import org.json.JSONArray;
import org.json.JSONObject;
import javafx.scene.text.Text;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
//import java.util.HashMap;
//import java.util.List;
import java.time.format.DateTimeFormatter;  

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

        for(int i = 0; i < this.jArr.length();i++){      
            //jArray.get for some reason returns an Object and not an JSONObject, so casting i to JSONObj here
            JSONObject jObj = ((JSONObject) jArr.get(i));
            DecimalFormat formatter = new DecimalFormat("#0.0000000");
            teksti.setText(teksti.getText() + jObj.getString("Aika") + "\t" + formatter.format(jObj.getDouble("Kosteus")) + "\t" + formatter.format(jObj.getDouble("Lampotila")) + "\t" + formatter.format(jObj.getDouble("Paine")) + "\t" +  "\n");
        }
        return teksti;
    }

    public ArrayList<JSONObject> JsonArrayToList(){
        ArrayList<JSONObject> lista = new ArrayList<JSONObject>();
        for (int i = 0; i<this.jArr.length();i++) {
            JSONObject jObj = ((JSONObject) jArr.get(i));
            lista.add(jObj);
        }
        return lista;
    }
    public JSONArray ArrListToJsnArr(ArrayList<JSONObject> ArrList){
        JSONArray jarr = new JSONArray(); 
        for (JSONObject jsonObject : ArrList) {
            jarr.put(jsonObject);
        }
        return jarr;
    }
    

    public Text textSort(String nappi){
        //Taking the current JSONArray object and making an ArrayList copy of it
        this.JArrList = JsonArrayToList();
        switch (nappi) {
            case "Aika":
                //ArrayList<JSONObject> Lista = new ArrayList<>();
                //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                //int last = 0;
                //System.out.println(this.JArrList);
                Collections.sort(this.JArrList, new AikaCompare());
                break;
            case "AikaReverse":
                //ArrayList<JSONObject> Lista2 = new ArrayList<>();
                //DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
                //int last = 0;
                Collections.sort(this.JArrList, new AikaCompare());
                Collections.reverse(this.JArrList);
                break;
            case "Kosteus":

                break;
            case "Lampotila":

                break;
            default:
                break;
        }

        //System.out.println(this.JArrList);
        //Updating the current JSONArray to match the JSONObject ArrayList
        this.jArr = this.ArrListToJsnArr(JArrList);
        return this.jArrToText();
    }
}
class AikaCompare implements Comparator<JSONObject>{
    @Override
    public int compare(JSONObject lhs, JSONObject rhs){
        //Yeah yeah i know it looks but, but works ;D
        Long lhs2 = Long.valueOf(lhs.getString("Aika").replaceAll("[^0-9]", ""));
        Long rhs2 = Long.valueOf(rhs.getString("Aika").replaceAll("[^0-9]", ""));

        return lhs2 > rhs2 ? -1 : lhs2 < rhs2 ? 1 : 0;
        //System.out.println(i);
        
    }
}
