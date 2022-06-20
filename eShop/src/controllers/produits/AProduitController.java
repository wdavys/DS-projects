package controllers.produits;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import models.Categorie;
import models.Photo;
import models.Produit;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AProduitController implements Initializable {

    @FXML private Pane photo;
    @FXML private Button btnExit;
    @FXML private ImageView imgProduit;
    @FXML private Text slideStatus;
    @FXML private TextField nomPro;
    @FXML private TextField qte;
    @FXML private TextField prix;
    @FXML private TextArea description;
    @FXML private ChoiceBox<String> idCategorie;
    @FXML private TextField codeFour;
    private VProduitController vContextController;
    private int currentImgIndex = 1;
    private ArrayList<String> imgUrls = new ArrayList<>();

    @FXML public void handlePaneHover() {
        if (imgUrls.size() > 0) {
            photo.getChildren().get(1).setVisible(true);
            photo.getChildren().get(2).setVisible(true);
            photo.getChildren().get(3).setVisible(true);
        }
    }

    @FXML public void handlePaneOut() {
        photo.getChildren().get(1).setVisible(false);
        photo.getChildren().get(2).setVisible(false);
        photo.getChildren().get(3).setVisible(false);
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

    @FXML public void handleBtnExitClick() {
        ((Stage)(btnExit.getScene().getWindow())).close();
    }

    @FXML public void handleBtnConfirmClick() {
        if (!nomPro.getText().isEmpty()) {
            Produit vProduit = new Produit(nomPro.getText());
            vProduit.setCodeFour(codeFour.getText());
            vProduit.setIdCategorie(Categorie.findIdCat(idCategorie.getValue()));
            vProduit.setQte(Integer.valueOf(qte.getText()));
            vProduit.setDescription(description.getText());
            vProduit.setPrix(Float.valueOf(prix.getText()));
            vProduit.setActif(1);
            vProduit.save();
            int tmp = vProduit.fetchCodePro();
            for (String s : imgUrls) {
                Photo ph = new Photo(s);
                ph.setCodePro(tmp);
                ph.save();
            }
        }
        vContextController.produits = Produit.getAll();
        vContextController.refreshTable();
        handleBtnExitClick();
    }

    public void initialize(VProduitController contextController) {
        vContextController = contextController;
        refreshDisplay();
    }

    private void refreshDisplay() {
        idCategorie.getItems().addAll(Categorie.getAllNomCat());
    }

    @FXML public void ajouter() {
        FileChooser choose = new FileChooser();
        choose.getExtensionFilters().add(new ExtensionFilter("Images","*.png","*.jpg","*.jpeg"));
        File image = choose.showOpenDialog(new Stage());
        try {
            if (image != null) {
                imgUrls.add("file:"+image.getPath());
                //System.out.println(image.getPath());
                currentImgIndex = imgUrls.size();
                displayCurrent();
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML public void modification() {
        FileChooser choose = new FileChooser();
        choose.getExtensionFilters().add(new ExtensionFilter("Images","*.png","*.jpg","*.jpeg"));
        File image = choose.showOpenDialog(new Stage());
        try {
            if (image != null) {
                imgProduit.setImage(new Image(new FileInputStream(image.getPath())));
                imgUrls.set(currentImgIndex - 1, "file:"+image.getPath());
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML void delCurrentImage() {
        if (imgUrls.size() > 0) {
            imgUrls.remove(currentImgIndex - 1);
            if (imgUrls.size() > 0) {
                if (currentImgIndex > 1)
                    currentImgIndex--;
                displayCurrent();
            }
            else {
                imgProduit.setImage(null);
                slideStatus.setText("Pas d'images");
            }
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) { }
}
