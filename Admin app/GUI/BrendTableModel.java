/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import database.Brend;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class BrendTableModel extends AbstractTableModel{
//data
    private ArrayList<Brend> list;
//constructor
    public BrendTableModel(ArrayList<Brend> list){
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
       return 3;
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
            a = list.get(rowIndex).getCompany();
            break;
            default:
                a=0;
                break;
        }
        return a;
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
                s = "Company";
                break;
            default:
               s = "";
               break;
        }
        return s;
    }
}
