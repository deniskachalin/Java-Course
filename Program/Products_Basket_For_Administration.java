
package Program;

import GUI.GUI;
import database.Database;
import java.sql.SQLException;
import java.text.ParseException;

public class Products_Basket_For_Administration {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException {
        try{
            Database.connectionBD();
        }
        catch (SQLException exp){
            GUI.ErrorMessage("Database is off");
            System.exit(0);
        }
        GUI g = new GUI("Products basket");
    }
}
