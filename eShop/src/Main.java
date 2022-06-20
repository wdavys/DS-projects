import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.BD;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        BD.ConnexionMySql();
        VBox login = FXMLLoader.load(getClass().getResource("views/login/login.fxml"));

        primaryStage.setTitle("Connexion - Welcome ShopDB");
        primaryStage.setScene(new Scene(login, 600, 600));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
