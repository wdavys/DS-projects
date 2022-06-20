package controllers.categories;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Categorie;
import java.net.URL;
import java.util.ResourceBundle;

public class ACategorieController implements Initializable {

    @FXML private Button btnExit;
    @FXML private TextField nomCat;
    private VCategorieController vContextController;

    @FXML public void handleBtnExitClick() {
        ((Stage)(btnExit.getScene().getWindow())).close();
    }

    @FXML public void handleBtnConfirmClick() {
        String nomCat = this.nomCat.getText();
        if (!nomCat.isEmpty()) {
            Categorie newCat = new Categorie(nomCat);
            newCat.save();
        }
        vContextController.categories = Categorie.getAll();
        vContextController.refreshTable();
        handleBtnExitClick();
    }

    public void initialize(VCategorieController contextController) {
        vContextController = contextController;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { }
}
