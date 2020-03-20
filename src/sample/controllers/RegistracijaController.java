package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.models.Korisnik;
import utils.ConnectionUtil;

public class RegistracijaController implements Initializable {

    @FXML
    private TextField txtIme;

    @FXML
    private TextField txtPrezime;

    @FXML
    private TextField txtKorisnickoIme;

    @FXML
    private PasswordField txtSifra;

    @FXML
    private Label lblErrors;

    @FXML
    private Button btnPrijava;

    @FXML
    private Button btnRegistrirajSe;

    @FXML
    public void handleRegistration(MouseEvent event){

        if(event.getSource() == btnRegistrirajSe){
            String ime = txtIme.getText();
            String prezime = txtPrezime.getText();
            String kIme = txtKorisnickoIme.getText();
            String sifra = txtSifra.getText();
            Korisnik korisnik = new Korisnik(0,ime, prezime, kIme, sifra);
            if(Korisnik.add(korisnik)) {

                lblErrors.setTextFill(Color.GREEN);
                lblErrors.setText("Registracija uspješno obavljena.");
            } else{
                lblErrors.setText("Registracija neuspješna.");

            }
        }

        if(event.getSource() == btnPrijava){
            try {


                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setMaximized(false);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/sample/views/Login.fxml")));
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
