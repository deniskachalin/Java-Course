/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import database.BasketLine;
import database.Database;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

/**
 *
 * @author Asus
 */
public class CheckFrame extends JFrame{

    public CheckFrame(ArrayList<BasketLine> list) {
        super("Check");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setPreferredSize(new Dimension(400, 170));
        setResizable(false);
        setLayout(new BorderLayout());
        
        JPanel panel = new JPanel(new GridLayout(2+list.size(), 3));
        panel.add(new JLabel("Product"));
        panel.add(new JLabel("Amount"));
        panel.add(new JLabel("Price"));
        int finalPrice = 0;
        
        for (int i = 0; i<list.size();i++){
            panel.add(new JLabel(list.get(i).getName()));
            panel.add(new JLabel(""+list.get(i).getAmount()));
            panel.add(new JLabel(""+list.get(i).getPrice()));
            
            finalPrice += list.get(i).getPrice();
        }
        panel.add(new JLabel("In total pay: " + finalPrice+ "₽"));
        
        String checkNumber= "";
        for (int i = 0; i<list.size();i++){
            try {
                checkNumber+=Database.MakeTransaction(list.get(i).getId(), -list.get(i).getAmount(), "bought");
            } catch (SQLException ex) {}
        }
        panel.add(new JLabel("Check number"));
        panel.add(new JLabel(""+ checkNumber));
        add(new JScrollPane(panel));
        
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
}
