package controllers.gestionnaires;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import models.Gestionnaire;
import java.net.URL;
import java.util.ResourceBundle;

public class MGestionnaireController implements Initializable {

    @FXML private Button btnExit;
    @FXML private Label idGest;
    @FXML private TextField nomGest;
    @FXML private TextField login;
    @FXML private TextField password;
    @FXML private ChoiceBox<String> typeGest;
    @FXML private CheckBox actif;
    private Gestionnaire pGestionnaire;
    private PGestionnaireController pContextController;

    @FXML public void handleBtnExitClick() {
        ((Stage)(btnExit.getScene().getWindow())).close();
    }

    @FXML public void handleBtnConfirmClick() {
        pGestionnaire.setNomGest(nomGest.getText());
        pGestionnaire.setLogin(login.getText());
        pGestionnaire.setPassword(password.getText());
        int actif = this.actif.isSelected() ? 1 : 0;
        pGestionnaire.setActif(actif);
        int typeGest = this.typeGest.getValue().equals("Administrateur") ? 1 : 0;
        pGestionnaire.setTypeGest(typeGest);
        pGestionnaire.update();
        pContextController.refreshDisplay();
        handleBtnExitClick();
    }

    public void initialize(Gestionnaire gestionnaire, PGestionnaireController contextController) {
        pGestionnaire = gestionnaire;
        pContextController = contextController;
        refreshDisplay();
    }

    private void refreshDisplay() {
        idGest.setText("ID Gestionnaire : " + String.valueOf(pGestionnaire.getIdGest()));
        nomGest.setText(pGestionnaire.getNomGest());
        typeGest.getItems().addAll("Administrateur", "Utilisateur");
        typeGest.setValue(pGestionnaire.getTypeGest());
        login.setText(pGestionnaire.getLogin());
        password.setText(pGestionnaire.getPassword());
        if (pGestionnaire.getActif() == 1) {
            actif.setSelected(true);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { }
}
