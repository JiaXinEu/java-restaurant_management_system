package RestaurantSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Paint;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class LoginController {
    //fxml id for login page
    @FXML
    private Button loginCancelButton,loginButton,loginHere,loginExit;
    @FXML
    private Label loginMessage;
    @FXML
    private TextField loginUsername;
    @FXML
    private PasswordField loginPassword;

    //login controller
    //attached to word here, switch scene to menu when clicked
    public void loginHereButtonOnAction(ActionEvent e){
        Stage stage = (Stage) loginCancelButton.getScene().getWindow();
        try {
            Parent root = FXMLLoader.load(getClass().getResource("appetizermenu.fxml"));
            Scene scene = new Scene(root,1315,810);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    //check content of field, attached to login button on login page called when mouse click, called when keyCode on any field is ENTER
    public void loginButtonOnAction(){  //username:jiaxin password:123456
        //call method if both fields are not empty
        if (!loginUsername.getText().isEmpty() && !loginPassword.getText().isEmpty()){
            String validate=validateLogin();
            Parent root;
            Scene scene;
            Stage stage = (Stage) loginUsername.getScene().getWindow();
            //manager's operation if admin is manager
            if(validate.equals("manager")){
                try {
                    root = FXMLLoader.load(getClass().getResource("managerpage.fxml"));
                    scene = new Scene(root,400,300);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
            //only allow order if normal admin
            else if(validate.equals("normal")){
                try {
                    root = FXMLLoader.load(getClass().getResource("appetizermenu.fxml"));
                    scene = new Scene(root,1315,810);
                    stage.setScene(scene);
                    stage.show();
                } catch (IOException ie) {
                    ie.printStackTrace();
                }
            }
            //set the stage in the middle of screen
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
            //display error message in red on label if either one field is empty
        } else{
            loginMessage.setTextFill(Paint.valueOf("RED"));
            loginMessage.setText("Username and password cannot be blank");
            loginUsername.requestFocus();
        }
    }

    //check data entered in database
    public String validateLogin(){
        DatabaseConnection connectNow = new DatabaseConnection(); //instantiate new connection
        Connection connectDb= connectNow.getConnection(); //establish connection with database connection returned
        String verifyLogin="SELECT count(1) FROM admin WHERE username='"+ loginUsername.getText().trim() + "' AND password='"
                +loginPassword.getText().hashCode()+"'"; //password is hashed before selecting from database

        try{
            Statement statement=connectDb.createStatement();
            ResultSet query = statement.executeQuery(verifyLogin); //apply select query

            while(query.next()){
                if(query.getInt(1)==1){ //return column value of first column which is count()
                    loginMessage.setTextFill(Paint.valueOf("BLUE"));
                    loginMessage.setText("Logging in...");
                    if(validateManager())return "manager";
                    else return "normal";
                }else{ //empty set if return 0
                    loginMessage.setTextFill(Paint.valueOf("RED"));
                    loginMessage.setText("Incorrect username or password");
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return "";
    }

    //check if logged-in user is a manager
    public boolean validateManager(){
        getAdminName();
        DatabaseConnection connectNow = new DatabaseConnection(); //instantiate new connection
        Connection connectDb= connectNow.getConnection(); //establish connection with database connection returned
        String verifyManager="SELECT manager FROM admin WHERE username='"+ loginUsername.getText().trim()
                + "' AND password='" +loginPassword.getText().hashCode()+"'"; //password is hashed before selecting from database

        try{
            Statement statement=connectDb.createStatement();
            ResultSet query = statement.executeQuery(verifyManager); //apply select query

            while(query.next()){
                if(query.getInt(1)==1) { //return tinyint of first column which is manager
                    return (true);
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return false;
    }

    //set admin name in endController
    public void getAdminName(){
        DatabaseConnection connectNow = new DatabaseConnection(); //instantiate new connection
        Connection connectDb= connectNow.getConnection(); //establish connection with database connection returned
        String getAdmin="SELECT adminName FROM admin WHERE username='"+ loginUsername.getText().trim()
                + "' ";

        try{
            Statement statement=connectDb.createStatement();
            ResultSet query = statement.executeQuery(getAdmin); //apply select query

            while(query.next()){
                EndController.admin= String.valueOf(query.getString(1));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //keyboard event handler attached to all textfields and passwordfields
    public void switchField(KeyEvent e){
        //set focused field to next field below if down key pressed
        if(e.getCode()== KeyCode.DOWN) {
            if(e.getSource()==loginUsername) loginPassword.requestFocus();
            else if(e.getSource()==loginPassword) loginButton.requestFocus();
            //set focused field to previous field above if up key pressed
        }else if(e.getCode()==KeyCode.UP){
            if(e.getSource()==loginPassword) loginUsername.requestFocus();
            else if(e.getSource()==loginExit) loginHere.requestFocus();
            //call method to register or login when enter key pressed
        }else if(e.getCode().equals(KeyCode.ENTER)){
            if(e.getSource()==loginUsername ||e.getSource()==loginPassword)loginButtonOnAction();
        }
    }

    //event handler when cancel button on login or register page is clicked
    public void cancelButtonOnAction(ActionEvent e){
        Stage stage;
        //get stage where button is clicked
        stage = (Stage) loginCancelButton.getScene().getWindow();
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

    //attached to all exit button, close current stage when clicked
    public void exitButtonOnAction(ActionEvent e){
        Button exit= (Button) e.getSource();
        Stage stage = (Stage) exit.getScene().getWindow();
        stage.close();
    }
}
