package controllers.produits;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Delta;
import models.Produit;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VProduitController implements Initializable {

    @FXML private Pagination pagination;
    @FXML private Button btnAdd;
    @FXML private ToggleGroup groupFiltres;
    @FXML private TextField searchBox;
    private final Delta dragDelta = new Delta();
    public ArrayList<Produit> produits = null;
    private int lastIndex, displace;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        produits = Produit.getAll();
        //System.out.println(produits.size());
        btnAdd.setOnAction(event -> showAddProduitDialog());
        refreshTable();
    }

    public void refreshTable() {
        displace = produits.size() % rowsPerPage();
        if (displace > 0) {
            lastIndex = produits.size() / rowsPerPage();
        } else {
            lastIndex = produits.size() / rowsPerPage() - 1;
        }

        pagination.setPageCount(lastIndex + 1);
        pagination.setPageFactory(this::createPage);
    }

    private int rowsPerPage() {
        return 11;
    }

    private VBox createPage(int pageIndex) {
        final int COL_WIDTH = 300;
        VBox box = new VBox(5);

        TableView<Produit> table = new TableView<>();
        TableColumn<Produit, Integer> col1 = new TableColumn<>("Code ");
        col1.setCellValueFactory(new PropertyValueFactory<>("codePro"));
        col1.setMaxWidth(COL_WIDTH);

        TableColumn<Produit, String> col2 = new TableColumn<>("Nom ");
        col2.setCellValueFactory(new PropertyValueFactory<>("nomPro"));
        col2.setMaxWidth(COL_WIDTH);

        TableColumn<Produit, Float> col3 = new TableColumn<>("Prix ");
        col3.setCellValueFactory(new PropertyValueFactory<>("prix"));
        col3.setMaxWidth(COL_WIDTH);

        TableColumn<Produit, String> col4 = new TableColumn<>("Fournisseur ");
        col4.setCellValueFactory(new PropertyValueFactory<>("codeFour"));
        col4.setMaxWidth(COL_WIDTH);

        TableColumn<Produit, String> col5 = new TableColumn<>("Catégorie ");
        col5.setCellValueFactory(new PropertyValueFactory<>("categorie"));
        col5.setMaxWidth(COL_WIDTH);

        table.getColumns().addAll(col1, col2, col3, col4, col5);
        if (pageIndex == lastIndex && displace > 0) {
            table.setItems(FXCollections.observableArrayList(produits.subList(pageIndex * rowsPerPage(), (pageIndex) * rowsPerPage() + displace)));
        }
        else if (pageIndex < lastIndex || displace == 0) {
            table.setItems(FXCollections.observableArrayList(produits.subList(pageIndex * rowsPerPage(), (pageIndex+1) * rowsPerPage())));
        }

        table.setPadding(new Insets(10));
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showProduitDetails(newValue));

        box.getChildren().add(table);
        return box;
    }

    @FXML private void refreshResults() {
        ToggleButton filtre = (ToggleButton)groupFiltres.getSelectedToggle();
        if (filtre != null && filtre.getText().equals("Nom")) {
            String pattern = searchBox.getText();
            if (!pattern.isEmpty())
                produits = Produit.find(pattern);
            else
                produits = Produit.getAll();
            refreshTable();
        }
    }

    private void showProduitDetails(Produit produit) {
        Stage modal = new Stage(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/produits/pProduit.fxml"));
        Parent presentation;
        try {
            presentation = loader.load();
            Scene scene = new Scene(presentation);
            scene.setFill(Color.TRANSPARENT);
            modal.setScene(scene);
            modal.show();

            PProduitController controller = loader.getController();
            controller.initialize(produit, this);

            presentation.setOnMousePressed(ev -> {
                dragDelta.setX(modal.getX() - ev.getScreenX());
                dragDelta.setY(modal.getY() - ev.getScreenY());
            });

            presentation.setOnMouseDragged(ev -> {
                modal.setX(ev.getScreenX() + dragDelta.getX());
                modal.setY(ev.getScreenY() + dragDelta.getY());
            });

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private void showAddProduitDialog() {
        Stage modal = new Stage(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/produits/aProduit.fxml"));
        Parent presentation;
        try {
            presentation = loader.load();
            Scene scene = new Scene(presentation);
            scene.setFill(Color.TRANSPARENT);
            modal.setScene(scene);
            modal.show();

            AProduitController controller = loader.getController();
            controller.initialize(this);

            presentation.setOnMousePressed(ev -> {
                dragDelta.setX(modal.getX() - ev.getScreenX());
                dragDelta.setY(modal.getY() - ev.getScreenY());
            });

            presentation.setOnMouseDragged(ev -> {
                modal.setX(ev.getScreenX() + dragDelta.getX());
                modal.setY(ev.getScreenY() + dragDelta.getY());
            });

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }
}
