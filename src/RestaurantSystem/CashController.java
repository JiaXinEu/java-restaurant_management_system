package RestaurantSystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.URL;
import java.util.ResourceBundle;

public class CashController implements Initializable {
    //fxml id for all referenced components on cash page
    @FXML
    private Label change,total,errorLabel;
    @FXML
    private TextField paid;
    @FXML
    private Button payButton,doneButton;

    public static BigDecimal totalPayable;  //references to value of round grand total label on checkout.fxml, set by Checkout.fxml
    public static boolean confirmation;  //references to value of button selected on paymentconfirmation.fxml, set by PaymentConfirmationController.fxml

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        total.setText(total.getText()+totalPayable);  //initialize scene to show the total to be paid
        doneButton.setDisable(true);
    }

    public void pay(ActionEvent e){
        change.setText(change.getText().substring(0,change.getText().indexOf(" ")+1));  //take substring up to space without including any text after space
        if(paid.getText().equals(""))return;
        BigDecimal amtPaid=new BigDecimal(paid.getText()).setScale(2, RoundingMode.HALF_UP);
        if(amtPaid.compareTo(totalPayable)<0){  //check if amount paid is less than needed
            errorLabel.setText("Insufficient amount");
        }
        else {
            confirmation=false;
            Stage secondaryStage=new Stage();
            secondaryStage.initStyle(StageStyle.UNDECORATED);
            try {
                secondaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("paymentconfirmation.fxml")),300,200));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            secondaryStage.showAndWait();  //show warning stage and wait until stage is closed
            CheckoutController.confirmation=confirmation;
            if(!confirmation){
                Stage stage=(Stage) payButton.getScene().getWindow();
                stage.close();
            };  //check value of confirmation assigned in warning controller
            errorLabel.setText("");
            change.setText(change.getText()+amtPaid.subtract(totalPayable).setScale(2,RoundingMode.HALF_UP));  //calculate change
            doneButton.setDisable(false);  //enable done button
        }
    }

    public void doneOnAction(ActionEvent e){
        EndController.paid=paid.getText();  //pass value to end controller
        EndController.change=change.getText().substring(change.getText().indexOf(" ")+1);
        Stage stage= (Stage) doneButton.getScene().getWindow();  //close window
        stage.close();
    }

    public void switchFocus(KeyEvent e){
        if(e.getCode()== KeyCode.ENTER){
            if(e.getSource()==paid){
                payButton.requestFocus();
            }
        }else if(e.getCode()==KeyCode.RIGHT){
            if(e.getSource()==paid){
                payButton.requestFocus();
            }else if(e.getSource()==payButton){
                doneButton.requestFocus();
            }
        }
    }

}
