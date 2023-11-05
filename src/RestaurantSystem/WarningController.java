package RestaurantSystem;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class WarningController {

    public void confirm(ActionEvent e){
        Button button= (Button) e.getSource();
        //pass confirm or cancel to inventory controller
        if(button.getText().equals("Confirm")){
            InventoryController.confirmation=true;
            DeregisterController.confirmation=true;
        }
        else {
            InventoryController.confirmation=false;
            DeregisterController.confirmation=false;
        }
        Stage stage= (Stage) button.getScene().getWindow();
        stage.close();
    }
}
