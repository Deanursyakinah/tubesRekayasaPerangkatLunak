package view;

import javax.swing.*;
import controller.Controller;
import model.Item;
import model.Publisher;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class HomePublisher {
    Controller con = Controller.getInstance();
    JFrame home_publisher;
    JButton btnAddItem;
    JButton btnBack;
    JButton btnEditItem;
    JButton btnRemovedGame;
    JButton btnShowRemovedGame;

    public HomePublisher (Publisher publisher){
        home_publisher = new JFrame("Home");
        home_publisher.setSize(300, 400);
        home_publisher.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        home_publisher.setLocationRelativeTo(null);
        home_publisher.setLayout(null);
        home_publisher.getContentPane().setBackground(Color.DARK_GRAY);

        JLabel welcomePublisher = new JLabel(" Welcome to Publisher Menu ");
        welcomePublisher.setBounds(70, 25, 170, 23);
        welcomePublisher.setForeground(Color.WHITE);
        home_publisher.add(welcomePublisher);

        JSeparator garisPemisah = new JSeparator();
        garisPemisah.setBounds(50, 45, 190, 5);
        garisPemisah.setForeground(Color.LIGHT_GRAY);
        home_publisher.add(garisPemisah);

        btnAddItem = new JButton("Add Item");
        btnAddItem.setBounds(50, 90, 190, 23);
        btnAddItem.setForeground(Color.WHITE);
        btnAddItem.setBackground(Color.decode("#717D7E"));
        home_publisher.add(btnAddItem);

        btnEditItem = new JButton("Edit Item");
        btnEditItem.setBounds(50, 120, 190, 23);
        btnEditItem.setForeground(Color.WHITE);
        btnEditItem.setBackground(Color.decode("#717D7E"));
        home_publisher.add(btnEditItem);

        btnShowRemovedGame = new JButton("Show Removed Items");
        btnShowRemovedGame.setBounds(50, 150, 190, 23);
        btnShowRemovedGame.setForeground(Color.WHITE);
        btnShowRemovedGame.setBackground(Color.decode("#717D7E"));
        home_publisher.add(btnShowRemovedGame);

        btnRemovedGame = new JButton("Remove Item");
        btnRemovedGame.setBounds(50, 180, 190, 23);
        btnRemovedGame.setForeground(Color.WHITE);
        btnRemovedGame.setBackground(Color.decode("#717D7E"));
        home_publisher.add(btnRemovedGame);

        // Bagian Button Logout
        btnBack = new JButton("Logout");
        btnBack.setBounds(50, 270, 190, 23);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.decode("#717D7E"));
        home_publisher.add(btnBack);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Login();
                home_publisher.setVisible(false);
            }
        });

        btnAddItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddItem(publisher);

                home_publisher.dispose();
            }
        });

        btnShowRemovedGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Item> removeItems = con.getRemoveItem();
                new ShowRemovedGame(publisher, removeItems);
                home_publisher.dispose();
            }
        });

        btnRemovedGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Item> items = con.getItemListRemove();
                new RemovedGame(publisher, items);
                home_publisher.dispose();
            }
        });
        home_publisher.setVisible(true);

        btnEditItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ArrayList<Item> itemPublisher = con.getAllItem();
                new EditItem(publisher, itemPublisher);
                home_publisher.dispose();
            }
        });
        home_publisher.setVisible(true);
    }

}
