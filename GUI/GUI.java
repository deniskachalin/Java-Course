
package GUI;

import database.Brend;
import database.Category;
import database.Database;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import java.sql.*;
import java.text.ParseException;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTabbedPane;
import javax.swing.RowSorter;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class GUI extends JFrame {

    
    private JButton insertButton, deleteButton, updateButton, selectButton, refreshButton, selectTransactionButton, makeTransactionButton;
    private JPanel DatabaseButtonsPanel, transactionPanel, DatabasePanel, transactionButtonPanel;
    protected static JButton filterButton;
    private JTabbedPane tabbedPane;
    protected static JTable databaseTable, transactionTable;
    private JMenuBar menuBar;
    protected static JMenu menu;
    private JMenuItem menuItem1,menuItem2,menuItem3;
    private static ArrayList data;
    private static ArrayList transactions;
    protected static ProductTableModel productTableModel;
    protected static CategoryTableModel categoryTableModel;
    protected static BrendTableModel brendTableModel;
    protected static TransactionTableModel transactionTableModel;
    protected static String[] categoryNameList, brendNameList;
    protected static int[] categoryIDList, brendIDList;
    
    public GUI(String s) throws ParseException, SQLException {
//frame setting
        super(s);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setPreferredSize(new Dimension(850, 500));
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
//initializing menu
        init();
        refreshTable();
//load data
        menuItem1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setText("Products");
                filterButton.setEnabled(true);
                filterButton.setText("Filter");
                refreshTable();
            }
        });
        
        menuItem2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setText("Brends");
                filterButton.setEnabled(false);
                filterButton.setText("Filter");
                refreshTable();
            }
        });
        
        menuItem3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                menu.setText("Categories");
                filterButton.setEnabled(false);
                filterButton.setText("Filter");
               refreshTable();
            }
        });

//setting actionListener's for buttons
        insertButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    AddFrame a = new AddFrame(menu.getText());    
                } catch (SQLException ex) {} catch (ParseException ex) {}
            }});
        deleteButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                try {
                    DeleteFrame d = new DeleteFrame(menu.getText());
                } catch (SQLException ex) {} catch (ParseException ex) {}
                getTransactions(-1);
            }});
        updateButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    ChangeFrame c = new ChangeFrame(menu.getText());
                } catch (SQLException ex) {} catch (ParseException ex) {}
            }});
        selectButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    SelectFrame s = new SelectFrame(menu.getText());
                } catch (SQLException ex) {}
                 
            }});
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (filterButton.getText().equals("Filter")){
                    try {
                        getLists();
                        FilterFrame ff = new FilterFrame();
                    } catch (SQLException ex) {}
                }else if (filterButton.getText().equals("Calcel filter")){
                    refreshTable();
                    filterButton.setText("Filter");
                }
            }});
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                getTransactions(-1);
            }});
        selectTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SelectTransactionFrame sft = new SelectTransactionFrame();
            }});
        makeTransactionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MakeTransactionFrame mtf = new MakeTransactionFrame();
            }});
        setVisible(true);
    }
   
   public void init(){
        DatabasePanel = new JPanel(new BorderLayout());
        transactionPanel = new JPanel(new BorderLayout());
        
        menuBar = new JMenuBar();
        menu = new JMenu("Products");
        menuItem1 = new JMenuItem("Products");
        menuItem2 = new JMenuItem("Brends");
        menuItem3 = new JMenuItem("Categories");
        menuBar.add(menu);
        menu.add(menuItem1);
        menu.add(menuItem2);
        menu.add(menuItem3);
        setJMenuBar(menuBar);
        
        insertButton = new JButton("Add");
        deleteButton = new JButton("Delete");
        updateButton = new JButton("Change");
        selectButton = new JButton("Find");
        filterButton = new JButton("Filter");
        DatabaseButtonsPanel = new JPanel(new GridLayout(1,5));
        DatabaseButtonsPanel.add(insertButton);
        DatabaseButtonsPanel.add(deleteButton);
        DatabaseButtonsPanel.add(updateButton);
        DatabaseButtonsPanel.add(selectButton);
        DatabaseButtonsPanel.add(filterButton);
        DatabasePanel.add(DatabaseButtonsPanel,BorderLayout.NORTH);
        
        databaseTable = new JTable();
        DatabasePanel.add(new JScrollPane(databaseTable),BorderLayout.CENTER);
        
        transactionButtonPanel = new JPanel(new GridLayout(1,2));
        transactionTable = new JTable();
        transactionPanel.add(new JScrollPane(transactionTable),BorderLayout.CENTER);
        transactionPanel.add(transactionButtonPanel,BorderLayout.NORTH);
        refreshButton = new JButton("Refresh");
        selectTransactionButton = new JButton("Find by ID");
        makeTransactionButton = new JButton("Make transaction");
        transactionButtonPanel.add(refreshButton);
        transactionButtonPanel.add(selectTransactionButton);
        transactionButtonPanel.add(makeTransactionButton);
        
        tabbedPane = new JTabbedPane(JTabbedPane.TOP);
        tabbedPane.addTab("Database", DatabasePanel);
        tabbedPane.addTab("Transactions", transactionPanel);
        
        add(tabbedPane);
        getTransactions(-1);
   }
  
  public static void refreshTable(){
       if (menu.getText()=="Products"){
           try {
                data = Database.GetProducts();
                } catch (SQLException ex) {}
            productTableModel = new ProductTableModel(data);
            databaseTable.setModel(productTableModel);
            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(productTableModel);
            databaseTable.setRowSorter(sorter);
            productTableModel.fireTableDataChanged();
       }
       
       if (menu.getText()=="Brends"){
           try {
                data = Database.GetBrends();
                } catch (SQLException ex) {}
            brendTableModel = new BrendTableModel(data);
            databaseTable.setModel(brendTableModel);
            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(brendTableModel);
            databaseTable.setRowSorter(sorter);
            brendTableModel.fireTableDataChanged();
       }
       
       if (menu.getText()=="Categories"){
           try {
                data = Database.GetCategories();
                } catch (SQLException ex) {}
            categoryTableModel = new CategoryTableModel(data);
            databaseTable.setModel(categoryTableModel);
            RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(categoryTableModel);
            databaseTable.setRowSorter(sorter);
            categoryTableModel.fireTableDataChanged();
       }
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
  public static void getTransactions(int id){
    try{
        if (id == -1){
            transactions = Database.GetTransactions();
        }
        else{
            transactions = Database.GetTransactionsForID(id);
            if (transactions.size() == 0){
                ErrorMessage("Nothing was found");
                return;
            }
        }
        transactionTableModel = new TransactionTableModel(transactions);
        transactionTable.setModel(transactionTableModel);
        RowSorter<TableModel> sorter = new TableRowSorter<TableModel>(transactionTableModel);
        transactionTable.setRowSorter(sorter);
        transactionTableModel.fireTableDataChanged();
    }
    catch(SQLException exp){}
}
  
    public static void ErrorMessage(String message){
        JOptionPane.showMessageDialog(new JFrame(), message, "Error", 0);
    }
    public static void InformationMessage(String message){
        JOptionPane.showMessageDialog(new JFrame(), message, "Information", 1);
    }
}