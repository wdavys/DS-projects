package controllers.login;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import models.Gestionnaire;
import models.Session;
import models.StringsFr;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML private Button btnConnect;
    @FXML private Text errorText;
    @FXML public TextField pwd, log;

    @FXML private void hideText() {
        errorText.setVisible(false);
    }

    @FXML private void fire(KeyEvent e) {
        if (KeyCode.ENTER == e.getCode()) {
            btnConnect.fire();
        }
    }

    @FXML private void handleBtnConnectClick() {
        if (Gestionnaire.checkLogin(log.getText(), pwd.getText()) != null) {
            Session.logIn(Gestionnaire.checkLogin(log.getText(), pwd.getText()));
            if (Session.actif && Session.isloggedIn) {
                loadTabPane();
            }
            else if (!Session.actif) {
                errorText.setText(StringsFr.ISUNACTIF);
                errorText.setVisible(true);
            }
        }
        else {
            errorText.setText(StringsFr.RETRY);
            errorText.setVisible(true);
        }
    }
    private void loadTabPane() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/menu/menu.fxml"));
        Stage secondaryStage = new Stage();
        AnchorPane root;
        try {
            root = loader.load();
            secondaryStage.setScene(new Scene(root, 1280, 900));
            secondaryStage.setTitle("ShopDB");
            Parent[] interfaces = new Parent[3];
            interfaces[0] = FXMLLoader.load(getClass().getResource("../../views/categories/vCategorie1.fxml"));
            interfaces[1] = FXMLLoader.load(getClass().getResource("../../views/produits/vProduit1.fxml"));
            interfaces[2] = FXMLLoader.load(getClass().getResource("../../views/gestionnaires/vGestionnaire1.fxml"));

            for (int i=0; i<3; i++) {
                ((TabPane) root.getChildren().get(0)).getTabs().get(i).setContent(interfaces[i]);
            }

            if (Session.typeUser.equals(StringsFr.MAGASINIER)) {
                ((TabPane)root.getChildren().get(0)).getTabs().remove(2);
                ((TabPane)root.getChildren().get(0)).getTabs().remove(2);
            }

            else if (Session.typeUser.equals(StringsFr.CAISSIER)) {
                ((TabPane)root.getChildren().get(0)).getTabs().remove(0);
                ((TabPane)root.getChildren().get(0)).getTabs().remove(0);
                ((TabPane)root.getChildren().get(0)).getTabs().remove(0);
            }

            ((Stage)(btnConnect.getScene().getWindow())).close();
            secondaryStage.show();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pwd.setOnKeyPressed(this::fire);
    }
}
