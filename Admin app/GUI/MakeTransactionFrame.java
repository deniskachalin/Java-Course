/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import database.Database;
import database.ExceptionForTransanctions; 
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Asus
 */
public class MakeTransactionFrame extends JFrame{
    private JRadioButton importRB, removeRB, returnRB;
    private ButtonGroup bg;
    private JLabel transactionLabel, idLabel, amountLabel;
    private JTextField idTextField, amountTextField;
    private JPanel infoPanel;
    private JButton applyButton;
    private PlainDocument doc;
    
    public MakeTransactionFrame() {
        super("Make transaction");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 170));
        setResizable(false);
        setLayout(new BorderLayout());
        
        init();
        applyButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (importRB.isSelected()){
                        Database.MakeTransaction(Integer.parseInt(idTextField.getText()), Integer.parseInt(amountTextField.getText()),"imported");
                    }else if (removeRB.isSelected()){
                        Database.MakeTransaction(Integer.parseInt(idTextField.getText()), -Integer.parseInt(amountTextField.getText()),"removed");
                    } else if (returnRB.isSelected()){
                        Database.MakeTransaction(Integer.parseInt(idTextField.getText()), Integer.parseInt(amountTextField.getText()),"returned");    
                    }else{
                        GUI.InformationMessage("Choose a transaction type");
                        return;
                    }
                } catch (SQLException ex) {
                        GUI.ErrorMessage("There are no product with this id");
                        return;
                    }catch (NumberFormatException exp){
                        GUI.ErrorMessage("Fill all fields");
                        return;
                    }catch (ExceptionForTransanctions exp){
                        GUI.ErrorMessage("Incorrect amount");
                        return;
                    }
                GUI.refreshTable();
                GUI.getTransactions(-1);
                dispose();
            }
        });
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    public void init(){
        infoPanel = new JPanel(new GridLayout(3,4));
        add(infoPanel, BorderLayout.CENTER);
        
        importRB = new JRadioButton("Import");
        removeRB = new JRadioButton("Remove");
        returnRB = new JRadioButton("Return");
        
        bg = new ButtonGroup();
        bg.add(importRB);
        bg.add(removeRB);
        bg.add(returnRB);
        
        transactionLabel = new JLabel("Transaction type");
        idLabel = new JLabel("Product ID");
        amountLabel = new JLabel("Product amount");
        
        idTextField = new JTextField();
        doc = (PlainDocument) idTextField.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        amountTextField = new JTextField();
        doc = (PlainDocument) amountTextField.getDocument();
        doc.setDocumentFilter(new DigitFilter());
        
        infoPanel.add(transactionLabel);
        infoPanel.add(importRB);
        infoPanel.add(removeRB);
        infoPanel.add(returnRB);
        infoPanel.add(idLabel);
        infoPanel.add(idTextField);
        infoPanel.add(new JPanel());
        infoPanel.add(new JPanel());
        infoPanel.add(amountLabel);
        infoPanel.add(amountTextField);
        
        applyButton = new JButton("Apply");
        add(applyButton, BorderLayout.SOUTH);
    }
}