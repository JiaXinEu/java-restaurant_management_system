package RestaurantSystem;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;

//class to initialise sample data for menu
public class Menu {
    private static BigDecimal bd;
    private static ArrayList<Food>menu=new ArrayList<>();
    public static void addMenu() {
        Food f;
        f = new Food();
        f.setName("M1.Chicken Fried Rice");
        f.setType("Main");
        bd = new BigDecimal(10.90).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/chickenfriedrice.png");
        f.setColor("ffd28f");
        menu.add(f);
        //code omitted

        f = new Food();
        f.setName("M2.Seafood Fried Rice");
        f.setType("Main");
        bd = new BigDecimal(12.50).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/seafoodfriedrice.png");
        f.setColor("8dbd1f");
        menu.add(f);

        f = new Food();
        f.setName("M3.Nasi Lemak");
        f.setType("Main");
        bd = new BigDecimal(11.90).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/nasilemak.png");
        f.setColor("09581e");
        menu.add(f);

        f = new Food();
        f.setName("M4.Sweet n Sour Fish");
        f.setType("Main");
        bd = new BigDecimal(12.00).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/sweetnsourfish.png");
        f.setColor("df6a5d");
        menu.add(f);

        f = new Food();
        f.setName("M5.Burritos Bowl");
        f.setType("Main");
        bd = new BigDecimal(13.90).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/burritobowl.png");
        f.setColor("75d7a6");
        menu.add(f);

        f = new Food();
        f.setName("M6.Javanese Noodle");
        f.setType("Main");
        bd = new BigDecimal(9.60).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/javanesenoodle.png");
        f.setColor("de7d54");
        menu.add(f);

        f = new Food();
        f.setName("M7.Kimchi Ramen");
        f.setType("Main");
        bd = new BigDecimal(11.60).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/kimchiramen.png");
        f.setColor("ea663e");
        menu.add(f);

        f = new Food();
        f.setName("M8.Chicken Bolognese");
        f.setType("Main");
        bd = new BigDecimal(12.00).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/spaghettibolognese.png");
        f.setColor("fa5300");
        menu.add(f);

        f = new Food();

        f.setName("M9.Fried Noodle");
        f.setType("Main");
        bd = new BigDecimal(9.90).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/friednoodle.png");
        f.setColor("cd902d");
        menu.add(f);

        f = new Food();
        f.setName("M10.Mac N Cheese");
        f.setType("Main");
        bd = new BigDecimal(13.00).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/macncheese.png");
        f.setColor("edc95e");
        menu.add(f);

        f = new Food();
        f.setName("M11.Fish N Chips");
        f.setType("Main");
        bd = new BigDecimal(14.00).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/fishnchips.png");
        f.setColor("dab971");
        menu.add(f);

        f = new Food();
        f.setName("M12.Chicken Porridge");
        f.setType("Main");
        bd = new BigDecimal(9.60).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/chickenporridge.png");
        f.setColor("8f8ab7");
        menu.add(f);

        f = new Food();
        f.setName("M13.Seafood TomYum");
        f.setType("Main");
        bd = new BigDecimal(15.90).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/seafoodtomyum.png");
        f.setColor("dfa934");
        menu.add(f);

        f = new Food();
        f.setName("M14.Cheese Pizza");
        f.setType("Main");
        bd = new BigDecimal(15.00).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/pizza.png");
        f.setColor("ffa970");
        menu.add(f);

        f = new Food();
        f.setName("A1.Caesar Salad");
        f.setType("Appetizer");
        bd = new BigDecimal(8.00).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/ceasarsalad.png");
        f.setColor("2fc15b");
        menu.add(f);

        f = new Food();
        f.setName("A2.Coleslaw");
        f.setType("Appetizer");
        bd = new BigDecimal(8.00).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/coleslaw.png");
        f.setColor("bc679e");
        menu.add(f);

        f = new Food();
        f.setName("A3.Dumplings");
        f.setType("Appetizer");
        bd = new BigDecimal(10.00).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/dumpling.png");
        f.setColor("bc7824");
        menu.add(f);

        f = new Food();
        f.setName("A4.Canapes");
        f.setType("Appetizer");
        bd = new BigDecimal(13.00).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/canapes.png");
        f.setColor("fa5d1e");
        menu.add(f);

        f = new Food();
        f.setName("A5.Mushroom Soup");
        f.setType("Appetizer");
        bd = new BigDecimal(9.50).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/mushroomsoup.png");
        f.setColor("bf894a");
        menu.add(f);

        f = new Food();
        f.setName("A6.Spring Roll");
        f.setType("Appetizer");
        bd = new BigDecimal(9.50).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/springroll.png");
        f.setColor("d19410");
        menu.add(f);

        f = new Food();
        f.setName("A7.Chicken Nachos");
        f.setType("Appetizer");
        bd = new BigDecimal(12.60).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/nachos.png");
        f.setColor("dc3004");
        menu.add(f);

        f = new Food();
        f.setName("A8.Curly Fries");
        f.setType("Appetizer");
        bd = new BigDecimal(9.20).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/curlyfries.png");
        f.setColor("c0b026");
        menu.add(f);

        f = new Food();
        f.setName("A9.Bruschetta");
        f.setType("Appetizer");
        bd = new BigDecimal(9.20).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/bruschetta.png");
        f.setColor("aacf3a");
        menu.add(f);

        f = new Food();
        f.setName("A10.Naan");
        f.setType("Appetizer");
        bd = new BigDecimal(9.20).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/naan.png");
        f.setColor("c0b026");
        menu.add(f);

        f = new Food();
        f.setName("D1.Apple Juice");
        f.setType("Dessert");
        bd = new BigDecimal(6.50).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/applejuice.png");
        f.setColor("e7a408");
        menu.add(f);

        f = new Food();
        f.setName("D2.Orange Juice");
        f.setType("Dessert");
        bd = new BigDecimal(6.00).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/orangejuice.png");
        f.setColor("e75e08");
        menu.add(f);

        f = new Food();
        f.setName("D3.Grape Juice");
        f.setType("Dessert");
        bd = new BigDecimal(6.50).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/grapejuice.png");
        f.setColor("94054d");
        menu.add(f);

        f = new Food();
        f.setName("D4.Chocolate Cake");
        f.setType("Dessert");
        bd = new BigDecimal(10.50).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/chocolatecake.png");
        f.setColor("542b03");
        menu.add(f);

        f = new Food();
        f.setName("D5.Egg Tart");
        f.setType("Dessert");
        bd = new BigDecimal(6.50).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/eggtart.png");
        f.setColor("e0bb1a");
        menu.add(f);

        f = new Food();
        f.setName("D6.Fruit Platter");
        f.setType("Dessert");
        bd = new BigDecimal(9.90).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/fruitplatter.png");
        f.setColor("cd1e18");
        menu.add(f);

        f = new Food();
        f.setName("D7.Hot Latte");
        f.setType("Dessert");
        bd = new BigDecimal(9.50).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/hotlatte.png");
        f.setColor("d97930");
        menu.add(f);

        f = new Food();
        f.setName("D8.Iced Mocha");
        f.setType("Dessert");
        bd = new BigDecimal(10.00).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/icedmocha.png");
        f.setColor("4e2404");
        menu.add(f);

        f = new Food();
        f.setName("D9.Panna Cotta");
        f.setType("Dessert");
        bd = new BigDecimal(6.90).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/pannacotta.png");
        f.setColor("d59620");
        menu.add(f);

        f = new Food();
        f.setName("D10. Smoothie");
        f.setType("Dessert");
        bd = new BigDecimal(8.50).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/smoothie.png");
        f.setColor("e133a7");
        menu.add(f);

        f = new Food();
        f.setName("D11. Sundae");
        f.setType("Dessert");
        bd = new BigDecimal(8.90).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/sundae.png");
        f.setColor("281406");
        menu.add(f);

        f = new Food();
        f.setName("D12. Waffle");
        f.setType("Dessert");
        bd = new BigDecimal(9.90).setScale(2, RoundingMode.HALF_UP);
        f.setPrice(bd);
        f.setImg("/image/waffle.png");
        f.setColor("e23c3c");
        menu.add(f);
    }

    //write all food to file
    public static void writeMenu(){
        addMenu();
        try {
            FileOutputStream fos=new FileOutputStream("src/data/Menu.bin");
            ObjectOutputStream oos=new ObjectOutputStream(fos);
            oos.writeObject(menu);
            fos.close();
            oos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
