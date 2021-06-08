/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import database.Database;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Asus
 */
public class DeleteFrame extends JFrame {
    private JPanel main, info;
    private JButton apply;
    private JTextField id;
    private final PlainDocument doc;
    
    public DeleteFrame(String what) throws SQLException, ParseException{
        super("Delete "+what.substring(0, what.length()-1));
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(300, 100));
        setResizable(false);
        setLayout(new BorderLayout());
        
        main = new JPanel(new BorderLayout());
        id = new JTextField();
        doc = (PlainDocument) id.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        apply = new JButton("Apply");
        info = new JPanel(new GridLayout(1,2));
        info.add(new JLabel("Id"));
        info.add(id);
        main.add(info, BorderLayout.CENTER);
        main.add(apply, BorderLayout.SOUTH);
        
        switch(what){
            case("Products"):
                apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(Database.selectProduct(  Integer.parseInt("0"+id.getText()))==null){
                        GUI.ErrorMessage("Nothing was found");
                        return;
                    }else {
                        Database.deleteProduct(Integer.parseInt("0"+id.getText()));
                        GUI.InformationMessage("Deleted successufully");
                    }
                    } catch (SQLException ex) {}
                dispose();
                GUI.refreshTable();
            }});
                break;
            case ("Brends"):
                apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(Database.selectBrend(Integer.parseInt("0"+id.getText()))==null){
                        GUI.ErrorMessage("Nothing was found");
                        return;
                    }else {
                        if (Database.deleteBrendIf(Integer.parseInt("0"+id.getText())).equals("Exists")){
                            GUI.ErrorMessage("This brend is used");
                            return;
                        }else{
                            Database.deleteBrend(Integer.parseInt("0"+id.getText()));
                            GUI.InformationMessage("Deleted successufully");
                    }}} catch (SQLException ex) {}
                dispose();
                GUI.refreshTable();
            }});
                break;
            case ("Categories"):
                apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if(Database.selectCategory(Integer.parseInt("0"+id.getText()))==null){
                        GUI.ErrorMessage("Nothing was found");
                        return;
                    }else {
                        if (Database.deleteCategoryCondition(Integer.parseInt("0"+id.getText())).equals("Exists")){
                            GUI.ErrorMessage("This category is used");
                            return;
                        }else{
                        Database.deleteCategory(Integer.parseInt("0"+id.getText()));
                        GUI.InformationMessage("Deleted successufully");
                        }}} catch (SQLException ex) {}
                dispose();
                GUI.refreshTable();}});
                break;
            default:
                return;
        }
        add(main);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }}
