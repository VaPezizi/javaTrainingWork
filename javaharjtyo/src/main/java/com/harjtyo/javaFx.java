package com.harjtyo;

import java.util.ArrayList;

import org.json.JSONObject;


import javafx.application.Application;  // sovellus
import javafx.stage.Stage;              // (pää)ikkuna

import javafx.scene.Scene;              // näkymä
import javafx.scene.layout.BorderPane;  // asettelu
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.geometry.HPos;

import javafx.geometry.Insets;
import javafx.geometry.Orientation;

import javafx.scene.control.Label;      // komponentti (teksti)
import javafx.scene.control.Button;     // komponentti (painike)

import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;

public class javaFx extends Application{
    // d
    //private Noppa n1;

    @Override
    public void start(Stage ikkuna) {

        Label otsikkoLabel = new Label("Mittaukset");

        BorderPane Asettelu = new BorderPane();

        Asettelu.setPadding(new Insets(5));
        Asettelu.setTop(otsikkoLabel);

        //Making the textHandle object with data read from the "data.json" file. The data is read with "dataProcessor.lueData"
        textProcess textHandler = new textProcess(dataProcessor.lueData("./javaharjtyo/src/main/java/com/harjtyo/data.json"));
        //Text teksti = new Text(textHandler.jArrToText().getText());
        Text teksti = new Text(textHandler.textSort("Aika").getText());
        //teksti.setText();

        ScrollPane scrollPane = new ScrollPane(teksti);
        scrollPane.setMinWidth(500);
        scrollPane.setMaxHeight(650);
        
        //Yeah yeah i know its ugly to do it this way
        Label mittaustenTitle = new Label("Aika: \t\t\t\tKosteus:\t\tLampotila:\tPaine:\t\tSähkökäyttö:");

        //FlowPane for the scrollpane and the Labels
        FlowPane rightFlowPane = new FlowPane();
        rightFlowPane.setPadding(new Insets(10));
        rightFlowPane.setHgap(10);
        rightFlowPane.getChildren().addAll(mittaustenTitle, scrollPane);
        rightFlowPane.setPrefHeight(400);
        rightFlowPane.setMaxHeight(400);
        Asettelu.setRight(rightFlowPane);
        
        //Creating buttons for different sorting methods
        Button aikaButton = new Button("Aika");
        Label lajitteluLabel = new Label("Lajittelu: Aika, Uusin ensin");

        //Input field and labels for time input column
        Label input1Label = new Label("Lisää mittaus:");

        //Input field and labels for dampness
        Label input2Label = new Label("Kosteus: ");
        TextField kosteusTextField = new TextField();
        kosteusTextField.setMaxWidth(170);

        //Input fields and labels for temperature
        Label input3Label = new Label("Lampotila: ");
        TextField lampoTextField = new TextField();
        lampoTextField.setMaxWidth(170);

        //Input field and label for pressure:
        Label input4Label = new Label("Paine: ");
        TextField paineTextField = new TextField();
        paineTextField.setMaxWidth(170);

        Label sahkoLabel = new Label("Sahkönkulutus: ");
        TextField sahkoTextField = new TextField();
        sahkoTextField.setMaxWidth(170);

        //Label and input field for removing data
        //I decided to make the data removal be done by entering a specific time
        Label input5Label = new Label("Tekstin poisto ajan mukaan:");
        TextField deleteTextField = new TextField();
        deleteTextField.setMaxWidth(170);

        //Label and input for fetching data by date
        Label dateDataLabel = new Label("Hae tietyn ajan mittaukset:");
        ArrayList<JSONObject> JsonArray = textHandler.getArrayList();
        TextField dateDataField = new TextField(JsonArray.get(0).getString("Aika"));
        dateDataField.setMaxWidth(170);
        TextField dateDataField2 = new TextField(JsonArray.get(JsonArray.size() -1).getString("Aika"));

        //Text for telling if text removal worked
        Text deleteText = new Text("");


        Button dataRemoveButton = new Button("Poista");

        Button dateDataButton = new Button("Hae");

        Button dataAddButton = new Button("Lisaa");
        
        Button kosteusButton = new Button("Kosteus");

        Button lampotilaButton = new Button("Lampotila");

        Button paineButton = new Button("Paine");

        Button sahkoButton = new Button("Sähkö");

        Button clearButton = new Button("Palauta");
        
        FlowPane tekstit = new FlowPane();
        tekstit.setPadding(new Insets(10));
        tekstit.setHgap(20);
        tekstit.getChildren().addAll(aikaButton, kosteusButton, lampotilaButton, paineButton, sahkoButton);
        tekstit.getChildren().addAll(lajitteluLabel);
        tekstit.setMaxWidth(500);
        //tekstit.getChildren().addAll(inputLabel, textField);

        FlowPane datainFlowPane = new FlowPane(Orientation.VERTICAL);
        datainFlowPane.setVgap(5);
        datainFlowPane.setColumnHalignment(HPos.LEFT);
        datainFlowPane.getChildren().add(tekstit);
        datainFlowPane.setMaxWidth(500);
        datainFlowPane.setPrefWidth(500);

        HBox aikaValiHBox = new HBox();
        aikaValiHBox.setPadding(new Insets(10));
        aikaValiHBox.setSpacing(10);
        aikaValiHBox.getChildren().addAll(dateDataField, new Text("-"), dateDataField2);

        HBox dateDataBox = new HBox();
        dateDataBox.setPadding(new Insets(10));
        dateDataBox.setSpacing(10);
        dateDataBox.getChildren().addAll(dateDataButton, clearButton);


        datainFlowPane.getChildren().addAll(input1Label, input2Label, kosteusTextField,
        input3Label, lampoTextField, input4Label, paineTextField, sahkoLabel, sahkoTextField , dataAddButton, input5Label, 
        deleteTextField, dataRemoveButton, deleteText, dateDataLabel, aikaValiHBox, dateDataBox);


        //datainFlowPane.getChildren().add(aikaTextField);
        datainFlowPane.setMaxWidth(50);

        //Asettelu.setCenter(aikaInfo);
        //Asettelu.setCenter(mittausInputFlowPane);
        Asettelu.setLeft(datainFlowPane);

        //teksti.setText(textHandler.textSort("Aika").getText());
        Scene nakyma = new Scene(Asettelu, 1280, 720);
        
        ikkuna.setScene(nakyma);
        ikkuna.setTitle("testi");
        
        ikkuna.show();

        //Lamda operation for sorting data by time
        aikaButton.setOnAction((event -> {
                //Text teksti = textHandler.textSort("Aika");
            if (lajitteluLabel.getText() != "Lajittelu: Aika, Uusin ensin") {
                teksti.setText(textHandler.textSort("Aika").getText());
                lajitteluLabel.setText("Lajittelu: Aika, Uusin ensin");
            }else{
                teksti.setText(textHandler.textSort("Reverse").getText());
                lajitteluLabel.setText("Lajittelu: Aika, Vanhin ensin");
            }
            //silmaluku.setText(String.valueOf(n1.getSilmaluku()));
            }
        ));
        kosteusButton.setOnAction((event -> {
            //Text teksti = textHandler.textSort("Aika");
            if (lajitteluLabel.getText() != "Lajittelu: Kosteus, Isoin ensin") {
                teksti.setText(textHandler.textSort("Kosteus").getText());
                lajitteluLabel.setText("Lajittelu: Kosteus, Isoin ensin");
            }else{
                teksti.setText(textHandler.textSort("Reverse").getText());
                lajitteluLabel.setText("Lajittelu: Kosteus, Pienin ensin");
            }
            //silmaluku.setText(String.valueOf(n1.getSilmaluku()));
            }
        ));
        lampotilaButton.setOnAction((event -> {
            //Text teksti = textHandler.textSort("Aika");
            if (lajitteluLabel.getText() != "Lajittelu: Lampotila, Isoin ensin") {
                teksti.setText(textHandler.textSort("Lampotila").getText());
                lajitteluLabel.setText("Lajittelu: Lampotila, Isoin ensin");
            }else{
                teksti.setText(textHandler.textSort("Reverse").getText());
                lajitteluLabel.setText("Lajittelu: Lampotila, Pienin ensin");
            }
            //silmaluku.setText(String.valueOf(n1.getSilmaluku()));
            }
        ));
        paineButton.setOnAction((event -> {
            //Text teksti = textHandler.textSort("Aika");
            if (lajitteluLabel.getText() != "Lajittelu: Paine, Isoin ensin") {
                teksti.setText(textHandler.textSort("Paine").getText());
                lajitteluLabel.setText("Lajittelu: Paine, Isoin ensin");
            }else{
                teksti.setText(textHandler.textSort("Reverse").getText());
                lajitteluLabel.setText("Lajittelu: Paine, Pienin ensin");
            }
            //silmaluku.setText(String.valueOf(n1.getSilmaluku()));
            }
        ));

        sahkoButton.setOnAction((event -> {
            //Text teksti = textHandler.textSort("Aika");
            if (lajitteluLabel.getText() != "Lajittelu: Sähkö, Isoin ensin") {
                teksti.setText(textHandler.textSort("Sahko").getText());
                lajitteluLabel.setText("Lajittelu: Sähkö, Isoin ensin");
            }else{
                teksti.setText(textHandler.textSort("Reverse").getText());
                lajitteluLabel.setText("Lajittelu: Sähkö, Pienin ensin");
            }
            //silmaluku.setText(String.valueOf(n1.getSilmaluku()));
            }
        ));

        //Lamda for the button that adds data by input
        dataAddButton.setOnAction((event -> {
            //String aika = aikaTextField.getText();
            String kosteus = kosteusTextField.getText();
            String lampo = lampoTextField.getText();
            String paine = paineTextField.getText();
            String sahko = sahkoTextField.getText();
            String regex = "\\d+";
            //Seeing if input data matches the specified regex, wich needs to be decimals
            if(kosteus.matches(regex) && lampo.matches(regex) && paine.matches(regex) && sahko.matches(regex)){
                textHandler.addData(kosteus, lampo, paine, sahko);
                teksti.setText(textHandler.textSort("Aika").getText());
                lajitteluLabel.setText("Lajittelu: Aika, Uusin ensin");
            }else{
                input1Label.setText("Virheellinen Syöte!");
            }
        }));
        //Lamda for the button that removes data by input
        dataRemoveButton.setOnAction((event -> {
            String aika = deleteTextField.getText();
            deleteText.setText(textHandler.removeData(aika));

            teksti.setText(textHandler.textSort("Aika").getText());
            lajitteluLabel.setText("Lajittelu: Aika, Uusin ensin");
        }));
        dateDataButton.setOnAction((event -> {
            //fetchedData.setText(textHandler.searchData(dateDataField.getText(), dateDataField2.getText()));
            teksti.setText(textHandler.searchData(dateDataField.getText(), dateDataField2.getText()).getText());

        }));
        clearButton.setOnAction((event -> {
            teksti.setText(textHandler.textSort("Aika").getText());
            lajitteluLabel.setText("Lajittelu: Aika, Uusin ensin");
        }));
        
    }


    public static void main(String[] args) {
        //launch(JavaFxNoppa.class, args);
    }
}