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
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Asus
 */
public class SelectFrame extends JFrame{

    private JPanel main, search;
    private JButton apply;
    private JTextField id;
    private final PlainDocument doc;
    public SelectFrame(String what) throws SQLException{
        super("Find "+what.substring(0, what.length()-1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 100));
        setResizable(false);
        setLayout(new BorderLayout());
        
        main = new JPanel(new BorderLayout());
        search = new JPanel(new GridLayout(1,2));
        id = new JTextField();
        doc = (PlainDocument) id.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        apply = new JButton("apply");
        search.add(new JLabel("Id"));
        search.add(id);
        main.add(search,BorderLayout.CENTER);
        main.add(apply,BorderLayout.SOUTH);
        
        switch(what){
            case("Products"):
            {
                apply.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        
                        try {
                            productView(Database.selectProduct(Integer.parseInt("0"+id.getText())));
                            dispose();
                        } catch (SQLException ex) {} 
                    }});
            }
                break;
            case ("Brends"):
                apply.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            brendView(Database.selectBrend(Integer.parseInt("0"+id.getText())));
                            dispose();
                        } catch (SQLException ex) {}
                    }});
                break;
            case ("Categories"):
                apply.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        try {
                            categoryView(Database.selectCategory(Integer.parseInt("0"+id.getText())));
                            dispose();
                        } catch (SQLException ex) {}
                    }});
                break;
            default:
                return;
        }
        add(main);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
   public void productView(Product product){
       if(product == null){
           GUI.InformationMessage("Nothing was found");
           return;
       }
       JDialog d = new JDialog();
       d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       d.setPreferredSize(new Dimension(600, 300));
       d.setResizable(false);
       d.setTitle("Product");
       JPanel p = new JPanel(new GridLayout(6,2));
       p.add(new JLabel("name"));
       p.add(new JLabel(product.getName()));
       p.add(new JLabel("price"));
       p.add(new JLabel(""+product.getPrice()));
       p.add(new JLabel("measurment"));
       p.add(new JLabel(product.getMeasurement()));
       p.add(new JLabel("amount"));
       p.add(new JLabel(""+product.getAmount()));
       p.add(new JLabel("brend"));
       p.add(new JLabel(product.getBrend()));
       p.add(new JLabel("category"));
       p.add(new JLabel(product.getCategory()));
       d.add(p);
       
       d.pack();
       d.setLocationRelativeTo(null);
       d.setVisible(true);
   }
   public void brendView(Brend brend){
       if(brend == null){
           GUI.InformationMessage("Nothing was found");
           return;
       }
       JDialog d = new JDialog();
       d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       d.setPreferredSize(new Dimension(600, 130));
       d.setResizable(false);
       d.setTitle("Brend");
       JPanel p = new JPanel(new GridLayout(2,2));
       p.add(new JLabel("name"));
       p.add(new JLabel(brend.getName()));
       p.add(new JLabel("company"));
       p.add(new JLabel(brend.getCompany()));
       d.add(p);
       
       d.pack();
       d.setLocationRelativeTo(null);
       d.setVisible(true);
   }
   public void categoryView(Category category){
       if(category == null){
           GUI.InformationMessage("Nothing was found");
           return;
       }
       JDialog d = new JDialog();
       d.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
       d.setPreferredSize(new Dimension(600, 130));
       d.setResizable(false);
       d.setTitle("Category");
       JPanel p = new JPanel(new GridLayout(2,2));
       p.add(new JLabel("name"));
       p.add(new JLabel(category.getName()));
       p.add(new JLabel("hall"));
       p.add(new JLabel(""+category.getHall()));
       d.add(p);
       
       d.pack();
       d.setLocationRelativeTo(null);
       d.setVisible(true);
   }
}
