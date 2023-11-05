package RestaurantSystem;

import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class ConnectbankController {
    //fxml id for all referenced components on checkout page
    @FXML
    private Circle circle1, circle2,circle3,circle4;

    @FXML
    private Button doneButton;

    @FXML
    private Label connectingLabel;

    @FXML
    private HBox loadingHbox;

    public static boolean confirmation=false;


    //create animation bounce, take in node and double value of duration
    public void bounce(Node n, double duration){
        TranslateTransition move=new TranslateTransition();
        move.setByY(10);
        move.setNode(n);
        move.setAutoReverse(true);
        move.setCycleCount(8);
        move.setDuration(Duration.millis(duration));
        move.play();
        if(n==circle4) move.setOnFinished(event -> doneButton.setVisible(true));
    }

    public void enterButtonOnAction(ActionEvent e){
        confirmation=false;
        Stage secondaryStage=new Stage();
        secondaryStage.initStyle(StageStyle.UNDECORATED);
        try {
            secondaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("paymentconfirmation.fxml")),300,200));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        secondaryStage.showAndWait();  //show warning scene on new stage and wait
        CheckoutController.confirmation=confirmation;
        if(!confirmation) {
            Stage stage=(Stage) doneButton.getScene().getWindow();
            stage.close();
        }
        connectingLabel.setVisible(true);  //set label to visible
        loadingHbox.setVisible(true);
        bounce(circle1,500);  //play animation on circles
        bounce(circle2,600);
        bounce(circle3,700);
        bounce(circle4,800);
    }

    public void doneOnAction(ActionEvent e){
        Stage stage= (Stage) doneButton.getScene().getWindow();
        stage.close();  //close stage
    }
}
