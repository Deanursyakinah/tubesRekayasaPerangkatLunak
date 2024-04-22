package view;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import controller.Controller;
import model.Item;
import model.User;
import java.awt.Color;
import java.util.ArrayList;

public class ShowGameLibrary {
    Controller con = Controller.getInstance();
    JFrame container;
    JButton btnBack;

    public ShowGameLibrary(User user,ArrayList<Item> library) {
        container = new JFrame("Show User Library");
        container.setSize(500, 380);
        container.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        container.setLocationRelativeTo(null);
        container.setLayout(null);
        container.getContentPane().setBackground(Color.DARK_GRAY);
        
        String[] columnNames = {"Name", "Description"};

        Object[][] data = new Object[library.size()][4]; 

        for (int i = 0; i < library.size(); i++) {
            Item items = library.get(i);
            data[i][0] = items.getName();
            data[i][1] = items.getDescription();
        }

        DefaultTableModel model = new DefaultTableModel(data, columnNames);

        JTable libraryTable = new JTable(model);

        JScrollPane scrollPane = new JScrollPane(libraryTable);
        scrollPane.setBounds(15, 50, 450, 150);
        container.add(scrollPane);

        libraryTable.setBackground(Color.DARK_GRAY);
        libraryTable.setForeground(Color.WHITE);
        libraryTable.getTableHeader().setBackground(Color.DARK_GRAY);
        libraryTable.getTableHeader().setForeground(Color.WHITE);
        scrollPane.getViewport().setBackground(Color.DARK_GRAY);

        JSeparator garisPemisah = new JSeparator();
        garisPemisah.setBounds(15, 230, 450, 5);
        garisPemisah.setForeground(Color.LIGHT_GRAY);
        container.add(garisPemisah);

        //Bagian Button Back
        btnBack = new JButton("Back");
        btnBack.setBounds(315, 250, 150, 23);
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
