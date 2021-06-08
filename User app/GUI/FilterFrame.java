/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import static GUI.GUI.brendNameList;
import static GUI.GUI.categoryNameList;
import static GUI.GUI.productTableModel;
import database.Database;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import static GUI.GUI.table;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

/**
 *
 * @author Asus
 */
public class FilterFrame extends JFrame{

    private JPanel main;
    private JButton apply;
    private JRadioButton categoryFlag, brendFlag;
    private ButtonGroup bg;
    private JComboBox category, brend;
    private ActionListener b,c;
    public FilterFrame() throws SQLException  {
        super("Filter");
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 130));
        setResizable(false);
        setLayout(new BorderLayout());
        
        main = new JPanel(new GridLayout(2,2));
        apply = new JButton("apply");
        apply.setEnabled(false);
        
        categoryFlag = new JRadioButton("By category");
        brendFlag = new JRadioButton("By brend");
        bg = new ButtonGroup();
        bg.add(categoryFlag);
        bg.add(brendFlag);
        
        main.add(categoryFlag);
        main.add(brendFlag);
        
        GUI.getLists();
        category= new JComboBox(categoryNameList);
        category.setEnabled(false);
        brend = new JComboBox(brendNameList);
        brend.setEnabled(false);
        main.add(category);
        main.add(brend);
        
        add(main,BorderLayout.CENTER);
        add(apply,BorderLayout.SOUTH);
        
        c = new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
                try {
                    productTableModel = new ProductTableModel(Database.filterByCategory(GUI.categoryIDList[category.getSelectedIndex()]));
                } catch (SQLException ex) {}
                table.setModel(productTableModel);
                productTableModel.fireTableDataChanged();
                RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(productTableModel);
                table.setRowSorter(sorter);
                table.getColumnModel().removeColumn(table.getColumnModel().getColumn(5));
                GUI.filterButton.setText("Cancel filter");
                dispose();
          }
        };
        b = new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
                try {
                    productTableModel = new ProductTableModel(Database.filterByBrend(GUI.brendIDList[brend.getSelectedIndex()]));
                } catch (SQLException ex) {}
                table.setModel(productTableModel);
                productTableModel.fireTableDataChanged();
                RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(productTableModel);
                table.setRowSorter(sorter);
                table.getColumnModel().removeColumn(table.getColumnModel().getColumn(5));
                GUI.filterButton.setText("Cancel filter");
                dispose();
          }
        };
         categoryFlag.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              if (categoryFlag.isSelected()){
                  apply.removeActionListener(b);
                  category.setEnabled(true);
                  brend.setEnabled(false);
                  apply.setEnabled(true);
                  apply.addActionListener(c);
              }
          }
        });
        brendFlag.addActionListener(new ActionListener() {
          @Override
          public void actionPerformed(ActionEvent e) {
              if (brendFlag.isSelected()){
                  apply.removeActionListener(c);
                  category.setEnabled(false);
                  brend.setEnabled(true);
                  apply.setEnabled(true);
                  apply.addActionListener(b);
              }
          }
        });
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    
    
}
