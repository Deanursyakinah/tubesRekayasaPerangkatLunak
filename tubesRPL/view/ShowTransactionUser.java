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
import javax.swing.table.DefaultTableModel;
import controller.Controller;
import model.User;
import model.ShoppingCart;

/**
 *
 * @author abil
 */
public class ShowTransactionUser {
    Controller con = Controller.getInstance();
    JFrame container;
    JButton btnBack;


    public ShowTransactionUser(User user) {
        container = new JFrame("Show User Transaction");
        container.setSize(480, 300);
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLocationRelativeTo(null);
        container.setLayout(null);
        container.getContentPane().setBackground(Color.DARK_GRAY);

        JLabel title = new JLabel("Show User Transaction");
        title.setBounds(15, 15, 150, 23);
        title.setForeground(Color.WHITE);
        container.add(title);

        JSeparator garisPemisah = new JSeparator();
        garisPemisah.setBounds(15, 180, 420, 5);
        garisPemisah.setForeground(Color.LIGHT_GRAY);
        container.add(garisPemisah);

        ArrayList<ShoppingCart> transaction = con.getShoppingCart(user.getId());

        String[] columnNames = {"Transaction_Id", "Item_id","User_id","Item_Name","Description"};

        Object[][] data = new Object[transaction.size()][5]; 

        for (int i = 0; i < transaction.size(); i++) {
            ShoppingCart cart = transaction.get(i);
            data[i][0] = cart.getTransactionID(); 
            data[i][1] = cart.getitemID(); 
            data[i][2] = con.getTransactionByID(cart.getTransactionID()).getUserID();
            data[i][3] = con.getItemById(cart.getitemID()).getName(); 
            data[i][4] = cart.getDescription() ;
        }
    
        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        JTable userTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(userTable);
        scrollPane.setBounds(15, 50, 420, 110);
        container.add(scrollPane);

        userTable.setBackground(Color.DARK_GRAY);
        userTable.setForeground(Color.WHITE);
        userTable.getTableHeader().setBackground(Color.DARK_GRAY);
        userTable.getTableHeader().setForeground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);

        //Bagian Button Back
        btnBack = new JButton("Back");
        btnBack.setBounds(270, 205, 150, 23);
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
