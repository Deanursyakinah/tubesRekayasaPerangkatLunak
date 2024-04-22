/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import controller.Controller;
import model.Admin;
import model.ShoppingCart;


/**
 *
 * @author abil
 */
public class ShowMonthlyTransaction {
    Controller con = Controller.getInstance();
    JTextField year;
    JTextField month;
    JFrame container;
    JButton btnBack;

    public ShowMonthlyTransaction(Admin admin,ArrayList<ShoppingCart> transaction) {
        container = new JFrame("Show Monthly Transaction");
        container.setSize(500, 300);
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLocationRelativeTo(null);
        container.setLayout(null);
        container.getContentPane().setBackground(Color.DARK_GRAY);

        JLabel title = new JLabel("Show Monthly Transaction");
        title.setBounds(15, 15, 150, 23);
        title.setForeground(Color.WHITE);
        container.add(title);

        String[] columnNames = {"Transaction_Id", "Item_id","User_id","Item_Name","Description"};

        Object[][] data = new Object[transaction.size()][5]; 

        for (int i = 0; i < transaction.size(); i++) {
            ShoppingCart user = transaction.get(i);
            data[i][0] = user.getTransactionID(); 
            data[i][1] = user.getitemID(); 
            data[i][2] = con.getTransactionByID(user.getTransactionID()).getUserID();
            data[i][3] = con.getItemById(user.getitemID()).getName(); 
            data[i][4] = user.getDescription() ;
        }
    
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        JTable userTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(15, 50, 450, 150);
        container.add(scrollPane);

        userTable.setBackground(Color.DARK_GRAY);
        userTable.setForeground(Color.WHITE);
        userTable.getTableHeader().setBackground(Color.DARK_GRAY);
        userTable.getTableHeader().setForeground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);

        JSeparator garisPemisah = new JSeparator();
        garisPemisah.setBounds(28, 150, 410, 5);
        garisPemisah.setForeground(Color.LIGHT_GRAY);
        container.add(garisPemisah);

        //Bagian Button Back
        btnBack = new JButton("Back");
        btnBack.setBounds(310, 220, 150, 23);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.decode("#717D7E"));
        container.add(btnBack);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new SelectMonthYear(admin);
                container.setVisible(false);
            }
        });

        container.setVisible(true);

    }
}
