/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import database.BasketLine;
import database.Brend;
import database.Category;
import database.Database;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.RowFilter;
import javax.swing.RowSorter;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Asus
 */
public class GUI extends JFrame{
    protected static ProductTableModel productTableModel;
    protected static BasketTableModel basketTableModel;
    protected static String[] categoryNameList, brendNameList;
    private static ArrayList data;
    private static ArrayList<BasketLine> basketList;
    protected static JTable table, basket;
    protected static int[] categoryIDList, brendIDList;
    private JButton addToBasketButton, buyButton, remove, clear;
    protected static JButton filterButton;
    private JPanel menuPanel, tablePanel, basketPanel;
    private JTabbedPane tp;
    private JLabel price, sum;
    private JTextField nameTextField, amountTextField;
    private TableRowSorter<TableModel> rowSorter;
    private PlainDocument doc;
   
    
    public GUI(String s) throws SQLException, ParseException{
        super(s);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(850, 500));
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        init();
        refreshTable();
        refreshBasket();
        
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                calcSumPrice();
            }
        });
        
        amountTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calcSumPrice();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calcSumPrice();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        nameTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                String text = nameTextField.getText();
                 if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(text,0));
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                String text = nameTextField.getText();

                if (text.trim().length() == 0) {
                    rowSorter.setRowFilter(null);
                } else {
                    rowSorter.setRowFilter(RowFilter.regexFilter(text,0));
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
        
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filterButton.getText().equals("Filter")){
                    try {
                        getLists();
                        FilterFrame ff = new FilterFrame();
                    } catch (SQLException ex) {}
                }else if (filterButton.getText().equals("Cancel filter")){
                    refreshTable();
                    filterButton.setText("Filter");
                }
            }
        });
        addToBasketButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) throws IndexOutOfBoundsException{
               
                int id = Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 5).toString());
                String n = table.getValueAt(table.getSelectedRow(), 0).toString();
                double pr = Double.parseDouble(price.getText().substring(11));
                int a = Integer.parseInt(amountTextField.getText());
                
                BasketLine bL = new BasketLine(id, n, pr, a);
                for ( int i = 0; i < basketList.size(); i++){
                    if (basketList.get(i).getId() == bL.getId()){
                        if (basketList.get(i).getAmount() + bL.getAmount() <= Integer.parseInt(table.getModel().getValueAt(table.getSelectedRow(), 2).toString().substring(11))){
                            basketList.get(i).setAmount(basketList.get(i).getAmount()+bL.getAmount());
                            basketList.get(i).setPrice(basketList.get(i).getPrice()+bL.getPrice());
                            basketTableModel.fireTableDataChanged();
                            resultPrice();
                            return;
                        }
                        else {
                            ErrorMessage("Dont have enough amount");
                            return;
                        }
                    }
                }
                basketList.add(bL);
                basketTableModel.fireTableDataChanged();
                resultPrice();
            }
        });
        buyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CheckFrame cf = new CheckFrame(basketList);
                basketList.clear();
                refreshBasket();
                refreshTable();
            }
        } );
        remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    basketList.remove(basket.getSelectedRow());
                    refreshBasket();
                }catch (Exception ex){}
            }
        });
        clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                basketList = new ArrayList<>();
                refreshBasket();
            }
        });
    }
    public void resultPrice(){
        double sss = 0;
        for (int i = 0; i < basketList.size(); i++){
            sss+= basketList.get(i).getPrice();
        }
        sum.setText("To pay: "+String.format("%.2f₽",sss));
    }
    
    public void init(){
        tablePanel = new JPanel(new BorderLayout());
        
        addToBasketButton = new JButton("Add to basket");
        addToBasketButton.setEnabled(false);
        
        filterButton = new JButton("Filter");
        amountTextField = new JTextField();
        doc = (PlainDocument) amountTextField.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        nameTextField = new JTextField();
        price = new JLabel("");
        
        menuPanel = new JPanel(new GridLayout(2,4));
        menuPanel.add(new JLabel("Enter amount"));
        menuPanel.add(amountTextField);
        menuPanel.add(new JLabel("Start writing product name"));
        menuPanel.add(nameTextField);
        menuPanel.add(price);
        menuPanel.add(addToBasketButton);
        menuPanel.add(new JLabel("Or use a filter"));
        menuPanel.add(filterButton);
        
        
        
        tablePanel.add(menuPanel,BorderLayout.NORTH);
        
        table = new JTable();
        tablePanel.add(new JScrollPane(table),BorderLayout.CENTER);
        
        basketList = new ArrayList<BasketLine>();
        basketPanel = new JPanel(new BorderLayout());
        var p = new JPanel(new GridLayout(1, 2));
        sum = new JLabel("");
        buyButton = new JButton("Buy");
        p.add(sum);
        p.add(buyButton);
       
        basketTableModel = new BasketTableModel(basketList);
        basket = new JTable(basketTableModel);
        basketTableModel.fireTableDataChanged();
        basketPanel.add(p, BorderLayout.SOUTH);
        basketPanel.add(new JScrollPane(basket), BorderLayout.CENTER);
        remove = new JButton("Remove item");
        clear = new JButton("Clear basket");
        var p1 = new JPanel(new GridLayout(1,2));
        p1.add(remove);
        p1.add(clear);
        basketPanel.add(p1, BorderLayout.NORTH);
        
        tp = new JTabbedPane(JTabbedPane.TOP);
        tp.addTab("Catalog", tablePanel);
        tp.addTab("Basket", basketPanel);
        
        add(tp);
        
        
    }
    
    public static void getLists() throws SQLException{  
      ArrayList<Category> cat=Database.GetCategories();
        categoryNameList = new String[cat.size()];
        categoryIDList = new int[cat.size()];
        for (int i = 0; i<cat.size();i++){
            categoryNameList[i] = cat.get(i).getName();
            categoryIDList[i] = cat.get(i).getId();
        }
        
        ArrayList<Brend> br=Database.GetBrends();
        brendNameList = new String[br.size()];
        brendIDList = new int[br.size()];
        for (int i = 0; i<br.size();i++){
            brendNameList[i] = br.get(i).getName();
            brendIDList[i] = br.get(i).getId();
        }
   }
    public void calcSumPrice(){
        String text = amountTextField.getText();
        String am ="";
        if (text.equals("")){
            return;
        }
        try{
        if (table.getValueAt(table.getSelectedRow(), 2).toString().equals("Out of stock")){
            addToBasketButton.setEnabled(false);
            price.setText("Product out of stock");
            return;
        }
        am = table.getValueAt(table.getSelectedRow(), 2).toString().substring(11);
        }catch (IndexOutOfBoundsException exp){}
        
        try{
            Integer.parseInt(text);
            Integer.parseInt(am);
            
        }catch (NumberFormatException exp){
            price.setText("Choose a product");
            return;
        }
        if (text.trim().length() == 0) {
            price.setText("");
        }else if (Integer.parseInt(text) > Integer.parseInt(am)){
            addToBasketButton.setEnabled(false);
            price.setText("Dont have such amount");
        } else if ( Integer.parseInt(text) == 0){
            addToBasketButton.setEnabled(false);
            price.setText("Um...amount equals 0?");
        }
        else {
            addToBasketButton.setEnabled(true);
            try{
                String pr = table.getValueAt(table.getSelectedRow(), 1).toString();
                char[] ar = pr.toCharArray();
                for(int i =0; i< ar.length; i++){
                    if (ar[i] == '₽'){
                    pr = pr.substring(0, i);
                    }
                }
                double s = Double.parseDouble(pr) * Integer.parseInt(text);
                price.setText("Sum price: "+String.format("%.2f", s));
            }catch(IndexOutOfBoundsException exp){}
        }
    }
    
    public void refreshTable(){
        try {
                data = Database.GetProducts();
                } catch (SQLException ex) {}
        productTableModel = new ProductTableModel(data);
        table.setModel(productTableModel);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(productTableModel);
        table.setRowSorter(sorter);
        productTableModel.fireTableDataChanged();
        rowSorter = new TableRowSorter<>(table.getModel());
        table.setRowSorter(rowSorter);
        table.getColumnModel().removeColumn(table.getColumnModel().getColumn(5));
    }
    public void refreshBasket(){
        //need to divide to init unit and refresh unit
        basketTableModel = new BasketTableModel(basketList);
        basket.setModel(basketTableModel);
        basket.getColumnModel().removeColumn(basket.getColumnModel().getColumn(3));
        basketTableModel.fireTableDataChanged();
        
    }
    public static void ErrorMessage(String message){
        JOptionPane.showMessageDialog(new JFrame(), message, "Error", 0);
    }
}
