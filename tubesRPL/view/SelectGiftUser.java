package view;
import model.Item;
import model.Publisher;
import model.ShoppingCart;
import model.User;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;

import controller.Controller;

public class SelectGiftUser {
        Controller con = Controller.getInstance();
        JFrame update_item;
        JLabel UpdateItemMenu = new JLabel("Select User");
        JLabel gameOrDLC = new JLabel("Select User ID : ");
        JTextField isiIdGameOrDLC;

        JButton btnSubmit = new JButton("Buy Game");
        JButton btnBack = new JButton("Back");

        Object[][] data = null;
        JTable itemTable = null;
        String[] columnNames = {"ID", "Name"};
        User user;
        ArrayList<ShoppingCart> cart;

    public void loadTable()
    {
        itemTable.clearSelection();
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        itemTable.setModel(model);
    }

    public SelectGiftUser (User user, ArrayList<ShoppingCart> cart, double total) {
        this.user = user;
        this.cart = cart;
        update_item = new JFrame("Add Item");
        update_item.setSize(450, 400);
        update_item.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        update_item.setLocationRelativeTo(null);
        update_item.setLayout(null);
        update_item.getContentPane().setBackground(Color.DARK_GRAY);

        UpdateItemMenu.setBounds(20, 25, 170, 23);
        UpdateItemMenu.setForeground(Color.WHITE);
        update_item.add(UpdateItemMenu);

        JSeparator garisPemisah = new JSeparator();
        garisPemisah.setBounds(20, 45, 75, 5);
        garisPemisah.setForeground(Color.LIGHT_GRAY);
        update_item.add(garisPemisah);

        gameOrDLC.setBounds(20, 60, 200, 23);
        gameOrDLC.setForeground(Color.WHITE);
        update_item.add(gameOrDLC);

        JSeparator garisPemisah2 = new JSeparator();
        garisPemisah2.setBounds(20, 90, 390, 5);
        garisPemisah2.setForeground(Color.LIGHT_GRAY);
        update_item.add(garisPemisah2);


        ArrayList<User> users = con.getAllUserList();
        data = new Object[users.size()][2]; 

        for (int i = 0; i < users.size(); i++) {
            User item = users.get(i);
            data[i][0] = item.getId(); 
            data[i][1] = item.getName(); 
        }
    
        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);

        itemTable = new JTable(model);
        loadTable();
        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setBounds(20, 110, 390, 80);
        update_item.add(scrollPane);
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e)
            {
                if (e.getValueIsAdjusting()) { return; }
                String src = e.getSource().toString();
                int start = src.indexOf("{") + 1;
                int stop = src.length() - 1;
                String s = src.substring(start, stop);
                if (s.isEmpty()) { return; }
                int index = Integer.parseInt(s);
                isiIdGameOrDLC.setText(String.valueOf((Integer)data[index][0]));

            }
        });


        isiIdGameOrDLC = new JTextField();
        isiIdGameOrDLC.setBounds(130, 210, 260, 23);
        isiIdGameOrDLC.setForeground(Color.WHITE);
        isiIdGameOrDLC.setBackground(Color.DARK_GRAY);
        update_item.add(isiIdGameOrDLC);

        btnSubmit.setBounds(40, 255, 160, 23);
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setBackground(Color.decode("#717D7E"));
        update_item.add(btnSubmit);

        btnBack.setBounds(210, 255, 160, 23);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.decode("#717D7E"));
        update_item.add(btnBack);

        itemTable.setBackground(Color.DARK_GRAY);
        itemTable.setForeground(Color.WHITE);
        itemTable.getTableHeader().setBackground(Color.DARK_GRAY);
        itemTable.getTableHeader().setForeground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);


        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double total = 0;
                for (int i = 0; i < cart.size(); i++) {
                    total += con.getItemById(cart.get(i).getitemID()).getPrice();
                }
        
                new ShowShoppingCart(user, cart);
                update_item.setVisible(false);
                con.updateWallet(user, -total);
                
                ArrayList<ShoppingCart> copyCart = new ArrayList<>(cart);
                for (ShoppingCart c : copyCart) {
                    if (cart != null) {
                        con.gift(user, Integer.parseInt(isiIdGameOrDLC.getText()), c);
                        cart.remove(c); // Hapus item yang sudah digift
                    }
                }
        
                JOptionPane.showMessageDialog(null, "gift success");
                new HomeUser(user);
                update_item.setVisible(false);
            }
        });        
        
        update_item.setVisible(true);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new ShowShoppingCart(user, cart);
                update_item.setVisible(false);
            }
        });        

    }
    
}
