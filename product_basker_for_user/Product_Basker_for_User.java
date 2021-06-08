/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package product_basker_for_user;

import GUI.GUI;
import database.Database;
import java.sql.SQLException;
import java.text.ParseException;


public class Product_Basker_for_User {

    public static void main(String[] args) throws ClassNotFoundException, SQLException, ParseException  {
        try{
        Database.connectionBD();
        }
        catch (SQLException exp){
            GUI.ErrorMessage("Database is off");
            System.exit(0);
        }
            GUI g = new GUI("Products basket");
            g.setVisible(true);
    }
    
}
