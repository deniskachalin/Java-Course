/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import database.BasketLine;
import database.Product;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.table.AbstractTableModel;

public class BasketTableModel extends AbstractTableModel{  
//data
    private ArrayList<BasketLine> list;
//constructor
    public BasketTableModel(ArrayList<BasketLine> list){
        this.list = list;
    }

    public ArrayList<BasketLine> getList() {
        return list;
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
       return 4;
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
                s = "Amount";
                break;
            case 3: 
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
            a = String.format("%.2f", list.get(rowIndex).getPrice())+"₽";
            break;
            case 2:
            a = list.get(rowIndex).getAmount();
            break;
            case 3:
            a = list.get(rowIndex).getId();
            break;
            default:
                a=0;
        }
        return a;
}
}
