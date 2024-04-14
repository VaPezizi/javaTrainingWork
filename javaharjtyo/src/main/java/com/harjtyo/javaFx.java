package com.harjtyo;

import javafx.application.Application;  // sovellus
import javafx.stage.Stage;              // (pää)ikkuna

//import javafx.geometry.Orientation;  
import javafx.scene.Scene;              // näkymä
import javafx.scene.layout.BorderPane;  // asettelu
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
//import javafx.geometry.Pos;             // komponentin sijoittelu
import javafx.geometry.Insets;
//import javafx.geometry.Pos;          // komponentin reunukset (padding)
import javafx.scene.control.Label;      // komponentti (teksti)
import javafx.scene.control.Button;     // komponentti (painike)

//import javafx.scene.layout.StackPane;  
//import javafx.stage.Stage;  
import javafx.scene.control.ScrollPane;

// c, kommentoitu ulos kohdassa d
import javafx.event.EventHandler;       // tapahtumankäsittely
import javafx.event.ActionEvent;        // tapahtumankäsittely

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
        Label aikaLabel = new Label("Lajittelu: Aika, Vanhin ensin");

        Button kosteusButton = new Button("Kosteus");

        Button lampotilaButton = new Button("Lampotila");


        FlowPane tekstit = new FlowPane();
        tekstit.setPadding(new Insets(5));
        tekstit.setHgap(10);
        tekstit.getChildren().addAll(aikaButton, kosteusButton, lampotilaButton);
        tekstit.getChildren().addAll(aikaLabel);

        Asettelu.setLeft(tekstit);


        Scene nakyma = new Scene(Asettelu, 800, 720);
        
        ikkuna.setScene(nakyma);
        ikkuna.setTitle("testi");
        
        ikkuna.show();

        //Lamda operation for sorting data by time
        aikaButton.setOnAction((event -> {
                //Text teksti = textHandler.textSort("Aika");
            if (aikaLabel.getText() == "Lajittelu: Aika, Vanhin ensin") {
                teksti.setText(textHandler.textSort("Aika").getText());
                aikaLabel.setText("Lajittelu: Aika, Uusin ensin");
            }else{
                teksti.setText(textHandler.textSort("AikaReverse").getText());
                aikaLabel.setText("Lajittelu: Aika, Vanhin ensin");
            }
            //silmaluku.setText(String.valueOf(n1.getSilmaluku()));
            }
        ));
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
    public void paivitaNoppa(BorderPane noppaAsettelu) {
        Label tahot = (Label)noppaAsettelu.getTop();
        Label silmaluku = (Label)noppaAsettelu.getCenter();
        
        //tahot.setText("d" + this.n1.getTahoja());
        //silmaluku.setText(String.valueOf(this.n1.getSilmaluku()));
    }

    public static void main(String[] args) {
        //launch(JavaFxNoppa.class, args);
    }
}