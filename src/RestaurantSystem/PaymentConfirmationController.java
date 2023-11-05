package RestaurantSystem;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class PaymentConfirmationController {
    public void confirm(ActionEvent e){
        Button button= (Button) e.getSource();
        //pass confirm or cancel to inventory controller
        if(button.getText().equals("Confirm")){
            CashController.confirmation=true;
            ConnectbankController.confirmation=true;
        }
        else {
            CashController.confirmation=false;
            ConnectbankController.confirmation=false;
        }
        Stage stage= (Stage) button.getScene().getWindow();
        stage.close();
    }
}
