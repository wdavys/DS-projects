package controllers.categories;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import models.Categorie;
import models.Delta;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VCategorieController implements Initializable {

    @FXML private Pagination pagination;
    @FXML private Button btnAdd;
    @FXML private ToggleGroup groupFiltres;
    @FXML private TextField searchBox;
    private final Delta dragDelta = new Delta();
    public ArrayList<Categorie> categories = null;
    private int lastIndex, displace;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categories = Categorie.getAll();
        btnAdd.setOnAction(event -> showAddCategorieDialog());
        refreshTable();
    }

    public void refreshTable() {
        displace = categories.size() % rowsPerPage();
        if (displace > 0) {
            lastIndex = categories.size() / rowsPerPage();
        }
        else {
            lastIndex = categories.size() / rowsPerPage() - 1;
        }

        pagination.setPageCount(lastIndex + 1);
        pagination.setPageFactory(this::createPage);
    }

    private int rowsPerPage() {
        return 10;
    }

    private VBox createPage(int pageIndex) {
        final int COL_WIDTH = 300;

        VBox box = new VBox(5);
        TableView<Categorie> table = new TableView<>();
        TableColumn<Categorie, Integer> col1 = new TableColumn<>("ID ");
        col1.setCellValueFactory(new PropertyValueFactory<>("idCat"));
        col1.setMaxWidth(COL_WIDTH);

        TableColumn<Categorie, String> col2 = new TableColumn<>("Nom ");
        col2.setCellValueFactory(new PropertyValueFactory<>("nomCat"));
        col2.setMaxWidth(COL_WIDTH);

        table.getColumns().addAll(col1, col2);
        if (pageIndex == lastIndex && displace > 0) {
            table.setItems(FXCollections.observableArrayList(categories.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace)));
        }
        else if (pageIndex < lastIndex || displace == 0) {
            table.setItems(FXCollections.observableArrayList(categories.subList(pageIndex * rowsPerPage(), (pageIndex+1) * rowsPerPage())));
        }

        table.setPadding(new Insets(10));
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showCategorieDetails(newValue));

        box.getChildren().add(table);
        return box;
    }

    @FXML private void refreshResults() {
        ToggleButton filtre = (ToggleButton)groupFiltres.getSelectedToggle();
        if (filtre != null) {
            String pattern = searchBox.getText();
            if (!pattern.isEmpty())
                categories = Categorie.find(pattern);
            else
                categories = Categorie.getAll();
            refreshTable();
        }
    }

    private void showCategorieDetails(Categorie categorie) {
        Stage modal = new Stage(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/categories/pCategorie.fxml"));
        Parent presentation;
        try {
            presentation = loader.load();
            Scene scene = new Scene(presentation);
            scene.setFill(Color.TRANSPARENT);
            modal.setScene(scene);
            modal.show();

            PCategorieController controller = loader.getController();
            controller.initialize(categorie, this);

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

    private void showAddCategorieDialog() {
        Stage modal = new Stage(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/categories/aCategorie.fxml"));
        Parent presentation;
        try {
            presentation = loader.load();
            Scene scene = new Scene(presentation);
            scene.setFill(Color.TRANSPARENT);
            modal.setScene(scene);
            modal.show();

            ACategorieController controller = loader.getController();
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
