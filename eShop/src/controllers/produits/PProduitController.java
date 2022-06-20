package controllers.produits;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Delta;
import models.Photo;
import models.Produit;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class PProduitController implements Initializable {

    @FXML private Button btnExit;
    @FXML private ImageView imgProduit;
    @FXML private Text slideStatus;
    @FXML private Text codePro;
    @FXML private Label nomPro;
    @FXML private Label prix;
    @FXML private Label qte;
    @FXML private Label description;
    @FXML private Label codeFour;
    @FXML private Label actif;
    @FXML private Label idCategorie;
    public ArrayList<Photo> photos = null;
    public ArrayList<String> imgUrls;
    private int currentImgIndex;
    private final Delta dragDelta = new Delta();
    private Produit vProduit;
    private VProduitController vContextController;

    @FXML public void handleBtnExitClick() {
        vContextController.refreshTable();
        ((Stage)(btnExit.getScene().getWindow())).close();
    }

    @FXML public void handleBtnModifyClick() {
        showModificationDialog();
    }

    private void showModificationDialog() {
        Stage modal = new Stage(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/produits/mProduit.fxml"));
        Parent modification;
        try {
            modification = loader.load();
            Scene scene = new Scene(modification);
            scene.setFill(Color.TRANSPARENT);
            modal.setScene(scene);
            modal.show();

            MProduitController controller = loader.getController();
            controller.initialize(vProduit,this);

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

    public void initialize(Produit produit, VProduitController contextController) {
        vProduit = produit;
        vContextController = contextController;
        refreshDisplay();
    }

    public void refreshDisplay() {
        codePro.setText("Code Produit - " + String.valueOf(vProduit.getCodePro()));
        nomPro.setText(vProduit.getNomPro());
        qte.setText(String.valueOf(vProduit.getQte()));
        prix.setText(String.valueOf(vProduit.getPrix()));
        idCategorie.setText(vProduit.getCategorie());
        codeFour.setText(vProduit.getCodeFour());
        description.setText(vProduit.getDescription());
        String val = vProduit.getActif() == 0 ? "Non" : "Oui";
        actif.setText(val);

        photos = Photo.getAll(vProduit.getCodePro());
        imgUrls = Photo.getAllLien(vProduit.getCodePro());
        if (imgUrls.size() > 0) {
            currentImgIndex = 1;
            displayCurrent();
        }
    }

    @FXML private void displayPrevious() {
        if (currentImgIndex > 1) {
            currentImgIndex--;
            imgProduit.setImage(new Image(String.valueOf(new File(imgUrls.get(currentImgIndex - 1)))));
            slideStatus.setText(currentImgIndex + " sur " + String.valueOf(imgUrls.size()));
        }
    }

    @FXML private void displayNext() {
        if (currentImgIndex < imgUrls.size()) {
            currentImgIndex++;
            imgProduit.setImage(new Image(String.valueOf(new File(imgUrls.get(currentImgIndex - 1)))));
            //System.out.println(String.valueOf(new File(imgUrls.get(currentImgIndex - 1))));
            slideStatus.setText(currentImgIndex + " sur " + String.valueOf(imgUrls.size()));
        }
    }

    @FXML private void displayCurrent() {
        imgProduit.setImage(new Image(String.valueOf(new File(imgUrls.get(currentImgIndex - 1)))));
        //System.out.println(String.valueOf(new File(imgUrls.get(currentImgIndex - 1))));
        slideStatus.setText(currentImgIndex + " sur " + String.valueOf(imgUrls.size()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) { }
}
