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
import java.text.DecimalFormat;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;
import javax.swing.text.PlainDocument;

public class AddFrame extends JFrame{
    private JPanel main, info;
    private JButton apply;
    private JTextField name, amount, company, hall;
    private JFormattedTextField price;
    private final JComboBox measurment= new JComboBox(new String[]{"kilogram","piece","litre"});;
    private JComboBox brendList, categoryList;
    private PlainDocument doc;
    private final MaskFormatter doubleFormatter = new MaskFormatter("####.##");
   
    
    
    public AddFrame(String what) throws SQLException, ParseException{
        super("Add "+what.substring(0, what.length()-1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setResizable(false);
        setLayout(new BorderLayout());
        
        main = new JPanel(new BorderLayout());
        name = new JTextField();
        apply = new JButton("Apply");
        
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
    public void productPanel() {
        info = new JPanel(new GridLayout(6,2));
        setPreferredSize(new Dimension(600, 300));
        try {GUI.getLists();} catch (SQLException ex) {}
        amount = new JTextField("");
        doc = (PlainDocument) amount.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        doubleFormatter.setPlaceholderCharacter('0');
        price = new JFormattedTextField(doubleFormatter);
        brendList = new JComboBox(GUI.brendNameList);
        categoryList = new JComboBox(GUI.categoryNameList);
        
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
        DecimalFormat df = new DecimalFormat("0.00");
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (name.getText().length() >49){
                    GUI.ErrorMessage("Incorrect Name");
                    return;
                }
                try {
                    Database.insertProduct(new Product(name.getText(),Double.parseDouble(price.getText()),
                            (String)measurment.getSelectedItem(),Integer.parseInt("0"+amount.getText())
                            ,GUI.categoryIDList[categoryList.getSelectedIndex()]
                            ,GUI.brendIDList[brendList.getSelectedIndex()]));
                } catch (SQLException ex) {}
                catch (ArrayIndexOutOfBoundsException aioobe) {
                    GUI.ErrorMessage("Enter atleast 1 brend and category before adding products");
                    return;
                }
                catch (NumberFormatException nfe) {
                    GUI.ErrorMessage("Incorrect amount");
                    return;
                }
                GUI.InformationMessage("Added Successufully");
                dispose();
                GUI.refreshTable();
            }});}
    public void brendPanel(){
        info = new JPanel(new GridLayout(2,2));
        setPreferredSize(new Dimension(600, 130));
        company = new JTextField();
        
        info.add(new JLabel("Name"));
        info.add(name);
        info.add(new JLabel("Company"));
        info.add(company);
        
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
                    Database.insertBrend(new Brend(name.getText(),company.getText()));
                } catch (SQLException ex) {}
                GUI.InformationMessage("Added Successufully");
                dispose();
                GUI.refreshTable();
            }});}
    public void categoryPanel(){
        info = new JPanel(new GridLayout(2,2));
        setPreferredSize(new Dimension(600, 130));
        hall = new JTextField();
        doc = (PlainDocument) hall.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        
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
                    Database.insertCategory(new Category(name.getText(),Integer.parseInt(hall.getText())));
                } catch (SQLException ex) {}
                catch (NumberFormatException nfe) {
                    GUI.ErrorMessage("Hall should be a number");
                    return;
                }
                GUI.InformationMessage("Added Successufully");
                dispose();
                GUI.refreshTable();
            }});}}
