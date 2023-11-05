package RestaurantSystem;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.*;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

//similar to appetizer controller but only load food of type main
public class MainController implements Initializable{
    //fxml id for main menu page
    @FXML
    private GridPane grid;

    @FXML
    private AnchorPane mainAnchor;

    @FXML
    private ScrollPane scroll;

    @FXML
    private VBox chosenFood;

    @FXML
    private ImageView foodImage;

    @FXML
    private Label foodNameLabel;

    @FXML
    private Label foodPriceLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private Button addButton;

    @FXML
    private Button minusButton;

    @FXML
    private Button addToCartButton;

    @FXML
    private Button appetizerButton;

    @FXML
    private Button dessertButton;

    @FXML
    private Label totalLabel;

    @FXML
    private Button checkoutButton;

    @FXML
    private TextField qtyLabel;

    @FXML
    private Label checkoutErrorLabel;

    private ArrayList<Food> main = new ArrayList<Food>();
    private Image img;
    private MyListener myListener;
    private static BigDecimal bd;
    public static Order order;


    private ArrayList <Food> getData(){
        ArrayList<Food> main=new ArrayList<>();
        try {
            FileInputStream fis=new FileInputStream("src/data/Menu.bin");
            ObjectInputStream ois=new ObjectInputStream(fis);
            main=(ArrayList<Food>) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e){

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return main;
    }

    private void setChosenFood(Food food){
        foodNameLabel.setText(food.getName());
        foodPriceLabel.setText("RM "+food.getPrice());
        qtyLabel.setText("0");
        img=new Image(getClass().getResourceAsStream(food.getImg()));
        foodImage.setImage(img);
        chosenFood.setStyle("-fx-background-color:#"+food.getColor()+";\n" +
                "    -fx-background-radius:30;");
        addButton.setStyle("-fx-background-color:#"+food.getColor()+";");
        minusButton.setStyle("-fx-background-color:#"+food.getColor()+";");
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scroll.setFocusTraversable(true);
        scroll.requestFocus();
        totalLabel.setText(String.valueOf(order.getTotal()));
        main.addAll(getData());
        for(Food f:main) {
            if (f.getType().equals("Main")) {
                setChosenFood(f);
                myListener = new MyListener() {
                    @Override
                    public void onClickListener(Food food) {
                        setChosenFood(food);
                    }
                };
                break;
            }
        }
        int row=1;
        int column =0;
        try {
            for (int i = 0; i < main.size(); i++) {
                if (main.get(i).getType().equals("Main")) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("item.fxml"));
                    AnchorPane anchorPane = loader.load();
                    FoodController foodController = loader.getController();
                    foodController.setData(main.get(i), myListener);

                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    grid.setHgap(50);
                    grid.setVgap(30);
                    grid.add(anchorPane, column++, row, 1, 1);
                    grid.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                    grid.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                    grid.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                    grid.setMargin(anchorPane, new Insets(10));
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logoutButtonOnAction(ActionEvent e){
        order=new Order();
        AppetizerController.order=new Order();
        CheckoutController.order=new Order();
        DessertController.order=new Order();
        EndController.admin="Guest";
        Stage stage;
        //get stage where button is clicked
        stage = (Stage) logoutButton.getScene().getWindow();
        Parent root;
        //set scene to startup scene
        try {
            root = FXMLLoader.load(getClass().getResource("startup.fxml"));
            Scene scene = new Scene(root,600,400);
            stage.setScene(scene);
            stage.show();
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void qtyButtonOnAction(ActionEvent e){
        if (e.getSource()==addButton) {
            qtyLabel.setText(String.valueOf(Integer.parseInt(qtyLabel.getText()) + 1));
        }else{
            if(Integer.parseInt(qtyLabel.getText())>0) {
                qtyLabel.setText(String.valueOf(Integer.parseInt(qtyLabel.getText()) - 1));
            }
        }
    }

    public void addToCart(ActionEvent e) {
        int qty = Integer.parseInt(qtyLabel.getText());
        double total=Double.parseDouble(totalLabel.getText());
        if (qty != 0) {
            Food f = new Food();
            f.setName(foodNameLabel.getText());
            for (Food food : main) {
                if (food.getName().equals(f.getName())) {
                    f = food;
                    break;
                }
            }
            order.addCart(new Cart(f, Integer.parseInt(qtyLabel.getText())));
            bd=BigDecimal.valueOf(total).add(BigDecimal.valueOf(qty).multiply(f.getPrice()));
            order.setTotal(bd);
            totalLabel.setText(String.valueOf(order.getTotal()));
            qtyLabel.setText("0");
        }
    }

    public void switchMenu(ActionEvent e){
        Scene scene;
        //get stage where button is clicked
        scene= appetizerButton.getScene();
        StackPane menuPane= (StackPane) scene.getRoot();
        Parent root;
        String file="";
        if(e.getSource()==appetizerButton) {
            file = "appetizermenu.fxml";
            AppetizerController.order=order;
        }
        else if(e.getSource()==dessertButton){
            file="dessertmenu.fxml";
            DessertController.order=order;
        }else if(e.getSource()==checkoutButton){
            if(order.getCart().size()==0){
                checkoutErrorLabel.setText("No food in cart!");
                //translate slightly and return to position in 0.1s for 5 times, then hide message
                TranslateTransition move=new TranslateTransition();
                move.setByX(2);
                move.setDuration(Duration.millis(100));
                move.setCycleCount(5);
                move.setAutoReverse(true);
                move.setNode(checkoutErrorLabel);
                move.setOnFinished(event-> {
                    checkoutErrorLabel.setText("");
                });
                move.play();
                return;
            }else {
                file = "checkout.fxml";
                CheckoutController.order = order;
            }
        }
        //set scene to startup scene
        try {
            root = FXMLLoader.load(getClass().getResource(file));
            if(e.getSource()==checkoutButton || e.getSource()==dessertButton){
                root.translateXProperty().set(scene.getWidth());
            }else {
                root.translateXProperty().set(scene.getWidth()*-1);
            }
            menuPane.getChildren().add(root);
            Timeline timeline =new Timeline();
            KeyValue kv= new KeyValue(root.translateXProperty(),0, Interpolator.EASE_IN);
            KeyFrame kf=new KeyFrame(Duration.seconds(0.5),kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event ->{
                menuPane.getChildren().remove(mainAnchor);
            });
            timeline.play();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    public void switchFocus(KeyEvent e) {
        if (e.getCode() == KeyCode.LEFT) {
            if (e.getSource() == scroll) {
                addButton.requestFocus();
            } else if (e.getSource() == qtyLabel) {
                addButton.requestFocus();
            }
        } else if (e.getCode() == KeyCode.ENTER) {
            if (e.getSource() == qtyLabel) {
                addToCartButton.requestFocus();
            } else if (e.getSource() == scroll) {
                AnchorPane pane = (AnchorPane) grid.getChildren().get(0);
                pane.getChildren().get(0).requestFocus();
            }
        } else if (e.getCode() == KeyCode.RIGHT) {
            if (e.getSource() == qtyLabel) {
                minusButton.requestFocus();
            }
        }
    }
}
