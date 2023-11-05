package RestaurantSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;

public class ManagerController {
    @FXML
    private Button orderButton, registerButton, managerLogout, deregisterButton;

    public void logoutButtonOnAction(ActionEvent e){
        EndController.admin="Guest";
        Stage stage;
        //get stage where button is clicked
        stage = (Stage) managerLogout.getScene().getWindow();
        Parent root;
        //set scene to startup scene
        try {
            root = FXMLLoader.load(getClass().getResource("startup.fxml"));
            Scene scene = new Scene(root,600,400);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void operationButtonOnAction(ActionEvent e){
        Stage stage= (Stage) registerButton.getScene().getWindow();
        Parent root;
        Scene scene;
        try {
            //load register scene
            if(e.getSource()==registerButton) {
                root = FXMLLoader.load(getClass().getResource("register.fxml"));
                scene = new Scene(root, 520, 400);
            //load appetizer menu scene
            }else if(e.getSource()==orderButton) {
                root = FXMLLoader.load(getClass().getResource("appetizerMenu.fxml"));
                scene = new Scene(root, 1315, 810);
            }else if(e.getSource()==deregisterButton){
                root = FXMLLoader.load(getClass().getResource("deregister.fxml"));
                scene = new Scene(root, 600, 300);
            //load inventory screen
            }else{
                root = FXMLLoader.load(getClass().getResource("inventory.fxml"));
                scene = new Scene(root,600,600);
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
