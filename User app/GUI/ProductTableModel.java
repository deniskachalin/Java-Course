/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import database.Product;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ProductTableModel extends AbstractTableModel{  
//data
    private ArrayList<Product> list;
//constructor
    public ProductTableModel(ArrayList<Product> list){
        this.list = list;
    }
//insert values in table
//set row amount
    @Override
    public int getRowCount() {
        return list.size();
    }
//set column amount
    @Override
    public int getColumnCount() {
       return 6;
    }
//set header
    @Override
    public String getColumnName(int columnIndex){
        String s;
        switch(columnIndex){
            case 0:
                s = "Name";
                break;
            case 1:
                s = "Price";
                break;
            case 2:
                s = "In stock";
                break;
            case 3:
                s = "Category";
                break;
            case 4:
                s = "Brend";
                break;
            case 5: 
                s = "ID";
                break;
            default:
               s = "";
        }
        return s;
    }
//getter for values in cells
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object a;
        switch(columnIndex){
            case 0:
            a = list.get(rowIndex).getName();
            break;
            case 1:
            a = list.get(rowIndex).getPrice() + " ₽ for " + list.get(rowIndex).getMeasurement();
            break;
            case 2:
            a = list.get(rowIndex).getAmount()>0? " In stock: " + list.get(rowIndex).getAmount() : "Out of stock";
            break;
            case 3:
            a = list.get(rowIndex).getCat();
            break;
            case 4:
            a = list.get(rowIndex).getBr();
            break;
            case 5:
            a = list.get(rowIndex).getId();
            break;
            default:
                a=0;
        }
        return a;
}
}
