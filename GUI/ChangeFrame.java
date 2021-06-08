/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import database.Brend;
import database.Category;
import database.Database;
import database.Product;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Asus
 */
public class ChangeFrame extends JFrame{
    private JPanel main, info, search;
    private JButton apply, find;
    private JTextField name, amount, company, hall,id;
    private JTextField price;
    private final JComboBox measurment= new JComboBox(new String[]{"kilogram","piece","litre"});;
    private JComboBox brendList, categoryList;
    private PlainDocument doc;
    
    
    public ChangeFrame(String what) throws SQLException, ParseException{
        super("Change "+what.substring(0, what.length()-1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        
        main = new JPanel(new BorderLayout());
        search = new JPanel(new GridLayout(1,3));
        name = new JTextField();
        name.setEnabled(false);
        measurment.setEnabled(false);
        id = new JTextField();
        doc = (PlainDocument) id.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        apply = new JButton("Apply");
        apply.setEnabled(false);
        find = new JButton("Find");
        
        search.add(new JLabel("Id"));
        search.add(id);
        search.add(find);
        main.add(search,BorderLayout.NORTH);
        
        switch(what){
            case("Products"):
                productPanel();
                break;
            case ("Brends"):
                brendPanel();
                break;
            case ("Categories"):
                categoryPanel();
                break;
            default:
                return;
        }
        add(main);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void productPanel() throws SQLException{
        info = new JPanel(new GridLayout(6,2));
        setPreferredSize(new Dimension(600, 300));
        GUI.getLists();
        amount = new JTextField();
        amount.setEnabled(false);
        doc = (PlainDocument) amount.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        price = new JTextField();
        price.setEnabled(false);
        brendList = new JComboBox(GUI.brendNameList);
        brendList.setEnabled(false);
        categoryList = new JComboBox(GUI.categoryNameList);
        categoryList.setEnabled(false);
        
        info.add(new JLabel("Name"));
        info.add(name);
        info.add(new JLabel("Price"));
        info.add(price);
        info.add(new JLabel("Measurment"));
        info.add(measurment);
        info.add(new JLabel("Amount"));
        info.add(amount);
        info.add(new JLabel("Category"));
        info.add(categoryList);
        info.add(new JLabel("Brend"));
        info.add(brendList);
        main.add(info, BorderLayout.CENTER);
        main.add(apply, BorderLayout.SOUTH);
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.getText().length() >49){
                    GUI.ErrorMessage("Incorrect Name");
                    return;
                }
                try {
                    Database.changeProduct(new Product(name.getText(),Double.parseDouble(price.getText()),
                           (String)measurment.getSelectedItem(),Integer.parseInt("0"+amount.getText())
                            ,GUI.categoryIDList[categoryList.getSelectedIndex()]
                           ,GUI.brendIDList[brendList.getSelectedIndex()]),Integer.parseInt("0"+id.getText()));
                } catch (SQLException ex) {}
                catch (NumberFormatException ex){
                    GUI.ErrorMessage("Incorrect value");
                    return;
                }
                dispose();
                GUI.refreshTable();
            }});
        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Product p = Database.selectProduct(Integer.parseInt("0"+id.getText()));
                    name.setText(p.getName());
                    price.setText(String.format("%.2f", p.getPrice()));
                    measurment.setSelectedItem(p.getMeasurement());
                    amount.setText(""+p.getAmount());
                    categoryList.setSelectedItem(p.getCategory());
                    brendList.setSelectedItem(p.getBrend());
                } catch (SQLException ex) {}
                catch (NullPointerException ex){
                    GUI.ErrorMessage("Nothing was found");
                    return;
                }
                id.setEnabled(false);
                find.setEnabled(false);
                name.setEnabled(true);
                price.setEnabled(true);
                measurment.setEnabled(true);
                amount.setEnabled(true);
                categoryList.setEnabled(true);
                brendList.setEnabled(true);
                apply.setEnabled(true);
            }});}
    public void brendPanel(){
        info = new JPanel(new GridLayout(2,2));
        setPreferredSize(new Dimension(600, 180));
        company = new JTextField();
        
        info.add(new JLabel("Name"));
        info.add(name);
        info.add(new JLabel("Company"));
        info.add(company);
        company.setEnabled(false);
        
        main.add(info, BorderLayout.CENTER);
        main.add(apply, BorderLayout.SOUTH);
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.getText().length()>49 || company.getText().length() > 49){
                    GUI.ErrorMessage("Incorrect value");
                    return;
                }
                try {
                    Database.changeBrend(new Brend(name.getText(),company.getText()),Integer.parseInt("0"+id.getText()));
                } catch (SQLException ex) {}
                dispose();
                GUI.refreshTable();
            }});
        find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Brend p = Database.selectBrend(Integer.parseInt("0"+id.getText()));
                    name.setText(p.getName());
                    company.setText(p.getCompany());
                } catch (SQLException ex) {}
                catch (NullPointerException ex){
                    GUI.ErrorMessage("Nothing was found");
                }
                id.setEnabled(false);
                find.setEnabled(false);
                name.setEnabled(true);
                company.setEnabled(true);
                apply.setEnabled(true);
            }});}
    public void categoryPanel(){
        info = new JPanel(new GridLayout(2,2));
        setPreferredSize(new Dimension(600, 180));
        hall = new JTextField();
        doc = (PlainDocument) hall.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        hall.setEnabled(false);
        
        info.add(new JLabel("Name"));
        info.add(name);
        info.add(new JLabel("Hall"));
        info.add(hall);
        
        main.add(info, BorderLayout.CENTER);
        main.add(apply, BorderLayout.SOUTH);
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                 if (name.getText().length()>49){
                    GUI.ErrorMessage("Incorrect value");
                    return;
                }
                try {
                    Database.changeCategory(new Category(name.getText(),Integer.parseInt("0"+hall.getText())),Integer.parseInt("0"+id.getText()));
                    
                } catch (SQLException ex) {}   
                dispose();
                GUI.refreshTable();
            }});
         find.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Category p = Database.selectCategory(Integer.parseInt("0"+id.getText()));
                    name.setText(p.getName());
                    hall.setText(""+p.getHall());
                } catch (SQLException ex) {} 
                catch (NullPointerException ex){
                    GUI.ErrorMessage("Nothing was found");
                }
                id.setEnabled(false);
                find.setEnabled(false);
                name.setEnabled(true);
                hall.setEnabled(true);
                apply.setEnabled(true);
            }});}
}

