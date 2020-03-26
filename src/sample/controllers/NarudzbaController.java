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
import sample.models.*;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;

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
    int myIndex=0;
    static Korisnik logiraniKorisnik;
    Item i1;
    static float cijena;




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

        else if (event.getSource() == btnIspisi) {
            int dodaniProizvodiSize = dodaniProizvodi.size();
            Date date = new Date(System.currentTimeMillis());
            int idKorisnik = logiraniKorisnik.getID();
            Racun racun = new Racun(date,idKorisnik);
            Racun.add(racun);
            for(int i=0; i < dodaniProizvodiSize; i++) {
                String nazivArtikla = dodaniProizvodi.get(i).getName();
                ArtiklRacun artiklRacun = new ArtiklRacun(0, racun.getId(),ArtiklRacun.getArtiklId(nazivArtikla),dodaniProizvodi.get(i).getQuantity());
                ArtiklRacun.add(artiklRacun);
            }

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

        else if (event.getSource() == btnPrebaci) {

        }

        else if (event.getSource() == btnPovratak) {
            if(logiraniKorisnik.getUloga() == 1)
            {
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
            } else {
                try {
                    Node node = (Node) event.getSource();
                    Stage stage = (Stage) node.getScene().getWindow();
                    stage.setMaximized(false);
                    stage.close();
                    Scene scene = new Scene(FXMLLoader.load(getClass().getResource("/sample/views/RadnikView.fxml")));
                    stage.setScene(scene);
                    stage.show();

                } catch (IOException ex) {
                    System.err.println(ex.getMessage());
                }
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
            if (a.getKolicina() >= 0) {
                Button btn = new Button();
                ImageView iw = new ImageView(a.getSlika());
                iw.setFitHeight(128);
                iw.setFitWidth(128);
                btn.setGraphic(iw);
                btn.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        odabraniProizvodi.add(a);

                        /*Item*/
                        i1 = new Item(a.getNaziv(), 1, a.getCijena());
                        if (map.get(a.getId()) != null) {
                            i1 = map.get(a.getId());
                            i1.setQuantity(i1.getQuantity() + 1);
                            i1.setPrice(i1.getQuantity() * a.getCijena());
                        }
                        //System.out.println(a.getId());
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

                                myIndex = TVRacun.getSelectionModel().getSelectedIndex();
                                String myRowData = TVRacun.getItems().get(myIndex).getName();
                                cijena = dodaniProizvodi.get(myIndex).getPrice() / dodaniProizvodi.get(myIndex).getQuantity();

                            }
                        });
                        btnPlus.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {

                                dodaniProizvodi.get(myIndex).setQuantity(TVRacun.getSelectionModel().getSelectedItem().getQuantity() + 1); //povecanje kolicine
                                dodaniProizvodi.get(myIndex).setPrice(Float.valueOf(dodaniProizvodi.get(myIndex).getQuantity()) * cijena);

                                TVRacun.refresh();
                                zaPlatit = zaPlatit + cijena;
                                txtUkupnoZaPlatit.setText(String.valueOf(zaPlatit));
                            }
                        });

                        btnMinus.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {

                                dodaniProizvodi.get(myIndex).setQuantity(TVRacun.getSelectionModel().getSelectedItem().getQuantity() - 1); //smanjenje kolicine
                                dodaniProizvodi.get(myIndex).setPrice(Float.valueOf(dodaniProizvodi.get(myIndex).getQuantity()) * cijena);
                                if (txtKolicina.getText() == "0" || dodaniProizvodi.get(myIndex).getQuantity() == 0) {
                                    dodaniProizvodi.remove(dodaniProizvodi.get(myIndex));
                                }

                                TVRacun.refresh();

                                zaPlatit = zaPlatit - cijena;
                                txtUkupnoZaPlatit.setText(String.valueOf(zaPlatit));
                            }
                        });

                        btnIzbrisi.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                dodaniProizvodi.remove(i1);
                                map.remove(a.getId());

                                TVRacun.refresh();
                                zaPlatit -= i1.getPrice();
                                txtUkupnoZaPlatit.setText(String.valueOf(zaPlatit));
                            }
                        });

                        btnIspisi.setOnAction(new EventHandler<ActionEvent>() {
                            @Override
                            public void handle(ActionEvent actionEvent) {
                                ObservableList<Artikl> art = (ObservableList<Artikl>) Artikl.readAll();
                                for (Artikl ab : art) {

                                    for (Item i1 : dodaniProizvodi) {

                                        if (ab.getNaziv().equalsIgnoreCase(i1.getName())) {

                                            int kol = ab.getKolicina() - i1.getQuantity(); //Umanjena kolicina za otkucanu kolicinu

                                            Artikl artikl = new Artikl(ab.getNaziv(), kol); //Update kolicine
                                            artikl.updateK(artikl);
                                        }

                                    }
                                }
                            }
                        });


                    }
                });
                GPArtikliTablica.add(btn, i, j);
                i++;
                if (i >= 4) {
                    i = 0;
                    j++;
                }
            }
        }
    }

}