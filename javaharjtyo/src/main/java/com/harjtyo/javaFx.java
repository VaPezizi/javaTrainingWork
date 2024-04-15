package com.harjtyo;

import java.util.ArrayList;

import org.json.JSONObject;

//import java.util.ArrayList;
//import java.util.Collection;
//import java.util.Collections;

//import org.json.JSONObject;

import javafx.application.Application;  // sovellus
import javafx.stage.Stage;              // (pää)ikkuna

//import javafx.geometry.Orientation;  
import javafx.scene.Scene;              // näkymä
import javafx.scene.layout.BorderPane;  // asettelu
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.geometry.HPos;
//import javafx.geometry.Pos;             // komponentin sijoittelu
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
//import javafx.geometry.Pos;          // komponentin reunukset (padding)
import javafx.scene.control.Label;      // komponentti (teksti)
import javafx.scene.control.Button;     // komponentti (painike)

//import javafx.scene.layout.StackPane;  
//import javafx.stage.Stage;  
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
// c, kommentoitu ulos kohdassa d
//import javafx.event.EventHandler;       // tapahtumankäsittely
//import javafx.event.ActionEvent;        // tapahtumankäsittely

public class javaFx extends Application{
    // d
    //private Noppa n1;

    @Override
    public void start(Stage ikkuna) {
        // c, muokattu d-kohdassa Noppa n1 => this.n1
        //this.n1 = new Noppa();
        /*ScrollBar scrollBar = new ScrollBar();
        scrollBar.setOrientation(Orientation.VERTICAL);  
        scrollBar.setUnitIncrement(1);
        scrollBar.setBlockIncrement(1);
        scrollBar.setValue(0);*/

        Label otsikkoLabel = new Label("Mittaukset");

        BorderPane Asettelu = new BorderPane();

        Asettelu.setPadding(new Insets(5));
        //noppaAsettelu.setRight(scrollBar);
        Asettelu.setTop(otsikkoLabel);
        

        textProcess textHandler = new textProcess(dataProcessor.lueData("./javaharjtyo/src/main/java/com/harjtyo/data.json"));
        Text teksti = new Text();
        teksti.setText(teksti.getText() + textHandler.jArrToText().getText());

        ScrollPane scrollPane = new ScrollPane(teksti);
        scrollPane.setFitToHeight(true);
        Asettelu.setRight(scrollPane);
        
        //Insets buttonInsets = new Insets(5);
        //Creating buttons for different sorting methods
        Button aikaButton = new Button("Aika");
        Label lajitteluLabel = new Label("Lajittelu: Aika, Uusin ensin");

        //Input field and labels for time input column

        Label input1Label = new Label("Lisää mittaus:");

        

        //TextField aikaTextField = new TextField("");
        //aikaTextField.setMaxWidth(170);

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

        //Label and input field for removing data
        //I decided to make the data removal be done by entering a specific time
        Label input5Label = new Label("Tekstin poisto ajan mukaan:");
        TextField deleteTextField = new TextField();
        deleteTextField.setMaxWidth(170);

        //Label and input for fetching data by date
        Label dateDataLabel = new Label("Hae tietyn ajan mittaukset:");
        ArrayList<JSONObject> JsonArray = textHandler.getArrayList();
        TextField dateDataField = new TextField(JsonArray.get(JsonArray.size() - 1).getString("Aika"));
        dateDataField.setMaxWidth(170);
        TextField dateDataField2 = new TextField(JsonArray.get(0).getString("Aika"));
        //Label fetchedData = new Label("Aika: (Huom annettava muodossa: 'year/month/day hour:minute:second)");
        //fetchedData.setMinWidth(170);
        //fetchedData.setMaxWidth(300);
        //Text for telling if text removal worked
        Text deleteText = new Text("");


        Button dataRemoveButton = new Button("Poista");

        Button dateDataButton = new Button("Hae");

        Button dataAddButton = new Button("Lisaa");
        
        Button kosteusButton = new Button("Kosteus");

        Button lampotilaButton = new Button("Lampotila");

        Button paineButton = new Button("Paine");

        FlowPane tekstit = new FlowPane();
        tekstit.setPadding(new Insets(10));
        tekstit.setHgap(20);
        tekstit.getChildren().addAll(aikaButton, kosteusButton, lampotilaButton, paineButton);
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


        datainFlowPane.getChildren().addAll(input1Label, input2Label, kosteusTextField,
        input3Label, lampoTextField, input4Label, paineTextField, dataAddButton, input5Label, 
        deleteTextField, dataRemoveButton, deleteText, dateDataLabel, aikaValiHBox, dateDataButton);


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
                teksti.setText(textHandler.textSort("AikaReverse").getText());
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
                teksti.setText(textHandler.textSort("KosteusReverse").getText());
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
                teksti.setText(textHandler.textSort("LampotilaReverse").getText());
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
                teksti.setText(textHandler.textSort("PaineReverse").getText());
                lajitteluLabel.setText("Lajittelu: Paine, Pienin ensin");
            }
            //silmaluku.setText(String.valueOf(n1.getSilmaluku()));
            }
        ));
        dataAddButton.setOnAction((event -> {
            //String aika = aikaTextField.getText();
            String kosteus = kosteusTextField.getText();
            String lampo = lampoTextField.getText();
            String paine = paineTextField.getText();
            String regex = "\\d+";
            if(kosteus.matches(regex) && lampo.matches(regex) && paine.matches(regex)){
                textHandler.addData(kosteus, lampo, paine);
                teksti.setText(textHandler.textSort("Aika").getText());
                lajitteluLabel.setText("Lajittelu: Aika, Uusin ensin");
            }else{
                input1Label.setText("Virheellinen Syöte!");
            }
        }));
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
        /*
        
        noppaAsettelu.setTop(tahot);
        BorderPane.setAlignment(noppaAsettelu.getTop(), Pos.CENTER);
        noppaAsettelu.setLeft(vahenna);
        noppaAsettelu.setCenter(silmaluku);
        
        noppaAsettelu.setBottom(heita);
        BorderPane.setAlignment(noppaAsettelu.getBottom(), Pos.CENTER);

        Scene nakyma = new Scene(noppaAsettelu);
        ikkuna.setScene(nakyma);

        // e
        paivitaNoppa(noppaAsettelu);
        */
        // c
        /*

        

        heita.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                n1.heita();
                silmaluku.setText(String.valueOf(n1.getSilmaluku()));
            }
        });
        */
        
        // c - edellinen nk. Lambda-lausekkeella:
        /*
        heita.setOnAction((event) -> {
            n1.heita();
            silmaluku.setText(String.valueOf(n1.getSilmaluku()));
        });
        

        // d - edellinen refaktoroituna käyttämään paivitaNoppa-metodia
        heita.setOnAction((event) -> {
            System.out.println("heitetty");
            
        });

        // e
        vahenna.setOnAction((event) -> {
            System.out.println("vahennettu");
        });

        lisaa.setOnAction((event) -> {
            System.out.println("lisatty");
        });
        
        // a
        ikkuna.setTitle("Ikkuna");
        ikkuna.show();*/
    }

    // d
    /*public void paivitaNoppa(BorderPane noppaAsettelu) {
        Label tahot = (Label)noppaAsettelu.getTop();
        Label silmaluku = (Label)noppaAsettelu.getCenter();
        
        //tahot.setText("d" + this.n1.getTahoja());
        //silmaluku.setText(String.valueOf(this.n1.getSilmaluku()));
    }*/

    public static void main(String[] args) {
        //launch(JavaFxNoppa.class, args);
    }
}