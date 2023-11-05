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
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class InventoryController implements Initializable {
    //fxml id for all referenced components on inventory page
    @FXML
    private TableColumn<Food, String> typeColumn,nameColumn,colorColumn,imgColumn;
    @FXML
    private TextField alterName,alterPrice,altertype,alterColor,alterImg;
    @FXML
    private TableColumn<Food, BigDecimal> priceColumn;
    @FXML
    private TableView<Food> table;
    @FXML
    private MenuButton editModeMenu;
    @FXML
    private Button refreshButton,confirmButton,backButton;
    @FXML
    private Label messageLabel;

    public static boolean confirmation=false;
    private static ObservableList<Food>list= FXCollections.observableArrayList();

    //method to read data from file
    public static ArrayList<Food> getData() {
        ArrayList<Food> food=new ArrayList<>();
        try {
            FileInputStream fis = new FileInputStream("src/data/Menu.bin");
            ObjectInputStream ois = new ObjectInputStream(fis);
            food = (ArrayList<Food>) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e) {

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return food;
    }

    //initialize scene
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setTable();
        table.requestFocus();
    }

    //setup table view based on menu
    public void setTable(){
        for(Food f:getData()){
            list.add(f);
        }
        //set value of each column based on the variable
        typeColumn.setCellValueFactory(new PropertyValueFactory<Food,String>("Type"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Food,String>("Name"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Food,BigDecimal>("Price"));
        imgColumn.setCellValueFactory(new PropertyValueFactory<Food,String>("Img"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<Food,String>("Color"));
        table.setItems(list);
    }

    //method to control menu button
    public void editMode(ActionEvent e) {
        for (MenuItem mi : editModeMenu.getItems()) {
            if (e.getSource() == mi) {
                editModeMenu.setText(mi.getText());  //set menu button to text of menu item selected
                if(mi.getText().equals("Remove")){
                    altertype.setDisable(true);  //disable all text fields
                    alterImg.setDisable(true);
                    alterName.setDisable(true);
                    alterPrice.setDisable(true);
                    alterColor.setDisable(true);
                }else {
                    alterImg.setDisable(false);  //enable all text fields
                    alterName.setDisable(false);
                    alterPrice.setDisable(false);
                    alterColor.setDisable(false);
                    altertype.setDisable(false);
                    if(mi.getText().equals("Add")){
                        altertype.clear();  //clear text in all text fields
                        alterImg.clear();
                        alterName.clear();
                        alterPrice.clear();
                        alterColor.clear();
                    }
                }
            }
        }
    }

    //remove previous data in table, set table with new data
    public void refresh(ActionEvent e){
        table.getItems().clear();
        setTable();
    }

    //event handler when enter is pressed on table view
    public void enterRow(KeyEvent e){
        if(e.getCode()== KeyCode.ENTER)
        select();
        else if(e.getCode()==KeyCode.RIGHT)
            editModeMenu.requestFocus();
    }

    //event handler when mouse is clicked on table view
    public void selectRow(MouseEvent e){
        select();
    }

    //set text fields corresponding to columns on selected row
    public void select(){
        Food selectedFood=table.getSelectionModel().getSelectedItem();  //get Food object selected
        if(!editModeMenu.getText().equals("Add")) {
            altertype.setText(selectedFood.getType());
            alterName.setText(selectedFood.getName());
            alterPrice.setText(String.valueOf(selectedFood.getPrice()));
            alterImg.setText(selectedFood.getImg());
            alterColor.setText(selectedFood.getColor());
        }
    }

    //event handler attached to confirm button
    public void confirmButtonOnAction(ActionEvent e){
        if(altertype.getText().isEmpty()||alterName.getText().isEmpty()||alterPrice.getText().isEmpty()||alterImg.getText().isEmpty()||alterColor.getText().isEmpty()){
            messageLabel.setText("All fields must be completed");
            messageLabel.setTextFill(Color.RED);
            return;
        }
        confirmation=false;
        //confirmation message before making changes
        Stage secondaryStage=new Stage();
        secondaryStage.initStyle(StageStyle.UNDECORATED);
        try {
            secondaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("warning.fxml")),300,200));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        secondaryStage.showAndWait();
        if(!confirmation)return;  //only perform following actions if confirm is true
        Food selected=table.getSelectionModel().getSelectedItem();  //get object selected on table
        //response according to the mode selected
        if(editModeMenu.getText().equals("Alter")) alter(selected);
        else if(editModeMenu.getText().equals("Remove"))remove(selected);
        else if(editModeMenu.getText().equals("Add"))add(getData().size());
        else {
            messageLabel.setText("Select mode to modify menu");
            messageLabel.setTextFill(Color.RED);
        }
        //empty all text fields when done
        altertype.clear();
        alterImg.clear();
        alterName.clear();
        alterPrice.clear();
        alterColor.clear();
    }

    //add new food that has been altered to position where it was removed
    public void alter(Food selected){
        add(remove(selected));
    }

    //write new array to file
    public void updateMenu(ArrayList<Food> menu){
        try {
            FileOutputStream fos=new FileOutputStream("src/data/Menu.bin");
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(menu);
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //remove food item and return the index where the element was removed
    public int remove(Food selected){
        int index=-1;
        ArrayList<Food> menu=getData();
        for(Food f:menu){
            if(f.getName().equals(selected.getName())){
                index=menu.indexOf(f);
                menu.remove(f);
                break;
            }
        }
        updateMenu(menu);
        messageLabel.setText("Inventory Updated!");
        messageLabel.setTextFill(Color.BLACK);
        return index;
    }

    //add food item to arraylist
    public void add(int index){
        String []types={"Appetizer","Main","Dessert"};
        ArrayList<Food> menu = getData();
        Boolean matchType=false;
        for(String s:types){
            if(altertype.getText().equalsIgnoreCase(s)) {  //type entered must be from the three valid types
                altertype.setText(s);
                matchType=true;
                break;
            }
        }
        try{
            if(!matchType) {
                throw new Exception();
            }
        } catch (Exception e) {
            messageLabel.setText("Invalid type!");
            messageLabel.setTextFill(Color.RED);
            return;
        }
        if(alterName.getText().indexOf(".")==-1){  //name must be made of code and name
            messageLabel.setText("Enter code and name (eg.M1.Name)");
            messageLabel.setTextFill(Color.RED);
            return;
        }

        //code and name must not be already in arraylist
        for(Food f:menu){
            if(f.getName().substring(0,f.getName().indexOf(".")).equals(alterName.getText().substring(0,alterName.getText().indexOf(".")))){
                messageLabel.setText("Code is already in menu");
                messageLabel.setTextFill(Color.RED);
                return;
            }else if(f.getName().substring(f.getName().indexOf(".")+1).equals(alterName.getText().substring(alterName.getText().indexOf(".")+1).trim())){
                messageLabel.setText("Name is already in menu");
                messageLabel.setTextFill(Color.RED);
                return;
            }
        }
        Food f = new Food();
        f.setType(altertype.getText());
        f.setName(alterName.getText());
        //price must be a number
        try {
            f.setPrice(new BigDecimal(alterPrice.getText()).setScale(2, RoundingMode.HALF_UP));
        }catch (Exception e){
            messageLabel.setText("Invalid Price");
            messageLabel.setTextFill(Color.RED);
            return;
        }
        f.setImg(alterImg.getText());
        f.setColor(alterColor.getText());
        menu.add(index, f);
        updateMenu(menu);
        messageLabel.setText("Inventory Updated!");
        messageLabel.setTextFill(Color.BLACK);
    }

    //different components request focus if for different key pressed on different previously focused components
    //for easier control on device without mouse
    public void switchFocus(KeyEvent e){
        if(e.getCode()==KeyCode.RIGHT){
            if(e.getSource()==editModeMenu){
                refreshButton.requestFocus();
            }
        }else if(e.getCode()==KeyCode.LEFT){
            if(e.getSource()==refreshButton){
                editModeMenu.requestFocus();
            }
        }else if(e.getCode()==KeyCode.DOWN){
            if(e.getSource()==altertype){
                alterName.requestFocus();
            }else if(e.getSource()==alterName){
                alterPrice.requestFocus();
            }else if (e.getSource()==alterPrice||e.getSource()==refreshButton) {
                alterImg.requestFocus();
            }else if (e.getSource()==alterImg){
                alterColor.requestFocus();
            }else if(e.getSource()==alterColor){
                confirmButton.requestFocus();
            }
        }else if(e.getCode()==KeyCode.UP){
            if(e.getSource()==alterName){
                altertype.requestFocus();
            }else if (e.getSource()==alterPrice) {
                alterName.requestFocus();
            }else if (e.getSource()==alterImg){
                alterPrice.requestFocus();
            }else if(e.getSource()==alterColor){
                alterImg.requestFocus();
            }else if(e.getSource()==altertype){
                refreshButton.requestFocus();
            }else if(e.getSource()==editModeMenu){
                table.requestFocus();
            }
        }
    }

    //return to manager page
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
}
