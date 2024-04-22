package view;

import javax.swing.*;

import controller.Controller;
import model.Item;
import model.ShoppingCart;
import model.User;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HomeUser {
    Controller con = Controller.getInstance();
    JFrame home_user;
    JButton btnShowGameList;
    JButton btnTopup;
    JButton btnShowGameLibrary;
    JButton btnShopingCart;
    JButton btnShowUserTransaction;
    JButton btnBack;
    
    public HomeUser (User user) {
        home_user = new JFrame("Home ");
        home_user.setSize(320, 350);
        home_user.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home_user.setLocationRelativeTo(null);
        home_user.setLayout(null);
        home_user.getContentPane().setBackground(Color.DARK_GRAY);

        JLabel welcomeUser = new JLabel(" welcome to steam ");
        welcomeUser.setBounds(100, 25, 170, 23);
        welcomeUser.setForeground(Color.WHITE);
        home_user.add(welcomeUser);

        JSeparator garisPemisah = new JSeparator();
        garisPemisah.setBounds(80, 45, 150, 5);
        garisPemisah.setForeground(Color.LIGHT_GRAY);
        home_user.add(garisPemisah);

        btnShowGameList = new JButton("Game list");
        btnShowGameList.setBounds(80, 60, 150, 23);
        btnShowGameList.setForeground(Color.WHITE);
        btnShowGameList.setBackground(Color.decode("#717D7E"));
        home_user.add(btnShowGameList);

        btnShowGameList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowGameList(user);
                home_user.dispose();
            }
        });

        btnShowGameLibrary = new JButton("Game Library");
        btnShowGameLibrary.setBounds(80, 90, 150, 23);
        btnShowGameLibrary.setForeground(Color.WHITE);
        btnShowGameLibrary.setBackground(Color.decode("#717D7E"));
        home_user.add(btnShowGameLibrary);

        btnShowGameLibrary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Item> library = con.getLibrary(user);
                new ShowGameLibrary(user,library);
                home_user.dispose();
            }
        });

        btnShopingCart = new JButton("Shopping Cart");
        btnShopingCart.setBounds(80, 120, 150, 23);
        btnShopingCart.setForeground(Color.WHITE);
        btnShopingCart.setBackground(Color.decode("#717D7E"));
        home_user.add(btnShopingCart);

        btnShopingCart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<ShoppingCart> cart = user.getCart();
                new ShowShoppingCart(user,cart);
                home_user.dispose();
            }
        });

        btnTopup = new JButton("Top Up");
        btnTopup.setBounds(80, 150, 150, 23);
        btnTopup.setForeground(Color.WHITE);
        btnTopup.setBackground(Color.decode("#717D7E"));
        home_user.add(btnTopup);

        btnTopup.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new TopUp(user);
                home_user.dispose();
            }
        });

        btnShowUserTransaction = new JButton("history transaction");
        btnShowUserTransaction.setBounds(80, 180, 150, 23);
        btnShowUserTransaction.setForeground(Color.WHITE);
        btnShowUserTransaction.setBackground(Color.decode("#717D7E"));
        home_user.add(btnShowUserTransaction);

        btnShowUserTransaction.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowTransactionUser(user);
                home_user.dispose();
            }
        });

        btnBack = new JButton("Log out");
        btnBack.setBounds(80, 230, 150, 23);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.decode("#717D7E"));
        home_user.add(btnBack);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                home_user.setVisible(false);
            }
        });

        btnShowGameList.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomeUser(user);
                home_user.setVisible(false);
            }
        });
        home_user.setVisible(true);
    }
}
