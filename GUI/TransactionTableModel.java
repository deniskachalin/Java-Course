/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import database.Transaction;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class TransactionTableModel extends AbstractTableModel{

    private ArrayList<Transaction> list;
    
    public TransactionTableModel(ArrayList<Transaction> list){
        this.list = list;
    }
    
    
    @Override
    public int getRowCount() {
        return list.size();
    }

    @Override
    public int getColumnCount() {
        return 5;
    }

    @Override
    public String getColumnName(int columnIndex){
        String s;
        switch(columnIndex){
            case 0:
                s = "ID";
                break;
            case 1:
                s = "Product_ID";
                break;
            case 2:
                s = "Amount_change";
                break;
            case 3:
                s = "Date";
                break;
            case 4:
                s = "Source";
                break;
            default:
               s = "";
               break;
        }
        return s;
    }
    
    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Object a;
        switch(columnIndex){
            case 0:
            a = list.get(rowIndex).getId();
            break;
            case 1:
            a = list.get(rowIndex).getProduct_id();
            break;
            case 2:
            a = list.get(rowIndex).getAmount_change();
            break;
            case 3:
            a = list.get(rowIndex).getDate();
            break;
            case 4:
            a = list.get(rowIndex).getType();
            break;
            default:
                a=0;
            break;
        }
        return a;
    }
    
}
