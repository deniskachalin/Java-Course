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

//set row amount
    @Override
    public int getRowCount() {
        return list.size();
    }
//set column amount
    @Override
    public int getColumnCount() {
       return 7;
    }
//set header
    @Override
    public String getColumnName(int columnIndex){
        String s;
        switch(columnIndex){
            case 0:
                s = "ID";
                break;
            case 1:
                s = "Name";
                break;
            case 2:
                s = "Price";
                break;
            case 3:
                s = "Measurement";
                break;
            case 4:
                s = "Amount";
                break;
            case 5:
                s = "Category";
                break;
            case 6:
                s = "Brend";
                break;
            default:
               s = "";
               break;
        }
        return s;
    }
//getter for values in cells
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object a;
        switch(columnIndex){
            case 0:
                a = list.get(rowIndex).getId();
                break;
            case 1:
                a = list.get(rowIndex).getName();
                break;
            case 2:
                a = list.get(rowIndex).getPrice();
                break;
            case 3:
                a = list.get(rowIndex).getMeasurement();
                break;
            case 4:
                a = list.get(rowIndex).getAmount();
                break;
            case 5:
                a = list.get(rowIndex).getCategory();
                break;
            case 6:
                a = list.get(rowIndex).getBrend();
                break;
            default:
                a=0;
            break;
        }
        return a;
}
}
