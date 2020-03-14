package sample.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminViewController implements Initializable {
    @FXML
    private Button btnNarudzba;

    @FXML
    private Button btnStorno;

    @FXML
    private Button btnSkladiste;

    @FXML
    private Button btnPoslovanje;

    @FXML
    private Button btnUpravljanjeOsobljem;

    @FXML
    private Button btnOdjava;


    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) {
        if (mouseEvent.getSource() == btnNarudzba) {
            closeCurrentWindows(mouseEvent);
            loadStage("/sample/views/Item.fxml");
        } else if (mouseEvent.getSource() == btnStorno) {
            closeCurrentWindows(mouseEvent);
            loadStage("/sample/views/Storno.fxml");
        } else if (mouseEvent.getSource() == btnSkladiste) {
            closeCurrentWindows(mouseEvent);
            loadStage("/sample/views/Skladiste.fxml");
        } else if (mouseEvent.getSource() == btnPoslovanje) {
            closeCurrentWindows(mouseEvent);
            loadStage("/sample/views/Poslovanje.fxml");
        } else if (mouseEvent.getSource() == btnUpravljanjeOsobljem) {
            closeCurrentWindows(mouseEvent);
            loadStage("/sample/views/UpravljanjeOsobljem.fxml");
        } else {
            closeCurrentWindows(mouseEvent);
            loadStage("/sample/views/Login.fxml");
        }
    }

    private void closeCurrentWindows(javafx.event.ActionEvent mouseEvent){
        Node node = (Node) mouseEvent.getSource();
        Stage stage = (Stage) node.getScene().getWindow();
        stage.setMaximized(false);
        stage.close();
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

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
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
