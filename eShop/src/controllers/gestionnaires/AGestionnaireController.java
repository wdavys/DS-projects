package controllers.gestionnaires;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Gestionnaire;
import java.net.URL;
import java.util.ResourceBundle;

public class AGestionnaireController implements Initializable {

    @FXML private Button btnExit;
    @FXML private TextField nomGest;
    @FXML private TextField login;
    @FXML private TextField password;
    @FXML private ChoiceBox<String> typeGest;

    private VGestionnaireController vContextController;

    @FXML public void handleBtnExitClick() {
        ((Stage)(btnExit.getScene().getWindow())).close();
    }

    @FXML public void handleBtnConfirmClick() {
        String nomGest = this.nomGest.getText();
        String login = this.login.getText();
        String password = this.password.getText();
        int typeGest = this.typeGest.getValue().equals("Administrateur") ? 1 : 0;
        if (!nomGest.isEmpty()) {
            Gestionnaire newGest = new Gestionnaire(nomGest, typeGest, login, password, 0);
            newGest.save();
        }
        vContextController.gestionnaires = Gestionnaire.getAll();
        vContextController.refreshTable();
        handleBtnExitClick();
    }

    public void initialize(VGestionnaireController contextController) {
        vContextController = contextController;
        refreshDisplay();
    }

    private void refreshDisplay() {
        typeGest.getItems().addAll("Administrateur", "Utilisateur");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { }
}
