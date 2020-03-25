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
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.models.Korisnik;
import utils.ConnectionUtil;


public class LoginController implements Initializable {

    @FXML
    private Label lblErrors;

    @FXML
    private TextField txtUsername;

    @FXML
    private TextField txtPassword;

    @FXML
    private Button btnSignin;

    @FXML
    private Button btnSignup;

    private String name;
    private int uloga;


    Connection con = null;
    PreparedStatement preparedStatement = null;
    ResultSet resultSet = null;

    public String getName() {
        return name;
    }

    public int getUloga() {
        return uloga;
    }

    @FXML
    public void handleButtonAction(MouseEvent event) {

        if (event.getSource() == btnSignin) {
            //login here
            if (logIn().equals("Success Admin")) {
                try {


                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.setMaximized(false);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/sample/views/AdminView.fxml")));
                    stage.setTitle("Administrator");
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }

            } else {

                try {


                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.setMaximized(false);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/sample/views/RadnikView.fxml")));
                    stage.setTitle("Konobar");
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
            }
        }

        if(event.getSource() == btnSignup){
            try {


                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setMaximized(false);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/sample/views/Registracija.fxml")));
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if (con == null) {
            lblErrors.setTextFill(Color.TOMATO);
            lblErrors.setText("Server Error : Check");
        } else {
            lblErrors.setTextFill(Color.GREEN);
            lblErrors.setText("Server is up : Good to go");
        }
    }

    public LoginController() {
        con = ConnectionUtil.conDB();
    }


    private String logIn() {
        String status = "Success";
        String korisnickoIme = txtUsername.getText();
        String sifra = txtPassword.getText();
        if(korisnickoIme.isEmpty() || sifra.isEmpty()) {
            setLblError(Color.TOMATO, "Empty credentials");
            status = "Error";
        } else {

            String sql = "SELECT * FROM Korisnik Where korisnickoIme = ? and sifra = ?";
            try {
                preparedStatement = con.prepareStatement(sql);
                preparedStatement.setString(1, korisnickoIme);
                preparedStatement.setString(2, sifra);
                resultSet = preparedStatement.executeQuery();
                if (!resultSet.next()) {
                    setLblError(Color.TOMATO, "Enter Correct Email/Password");
                    status = "Error";
                } else if(resultSet.getInt("uloga") == 1) {
                    setLblError(Color.GREEN, "Login Successful for " + korisnickoIme+" as administrator..Redirecting..");
                    this.uloga = 1;
                    this.name = korisnickoIme;
                    Korisnik logiraniKorisnik = new Korisnik(name,1);
                    NarudzbaController.logiraniKorisnik = logiraniKorisnik;
                    StornoController.logiraniKorisnik = logiraniKorisnik;
                    UpravljanjeOsobljemController.logiraniKorisnik = logiraniKorisnik;
                    SkladisteController.logiraniKorisnik = logiraniKorisnik;
                    status = "Success Admin";
                } else {
                    setLblError(Color.GREEN, "Login Successful for " +korisnickoIme+" as konobar..Redirecting..");
                    this.uloga = 0;
                    this.name = korisnickoIme;
                    Korisnik logiraniKorisnik = new Korisnik(name,0);
                    NarudzbaController.logiraniKorisnik = logiraniKorisnik;
                    StornoController.logiraniKorisnik = logiraniKorisnik;
                }
            } catch (SQLException ex) {
                System.err.println(ex.getMessage());
                status = "Exception";
            }
        }

        return status;
    }

    private void setLblError(Color color, String text) {
        lblErrors.setTextFill(color);
        lblErrors.setText(text);
        System.out.println(text);
    }
}
