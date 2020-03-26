package sample.controllers;

import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;
import sample.models.Artikl;
import sample.models.Korisnik;

import java.io.File;
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

    @FXML
    private TableColumn artiklDobavljac;

    @FXML
    private TableColumn artiklCijena;

    @FXML
    private TableColumn artiklKategorija;

    @FXML
    private TextField txtNaziv;

    @FXML
    private TextField txtDobavljaca;

    @FXML
    private TextField txtCijena;

    @FXML
    private MenuButton dropKategorije;

    @FXML
    private MenuItem topliNapitci;

    @FXML
    private MenuItem alkPica;

    @FXML
    private MenuItem gaziranaPica;

    @FXML
    private MenuItem negaziranaPica;

    @FXML
    private RadioButton radioAzuriraj;

    @FXML
    private RadioButton radioDodaj;
    @FXML
    private ImageView slikaArtikla;

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
        ToggleGroup group = new ToggleGroup();
        radioDodaj.setToggleGroup(group);
        radioAzuriraj.setToggleGroup(group);
        this.lblImeKorisnika.setText(logiraniKorisnik.getKorisnickoIme());
        if(logiraniKorisnik.getUloga() == 1) {
            this.lblUlogaKorisnika.setText("Administrator");
        } else {
            this.lblUlogaKorisnika.setText("Konobar");
        }

        this.artiklNaziv.setCellValueFactory(new PropertyValueFactory<>("naziv"));
        this.artiklDobavljac.setCellValueFactory(new PropertyValueFactory<>("dobavljac"));
        this.artiklCijena.setCellValueFactory(new PropertyValueFactory<>("cijena"));
        this.artiklKategorija.setCellValueFactory(new PropertyValueFactory<>("kategorija"));
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

            this.txtNaziv.setText(String.valueOf(this.selectedArticle.getNaziv()));
            this.txtKolicina.setText(String.valueOf(this.selectedArticle.getKolicina()));
            this.txtCijena.setText(String.valueOf(this.selectedArticle.getCijena()));
            this.txtDobavljaca.setText(String.valueOf(this.selectedArticle.getDobavljac()));
            this.dropKategorije.setText(String.valueOf(this.selectedArticle.getKategorija()));
            this.slikaArtikla.setImage(this.selectedArticle.getSlika());

        }
    }

    @FXML
    void spremiAction(ActionEvent event) {

        if(radioDodaj.isSelected()==true){
            String naziv = this.txtNaziv.getText();
            String dobaljvac = this.txtDobavljaca.getText();
            Float cijena = Float.valueOf(this.txtCijena.getText());
            Image slika = this.slikaArtikla.getImage();
            String kategorija = this.dropKategorije.getText();
            Integer kolicina = Integer.valueOf(this.txtKolicina.getText());
            Artikl a =  new Artikl(0,naziv,dobaljvac,cijena,slika,kategorija,kolicina);
            a.add(a);
        }
        else if(radioAzuriraj.isSelected()==true){
            try {
                String naziv = this.txtNaziv.getText();
                String dobaljvac = this.txtDobavljaca.getText();
                Float cijena = Float.valueOf(this.txtCijena.getText());
                Image slika = this.slikaArtikla.getImage();
                String kategorija = this.dropKategorije.getText();
                Integer kolicina = Integer.valueOf(this.txtKolicina.getText());
                Artikl a = new Artikl(0, naziv, dobaljvac, cijena, slika, kategorija, kolicina);
                a.update(a);
            } catch (Exception ex){
                System.out.println("Neispravan odabir " + ex);
            }


        }
        else{
            System.out.println("Neuspješno spremanje niste odabrali radio button vrstu spremanja");
        }

    }
    @FXML
    public void actionRadioAzuriraj(ActionEvent evt) {
        try {
            this.txtNaziv.setText(String.valueOf(this.selectedArticle.getNaziv()));
            this.txtKolicina.setText(String.valueOf(this.selectedArticle.getKolicina()));
            this.txtCijena.setText(String.valueOf(this.selectedArticle.getCijena()));
            this.txtDobavljaca.setText(String.valueOf(this.selectedArticle.getDobavljac()));
            this.dropKategorije.setText(String.valueOf(this.selectedArticle.getKategorija()));
            this.slikaArtikla.setImage(this.selectedArticle.getSlika());
        } catch (Exception ex){
            System.out.println("Pogreška pri ažuriranju!");
        }
    }
    @FXML
    public void actionRadioDodaj(ActionEvent evt) {
        this.txtNaziv.setText("");
        this.txtDobavljaca.setText("");
        this.txtCijena.setText("");
        this.slikaArtikla.setImage(null);
        this.dropKategorije.setText("");
        this.txtKolicina.setText("");

    }
    @FXML
    public void odaberiSliku(ActionEvent evt) {
        FileChooser fc = new FileChooser();
        fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg"));
        File datoteka = fc.showOpenDialog(null);
        if(datoteka != null) {
            Image binarnaSlika = new Image(datoteka.toURI().toString());
            this.slikaArtikla.setImage(binarnaSlika);
        } else
            System.out.println("Nije odabrana slika");
    }
    @FXML
    public void item1(ActionEvent evt) {
        System.out.println("Klikno si na item-1 u listi");
        this.dropKategorije.setText(topliNapitci.getText());
    }
    @FXML
    public void item2(ActionEvent evt) {
        System.out.println("Klikno si na item-2 u listi");
        this.dropKategorije.setText(alkPica.getText());
    }
    @FXML
    public void item3(ActionEvent evt) {
        System.out.println("Klikno si na item-3 u listi");
        this.dropKategorije.setText(gaziranaPica.getText());
    }
    @FXML
    public void item4(ActionEvent evt) {
        System.out.println("Klikno si na item-4 u listi");
        this.dropKategorije.setText(negaziranaPica.getText());
    }
    @FXML
    public void odabriKat(ActionEvent evt) {
        System.out.println("ODabirrrr"+dropKategorije.getId());
    }

    private void setLblAlert(Color color, String text) {
        lblAlert.setTextFill(color);
        lblAlert.setText(text);
    }

}
