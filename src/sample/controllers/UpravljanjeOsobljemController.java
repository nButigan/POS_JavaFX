package sample.controllers;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.models.Korisnik;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class UpravljanjeOsobljemController implements Initializable {

    @FXML
    private TableView TVradnik;

    @FXML
    private Label lblImeKorisnika;

    @FXML
    private Label lblUlogaKorisnika;

    @FXML
    private TableColumn colIme;

    @FXML
    private TableColumn colPrezime;

    @FXML
    private TableColumn colKorisnickoIme;

    @FXML
    private TableColumn colSifra;

    @FXML
    private TableColumn colUloga;

    @FXML
    private TextField txtIme;

    @FXML
    private TextField txtPrezime;

    @FXML
    private TextField txtKorisnickoIme;

    @FXML
    private TextField txtSifra;

    @FXML
    private TextField txtUloga;

    @FXML
    private RadioButton radioAdmin;

    @FXML
    private RadioButton radioKonobar;

    @FXML
    private Button btnSpremi;

    @FXML
    private Button btnIzbrisi;

    @FXML
    private Button btnNazad;
    @FXML
    private Label lblObavjest;

    Korisnik selectedKorisnik = null;
    static Korisnik logiraniKorisnik = null;
    private Object PropertyValueFactory;
    int uloga = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.lblImeKorisnika.setText(logiraniKorisnik.getKorisnickoIme());
        if(logiraniKorisnik.getUloga() == 1) {
            this.lblUlogaKorisnika.setText("Administrator");
        } else {
            this.lblUlogaKorisnika.setText("Konobar");
        }


        this.colIme.setCellValueFactory(new PropertyValueFactory<>("ime"));
        this.colPrezime.setCellValueFactory(new PropertyValueFactory<>("prezime"));
        this.colKorisnickoIme.setCellValueFactory(new PropertyValueFactory<>("korisnickoIme"));
        this.colUloga.setCellValueFactory(new PropertyValueFactory<>("uloga"));


        this.colSifra.setCellValueFactory(new PropertyValueFactory<>("sifra"));

        this.popuniKorisnike();

    }

    private void popuniKorisnike(){
        ObservableList<Korisnik> korisnici =  (ObservableList<Korisnik>) Korisnik.readAll();
        this.TVradnik.setItems(korisnici);
    }

    @FXML
    public void odaberiKorisnika(MouseEvent ev) {
        ToggleGroup group = new ToggleGroup();
        radioKonobar.setToggleGroup(group);
        radioAdmin.setToggleGroup(group);
        this.selectedKorisnik = (Korisnik) this.TVradnik.getSelectionModel().getSelectedItem();
        if (this.selectedKorisnik != null) {
            this.txtIme.setText(this.selectedKorisnik.getIme());
            this.txtPrezime.setText(this.selectedKorisnik.getPrezime());
            this.txtKorisnickoIme.setText(this.selectedKorisnik.getKorisnickoIme());

            if(this.selectedKorisnik.getUloga()==1){
                radioAdmin.setSelected(true);
                // this.txtUloga.setText("Admin");

            }else{
                radioKonobar.setSelected(true);
                //this.txtUloga.setText("Konobar");
            }

            this.txtSifra.setText(this.selectedKorisnik.getSifra());

        }
    }


    @FXML
    public void izbrisiKorisnika (ActionEvent ev) {
        Korisnik korisnik = (Korisnik) this.TVradnik.getSelectionModel().getSelectedItem();
        Korisnik.remove(korisnik);
        this.popuniKorisnike();
    }
    @FXML
    public void spremiPromjene (ActionEvent ev) {

        this.selectedKorisnik.setIme(this.txtIme.getText());
        this.selectedKorisnik.setPrezime(this.txtPrezime.getText());
        this.selectedKorisnik.setKorisnickoIme(this.txtKorisnickoIme.getText());
        this.selectedKorisnik.setSifra(this.txtSifra.getText());
        if(radioAdmin.isSelected()){
            this.selectedKorisnik.setUloga(1);
            uloga =  1;
        }else {
            this.selectedKorisnik.setUloga(0);
            uloga=0;
        }

        Korisnik korisnik =  new Korisnik(this.selectedKorisnik.getID(),
                this.txtIme.getText(),
                this.txtPrezime.getText(),
                this.txtKorisnickoIme.getText(),
                uloga,
                this.txtSifra.getText());

        boolean  provjera = korisnik.update(korisnik);

        this.popuniKorisnike();
        if (provjera==false) {
            lblObavjest.setTextFill(Color.TOMATO);
            lblObavjest.setText("Greška prilikom ažuriranja.");
        } else {
            lblObavjest.setTextFill(Color.GREEN);
            lblObavjest.setText("Uspješno ažurirano.");

        }
    }
    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) {
        if (mouseEvent.getSource() == btnNazad) {
            closeCurrentWindows(mouseEvent);
            loadStage("/sample/views/AdminView.fxml");
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

            stage.setTitle("Admin");
            stage.getIcons().add(new Image("@../../images/point-of-sale-icon.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            /*stage.sizeToScene();*/
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
