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
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import controller.Controller;
import model.Admin;
import model.User;

/**
 *
 * @author abil
 */
public class BanUser {
    Controller con = Controller.getInstance();
    JFrame container;
    JButton btnBack;
    JTextField id;
    JButton btnViewAllUser;
    JButton btnRegistrasi;

    public BanUser(Admin admin,ArrayList<User> nonBannedUsers) {
        container = new JFrame("Ban User");
        container.setSize(500, 380);
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLocationRelativeTo(null);
        container.setLayout(null);
        container.getContentPane().setBackground(Color.DARK_GRAY);

        JLabel title = new JLabel("Ban User");
        title.setBounds(15, 15, 150, 23);
        title.setForeground(Color.WHITE);
        container.add(title);

        String[] columnNames = {"ID", "Name", "Status", "Wallet"};

        Object[][] data = new Object[nonBannedUsers.size()][4]; 

        for (int i = 0; i < nonBannedUsers.size(); i++) {
            User user = nonBannedUsers.get(i);
            data[i][0] = user.getId(); 
            data[i][1] = user.getName(); 
            data[i][2] = user.getStatus().toString();
            data[i][3] = user.getWallet(); 
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
        garisPemisah.setBounds(15, 230, 450, 5);
        garisPemisah.setForeground(Color.LIGHT_GRAY);
        container.add(garisPemisah);

        // Account Id
        JLabel labelName = new JLabel("ID of user to be banned");
        id = new JTextField();
        labelName.setBounds(15, 250, 150, 23);
        id.setBounds(160, 250, 305, 23);
        labelName.setForeground(Color.WHITE);
        id.setForeground(Color.WHITE);
        id.setBackground(Color.DARK_GRAY);
        container.add(labelName);
        container.add(id);

        userTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        userTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
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
                id.setText(String.valueOf((Integer)data[index][0]));

            }
        });

        //Bagian Button Back
        btnBack = new JButton("Back");
        btnBack.setBounds(326, 290, 140, 23);
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

        //Bagian Button Banned
        btnBack = new JButton("Ban User");
        btnBack.setBounds(160, 290, 140, 23);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.decode("#717D7E"));
        container.add(btnBack);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                
                boolean ban = con.updateStatusUser(Integer.parseInt(id.getText()));
                if (ban){
                    JOptionPane.showMessageDialog(container, "User Succesfully banned","Success",JOptionPane.WARNING_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(container, "User Not Found","Error",
                            JOptionPane.WARNING_MESSAGE);
                }
                new BanUser(admin, nonBannedUsers);
                container.setVisible(false);
            }
        });

        container.setVisible(true);
    }
}
