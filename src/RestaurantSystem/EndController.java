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
import javafx.stage.StageStyle;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class EndController {

    @FXML
    private Button logoutButton, newButton,printButton;

    public static Order order;
    public static  String admin="Guest";
    //string to be used for printing receipt, values assigned in CheckoutController and CashController
    public static String eCharge,sCharge,dineMode,payMode,total,tableNo,paid,change,remark,discount;

    public void buttonOnAction(ActionEvent e){
        AppetizerController.order=new Order();
        CheckoutController.order=new Order();
        DessertController.order=new Order();
        MainController.order=new Order();
        Stage stage;
        //get stage where button is clicked
        stage = (Stage) logoutButton.getScene().getWindow();
        Parent root;
        String file="";
        Scene scene;
        //width and height of scene
        double height=0;
        double width=0;
        if(e.getSource()==logoutButton){
            file="startup.fxml";
            height=400;
            width=600;
            admin="Guest";
        }else if(e.getSource()==newButton ){
            file="appetizermenu.fxml";
            width=1315;
            height=810;
        }else if(e.getSource()==printButton){
            printReceipt();
            return;
        }
        //set scene to startup scene
        try {
            root = FXMLLoader.load(getClass().getResource(file));
            scene = new Scene(root,width,height);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    //text to be printed on receipt
    public void printReceipt(){
        StringBuilder string = new StringBuilder();
        string.append("Order Created By: "+admin+ " "+LocalDateTime.now());string.append(String.format("%20s \n","Table:"+tableNo));
        string.append(String.format("%-5s %-30s %-15s %-10s %s %n","Code","Food Name","Item Price(RM)","Quantity","Price(RM)"));
        for(Cart c: order.getCart()){
            string.append(String.format("%-5s %-30s %-15s %-10s %s %n",c.getFood().getName().substring(0,c.getFood().getName().indexOf(".")),
                    c.getFood().getName().substring(c.getFood().getName().indexOf(".")+1).trim(),c.getFood().getPrice(),c.getQuantity(),
                    new BigDecimal(c.getQuantity()).multiply(c.getFood().getPrice()).setScale(2, RoundingMode.HALF_UP)));
        }
        string.append(String.format("\n %70s \n","Extra Charge:"+eCharge+" ("+dineMode+")"));
        string.append(String.format("%70s \n","Discount:"+discount));
        string.append(String.format("%70s \n","Service Charge(6%):"+sCharge));
        string.append(String.format("%70s \n","Grand Total:"+total));
        if(payMode.equals("Cash")){
            string.append(String.format("%70s \n","Cash Paid:"+paid));
            string.append(String.format("%70s \n","Change:"+change));
        }
        string.append(String.format("%s \n","Remark:"+remark));
        System.out.println(string);
    }

}
