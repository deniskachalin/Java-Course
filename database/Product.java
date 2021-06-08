
package database;


public class Product {
    private int id;
    private String name;
    private double price;
    private String measurement;
    private int amount;
    private int category_id;
    private int brend_id;
    private String category;
    private String brend;
    
    public Product(int id,String name, double price, String measurement, int amount, int category_id, int brend_id, String category, String brend){
        this.id = id;
        this.name = name;
        this.price = price;
        this.measurement = measurement;
        this.amount = amount;
        this.category_id = category_id;
        this.brend_id = brend_id;
        this.category = category;
        this.brend = brend;
    }
    
    public Product(String name, double price, String measurement, int amount, int category_id, int brend_id){
        this.name = name;
        this.price = price;
        this.measurement = measurement;
        this.amount = amount;
        this.category_id = category_id;
        this.brend_id = brend_id;
    }


    public int getCategory_id() {
        return category_id;
    }

    public int getBrend_id() {
        return brend_id;
    }

    public String getCategory() {
        return category;
    }

    public String getBrend() {
        return brend;
    }

    public int getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public String getMeasurement() {
        return measurement;
    }  
}
