/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package database;


public class BasketLine {
    private int id;
    private String name;
    private double price;
    private int amount;
    
    public BasketLine(int id,String name, double price, int amount){
        this.id = id;
        this.name = name;
        this.price = price;
        this.amount = amount;
        
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

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
    
}
