package RestaurantSystem;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;


public class FoodController {
    //fxml id for food item
    @FXML
    private ImageView image;

    @FXML
    private Label nameLabel,priceLabel;

    private Food food;
    private MyListener myListener;

    //set up each pane for food when loaded
    public void setData(Food f, MyListener myListener){
        this.food=f;
        this.myListener=myListener;
        nameLabel.setText(f.getName());
        priceLabel.setText("RM "+f.getPrice());
        Image img=new Image(getClass().getResourceAsStream(f.getImg()));
        image.setImage(img);
    }

    //call method in interface when enter pressed or mouse clicked
    public void click(MouseEvent e) {
        myListener.onClickListener(food);
    }
    public void enter(KeyEvent e) {
        if(e.getCode()== KeyCode.ENTER )
        myListener.onClickListener(food);
    }
}
