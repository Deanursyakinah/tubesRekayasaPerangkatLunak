package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import controller.Controller;


import model.Publisher;
import model.DLC;
import model.Game;
import model.Item;

import javax.swing.JOptionPane;

public class AddItem {
    Controller con = Controller.getInstance();
    JFrame add_item;
    JLabel addItemMenu = new JLabel("Add Item");
    JLabel gameOrDLC = new JLabel("Add Game or DLC : ");
    JTextField isiNamaGame;
    JLabel namaGame = new JLabel("Name : ");
    JTextField isideskripsi;
    JLabel deskripsi = new JLabel("Deskripsi              : ");
    JTextField isiHarga;
    JLabel harga = new JLabel("Price                      : ");
    JLabel gameList = new JLabel("Game                      : ");
    JButton btnSubmit = new JButton("Submit");
    JButton btnBack = new JButton("Back");
    JRadioButton addGame = new JRadioButton("Game");
    JRadioButton addDLC = new JRadioButton("DLC");
    JComboBox<String> comboBox = null;
    

    int currY = 25;
    public void addItemPair(Component left, Component right, int width, int height)
    {
        if (left != null) { left.setBounds(20, currY, width, height); add_item.add(left); }
        if (right != null) { right.setBounds(130, currY, width, height); add_item.add(right); }
        currY += height + 5;
    }

    public void addItemPair(Component left, JRadioButton[] right, int width, int height, int interval)
    {
        if (left != null) { left.setBounds(20, currY, width, height); add_item.add(left); }
        int temp = 130;
        ButtonGroup bgAddItem = new ButtonGroup();
        for (JRadioButton c : right)
        {
            c.setBounds(temp, currY, interval, height);
            temp += interval;
            bgAddItem.add(c);
            add_item.add(c);
        }
        currY += height + 5;
    }

    public AddItem(Publisher publisher) {
        add_item = new JFrame("Add Item");
        add_item.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add_item.setLocationRelativeTo(null);
        add_item.setLayout(null);
        add_item.getContentPane().setBackground(Color.DARK_GRAY);

        addItemMenu.setForeground(Color.WHITE);
        addItemPair(addItemMenu, null, 170, 23);

        JSeparator garisPemisah = new JSeparator();
        garisPemisah.setForeground(Color.LIGHT_GRAY);
        addItemPair(garisPemisah, null, 60, 23);

        gameOrDLC.setForeground(Color.WHITE);
        addGame.setBackground(Color.DARK_GRAY);
        addGame.setForeground(Color.WHITE);
        addDLC.setBackground(Color.DARK_GRAY);
        addDLC.setForeground(Color.WHITE);
        addItemPair(gameOrDLC, new JRadioButton[]{addGame, addDLC}, 250, 23, 70);

        namaGame.setForeground(Color.WHITE);
        isiNamaGame = new JTextField();
        isiNamaGame.setForeground(Color.WHITE);
        isiNamaGame.setBackground(Color.DARK_GRAY);
        addItemPair(namaGame, isiNamaGame, 250, 23);

        deskripsi.setForeground(Color.WHITE);
        isideskripsi = new JTextField();
        isideskripsi.setForeground(Color.WHITE);
        isideskripsi.setBackground(Color.DARK_GRAY);
        addItemPair(deskripsi, isideskripsi, 250, 23);

        harga.setForeground(Color.WHITE);
        isiHarga = new JTextField();
        isiHarga.setForeground(Color.WHITE);
        isiHarga.setBackground(Color.DARK_GRAY);
        addItemPair(harga, isiHarga, 250, 23);

        gameList.setForeground(Color.WHITE);
        comboBox = new JComboBox<>();
        addItemPair(gameList, comboBox, 250, 23);

        JSeparator garisPemisah2 = new JSeparator();
        garisPemisah2.setForeground(Color.LIGHT_GRAY);
        garisPemisah2.setVisible(false);
        addItemPair(garisPemisah2, null, 350, 23);

        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setBackground(Color.decode("#717D7E"));
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.decode("#717D7E"));
        addItemPair(btnSubmit, btnBack,90, 23);


        addDLC.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (addDLC.isSelected())
                {
                    gameList.setVisible(true);
                    comboBox.setVisible(true);

                }
                else if (addGame.isSelected())
                {
                    comboBox.setVisible(false);
                    gameList.setVisible(false);
                }
            }
        });

        addGame.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                if (addDLC.isSelected())
                {
                    gameList.setVisible(true);
                    comboBox.setVisible(true);

                }
                else if (addGame.isSelected())
                {
                    comboBox.setVisible(false);
                    gameList.setVisible(false);
                }
            }
        });
        for (Item item : con.getItem())
        {
            if (item.getType().equals("Game") && item.getPublisherID() == publisher.getId()) { comboBox.addItem(item.getName()); }
        }
        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String namaGame = isiNamaGame.getText();
                String deskripsiGame = isideskripsi.getText();
                String hargaString = isiHarga.getText();
        
                // Check for empty fields
                if (namaGame.isEmpty() || deskripsiGame.isEmpty() || hargaString.isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Please fill in all fields");
                    return;
                }
        
                // Validate price input
                Double hargaGame;
                try {
                    hargaGame = Double.parseDouble(hargaString);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(null, "Invalid price format");
                    return;
                }
        
                if (addGame.isSelected()) {
                    Game game = new Game();
                    game.setName(namaGame);
                    game.setDescription(deskripsiGame);
                    game.setPrice(hargaGame);
        
                    // Check if publisher is not null before passing to insertNewGame
                    if (publisher != null) {
                        boolean insert = con.insertNewGame(game, publisher);
                        if (insert) {
                            JOptionPane.showMessageDialog(null, "Insert successful");
                            new HomePublisher(publisher);
                            add_item.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Insert failed");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Publisher information missing");
                    }
                }else if(addDLC.isSelected()){
                    DLC dlc = new DLC();
                    dlc.setName(namaGame);
                    dlc.setDescription(deskripsiGame);
                    dlc.setPrice(hargaGame);
        
                    // Check if publisher is not null before passing to insertNewGame
                    if (publisher != null) {
                        boolean insert = con.insertNewDLC(dlc, publisher);
                        if (insert) {
                            JOptionPane.showMessageDialog(null, "Insert successful");
                            new HomePublisher(publisher);
                            add_item.dispose();
                        } else {
                            JOptionPane.showMessageDialog(null, "Insert failed");
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Publisher information missing");
                    }
                } else{
                    JOptionPane.showMessageDialog(null, "select game or DLC !!!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        add_item.setVisible(true);
        add_item.setSize(430, currY + 75);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePublisher(publisher);
                add_item.dispose();
            }
        });
        
    }
}
