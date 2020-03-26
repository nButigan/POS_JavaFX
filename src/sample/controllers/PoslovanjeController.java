package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sample.models.Artikl;
import sample.models.Korisnik;
import sample.models.Poslovanje;
import sample.models.Racun;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class PoslovanjeController implements Initializable {

    @FXML
    private Label lblImeKorisnika;
    @FXML
    private Label lblUlogaKorisnika;
    @FXML
    private Button btnDanas;
    @FXML
    private  Button btn7dana;
    @FXML
    private  Button btn30dana;
    @FXML
    private  Button btnNazad;
    @FXML
    private Pane paneGraf;

    static Korisnik logiraniKorisnik = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.lblImeKorisnika.setText(logiraniKorisnik.getKorisnickoIme());
        if(logiraniKorisnik.getUloga() == 1) {
            this.lblUlogaKorisnika.setText("Administrator");
        } else {
            this.lblUlogaKorisnika.setText("Konobar");
        }

    }

    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) {
        if (mouseEvent.getSource() == btnNazad) {
            closeCurrentWindows(mouseEvent);
            loadStage("/sample/views/AdminView.fxml");
        } else if(mouseEvent.getSource() == btnDanas){
            List<Poslovanje> informacije1 = Poslovanje.dohvatiDanasnjeRacune();
            prikaziGrafZaDanas(informacije1);
        } else if(mouseEvent.getSource() == btn7dana){
            List<Poslovanje> informacije2 = Poslovanje.dohvatiRacunePosljednjih7dana();
            prikaziGrafZa7dana(informacije2);
        } else{
            List<Poslovanje> informacije3 = Poslovanje.dohvatiRacunePosljednjih30dana();
            prikaziGrafZa30dana(informacije3);
        }
    }

    private void closeCurrentWindows(javafx.event.ActionEvent mouseEvent){
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(false);
        stage.close();
    }

    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMaximized(false);

            stage.setTitle("Administrator");
            stage.getIcons().add(new Image("@../../images/point-of-sale-icon.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            /*stage.sizeToScene();*/
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void prikaziGrafZaDanas(List<Poslovanje> informacije){
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Artikli");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Količina");

        BarChart barChart = new BarChart(xAxis, yAxis);

        //Prikaz potrošnje artikala danas
        XYChart.Series danas = new XYChart.Series();
        danas.setName("Danas");
        for(int i=0;i<informacije.size(); i++) {
            danas.getData().add(new XYChart.Data(informacije.get(i).getNazivArtikla(),informacije.get(i).getKolicina()));
        }
        barChart.getData().add(danas);
        paneGraf.getChildren().clear();
        barChart.setPrefWidth(paneGraf.getWidth());
        barChart.setPrefHeight(paneGraf.getHeight());
        paneGraf.getChildren().add(barChart);

    }

    private void prikaziGrafZa7dana(List<Poslovanje> informacije){

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Artikli");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Količina");

        BarChart barChart = new BarChart(xAxis, yAxis);

        //Prikaz potrošnje artikala posjednih 7 dana
        XYChart.Series posljednjih7dana = new XYChart.Series();
        posljednjih7dana.setName("7 dana");
        for(int i=0;i<informacije.size(); i++) {
            posljednjih7dana.getData().add(new XYChart.Data(informacije.get(i).getNazivArtikla(),informacije.get(i).getKolicina()));
        }
        barChart.getData().add(posljednjih7dana);
        paneGraf.getChildren().clear();
        barChart.setPrefWidth(paneGraf.getWidth());
        barChart.setPrefHeight(paneGraf.getHeight());
        paneGraf.getChildren().add(barChart);

    }

    private void prikaziGrafZa30dana(List<Poslovanje> informacije){

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Artikli");

        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Količina");

        BarChart barChart = new BarChart(xAxis, yAxis);

        //Posljednjih mjesec dana
        XYChart.Series posljednjih30dana = new XYChart.Series();
        posljednjih30dana.setName("30 dana");
        for(int i=0;i<informacije.size(); i++) {
            posljednjih30dana.getData().add(new XYChart.Data(informacije.get(i).getNazivArtikla(),informacije.get(i).getKolicina()));
        }
        barChart.getData().add(posljednjih30dana);
        paneGraf.getChildren().clear();
        barChart.setPrefWidth(paneGraf.getWidth());
        barChart.setPrefHeight(paneGraf.getHeight());
        paneGraf.getChildren().add(barChart);

    }


}
