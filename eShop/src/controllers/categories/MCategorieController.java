package controllers.categories;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Categorie;
import java.net.URL;
import java.util.ResourceBundle;

public class MCategorieController implements Initializable {

    @FXML private Button btnExit;
    @FXML private TextField nomCat;
    @FXML private Label idCat;
    private Categorie pCategorie;
    private PCategorieController pContextController;

    @FXML public void handleBtnExitClick() {
        ((Stage)(btnExit.getScene().getWindow())).close();
    }

    @FXML public void handleBtnConfirm() {
        pCategorie.setNomCat(nomCat.getText());
        pCategorie.update();
        pContextController.refreshDisplay();
        handleBtnExitClick();
    }

    public void initialize(Categorie categorie, PCategorieController contextController) {
        pCategorie = categorie;
        pContextController = contextController;
        refreshDisplay();
    }

    private void refreshDisplay() {
        nomCat.setText(pCategorie.getNomCat());
        idCat.setText("ID Catégorie : " + String.valueOf(pCategorie.getIdCat()));
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { }
}
