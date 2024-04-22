package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
//import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
//import java.util.Iterator;
import controller.Controller;
//import model.Item;
import model.ShoppingCart;
import model.User;

public class ShowShoppingCart {
    Controller con = Controller.getInstance();
    JFrame container;
    JButton btnBuy = new JButton("buy");
    JButton btnGift = new JButton("Gift");
    JButton btnBack = new JButton("back");
    JLabel shoping_cart = new JLabel("Shopping cart");

    public ShowShoppingCart(User user, ArrayList<ShoppingCart> cart) {
        container = new JFrame("Shopping cart");
        container.setSize(480, 400);
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLocationRelativeTo(null);
        container.setLayout(null);
        container.getContentPane().setBackground(Color.DARK_GRAY);

        shoping_cart.setBounds(20, 25, 200, 23);
        shoping_cart.setForeground(Color.WHITE);
        container.add(shoping_cart);

        JSeparator garisPemisah = new JSeparator();
        garisPemisah.setBounds(20, 45, 80, 5);
        garisPemisah.setForeground(Color.LIGHT_GRAY);
        container.add(garisPemisah);

        String[] columnNames = { "Name", "type", "price" };

        Object[][] data = new Object[cart.size()][4];

        for (int i = 0; i < cart.size(); i++) {
            ShoppingCart shoppingCart = cart.get(i);
            data[i][0] = con.getItemById(shoppingCart.getitemID()).getName();
            data[i][1] = con.getItemById(shoppingCart.getitemID()).getType();
            data[i][2] = con.getItemById(shoppingCart.getitemID()).getPrice();

        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        JTable libraryTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(libraryTable);
        scrollPane.setBounds(20, 70, 420, 100);
        container.add(scrollPane);

        libraryTable.setBackground(Color.DARK_GRAY);
        libraryTable.setForeground(Color.WHITE);
        libraryTable.getTableHeader().setBackground(Color.DARK_GRAY);
        libraryTable.getTableHeader().setForeground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);

        JSeparator garisPemisah2 = new JSeparator();
        garisPemisah2.setBounds(20, 190, 420, 5);
        garisPemisah2.setForeground(Color.LIGHT_GRAY);
        container.add(garisPemisah2);

        btnBuy.setBounds(20, 210, 205, 23);
        btnBuy.setForeground(Color.WHITE);
        btnBuy.setBackground(Color.decode("#717D7E"));
        container.add(btnBuy);
        // final double finalTotal = total;
        btnBuy.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double total = 0;
                for (int i = 0; i < cart.size(); i++) {
                    total += con.getItemById(cart.get(i).getitemID()).getPrice();
                }

                JOptionPane.showMessageDialog(null, "Your Wallet : " + user.getWallet());
                
                if (user.getWallet() < total) {
                    double remainingAmount = total - user.getWallet();
                    JOptionPane.showMessageDialog(null, "Wallet funds not enough. Need additional: " + remainingAmount);
                } else {
                    ArrayList<ShoppingCart> copyCart = new ArrayList<>(cart);
                    
                    for (ShoppingCart c : copyCart) {
                        con.purchase(user, c);
                        cart.remove(c); 
                    }
                    con.updateWallet(user, -total);
                    JOptionPane.showMessageDialog(null, "Total Purchase : " + total);
                    JOptionPane.showMessageDialog(null, "Purchase Succesful");
                }
                new HomeUser(user);
                container.setVisible(false);
            }
        });

        btnGift.setBounds(235, 210, 205, 23);
        btnGift.setForeground(Color.WHITE);
        btnGift.setBackground(Color.decode("#717D7E"));
        container.add(btnGift);

        btnGift.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {

            double total = 0;
            for (int i = 0; i < cart.size(); i++) {
                total += con.getItemById(cart.get(i).getitemID()).getPrice();
            }

            if (user.getWallet() < total) {
                JOptionPane.showMessageDialog(null, "Wallet funds not enough");
            } else {
            new SelectGiftUser(user, cart, total);
                container.setVisible(false);
            }
        }
        });

        btnBack.setBounds(20, 250, 420, 23);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.decode("#717D7E"));
        container.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomeUser(user);
                container.setVisible(false);
            }
        });
        container.setVisible(true);
    }
}
