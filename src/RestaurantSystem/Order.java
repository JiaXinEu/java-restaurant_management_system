package RestaurantSystem;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

//class to hold all orders in cart
public class Order {
    private BigDecimal bd = new BigDecimal(0.00).setScale(2, RoundingMode.HALF_UP);
    private BigDecimal total=bd;
    private ArrayList<Cart> cartArr=new ArrayList<>();

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public ArrayList<Cart> getCart(){
        return cartArr;
    }

    //add food selected and the quantity to cart
    public void addCart(Cart c){
        Boolean repeated=false;
        for(Cart cart: cartArr){
            if(cart.getFood().getName().equals(c.getFood().getName())){
                cart.setQuantity(cart.getQuantity()+c.getQuantity());  //increment quantity if food is already in cart
                repeated=true;
            }
        }
        if(!repeated){
            cartArr.add(c);
        }
    }
}
