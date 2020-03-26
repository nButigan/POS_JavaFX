package sample.controllers;

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
import sample.models.Artikl;
import sample.models.ArtiklRacun;
import sample.models.Korisnik;
import sample.models.Racun;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class StornoController implements Initializable {
    @FXML
    private TableView stornoTablica;

    @FXML
    private TableColumn IdRacuna;

    @FXML
    private Label lblImeKorisnika;

    @FXML
    private Label lblUlogaKorisnika;

    @FXML
    private TextField txtIdRacuna;

    @FXML
    private Button btnOdustani;

    @FXML
    private Button btnStorniraj;

    @FXML
    private Label lblAlert;

    ObservableList<Racun> racuns = null;
    Racun selectedRacun = null;
    static Korisnik logiraniKorisnik;

    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) {
        if (mouseEvent.getSource() == btnOdustani) {
            closeCurrentWindows(mouseEvent);
            if(logiraniKorisnik.getUloga() == 1) {
                loadStage("/sample/views/AdminView.fxml");
            } else{
                loadStage("/sample/views/RadnikView.fxml");
            }
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
        this.IdRacuna.setCellValueFactory(new PropertyValueFactory<>("id"));
        this.popuniRacune();
    }

    private void popuniRacune () {
        ObservableList<Racun> racuns = (ObservableList<Racun>) Racun.readAll();
        this.stornoTablica.setItems(racuns);
    }

    public void odaberiRacun(MouseEvent mouseEvent) {
        this.selectedRacun = (Racun) this.stornoTablica.getSelectionModel().getSelectedItem();
        if(this.selectedRacun != null) {

            this.txtIdRacuna.setText(String.valueOf(this.selectedRacun.getId()));

        }
    }

    @FXML
    public void stornirajAction(ActionEvent event) {
        int r =  Integer.parseInt(txtIdRacuna.getText());
        if(ArtiklRacun.remove(r) == true && Racun.remove(r) == true) {
            setLblAlert(Color.GREEN, "Uspješno brisanje računa " + r + ".");
            popuniRacune();
            this.stornoTablica.refresh();
        } else{
            setLblAlert(Color.TOMATO, "Neuspješno brisanje računa " + r + ".");
        }
    }

    private void setLblAlert(Color color, String text) {
        lblAlert.setTextFill(color);
        lblAlert.setText(text);
    }
}
