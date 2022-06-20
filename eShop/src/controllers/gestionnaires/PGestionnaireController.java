package controllers.gestionnaires;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Delta;
import models.Gestionnaire;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PGestionnaireController implements Initializable {

    @FXML private Button btnExit;
    @FXML private Text idGest;
    @FXML private Label nomGest;
    @FXML private Label login;
    @FXML private Label password;
    @FXML private Label typeGest;
    @FXML private Label actif;
    private final Delta dragDelta = new Delta();
    private Gestionnaire vGestionnaire;
    private VGestionnaireController vContextController;

    @FXML public void handleBtnExitClick() {
        vContextController.refreshTable();
        ((Stage)(btnExit.getScene().getWindow())).close();
    }

    @FXML public void handleBtnModifyClick() {
        showModificationDialog();
    }

    private void showModificationDialog() {
        Stage modal = new Stage(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/gestionnaires/mGestionnaire.fxml"));
        Parent modification;
        try {
            modification = loader.load();
            Scene scene = new Scene(modification);
            scene.setFill(Color.TRANSPARENT);
            modal.setScene(scene);
            modal.show();

            MGestionnaireController controller = loader.getController();
            controller.initialize(vGestionnaire,this);

            modification.setOnMousePressed(ev -> {
                dragDelta.setX(modal.getX() - ev.getScreenX());
                dragDelta.setY(modal.getY() - ev.getScreenY());
            });

            modification.setOnMouseDragged(ev -> {
                modal.setX(ev.getScreenX() + dragDelta.getX());
                modal.setY(ev.getScreenY() + dragDelta.getY());
            });

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void initialize(Gestionnaire gestionnaire, VGestionnaireController contextController) {
        vGestionnaire = gestionnaire;
        vContextController = contextController;
        refreshDisplay();
    }

    public void refreshDisplay() {
        idGest.setText("ID Gestionnaire - " + String.valueOf(vGestionnaire.getIdGest()));
        nomGest.setText(vGestionnaire.getNomGest());
        login.setText(vGestionnaire.getLogin());
        password.setText(vGestionnaire.getPassword());
        String val = vGestionnaire.getActif() == 0 ? "Non" : "Oui";
        actif.setText(val);
        typeGest.setText(vGestionnaire.getTypeGest());
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { }
}
