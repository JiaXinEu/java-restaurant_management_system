package RestaurantSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class StartupController{
    //fxml id for startup page
    @FXML
    private Button startupLogin,startupGuest;

//general controller

    //attached to all exit button, close current stage when clicked
    public void exitButtonOnAction(ActionEvent e){
        Button exit= (Button) e.getSource();
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }


//startup controller
    //event handler when button clicked on startup page, attached to login and guest buttons
    public void startupOnAction(ActionEvent e) {
        Stage stage = (Stage) startupLogin.getScene().getWindow();
        Parent root;
        Scene scene = null;
        try {
            //switch to login scene if source is login button
            if(e.getSource()==startupLogin) {
                root = FXMLLoader.load(getClass().getResource("login.fxml"));
            }
            //switch to menu screen
            else{
                root = FXMLLoader.load(getClass().getResource("appetizerMenu.fxml"));
                scene = new Scene(root,1315,810);
            }
            //set root and size of scene
            if(e.getSource()!=startupGuest) {
                scene = new Scene(root, 520, 400);
            }
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

}
