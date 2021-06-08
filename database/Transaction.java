
package database;

import java.sql.*;

public class Transaction {
    private int id;
    private int product_id;
    private int amount_change;
    private Timestamp date;
    private String type;
    
    public Transaction(int id, int product_id, int amount_change, Timestamp date, String type){
        this.id = id;
        this.product_id = product_id;
        this.amount_change = amount_change;
        this.date = date;
        this.type = type;
    }

    public int getId() {
        return id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public int getAmount_change() {
        return amount_change;
    }

    public Timestamp getDate() {
        return date;
    }

    public String getType() {
        return type;
    }
    
}
