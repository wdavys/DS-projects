package controllers.categories;

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
import models.Categorie;
import models.Delta;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PCategorieController implements Initializable {

    @FXML private Button btnExit;
    @FXML private Label nomCat;
    @FXML private Text idCat;
    private final Delta dragDelta = new Delta();
    private Categorie vCategorie;
    private VCategorieController vContextController;

    @FXML public void handleBtnExitClick() {
        vContextController.refreshTable();
        ((Stage)(btnExit.getScene().getWindow())).close();
    }

    @FXML public void handleBtnModifyClick() {
        showModificationDialog();
    }

    private void showModificationDialog() {
        Stage modal = new Stage(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/categories/mCategorie.fxml"));
        Parent modification;
        try {
            modification = loader.load();
            Scene scene = new Scene(modification);
            scene.setFill(Color.TRANSPARENT);
            modal.setScene(scene);
            modal.show();

            MCategorieController controller = loader.getController();
            controller.initialize(vCategorie,this);

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

    public void initialize(Categorie categorie, VCategorieController contextController) {
        vCategorie = categorie;
        vContextController = contextController;
        refreshDisplay();
    }

    public void refreshDisplay() {
        idCat.setText("ID Catégorie - " + String.valueOf(vCategorie.getIdCat()));
        nomCat.setText(vCategorie.getNomCat());
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { }
}
