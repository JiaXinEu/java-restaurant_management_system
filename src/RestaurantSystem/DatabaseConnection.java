package RestaurantSystem;
import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {  //create database restaurant and import restaurant.sql from src/data
    public Connection databaseLink;

    public Connection getConnection(){
        String databaseName ="restaurant";
        String databaseUser ="root";
        String databasePassword ="";
        String url ="jdbc:mysql://localhost:3306/"+ databaseName;

        //install mysql.connection.jar to work
        try{
            Class.forName("com.mysql.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url,databaseUser,databasePassword);

        }catch (Exception e){
            System.out.println(e.toString());
        }

        return databaseLink;
    }
}
