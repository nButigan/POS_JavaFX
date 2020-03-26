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

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class RadnikViewController implements Initializable {

    @FXML
    private Button btnNarudzba;

    @FXML
    private Button btnStorno;

    @FXML
    private Button btnOdjava;

    @FXML
    private void handleButtonClicks(javafx.event.ActionEvent mouseEvent) {

        if (mouseEvent.getSource() == btnNarudzba) {
            closeCurrentWindows(mouseEvent);
            loadStage("/sample/views/Narudzba.fxml");
        } else if (mouseEvent.getSource() == btnStorno) {
            closeCurrentWindows(mouseEvent);
            loadStage("/sample/views/Storno.fxml");
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

    private void loadStage(String fxml) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource(fxml));
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setMaximized(false);

            //stage.setTitle("POS");
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

    }
}
