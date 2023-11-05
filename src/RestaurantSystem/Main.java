package RestaurantSystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        //Menu.writeMenu();  //only run on first run of application
        Parent root = FXMLLoader.load(getClass().getResource("startup.fxml"));
        primaryStage.setTitle("Restaurant System");
        primaryStage.initStyle(StageStyle.UNDECORATED);  //remove the window operation around stage
        primaryStage.setScene(new Scene(
                //root,1315,810));
                root,600,400));
        primaryStage.show();
        //set stage on centre of screen
        Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
        primaryStage.setX((primScreenBounds.getWidth() - primaryStage.getWidth()) / 2);
        primaryStage.setY((primScreenBounds.getHeight() - primaryStage.getHeight()) / 2);
    }
}
