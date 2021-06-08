
package database;


import java.util.ArrayList;
import java.sql.*;
import GUI.*;


public class Database {
//lists for data
    private static ArrayList<Product> productList;
    private static ArrayList<Category> categoryList;
    private static ArrayList<Brend> brendList;
    private static ArrayList<Transaction> transactionList;
//tools for database
    public static Connection connection;
    public static Statement statement;
    public static ResultSet resultSet;
//connecting to database
    public static void connectionBD() throws ClassNotFoundException, SQLException{
        connection = null;
        Class.forName("org.apache.derby.jdbc.ClientDriver");
        connection = DriverManager.getConnection("jdbc:derby://localhost:1527/Product_Basket");
        statement = connection.createStatement();
    }
//Get all transactions
    public static ArrayList<Transaction> GetTransactions() throws SQLException{
        transactionList = new ArrayList<Transaction>();
        resultSet = statement.executeQuery("SELECT * FROM app.transactions");
        while(resultSet.next()){
            Transaction transaction = new Transaction(resultSet.getInt("ID"), resultSet.getInt("Product_ID"), resultSet.getInt("Amount_Change"),resultSet.getTimestamp("DATE"), resultSet.getString("Source"));
            transactionList.add(transaction);
            }
        return transactionList;
    }
//Get all transactions for specific id
    public static ArrayList<Transaction> GetTransactionsForID(int id) throws SQLException {
        transactionList = new ArrayList<Transaction>();
        resultSet = statement.executeQuery(String.format("SELECT * FROM app.transactions where %s = Product_ID",id));
        while(resultSet.next()){
            Transaction transaction = new Transaction(resultSet.getInt("ID"), resultSet.getInt("Product_ID"), resultSet.getInt("Amount_Change"),resultSet.getTimestamp("DATE"),resultSet.getString("Source"));
            transactionList.add(transaction);
            }
        return transactionList;
    }
//Make transaction
    public static void MakeTransaction(int id, int amount, String source) throws SQLException, ExceptionForTransanctions{
        resultSet = statement.executeQuery(String.format("Select amount from app.Product where id = %s", id));
        int a = Integer.MIN_VALUE;
        while (resultSet.next()){
            a = resultSet.getInt(1);
        }
        if (a==Integer.MIN_VALUE){
            throw new SQLException();
        }else if (a+amount >= 0){
            statement.execute(String.format("insert into app.TRANSACTIONS (Product_id, AMOUNT_CHANGE, date, source) values (%s, %s, CURRENT_TIMESTAMP, '%s')", id, amount, source));
            statement.execute(String.format("update app.product set amount = amount + %s where id = %s",amount, id));
        }else {
            throw new ExceptionForTransanctions();
        }
    }
//Get all products
    public static ArrayList<Product> GetProducts() throws SQLException{
        productList = new ArrayList<Product>();
        resultSet = statement.executeQuery("SELECT app.PRODUCT.ID,app.PRODUCT.NAME,app.PRODUCT.PRICE,app.PRODUCT.MEASUREMENT,app.PRODUCT.AMOUNT, PRODUCT.CATEGORY_ID,PRODUCT.BREND_ID,app.CATEGORY.Name as Category,app.BREND.Name as Brend FROM APP.product INNER JOIN APP.CATEGORY ON CATEGORY_ID = APP.CATEGORY.ID INNER join app.BREND on BREND_ID = app.BREND.ID");
            while(resultSet.next()){
            Product product = new Product(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getDouble("PRICE"),resultSet.getString("MEASUREMENT"),resultSet.getInt("AMOUNT") ,resultSet.getInt("CATEGORY_ID"),resultSet.getInt("BREND_ID"),resultSet.getString("CATEGORY"), resultSet.getString("BREND"));
            productList.add(product);
            }
        return productList;
    }
//Get all categories
    public static ArrayList<Category> GetCategories() throws SQLException{
        categoryList = new ArrayList<Category>();
        resultSet = statement.executeQuery("SELECT * FROM app.category");
        while(resultSet.next()){
            Category category = new Category(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getInt("HALL"));
            categoryList.add(category);
        }
        return categoryList;
    }
//Get all brends
    public static ArrayList<Brend> GetBrends() throws SQLException{
        brendList = new ArrayList<Brend>();
        resultSet = statement.executeQuery("SELECT * FROM app.brend");
        while(resultSet.next()){
            Brend brend = new Brend(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getString("COMPANY"));
            brendList.add(brend);
        }
        return brendList;
    }
//closing coonection to database
    public static void сloseDB() throws ClassNotFoundException, SQLException
    {
        connection.close();
        statement.close();
        resultSet.close();
    }
//Delete product from database
    public static void deleteProduct(int id) throws SQLException{
        {
            statement.execute(String.format("delete from app.transactions where %s=product_id",id));
            statement.execute(String.format("Delete From app.product where %s=ID", id));
        }
    }
//Delete category from database
    public static void deleteCategory(int id) throws SQLException{
        {
            statement.execute(String.format("Delete From app.Category where id=%s and not exists (select * from app.product where Category_id = %s)", id,id));
        }
    }
//Check category for being used
    public static String deleteCategoryCondition(int id) throws SQLException{
        resultSet = statement.executeQuery(String.format("select 'Exists' as IF from app.product where Category_id = %s",id));
        while (resultSet.next()){
            String sr = resultSet.getString(1);
            return sr;
        }
        return " ";
    }
//Check brend for being used
    public static String deleteBrendIf(int id) throws SQLException{
        resultSet = statement.executeQuery(String.format("select 'Exists' as IF from app.product where Brend_id = %s",id));
        while (resultSet.next()){
            String sr = resultSet.getString("IF");
            return sr;
        }
        return " ";
    }
//Delete brend from database
    public static void deleteBrend(int id) throws SQLException{
        {
        statement.execute(String.format("Delete From app.brend where id=%s and not exists (select * from app.product where brend_id = %s)", id,id));
        }
    }
//Insert product to database
    public static void insertProduct(Product p) throws SQLException{   
        statement.execute(String.format("insert into app.product (name,price,measurement,amount,brend_id,category_id) values ('%s',%s,'%s',%s,%s,%s)",p.getName(),p.getPrice(),p.getMeasurement(),p.getAmount(),p.getBrend_id(),p.getCategory_id()));
    }
//Insert brend to database
    public static void insertBrend(Brend b) throws SQLException{   
        statement.execute(String.format("insert into app.brend (Name,Company) values ('%s','%s')",b.getName(),b.getCompany()));
    }
//Insert category to database
    public static void insertCategory(Category c) throws SQLException{   
        statement.execute(String.format("insert into app.category (Name,Hall) values ('%s',%s)",c.getName(),c.getHall()));
    }
//Change product in database
    public static void changeProduct(Product p, int id) throws SQLException{   
        statement.execute(String.format("update app.product set name = '%s',price=%s,measurement='%s', amount=%s,brend_id=%s,category_id=%s where id = %s",p.getName(),p.getPrice(),p.getMeasurement(),p.getAmount(),p.getBrend_id(),p.getCategory_id(),id));
    }
//Change brend in database
    public static void changeBrend(Brend b, int id) throws SQLException{   
        statement.execute(String.format("update app.brend set name ='%s', company ='%s' where ID = %s",b.getName(),b.getCompany(),id));
    }
//Change category in database
    public static void changeCategory(Category c, int id) throws SQLException{   
        statement.execute(String.format("update app.Category set name ='%s', hall =%s where ID = %s",c.getName(),c.getHall(),id));
    }
//Find product in database
    public static Product selectProduct(int id) throws SQLException{
        resultSet = statement.executeQuery(String.format("SELECT app.PRODUCT.ID,app.PRODUCT.NAME,app.PRODUCT.PRICE,app.PRODUCT.MEASUREMENT,app.PRODUCT.AMOUNT,PRODUCT.CATEGORY_ID,PRODUCT.BREND_ID,app.CATEGORY.Name as Category,app.BREND.Name as Brend FROM APP.product INNER JOIN APP.CATEGORY ON CATEGORY_ID = APP.CATEGORY.ID INNER join app.BREND on BREND_ID = app.BREND.ID where PRODUCT.ID = %s",id));
        while(resultSet.next()){
            Product pr = new Product(resultSet.getInt("ID"), resultSet.getString("NAME"),resultSet.getDouble("PRICE"),resultSet.getString("MEASUREMENT"),resultSet.getInt("AMOUNT"),resultSet.getInt("CATEGORY_ID"),resultSet.getInt("BREND_ID"),resultSet.getString("CATEGORY"),resultSet.getString("BREND"));
            return pr;
        }
        return null;
    }
//Find brend in database
    public static Brend selectBrend(int id) throws SQLException{
        resultSet = statement.executeQuery(String.format("SELECT * FROM app.brend where ID = %s",id));
        while(resultSet.next()){
            Brend br = new Brend(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getString("COMPANY"));
            brendList.add(br);
            return br;
        }
        return null;
    }
//Find category in database
    public static Category selectCategory(int id) throws SQLException{
        resultSet = statement.executeQuery(String.format("SELECT * FROM app.category where ID = %s",id));
        while(resultSet.next()){
            Category cat = new Category(resultSet.getInt("ID"), resultSet.getString("NAME"), resultSet.getInt("HALL"));
            categoryList.add(cat);
            return cat;
        }
        return null;
    }
//Filter for product by category
    public static ArrayList<Product> filterByCategory(int cat) throws SQLException{
        productList = new ArrayList<Product>();
        resultSet = statement.executeQuery(String.format("SELECT app.PRODUCT.ID,app.PRODUCT.NAME,app.PRODUCT.PRICE,app.PRODUCT.MEASUREMENT,app.PRODUCT.AMOUNT,PRODUCT.CATEGORY_ID,PRODUCT.BREND_ID,app.CATEGORY.Name as Category,app.BREND.Name as Brend FROM APP.product INNER JOIN APP.CATEGORY ON CATEGORY_ID = APP.CATEGORY.ID INNER join app.BREND on BREND_ID = app.BREND.ID where Product.Category_id = %s",cat));
        while(resultSet.next()){
            Product pr = new Product(resultSet.getInt("ID"), resultSet.getString("NAME"),resultSet.getDouble("PRICE"),resultSet.getString("MEASUREMENT"),resultSet.getInt("AMOUNT"),resultSet.getInt("CATEGORY_ID"),resultSet.getInt("BREND_ID"),resultSet.getString("CATEGORY"),resultSet.getString("BREND"));
            productList.add(pr);
        }
        return productList;
    }
//Filter for product by brend
    public static ArrayList<Product> filterByBrend(int br) throws SQLException{
        productList = new ArrayList<Product>();
        resultSet = statement.executeQuery(String.format("SELECT app.PRODUCT.ID,app.PRODUCT.NAME,app.PRODUCT.PRICE,app.PRODUCT.MEASUREMENT,app.PRODUCT.AMOUNT,PRODUCT.CATEGORY_ID,PRODUCT.BREND_ID,app.CATEGORY.Name as Category,app.BREND.Name as Brend FROM APP.product INNER JOIN APP.CATEGORY ON CATEGORY_ID = APP.CATEGORY.ID INNER join app.BREND on BREND_ID = app.BREND.ID where Product.Brend_id = %s",br));
        while(resultSet.next()){
                Product pr = new Product(resultSet.getInt("ID"), resultSet.getString("NAME"),resultSet.getDouble("PRICE"),resultSet.getString("MEASUREMENT"),resultSet.getInt("AMOUNT"),resultSet.getInt("CATEGORY_ID"),resultSet.getInt("BREND_ID"),resultSet.getString("CATEGORY"),resultSet.getString("BREND"));
                productList.add(pr);
        }
        return productList;
    }
}
