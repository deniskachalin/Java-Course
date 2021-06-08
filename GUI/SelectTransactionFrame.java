/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.PlainDocument;

public class SelectTransactionFrame extends JFrame{

    private JPanel main, search;
    private JButton apply;
    private JTextField id;
    private final PlainDocument doc;
    public SelectTransactionFrame() {
        super("Find product by id");
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
        
        apply.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                GUI.getTransactions(Integer.parseInt(id.getText()));
                dispose();
                }catch (NumberFormatException exp){
                    GUI.ErrorMessage("Enter a number");
                    return;
                }
            }
        });
            
                
        add(main);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
  
}
