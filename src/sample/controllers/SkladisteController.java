package sample.controllers;

import javafx.beans.property.SimpleStringProperty;
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
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.models.Artikl;
import sample.models.Korisnik;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class SkladisteController implements Initializable {

    @FXML
    private TableView skladisteTablica;

    @FXML
    private TableColumn artiklNaziv;

    @FXML
    private TableColumn artiklKolicina;

    @FXML
    private Label lblImeKorisnika;

    @FXML
    private Label lblUlogaKorisnika;

    @FXML
    private TextField txtKolicina;

    @FXML
    private  Button btnOdustani;

    @FXML
    private Button btnSpremi;

    @FXML
    private Label lblAlert;

    Artikl selectedArticle= null;
    static Korisnik logiraniKorisnik = null;

    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) {
        if (mouseEvent.getSource() == btnOdustani) {
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

            stage.setTitle("Administrator");
            stage.getIcons().add(new Image("@../../images/point-of-sale-icon.png"));
            stage.initModality(Modality.APPLICATION_MODAL);
            /*stage.sizeToScene();*/
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.lblImeKorisnika.setText(logiraniKorisnik.getKorisnickoIme());
        if(logiraniKorisnik.getUloga() == 1) {
            this.lblUlogaKorisnika.setText("Administrator");
        } else {
            this.lblUlogaKorisnika.setText("Konobar");
        }

        this.artiklNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        this.artiklKolicina.setCellValueFactory(new PropertyValueFactory<>("kolicina"));
        this.popuniArtikle();
    }
    private void popuniArtikle () {
        ObservableList<Artikl> artikli = (ObservableList<Artikl>) Artikl.readAll();
        this.skladisteTablica.setItems(artikli);
    }


    public void odaberiProizvod(MouseEvent mouseEvent) {
        this.selectedArticle = (Artikl) this.skladisteTablica.getSelectionModel().getSelectedItem();
        if(this.selectedArticle != null) {

            this.txtKolicina.setText(String.valueOf(this.selectedArticle.getKolicina()));

        }
    }
    @FXML
    void spremiAction(ActionEvent event) {
        Artikl a =  new Artikl(Integer.parseInt(txtKolicina.getText()),this.selectedArticle.getId());
       if(a.updateA(a) == true){
           setLblAlert(Color.GREEN, "Uspješno ažuriranje količine artikla.");
           popuniArtikle();
           this.skladisteTablica.refresh();
       } else{
           setLblAlert(Color.TOMATO, "Neuspješno ažuriranje količine artikla.");
       }

    }

    private void setLblAlert(Color color, String text) {
        lblAlert.setTextFill(color);
        lblAlert.setText(text);
    }



}
