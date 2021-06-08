/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


import java.util.ArrayList;
import java.sql.*;


public class Database {
//lists for data
   private static ArrayList<Product> products;
//tools for database
   public static Connection conn;
   public static Statement state;
   public static ResultSet result;
    private static ArrayList<Category> categoryList;
    private static ArrayList<Brend> brendList;
//connecting to database
   public static void connectionBD() throws ClassNotFoundException, SQLException {
       conn = null;
       Class.forName("org.apache.derby.jdbc.ClientDriver");
       conn = DriverManager.getConnection("jdbc:derby://localhost:1527/Product_Basket");
       state = conn.createStatement();
   }
   
   public static String MakeTransaction(int id, int amount, String source) throws SQLException{
        result = state.executeQuery(String.format("Select amount from app.Product where id = %s", id));
        int a = Integer.MIN_VALUE;
        while (result.next()){
            a = result.getInt(1);
        }
        if (a==Integer.MIN_VALUE){
            throw new SQLException();
        }else if (a+amount >= 0){
            state.execute(String.format("insert into app.TRANSACTIONS (Product_id, AMOUNT_CHANGE, date, source) values (%s, %s, CURRENT_TIMESTAMP, '%s')", id, amount, source));
            state.execute(String.format("update app.product set amount = amount + %s where id = %s",amount, id));
        }else {}
        result = state.executeQuery("Select id from app.Transactions order by id desc");
        while (result.next()){
            return result.getInt(1)+ " ";
        }
        return "";
   }
//getter for product table
    public static ArrayList<Product> GetProducts() throws SQLException{
        products = new ArrayList<Product>();
            result = state.executeQuery("SELECT app.PRODUCT.ID,app.PRODUCT.NAME,app.PRODUCT.PRICE,app.PRODUCT.MEASUREMENT,app.PRODUCT.AMOUNT,"
                    + "PRODUCT.CATEGORY_ID,PRODUCT.BREND_ID,app.CATEGORY.Name as Category,app.BREND.Name as Brend FROM APP.product \n" +
"INNER JOIN APP.CATEGORY ON CATEGORY_ID = APP.CATEGORY.ID \n" +
"INNER join app.BREND on BREND_ID = app.BREND.ID");
            while(result.next()){
             Product pr = new Product(result.getInt("ID"), result.getString("NAME"),
                     result.getDouble("PRICE"),result.getString("MEASUREMENT"),result.getInt("AMOUNT")
                     ,result.getInt("CATEGORY_ID"),result.getInt("BREND_ID"),result.getString("CATEGORY"),
                     result.getString("BREND"));
              products.add(pr);
             }
        return products;
    }
//closing coonection to database
    public static void сloseDB() throws ClassNotFoundException, SQLException
    {
    conn.close();
    state.close();
    result.close();
    }


    public static ArrayList<Product> filterByCategory(int cat) throws SQLException{
        products = new ArrayList<Product>();
            result = state.executeQuery(String.format("SELECT app.PRODUCT.ID,app.PRODUCT.NAME,app.PRODUCT.PRICE,app.PRODUCT.MEASUREMENT,app.PRODUCT.AMOUNT,"
                    + "PRODUCT.CATEGORY_ID,PRODUCT.BREND_ID,app.CATEGORY.Name as Category,app.BREND.Name as Brend FROM APP.product \n" +
"INNER JOIN APP.CATEGORY ON CATEGORY_ID = APP.CATEGORY.ID \n" +
"INNER join app.BREND on BREND_ID = app.BREND.ID where Product.Category_id = %s",cat));
            while(result.next()){
             Product pr = new Product(result.getInt("ID"), result.getString("NAME"),
                     result.getDouble("PRICE"),result.getString("MEASUREMENT"),result.getInt("AMOUNT")
                     ,result.getInt("CATEGORY_ID"),result.getInt("BREND_ID"),result.getString("CATEGORY"),
                     result.getString("BREND"));
              products.add(pr);
             }
        return products;
    }
    public static ArrayList<Product> filterByBrend(int br) throws SQLException{
        products = new ArrayList<Product>();
            result = state.executeQuery(String.format("SELECT app.PRODUCT.ID,app.PRODUCT.NAME,app.PRODUCT.PRICE,app.PRODUCT.MEASUREMENT,app.PRODUCT.AMOUNT,"
                    + "PRODUCT.CATEGORY_ID,PRODUCT.BREND_ID,app.CATEGORY.Name as Category,app.BREND.Name as Brend FROM APP.product \n" +
"INNER JOIN APP.CATEGORY ON CATEGORY_ID = APP.CATEGORY.ID \n" +
"INNER join app.BREND on BREND_ID = app.BREND.ID where Product.Brend_id = %s",br));
            while(result.next()){
             Product pr = new Product(result.getInt("ID"), result.getString("NAME"),
                     result.getDouble("PRICE"),result.getString("MEASUREMENT"),result.getInt("AMOUNT")
                     ,result.getInt("CATEGORY_ID"),result.getInt("BREND_ID"),result.getString("CATEGORY"),
                     result.getString("BREND"));
              products.add(pr);
             }
        return products;
    }
    
    public static ArrayList<Category> GetCategories() throws SQLException{
        categoryList = new ArrayList<Category>();
        result = state.executeQuery("SELECT * FROM app.category");
        while(result.next()){
            Category category = new Category(result.getInt("ID"), result.getString("NAME"), result.getInt("HALL"));
            categoryList.add(category);
        }
        return categoryList;
    }
//Get all brends
    public static ArrayList<Brend> GetBrends() throws SQLException{
        brendList = new ArrayList<Brend>();
        result = state.executeQuery("SELECT * FROM app.brend");
        while(result.next()){
            Brend brend = new Brend(result.getInt("ID"), result.getString("NAME"), result.getString("COMPANY"));
            brendList.add(brend);
        }
        return brendList;
    }
    
}
