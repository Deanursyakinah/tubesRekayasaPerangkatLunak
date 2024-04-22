package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSeparator;
import javax.swing.JTextField;


import controller.Controller;
import model.Admin;
import model.ShoppingCart;

public class SelectMonthYear {
    Controller con = Controller.getInstance();
    JTextField year;
    JTextField month;
    JFrame container;
    JButton btnBack;
    JButton search;

    public SelectMonthYear(Admin admin) {
        container = new JFrame("Show Monthly Transaction");
        container.setSize(480, 400);
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLocationRelativeTo(null);
        container.setLayout(null);
        container.getContentPane().setBackground(Color.DARK_GRAY);

        JLabel title = new JLabel("Show Monthly Transaction");
        title.setBounds(15, 15, 150, 23);
        title.setForeground(Color.WHITE);
        container.add(title);
        
        JSeparator garisPemisah = new JSeparator();
        garisPemisah.setBounds(28, 150, 410, 5);
        garisPemisah.setForeground(Color.LIGHT_GRAY);
        container.add(garisPemisah);

        // Input Year
        JLabel labelName = new JLabel("Input Year");
        year = new JTextField();
        labelName.setBounds(15, 182, 150, 23);
        year.setBounds(100, 182, 320, 23);
        labelName.setForeground(Color.WHITE);
        year.setForeground(Color.WHITE);
        year.setBackground(Color.DARK_GRAY);
        container.add(labelName);
        container.add(year);

        // Input Month
        JLabel labelMonth = new JLabel("Input Month");
        month = new JTextField();
        labelMonth.setBounds(15, 212, 150, 23);
        month.setBounds(100, 212, 320, 23);
        labelMonth.setForeground(Color.WHITE);
        month.setForeground(Color.WHITE);
        month.setBackground(Color.DARK_GRAY);
        container.add(labelMonth);
        container.add(month);

        //Bagian Button Back
        btnBack = new JButton("Back");
        btnBack.setBounds(270, 252, 150, 23);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.decode("#717D7E"));
        container.add(btnBack);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomeAdmin(admin);
                container.setVisible(false);
            }
        });

        //Bagian Button Search
        search = new JButton("Search");
        search.setBounds(100, 252, 140, 23);
        search.setForeground(Color.WHITE);
        search.setBackground(Color.decode("#717D7E"));
        container.add(search);
        container.setVisible(true);

         search.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int years = (Integer.parseInt(year.getText()));
                int monthh = (Integer.parseInt(month.getText()));
                ArrayList<ShoppingCart> transactions = con.getShoppingCartByMonth(monthh,years);
                new ShowMonthlyTransaction(admin,transactions);
                container.setVisible(false);
            }
        });
    }
}
