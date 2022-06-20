package controllers.menu;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class MenuController implements Initializable {

    @FXML private Label loginGest, typeGest;
    @FXML private Button btnLogOut;

    @FXML public void handleBtnLogOutClick() {
        Session.logOut();
        ((Stage)(btnLogOut.getScene().getWindow())).close();

        try {
            Stage primaryStage = new Stage();
            VBox login = FXMLLoader.load(getClass().getResource("../../views/login/login.fxml"));
            primaryStage.setTitle("Connexion - Welcome ShopDB");
            primaryStage.setScene(new Scene(login, 600, 600));
            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        loginGest.setText(" " + Session.login + " ");
        typeGest.setText(" " + Session.typeUser + " ");
        Produit.initializeDB();
        Categorie.initializeDB();
        Gestionnaire.initializeDB();
        Photo.initializeDB();
        GestionStock.initializeDB();
    }
}
