package sample.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import sample.models.Artikl;
import sample.models.Item;
import sample.models.Korisnik;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class NarudzbaController implements Initializable {

    @FXML
    private Button btnNajcesciArtikli;

    @FXML
    private Button btnTopliNapitci;

    @FXML
    private Button btnGaziranaPica;

    @FXML
    private Button btnNegaziranaPica;

    @FXML
    private Button btnAlkoholnaPica;

    @FXML
    private GridPane GPArtikliTablica;

    @FXML
    private Button btnMinus;

    @FXML
    private Button btnPlus;

    @FXML
    private Button btnIzbrisi;

    @FXML
    private Button btnIspisi;

    @FXML
    private Button btnPrebaci;

    @FXML
    private Button btnPovratak;

    @FXML
    private TextField txtUkupnoZaPlatit;

    @FXML
    private TextField txtKolicina;

    @FXML
    private TableView<Item> TVRacun;

    @FXML
    private TableColumn colArtikl;

    @FXML
    private TableColumn colKolicina;

    @FXML
    private TableColumn colCijena;

    @FXML
    private MenuButton DropNarudzbe;

    @FXML
    private Label lblImeKorisnika;

    @FXML
    private Label lblUlogaKorisnika;

    private ArrayList<Artikl> odabraniProizvodi = new ArrayList<Artikl>();
    private ObservableList<Item> dodaniProizvodi = FXCollections.observableArrayList();
    Map<Integer, Item> map = new HashMap<>();
    float zaPlatit=0;
    String name;
    static Korisnik logiraniKorisnik;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        this.lblImeKorisnika.setText(logiraniKorisnik.getKorisnickoIme());
        if(logiraniKorisnik.getUloga() == 1) {
            this.lblUlogaKorisnika.setText("Administrator");
        } else {
            this.lblUlogaKorisnika.setText("Konobar");
        }
    }


    public void handleButtonAction(MouseEvent event) {
        if (event.getSource() == btnNajcesciArtikli) {

        }

        else if (event.getSource() == btnTopliNapitci) {
            prikazArtikala("Topli napitci");
        }

        else if (event.getSource() == btnGaziranaPica) {
            prikazArtikala("Gazirana pica");
        }

        else if (event.getSource() == btnNegaziranaPica) {
            prikazArtikala("Negazirana pica");
        }

       else if (event.getSource() == btnAlkoholnaPica) {
            prikazArtikala("Alkoholna pica");
        }

        else if (event.getSource() == btnMinus) {
            int kolicina = Integer.parseInt(txtKolicina.getText());
            kolicina -= 1;
            txtKolicina.setText(String.valueOf(kolicina));

        }

        else if (event.getSource() == btnPlus) {
            int kolicina = Integer.parseInt(txtKolicina.getText());
            kolicina += 1;
            txtKolicina.setText(String.valueOf(kolicina));
        }

        else if (event.getSource() == btnIzbrisi) {

        }

        else if (event.getSource() == btnIspisi) {

        }

        else if (event.getSource() == btnPrebaci) {

        }

        else if (event.getSource() == btnPovratak) {
            try {
                Node node = (Node) event.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setMaximized(false);
                stage.close();
                Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/sample/views/AdminView.fxml")));
                stage.setScene(scene);
                stage.show();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }

    }

    public  void prikazArtikala(String kategorija){
        this.colArtikl.setCellValueFactory(new PropertyValueFactory<>("name"));
        this.colKolicina.setCellValueFactory(new PropertyValueFactory<>("quantity"));
        this.colCijena.setCellValueFactory(new PropertyValueFactory<>("price"));

        ObservableList<Artikl> articles = (ObservableList<Artikl>) Artikl.readByCategory(kategorija);
        int i = 0;
        int j = 0;
        for (Artikl a : articles) {
            Button btn = new Button();
            ImageView iw = new ImageView(a.getSlika());
            iw.setFitHeight(128);
            iw.setFitWidth(128);
            btn.setGraphic(iw);
            btn.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    odabraniProizvodi.add(a);

                    Item i1 = new Item(a.getNaziv(), 1, a.getCijena());
                    if (map.get(a.getId()) != null) {
                        i1 = map.get(a.getId());
                        i1.setQuantity(i1.getQuantity() + 1);
                        i1.setPrice(i1.getQuantity()*a.getCijena());
                    }
                    System.out.println(a.getId());
                    dodaniProizvodi.remove(i1);
                    dodaniProizvodi.add(i1);
                    zaPlatit = zaPlatit + a.getCijena();
                    map.put(a.getId(), i1);
                    TVRacun.setItems(dodaniProizvodi);
                    TVRacun.refresh();
                    txtUkupnoZaPlatit.setText(String.valueOf(zaPlatit));
                    TVRacun.setOnMousePressed(new EventHandler<MouseEvent>() {
                        @Override
                        public void handle(MouseEvent mouseEvent) {
                            txtKolicina.setText(String.valueOf(TVRacun.getSelectionModel().getSelectedItem().getQuantity()));
                        }
                    });
                }
            });
            GPArtikliTablica.getChildren().removeAll();
            GPArtikliTablica.add(btn, i, j);
            i++;
            if (i >= 4) {
                i = 0;
                j++;
            }
        }
    }

}
