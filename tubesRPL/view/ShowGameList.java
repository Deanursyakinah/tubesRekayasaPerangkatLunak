package view;

import model.Item;
import model.Publisher;
import model.User;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
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

public class ShowGameList {
    Controller con = Controller.getInstance();
    JFrame update_item;
    JLabel UpdateItemMenu = new JLabel("Buy Item");
    JLabel gameOrDLC = new JLabel("Buy Game or DLC : ");
    JTextField isiIdGameOrDLC;
    JLabel idItem = new JLabel("ID Game/DLC : ");
    JTextField isiNamaItem;
    JLabel namaItem = new JLabel("Game/DLC Name : ");
    JTextField isideskripsi;
    JLabel deskripsi = new JLabel("Deskripsi              : ");
    JTextField isiHarga;
    JLabel harga = new JLabel("Price                      : ");
    JButton btnSubmit = new JButton("Add to Cart");
    JButton btnBack = new JButton("Back");
    JRadioButton updateGame = new JRadioButton("Game");
    JRadioButton updateDLC = new JRadioButton("DLC");
    Object[][] data = null;
    JTable itemTable = null;
    String[] columnNames = { "ID", "Name", "Type", "Description", "Price", "Publisher Name" };

    User user;

    Object[][] getDataItem() {
        String game = "";
        if (updateDLC.isSelected()) {
            game = "DLC";
        } else if (updateGame.isSelected()) {
            game = "Game";
        }
        int count = 0;
        ArrayList<Item> items = con.getLibrary(user);
        for (int i = 0; i < data.length; i++) {
            if (game.equals((String) data[i][2])) {
                boolean found = false;
                for (Item item : items) {
                    if (item.getItemID() == (Integer) data[i][0]) {
                        found = true;
                        break;
                    }
                }
                count++;
            }
        }
        Object[][] data2 = new Object[count][6];
        int count2 = 0;
        for (int i = 0; i < data.length; i++) {
            if (game.equals(data[i][2])) {
                boolean found = false;
                for (Item item : items) {
                    if (item.getItemID() == (Integer) data[i][0]) {
                        found = true;
                        break;
                    }
                }

                data2[count2] = data[i];
                count2++;

            }
        }
        return data2;
    }

    public void loadTable() {
        itemTable.clearSelection();
        DefaultTableModel model = new DefaultTableModel(getDataItem(), columnNames);
        itemTable.setModel(model);
    }

    public ShowGameList(User user) {
        this.user = user;
        update_item = new JFrame("Show Game List");
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

        updateGame.setBounds(150, 60, 70, 23);
        updateDLC.setBounds(220, 60, 80, 23);
        updateGame.setBackground(Color.DARK_GRAY);
        updateGame.setForeground(Color.WHITE);
        updateDLC.setBackground(Color.DARK_GRAY);
        updateDLC.setForeground(Color.WHITE);
        update_item.add(updateGame);
        update_item.add(updateDLC);
        ButtonGroup bgUpdateItem = new ButtonGroup();
        updateDLC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTable();
            }
        });
        updateGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadTable();
            }
        });
        bgUpdateItem.add(updateGame);
        bgUpdateItem.add(updateDLC);

        JSeparator garisPemisah2 = new JSeparator();
        garisPemisah2.setBounds(20, 90, 390, 5);
        garisPemisah2.setForeground(Color.LIGHT_GRAY);
        update_item.add(garisPemisah2);

        ArrayList<Item> itemList = con.getItem();
        data = new Object[itemList.size()][6];

        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            data[i][0] = item.getItemID();
            data[i][1] = item.getName();
            data[i][2] = item.getType();
            data[i][3] = item.getDescription();
            data[i][4] = item.getPrice();
            data[i][5] = item.getPublisherID();
        }

        DefaultTableModel model = new DefaultTableModel(new Object[0][0], columnNames);

        itemTable = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setBounds(20, 110, 390, 80);
        update_item.add(scrollPane);
        itemTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        itemTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    return;
                }
                String src = e.getSource().toString();
                int start = src.indexOf("{") + 1;
                int stop = src.length() - 1;
                String s = src.substring(start, stop);
                if (s.isEmpty()) {
                    return;
                }
                int index = Integer.parseInt(s);
                isiIdGameOrDLC.setText(String.valueOf((Integer) getDataItem()[index][0]));

            }
        });

        idItem.setBounds(20, 210, 200, 23);
        idItem.setForeground(Color.WHITE);
        update_item.add(idItem);

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
                Item item = null;
                for (Object[] v : data) {
                    if (String.valueOf(v[0]).equals(isiIdGameOrDLC.getText())) {
                        for (Item item2 : con.getItem()) {
                            if (item2.getItemID() == (int) v[0]) {
                                item = item2;
                            }
                        }
                        break;
                    }
                }
                if (item != null) {
                    con.insertIntoShoppingCart(user, item);
                }
                update_item.dispose();

            }
        });
        update_item.setVisible(true);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomeUser(user);
                update_item.dispose();
            }
        });

    }
}
