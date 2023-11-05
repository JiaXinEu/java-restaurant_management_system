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
import javafx.scene.control.*;
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

public class AppetizerController implements Initializable{
    //fxml id for all referenced components on appetizer menu page
    @FXML
    private GridPane grid;
    @FXML
    private AnchorPane appetizerAnchor;
    @FXML
    private StackPane menuStackPane;
    @FXML
    private ScrollPane scroll;
    @FXML
    private VBox chosenFood;
    @FXML
    private ImageView foodImage;
    @FXML
    private Label foodNameLabel,foodPriceLabel,checkoutErrorLabel,totalLabel;
    @FXML
    private Button logoutButton,addButton,minusButton,addToCartButton,mainButton,checkoutButton,dessertButton;
    @FXML
    private TextField qtyLabel;

    private ArrayList<Food> appetizer = new ArrayList<Food>();
    private Image img;
    private MyListener myListener;  //interface
    private static BigDecimal bd;
    public static Order order=new Order();  //holds information of current order


    //method to load data from file into arraylist
    private ArrayList <Food> getData(){
        ArrayList<Food> appetizer=new ArrayList<>();
        try {
            FileInputStream fis=new FileInputStream("src/data/Menu.bin");
            ObjectInputStream ois=new ObjectInputStream(fis);
            appetizer=(ArrayList<Food>) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (EOFException e){

        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return appetizer;
    }

    //set chosen food information on larger display
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

    //initialize scene to load each food onto scene
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        scroll.setFocusTraversable(true);
        totalLabel.setText(String.valueOf(order.getTotal()));
        appetizer.addAll(getData());
        for(Food f:appetizer) {
            if (f.getType().equals("Appetizer")) {
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
        //load menu scene into each grid on grid pane
        try {
            for (int i = 0; i < appetizer.size(); i++) {
                if(appetizer.get(i).getType().equals("Appetizer")) {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getResource("item.fxml"));
                    AnchorPane anchorPane = loader.load();
                    FoodController foodController = loader.getController();
                    foodController.setData(appetizer.get(i), myListener);
                    if (column == 3) {
                        column = 0;
                        row++;
                    }

                    //layout of grid pane
                    grid.setHgap(50);
                    grid.setVgap(30);
                    grid.add(anchorPane, column++, row, 1, 1);
                    grid.setMinSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                    grid.setPrefSize(Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE);
                    grid.setMaxSize(Region.USE_PREF_SIZE, Region.USE_PREF_SIZE);
                    grid.setMargin(anchorPane, new Insets(10));
                }
                if(appetizer.get(i).getName().indexOf("Juice")!=-1){
                    CheckoutController.freeDrink= appetizer.get(i);
                }
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void logoutButtonOnAction(ActionEvent e){
        order=new Order();
        CheckoutController.order=new Order();
        MainController.order=new Order();
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
            //set stage to centre of screen
            Rectangle2D primScreenBounds = Screen.getPrimary().getVisualBounds();
            stage.setX((primScreenBounds.getWidth() - stage.getWidth()) / 2);
            stage.setY((primScreenBounds.getHeight() - stage.getHeight()) / 2);
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    //add or subtract quantity
    public void qtyButtonOnAction(ActionEvent e){
        if (e.getSource()==addButton) {
            qtyLabel.setText(String.valueOf(Integer.parseInt(qtyLabel.getText()) + 1));
        }else{
            if(Integer.parseInt(qtyLabel.getText())>0) {  //only subtract if more than 0
                qtyLabel.setText(String.valueOf(Integer.parseInt(qtyLabel.getText()) - 1));
            }
        }
    }

    //add food and quantity into arraylist when action on button
    public void addToCart(ActionEvent e) {
        int qty = Integer.parseInt(qtyLabel.getText());
        double total=Double.parseDouble(totalLabel.getText());
        if (qty != 0) {
            Food f = new Food();
            f.setName(foodNameLabel.getText());
            for (Food food : appetizer) {
                if (food.getName().equals(f.getName())) {
                    f = food;
                    break;
                }
            }
            order.addCart(new Cart(f, Integer.parseInt(qtyLabel.getText())));
            bd=BigDecimal.valueOf(total).add(BigDecimal.valueOf(qty).multiply(f.getPrice()));
            order.setTotal(bd);
            totalLabel.setText(String.valueOf(order.getTotal()));  //increment value of total
            qtyLabel.setText("0");  //set text field back to 0
            scroll.requestFocus();
        }
    }

    public void switchMenu(ActionEvent e){
        Scene scene;
        //get stage where button is clicked
        scene = mainButton.getScene();
        Parent root;
        String file="";
        //load different fxml file according to button clicked
        if(e.getSource()==mainButton){
            file="mainmenu.fxml";
            MainController.order=order;
        }
        else if(e.getSource()==dessertButton){
            file="dessertmenu.fxml";
            DessertController.order=order;
        }else if(e.getSource()==checkoutButton){
            if(order.getCart().size()==0){
                //error animation if cart is empty
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
            //only allow checkout if there is food ordered
            }else {
                file = "checkout.fxml";
                CheckoutController.order = order;
            }
        }
        //set scene to selected scene
        try {
            root = FXMLLoader.load(getClass().getResource(file));
            root.translateXProperty().set(scene.getWidth());
            menuStackPane.getChildren().add(root);
            //set timeline for scene sliding animation
            Timeline timeline =new Timeline();
            //scene ease in from right to left in 0.5 seconds
            KeyValue kv= new KeyValue(root.translateXProperty(),0, Interpolator.EASE_IN);
            KeyFrame kf=new KeyFrame(Duration.seconds(0.5),kv);
            timeline.getKeyFrames().add(kf);
            timeline.setOnFinished(event ->{  //remove previous pane from parent after animation
                menuStackPane.getChildren().remove(appetizerAnchor);
            });
            timeline.play();
        } catch (IOException ie) {
            ie.printStackTrace();
        }
    }

    //different components request focus if for different key pressed on different previously focused components
    //for easier control on device without mouse
    public void switchFocus(KeyEvent e) {
        if (e.getCode() == KeyCode.LEFT) {
            if (e.getSource() == scroll) {
                addButton.requestFocus();
            } else if (e.getSource() == qtyLabel) {
                addButton.requestFocus();
            }
        }else if(e.getCode()==KeyCode.ENTER){
            if(e.getSource()==qtyLabel) {
                addToCartButton.requestFocus();
            }else if(e.getSource()==scroll){
                AnchorPane pane= (AnchorPane) grid.getChildren().get(0);
                pane.getChildren().get(0).requestFocus();
            }
        }else if(e.getCode()==KeyCode.RIGHT){
            if(e.getSource()==qtyLabel){
                minusButton.requestFocus();
            }
        }

    }

}


