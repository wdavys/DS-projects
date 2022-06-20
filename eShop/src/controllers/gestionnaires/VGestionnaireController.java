package controllers.gestionnaires;

import javafx.scene.Parent;
import models.Delta;
import models.Gestionnaire;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class VGestionnaireController implements Initializable {

    @FXML private Pagination pagination;
    @FXML private Button btnAdd;
    @FXML private ToggleGroup groupFiltres;
    @FXML private TextField searchBox;
    private final Delta dragDelta = new Delta();
    public ArrayList<Gestionnaire> gestionnaires = null;
    private int lastIndex, displace;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gestionnaires = Gestionnaire.getAll();
        btnAdd.setOnAction(event -> showAddGestionnaireDialog());
        refreshTable();
    }

    public void refreshTable() {
        displace = gestionnaires.size() % rowsPerPage();
        if (displace > 0) {
            lastIndex = gestionnaires.size() / rowsPerPage();
        }
        else {
            lastIndex = gestionnaires.size() / rowsPerPage() - 1;
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

        TableView<Gestionnaire> table = new TableView<>();
        TableColumn<Gestionnaire, Integer> col1 = new TableColumn<>("ID ");
        col1.setCellValueFactory(new PropertyValueFactory<>("idGest"));
        col1.setMaxWidth(COL_WIDTH);

        TableColumn<Gestionnaire, String> col2 = new TableColumn<>("Nom ");
        col2.setCellValueFactory(new PropertyValueFactory<>("nomGest"));
        col2.setMaxWidth(COL_WIDTH);
        
        TableColumn<Gestionnaire, String> col3 = new TableColumn<>("Type ");
        col3.setCellValueFactory(new PropertyValueFactory<>("typeGest"));
        col3.setMaxWidth(COL_WIDTH);
        
        table.getColumns().addAll(col1, col2, col3);
        if (pageIndex == lastIndex && displace > 0) {
            table.setItems(FXCollections.observableArrayList(gestionnaires.subList(pageIndex * rowsPerPage(), pageIndex * rowsPerPage() + displace)));
        }
        else if (pageIndex < lastIndex || displace == 0) {
            table.setItems(FXCollections.observableArrayList(gestionnaires.subList(pageIndex * rowsPerPage(), (pageIndex+1) * rowsPerPage())));
        }

        table.setPadding(new Insets(10));
        table.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showGestionnaireDetails(newValue));

        box.getChildren().add(table);
        return box;
    }

    @FXML private void refreshResults() {
        ToggleButton filtre = (ToggleButton)groupFiltres.getSelectedToggle();
        if (filtre != null && filtre.getText().equals("Nom")) {
            String pattern = searchBox.getText();
            if (!pattern.isEmpty())
                gestionnaires = Gestionnaire.find(pattern);
            else
                gestionnaires = Gestionnaire.getAll();
            refreshTable();
        }
    }

    private void showGestionnaireDetails(Gestionnaire gestionnaire) {
        Stage modal = new Stage(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/gestionnaires/pGestionnaire.fxml"));
        Parent presentation;
        try {
            presentation = loader.load();
            Scene scene = new Scene(presentation);
            scene.setFill(Color.TRANSPARENT);
            modal.setScene(scene);
            modal.show();

            PGestionnaireController controller = loader.getController();
            controller.initialize(gestionnaire, this);

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

    private void showAddGestionnaireDialog() {
        Stage modal = new Stage(StageStyle.TRANSPARENT);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../../views/gestionnaires/aGestionnaire.fxml"));
        Parent presentation;
        try {
            presentation = loader.load();
            Scene scene = new Scene(presentation);
            scene.setFill(Color.TRANSPARENT);
            modal.setScene(scene);
            modal.show();

            AGestionnaireController controller = loader.getController();
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
