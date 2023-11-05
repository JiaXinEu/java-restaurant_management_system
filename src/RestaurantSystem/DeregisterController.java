package RestaurantSystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class DeregisterController implements Initializable {

    @FXML
    private TableColumn<Admin, String> adminColumn;
    @FXML
    private TableColumn<Admin, String> managerColumn;
    @FXML
    private TableColumn<Admin, String> signUpColumn;
    @FXML
    private TableColumn<Admin, String> usernameColumn;
    @FXML
    private TableView<Admin> table;
    @FXML
    private Button backButton,removeButton;

    public static boolean confirmation;
    ObservableList<Admin> list= FXCollections.observableArrayList();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
    }

    public void updateTable(){
        table.getItems().clear();  //clear all items from table view
        //define which column holds what value
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("Username"));
        adminColumn.setCellValueFactory(new PropertyValueFactory<>("AdminName"));
        managerColumn.setCellValueFactory(new PropertyValueFactory<>("Manager"));
        signUpColumn.setCellValueFactory(new PropertyValueFactory<>("SignDate"));
        DatabaseConnection connectNow = new DatabaseConnection(); //instantiate new connection
        Connection connectDb= connectNow.getConnection(); //establish connection with database connection returned

        //get data from database and load each row to observable list
        try{
            ResultSet query = connectDb.createStatement().executeQuery("SELECT * FROM admin"); //apply select query

            while(query.next()){
                list.add(new Admin(query.getString("username"), query.getString("adminName"),
                        query.getString("manager"), query.getString("signed_up_on")));
            }

        }catch(Exception e){
            e.printStackTrace();
        }
        table.setItems(list);  //add items from observable list to table view
        if(table.getItems().size()==1){  //must have at least one admin, remove button disabled if only one admin
            removeButton.setDisable(true);
        }
    }

    public void backButtonOnAction(ActionEvent e){
        Stage stage;
        //get stage where button is clicked
        stage = (Stage) backButton.getScene().getWindow();
        Parent root;
        //set scene to manager page scene
        try {
            root = FXMLLoader.load(getClass().getResource("managerpage.fxml"));
            Scene scene = new Scene(root,400,300);
            stage.setScene(scene);
            stage.show();
            //set scene to centre of screen
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void deregister(ActionEvent e){
        confirmation=false;
        Stage secondaryStage=new Stage();
        secondaryStage.initStyle(StageStyle.UNDECORATED);
        try {
            secondaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("warning.fxml")),300,200));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        secondaryStage.showAndWait();  //show stage with warning scene
        if(!confirmation)return;  //only proceed if confirmation is true
        DatabaseConnection connectNow = new DatabaseConnection(); //instantiate new connection
        Connection connectDb= connectNow.getConnection(); //establish connection with database connection returned
        Admin selected=table.getSelectionModel().getSelectedItem();
        PreparedStatement statement=null;
        String username=selected.getUsername();
        try{
            statement=connectDb.prepareStatement("DELETE FROM admin WHERE username= ?");  //prepare statement
            statement.setString(1,username);  //pass username as first parameter
            statement.execute();  //execute query to delete
        }catch(Exception exp){
            exp.printStackTrace();
        }
        updateTable();  //update table view
    }

    public void switchFocus(KeyEvent e){
        if(e.getCode()== KeyCode.RIGHT){
            if(e.getSource()==table){
                removeButton.requestFocus();
            }
        }else if(e.getCode()==KeyCode.UP){
            if(e.getSource()==backButton||e.getSource()==removeButton){
                table.requestFocus();
            }
        }
    }
}
