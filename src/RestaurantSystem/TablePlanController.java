package RestaurantSystem;


import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.stage.Stage;

public class TablePlanController {

    //get table number selected and pass table no. to checkout scene
    public void tableButtonOnAction(ActionEvent e){
        Button table= (Button) e.getSource();
        Table t=new Table();
        t.setTableNo(table.getText());
        CheckoutController.table=t;
        Stage stage = (Stage) table.getScene().getWindow();
        stage.close();
    }
}
