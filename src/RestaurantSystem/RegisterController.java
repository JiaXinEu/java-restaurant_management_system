package RestaurantSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Statement;

public class RegisterController {
    //fxml id for register page
    @FXML
    private Button registerCancelButton,registerButton,registerHere,registerExit;
    @FXML
    private Label registerMessage;
    @FXML
    private TextField registerName,registerUsername;
    @FXML
    private PasswordField registerPassword,registerConPassword;
    @FXML
    private CheckBox managerCheckBox;

    //register controller
    //attached to word here, switch scene to login when clicked
    public void registerHereButtonOnAction(ActionEvent e){
        EndController.admin="Guest";
        Stage stage = (Stage) registerCancelButton.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
            Scene scene = new Scene(root,520,400);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    //check content of field, attached to register button on register page called when mouse click, called when keyCode on any field is ENTER
    public void registerButtonOnAction(){
        registerMessage.setTextFill(Paint.valueOf("RED"));
        //output error message if any field is empty
        if (registerUsername.getText().isEmpty() || registerName.getText().isEmpty() ||registerPassword.getText().isEmpty()
                || registerConPassword.getText().isEmpty())registerMessage.setText("All fields must not be empty");
            //output error message if different values entered into password and confirm password
        else if(!registerPassword.getText().equals(registerConPassword.getText()))registerMessage.setText("Password does not match");
            //output error message if length of password is too short for security
        else if (registerPassword.getText().length()<6)registerMessage.setText("Password should be at least 6 characters");
        else {
            if(registerUser()){
                Stage stage= (Stage) registerButton.getScene().getWindow();
                try {
                    Parent root = FXMLLoader.load(getClass().getResource("managerpage.fxml"));
                    Scene scene = new Scene(root,400,300);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
        }
    }

    public boolean registerUser(){
        DatabaseConnection connectNow = new DatabaseConnection(); //instantiate new connection
        Connection connectDb= connectNow.getConnection(); //establish connection with database connection returned
        String name=registerName.getText();
        String username=registerUsername.getText();
        boolean manager=managerCheckBox.isSelected();
        int managerInt;
        if(manager)managerInt=1;
        else managerInt=0;
        String password= String.valueOf(registerPassword.getText().hashCode()); //hash password before inserting into database
        String insertFields="INSERT INTO admin(adminName, username, password, manager, signed_up_on) VALUES('";
        String insertValues=name+"','"+username+"','"+password+"',"+managerInt+", now())";
        String insertQuery=insertFields+insertValues; //join strings to form insert query

        try{
            Statement statement=connectDb.createStatement();
            statement.executeUpdate(insertQuery); //execute insert query
            registerMessage.setTextFill(Paint.valueOf("Blue"));
            registerMessage.setText("Successful registration!"); //output blue message if query ok
            return true;
        }catch(Exception e){
            registerMessage.setTextFill(Paint.valueOf("RED")); //output red error message if error where username is not unique
            registerMessage.setText("Username " +username+" has been taken");
        }
        return false;
    }

    //keyboard event handler attached to all textfields and passwordfields
    public void switchField(KeyEvent e){
        //set focused field to next field below if down key pressed
        if(e.getCode()== KeyCode.DOWN) {
            if(e.getSource()==registerName) registerUsername.requestFocus();
            else if(e.getSource()==registerUsername)registerPassword.requestFocus();
            else if(e.getSource()==registerPassword)registerConPassword.requestFocus();
            else if(e.getSource()==registerConPassword)registerButton.requestFocus();
            //set focused field to previous field above if up key pressed
        }else if(e.getCode()==KeyCode.UP){
            if(e.getSource()==registerUsername) registerName.requestFocus();
            else if(e.getSource()==registerPassword) registerUsername.requestFocus();
            else if(e.getSource()==registerConPassword) registerPassword.requestFocus();
            else if(e.getSource()==registerExit)registerHere.requestFocus();
            //call method to register or login when enter key pressed
        }else if(e.getCode().equals(KeyCode.ENTER)){
            if(e.getSource()==registerName ||e.getSource()==registerPassword ||e.getSource()==registerUsername||e.getSource()==registerConPassword)registerButtonOnAction();
        }
    }

    //event handler when cancel button on login or register page is clicked
    public void cancelButtonOnAction(ActionEvent e){
        Stage stage;
        //get stage where button is clicked
        stage = (Stage) registerCancelButton.getScene().getWindow();
        Parent root;
        //set scene to startup scene
        try {
            root = FXMLLoader.load(getClass().getResource("managerpage.fxml"));
            Scene scene = new Scene(root,400,300);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    //attached to all exit button, close current stage when clicked
    public void exitButtonOnAction(ActionEvent e){
        Button exit= (Button) e.getSource();
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
}
