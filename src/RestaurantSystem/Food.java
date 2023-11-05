package RestaurantSystem;

import java.io.Serializable;
import java.math.BigDecimal;

//class for food
public class Food implements Serializable {
    private String name;  //name with code of food
    private String type;  //type of food
    private BigDecimal price;  //single price of food, BigDecimal to keep ending 0 after decimal point
    private String img;  //string to image file
    private String color;  //string to colour for styling background

    //getters and setters
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
