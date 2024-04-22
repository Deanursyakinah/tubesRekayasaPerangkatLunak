package view;

import java.awt.Color;
import java.util.ArrayList;
import model.Publisher;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import controller.Controller;
import model.Item;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class RemovedGame {
    Controller con = Controller.getInstance();
    JTextField id;
    JFrame container;
    JButton btnSubmit;
    JButton btnBack;

    public RemovedGame(Publisher publisher, ArrayList<Item> nonBannedItems) {
        container = new JFrame("Removed Item");
        container.setSize(500, 380);
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLocationRelativeTo(null);
        container.setLayout(null);
        container.getContentPane().setBackground(Color.DARK_GRAY);
        
        String[] columnNames = {"item_id", "Name", "Type", "Price", "Description", "Publisher_id", "Item_status"};

        Object[][] data = new Object[nonBannedItems.size()][7]; 

        for (int i = 0; i < nonBannedItems.size(); i++) {
            Item items = nonBannedItems.get(i);
            data[i][0] = items.getItemID(); 
            data[i][1] = items.getName(); 
            data[i][2] = items.getType();
            data[i][3] = items.getPrice(); 
            data[i][4] = items.getDescription();
            data[i][5] = items.getPublisherID();
            data[i][6] = items.getStatus().toString();
        }
    
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        JTable itemTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(itemTable);
        scrollPane.setBounds(15, 50, 450, 150);
        container.add(scrollPane);


        itemTable.setBackground(Color.DARK_GRAY);
        itemTable.setForeground(Color.WHITE);
        itemTable.getTableHeader().setBackground(Color.DARK_GRAY);
        itemTable.getTableHeader().setForeground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);

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
                id.setText(String.valueOf((Integer)data[index][0]));

            }
        });

    

        JLabel labelName = new JLabel("ID of item to be removed");
        id = new JTextField();
        labelName.setBounds(15, 250, 150, 23);
        id.setBounds(160, 250, 305, 23);
        labelName.setForeground(Color.WHITE);
        id.setForeground(Color.WHITE);
        id.setBackground(Color.DARK_GRAY);
        container.add(labelName);
        container.add(id);

        //Bagian Button Back
        btnBack = new JButton("Back");
        btnBack.setBounds(326, 290, 140, 23);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.decode("#717D7E"));
        container.add(btnBack);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomePublisher(publisher);
                container.setVisible(false);
            }
        });

        //Bagian Button remove
        btnSubmit = new JButton("Remove Item");
        btnSubmit.setBounds(160, 290, 140, 23);
        btnSubmit.setForeground(Color.WHITE);
        btnSubmit.setBackground(Color.decode("#717D7E"));
        container.add(btnSubmit);


        btnSubmit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean ban = con.updateStatusItem(Integer.parseInt(id.getText()));
                if (ban){
                    JOptionPane.showMessageDialog(container, "Item Succesfully banned","Success",JOptionPane.WARNING_MESSAGE);
                    new HomePublisher(publisher);
                    container.dispose();
                } else {
                    JOptionPane.showMessageDialog(container, "Item Not Found","Error",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
        container.setVisible(true);
    }
}
