/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


public class Product {
    private int id;
    private String name;
    private double price;
    private String measurement;
    private int amount;
    private int cat_id;
    private int br_id;
    private String cat;
    private String br;
    
    public Product(int id,String name, double price, String measurement, int amount, int cat_id, int br_id,
            String cat, String br){
        this.id = id;
        this.name = name;
        this.price = price;
        this.measurement = measurement;
        this.amount = amount;
        this.cat_id = cat_id;
        this.br_id = br_id;
        this.cat = cat;
        this.br = br;
    }
    
    


    public int getCat_id() {
        return cat_id;
    }

    public int getBr_id() {
        return br_id;
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

    public String getCat() {
        return cat;
    }

    public String getBr() {
        return br;
    }


    
    
}
