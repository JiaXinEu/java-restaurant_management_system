package RestaurantSystem;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

public class CheckoutController implements Initializable {
    //fxml id for all referenced components on checkout page
    @FXML
    private AnchorPane checkoutAnchor;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Label codeLabel,extraChargeLabel,foodLabel,noLabel,priceLabel,ipLabel,qtyLabel,serviceCharge,grandTotal,discountMenu,errorLabel,discountLabel;
    @FXML
    private MenuButton menuButton,paymentMenu,remove;
    @FXML
    private Button backButton,confirmPaymentButton,selectTableButton,logoutButton;
    @FXML
    private TextField tableField;
    @FXML
    private TextArea remarkText;

    private  BigDecimal bd = BigDecimal.valueOf(0);
    public static Order order;
    private BigDecimal bdTotal;
    public static Table table=new Table();
    public static Food freeDrink;
    public  static boolean confirmation;
    private static String []discount={" No Discount "," Set Combo 15% Off"," Free Drink "};  //can be adjusted according to current available discount

    //initialize scene by displaying all the food in cart and the details
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        displayOrder();
    }

    public void displayOrder(){
        if(order.getCart().isEmpty()){  //return to menu if all item has been removed
            back();
        }
        bdTotal=new BigDecimal(0);
        bd=new BigDecimal(0);
        discountLabel.setText("");
        grandTotal.setText("");
        serviceCharge.setText("");
        checkSetCombo();  //checks for discount
        String code,name,price,qty,total;
        noLabel.setText("No.");
        codeLabel.setText("Code");
        foodLabel.setText("Food");
        ipLabel.setText("Item Price(RM)");
        qtyLabel.setText("Quantity");
        priceLabel.setText("Price(RM)");
        BigDecimal bd;
        remove.getItems().clear();  //remove all menu item in menu button
        int count=1;
        for(Cart o: order.getCart()){
            noLabel.setText(noLabel.getText()+"\n"+count);  //count of different food in list
            code=o.getFood().getName().substring(0,o.getFood().getName().indexOf("."));
            codeLabel.setText(codeLabel.getText()+"\n"+code);  //code of each food
            name=o.getFood().getName().substring(o.getFood().getName().indexOf(".")+1).trim();
            foodLabel.setText(foodLabel.getText()+"\n"+name);  //name of food
            price= String.valueOf(o.getFood().getPrice());
            ipLabel.setText(ipLabel.getText()+"\n"+price);  //single price of food
            qty= String.valueOf(o.getQuantity());
            qtyLabel.setText(qtyLabel.getText()+"\n"+qty);  //quantity of food
            bd=new BigDecimal(o.getQuantity()).multiply(o.getFood().getPrice()).setScale(2,RoundingMode.HALF_UP);  //calculate the price of each item by their quantity selected
            total= String.valueOf(bd);
            priceLabel.setText(priceLabel.getText()+"\n"+total);
            bdTotal=new BigDecimal(String.valueOf(bd.add(bdTotal))).setScale(2,RoundingMode.HALF_UP);  //calculate running total of price to pay as each item is added
            MenuItem menuItem=new MenuItem(String.valueOf(count));  //create new menu item for each order in cart
            menuItem.setOnAction(removeMenuButton);  //add event handler to menu item
            remove.getItems().add(menuItem);  //add menu item to menu button
            count++;
        }
        calcTotal(menuButton.getText());
    }

    public void checkSetCombo(){
        discountMenu.setText(discount[0]);
        int main=0,appetizer=0,dessert=0;
        for(Cart c:order.getCart()){
            if(c.getFood().getType().equals("Main")){
                main++;
            }else if(c.getFood().getType().equals("Appetizer")){
                appetizer++;
            }else{
                dessert++;
            }
        }
        if(appetizer>0 &&main>0&&dessert>0){
            discountMenu.setText(discount[1]);
        }else checkFreeDrink();
    }

    public void checkFreeDrink(){
        if(order.getTotal().compareTo(BigDecimal.valueOf(70))>0){
            discountMenu.setText(discount[2]);
            Cart free=new Cart(freeDrink,1);
            order.addCart(free);
        }
    }

    public void logoutButtonOnAction(ActionEvent e){
        order=new Order();
        AppetizerController.order=new Order();
        MainController.order=new Order();
        DessertController.order=new Order();
        table=new Table();
        EndController.admin="Guest";
        Stage stage;
        //get stage where button is clicked
        stage = (Stage) logoutButton.getScene().getWindow();
        Parent root;
        //set scene to startup scene when logout
        try {
            root = FXMLLoader.load(getClass().getResource("startup.fxml"));
            Scene scene = new Scene(root,600,400);
            stage.setScene(scene);
            stage.show();
            //set stage to centre of screen
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    //action on menu button and its menu items
    public void dineMode(ActionEvent e){
        for(MenuItem mi:menuButton.getItems()){
            if(e.getSource()==mi) {
                menuButton.setText(mi.getText());  //display text of menu item selected on menu button
            }
        }
        String dineMode=menuButton.getText();
        //extra charge based on selected mode
        calcTotal(dineMode);
    }

    public void calcTotal(String mode){
        bdTotal=order.getTotal();
        if(mode.equals("Dine In")) {
            bd = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP);
            extraChargeLabel.setText(String.valueOf(bd));
            tableField.setDisable(false);
            selectTableButton.setDisable(false);
        }else if(mode.equals("Take Away")){
            bd = new BigDecimal(1.00).setScale(2, RoundingMode.HALF_UP);
            extraChargeLabel.setText(String.valueOf(bd));
            tableField.setDisable(true);  //disable text field and button if take away selected
            selectTableButton.setDisable(true);
        }else return;
        if(discountMenu.getText().equals(discount[1])){
            discountLabel.setText("-"+bdTotal.multiply(BigDecimal.valueOf(0.15)).setScale(2,RoundingMode.HALF_UP));
            bdTotal=bdTotal.multiply(BigDecimal.valueOf(0.85));
        }else if(discountMenu.getText().equals(discount[2])){
            discountLabel.setText("-"+freeDrink.getPrice());
            bdTotal=bdTotal.subtract(freeDrink.getPrice());
        }
        BigDecimal temp=bdTotal;
        bdTotal=bdTotal.add(bd);  //calculate total including service charge
        BigDecimal sCharge=bdTotal.multiply(BigDecimal.valueOf(0.06)).setScale(2,RoundingMode.HALF_UP);  //multiply by 0.06 to get service charge
        serviceCharge.setText(String.valueOf(sCharge));
        bdTotal=new BigDecimal(String.valueOf(bdTotal.add(sCharge).setScale(2, RoundingMode.HALF_UP)));  //add service charge to total
        String decimal=String.valueOf(bdTotal).substring(String.valueOf(bdTotal).indexOf(".")+2);  //get value of digit at second decimal place
        //round value
        if(Integer.parseInt(decimal)<=2 && Integer.parseInt(decimal)>0 ){ //round down to 0.00 if ends with 1/2
            decimal="0";
        }else if(Integer.parseInt(decimal)>=3 && Integer.parseInt(decimal)<7){  //round up to 0.05 if ends with 3/4/6
            decimal="5";
        }else{  //round up to 0.1 if ends with 7/8/9
            decimal="0";
            bdTotal=bdTotal.add(BigDecimal.valueOf(0.1));
        }
        grandTotal.setText(String.valueOf(bdTotal).substring(0,String.valueOf(bdTotal).indexOf(".")+2)+decimal);  //display final total on label
        bdTotal=temp;
    }

    //method to display text from selected menu item on menu button
    public void paymentMode(ActionEvent e) {
        for (MenuItem mi : paymentMenu.getItems()) {
            if (e.getSource() == mi) {
                paymentMenu.setText(mi.getText());
            }
        }
    }

    EventHandler<ActionEvent> removeMenuButton=new EventHandler<ActionEvent>() {  //event handler attached to each menu item in remove menu button
        @Override
        public void handle(ActionEvent e) {
            MenuItem mi= (MenuItem) e.getSource();
            int index= Integer.parseInt(mi.getText());  //get index of element to remove
            Cart remove=order.getCart().get(index-1);  //remove order from cart
            int qty=remove.getQuantity();
            order.getCart().remove(index-1);
            bdTotal=bdTotal.subtract(BigDecimal.valueOf(qty).multiply(remove.getFood().getPrice()));  //decrease total
            order.setTotal(bdTotal.setScale(2,RoundingMode.HALF_UP));
            calcTotal(menuButton.getText());
            displayOrder();
        }
    };

    public void confirm(ActionEvent e){
        //get stage where button is clicked
        confirmation=false;
        if(grandTotal.getText().isEmpty()||menuButton.getText().equals("Select Dine Mode")||paymentMenu.getText().equals("Select Payment Mode")||(menuButton.getText().equals("Dine In")
                &&tableField.getText().isEmpty())){
            errorLabel.setText("Missing Order Details");
            TranslateTransition move=new TranslateTransition();
            move.setByX(2);
            move.setDuration(Duration.millis(100));
            move.setCycleCount(8);
            move.setAutoReverse(true);
            move.setNode(errorLabel);
            move.setOnFinished(event-> {
                errorLabel.setText("");
            });
            move.play();
            return;
        }
        Stage secondaryStage=new Stage();
        String file="";
        if(paymentMenu.getText().equals("Cash")) {
            file="cash.fxml";
            CashController.totalPayable=new BigDecimal(grandTotal.getText()).setScale(2, RoundingMode.HALF_UP);
        }
        else if(paymentMenu.getText().equals("E-Wallet")||paymentMenu.getText().equals("Credit Card")) file="connectbank.fxml";
        try {
            secondaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(file)),400,300));
            secondaryStage.initStyle(StageStyle.UNDECORATED);
            secondaryStage.showAndWait();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        if(!confirmation)return;
        Stage stage = (Stage) logoutButton.getScene().getWindow();
        Parent root;
        EndController.order=order;
        EndController.sCharge=serviceCharge.getText();
        EndController.dineMode=menuButton.getText();
        EndController.eCharge=extraChargeLabel.getText();
        EndController.total=grandTotal.getText();
        EndController.tableNo=tableField.getText();
        EndController.discount=discountLabel.getText();
        EndController.payMode=paymentMenu.getText();
        EndController.remark=remarkText.getText();
        //set scene to startup scene when logout
        try {
            root = FXMLLoader.load(getClass().getResource("endpage.fxml"));
            Scene scene = new Scene(root,600,400);
            stage.setScene(scene);
            stage.show();
            //set stage to centre of screen
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    //different components request focus if for different key pressed on different previously focused components
    //for easier control on device without mouse
    public void switchFocus(KeyEvent e){
        if(e.getCode()== KeyCode.RIGHT ||e.getSource()==selectTableButton){
            if(e.getSource()==menuButton){
                paymentMenu.requestFocus();
            }else if(e.getSource()==tableField){
                selectTableButton.requestFocus();
            }else if(e.getSource()==paymentMenu ||e.getSource()==backButton){
                confirmPaymentButton.requestFocus();
            }else if(e.getSource()==remove){
                menuButton.requestFocus();
            }
        }else if(e.getCode()==KeyCode.LEFT){
            if(e.getSource()==paymentMenu){
                selectTableButton.requestFocus();
            }else if(e.getSource()==confirmPaymentButton){
                backButton.requestFocus();
            }else if(e.getSource()==remove){
                scrollPane.requestFocus();
            }else if(e.getSource()==menuButton){
                remove.requestFocus();
            }
        }else if(e.getCode()==KeyCode.ENTER){
            if(e.getSource()==tableField){
                paymentMenu.requestFocus();
            }else if(e.getSource()==scrollPane){
                remove.requestFocus();
            }
        }

    }

    //open a pop-up window to select table based on table plan
    public void selectTableOnAction(ActionEvent e){
        try {
            Parent root = FXMLLoader.load(getClass().getResource("tableplan.fxml"));
            Stage stage=new Stage();  //create new stage
            stage.setScene(new Scene(root,600,400));
            stage.showAndWait();  //show stage and wait until stage is closed

        } catch (IOException ie) {
            ie.printStackTrace();
        }

        tableField.setText(table.getTableNo());  //set table no. selected on text field
    }

    public void backButtonOnAction(ActionEvent e){
        back();
    }

    public void back(){
        AppetizerController.order=order;  //pass order which may be modified to appetizer controller
        //get scene where button is clicked
        Scene scene= backButton.getScene();
        //get root of scene which is a stack pane
        StackPane menuStackPane= (StackPane) scene.getRoot();
        Parent root;
        //set root to selected scene
        try {
            root = FXMLLoader.load(getClass().getResource("appetizermenu.fxml"));
            //set direction of root animation from left to right
            root.translateXProperty().set(scene.getWidth()*-1);
            menuStackPane.getChildren().add(root);  //add children to stack pane
            Timeline timeline =new Timeline();
            KeyValue kv= new KeyValue(root.translateXProperty(),0, Interpolator.EASE_IN);
            KeyFrame kf=new KeyFrame(Duration.seconds(0.5),kv);  //set keyframe to 0.5 seconds
            timeline.getKeyFrames().add(kf);  //add keyframe to timeline
            timeline.setOnFinished(event ->{
                menuStackPane.getChildren().remove(checkoutAnchor);  //remove current scene after animation
            });
            timeline.play();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }


}
