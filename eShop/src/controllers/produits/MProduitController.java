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

public class MProduitController implements Initializable {

    @FXML private Pane photo;
    @FXML private Button btnExit;
    @FXML private ImageView imgProduit;
    @FXML private Text slideStatus;
    @FXML private Label codePro;
    @FXML private TextField nomPro;
    @FXML private TextField qte;
    @FXML private TextField prix;
    @FXML private TextArea description;
    @FXML private ChoiceBox<String> idCategorie;
    @FXML private TextField codeFour;
    @FXML private CheckBox actif;
    @FXML private ToggleGroup groupQte;
    private Produit pProduit;
    private PProduitController pContextController;
    private int currentImgIndex;
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
        pProduit.setNomPro(nomPro.getText());
        pProduit.setCodeFour(codeFour.getText());
        if (((RadioButton)groupQte.getSelectedToggle()).getText().equals("Ajouter")) {
            pProduit.setQte(pProduit.getQte() + Integer.valueOf(qte.getText()));
        }
        else {
            pProduit.setQte(pProduit.getQte() - Integer.valueOf(qte.getText()));
        }

        pProduit.setDescription(description.getText());
        pProduit.setPrix(Float.valueOf(prix.getText()));
        int actif = this.actif.isSelected() ? 1 : 0;
        pProduit.setActif(actif);
        pProduit.setIdCategorie(Categorie.findIdCat(idCategorie.getValue()));
        pProduit.setCategorie(idCategorie.getValue());
        pProduit.update();
        for (String s : imgUrls) {
            Photo ph = new Photo(s);
            ph.setCodePro(pProduit.getCodePro());
            ph.save();
        }
        pContextController.refreshDisplay();
        handleBtnExitClick();
    }

    public void initialize(Produit produit, PProduitController contextController) {
        pProduit = produit;
        pContextController = contextController;
        refreshDisplay();
    }

    private void refreshDisplay() {
        codePro.setText("Code Produit : " + String.valueOf(pProduit.getCodePro()));
        nomPro.setText(pProduit.getNomPro());
        qte.setText(String.valueOf(pProduit.getQte()));
        prix.setText(String.valueOf(pProduit.getPrix()));
        description.setText(pProduit.getDescription());
        codeFour.setText(pProduit.getCodeFour());
        idCategorie.getItems().addAll(Categorie.getAllNomCat());
        idCategorie.getSelectionModel().select(pProduit.getCategorie());
        if (pProduit.getActif() == 1) {
            actif.setSelected(true);
        }

        imgUrls = pContextController.imgUrls;
        if (imgUrls.size() > 0) {
            currentImgIndex = 1;
            displayCurrent();
        }
        for (Photo p : pContextController.photos) {
            p.delete();
        }
    }

    @FXML public void ajouter() {
        FileChooser choose = new FileChooser();
        choose.getExtensionFilters().add(new ExtensionFilter("Images","*.png","*.jpg","*.jpeg"));
        File image = choose.showOpenDialog(new Stage());
        try {
            imgUrls.add("file:"+image.getPath());
            System.out.println(image.getPath());
            currentImgIndex = imgUrls.size();
            displayCurrent();
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
            imgProduit.setImage(new Image(new FileInputStream(image.getPath())));
            imgUrls.set(currentImgIndex - 1, "file:"+image.getPath());
            for (String s : imgUrls) {
                System.out.println(s);
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @FXML void delCurrentImage(){
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

