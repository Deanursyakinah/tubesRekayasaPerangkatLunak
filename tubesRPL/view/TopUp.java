package view;

import javax.swing.*;

import controller.Controller;
import model.User;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TopUp {
    Controller con = Controller.getInstance();
    JFrame top_up;
    JLabel isiWallet = new JLabel("Isi Wallet");
    JTextField isiNominalTopUP ;
    JLabel nominalTopUP = new JLabel("Masukan Nominal Top Up  : ");
    JLabel pembayaran = new JLabel("pilih pembayaran                 :");
    String arrPembayaran[] = {"Paypal","Visa"};
    JComboBox<String> isiPembayaran = new JComboBox<>(arrPembayaran);
    JButton btnBayar = new JButton("Bayar");
    JButton btnBack = new JButton("Back");

    public TopUp(User user){
        top_up = new JFrame("isi wallet");
        top_up.setSize(400, 240);
        top_up.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        top_up.setLocationRelativeTo(null);
        top_up.setLayout(null);
        top_up.getContentPane().setBackground(Color.DARK_GRAY);

        isiWallet.setBounds(20, 25, 170, 23);
        isiWallet.setForeground(Color.WHITE);
        top_up.add(isiWallet);

        JSeparator garisPemisah = new JSeparator();
        garisPemisah.setBounds(20, 45, 60, 5);
        garisPemisah.setForeground(Color.LIGHT_GRAY);
        top_up.add(garisPemisah);

        nominalTopUP.setBounds(20, 60, 170, 23);
        nominalTopUP.setForeground(Color.WHITE);
        top_up.add(nominalTopUP);

        isiNominalTopUP = new JTextField();
        isiNominalTopUP.setBounds(180, 60, 160, 23);
        isiNominalTopUP.setForeground(Color.WHITE);
        isiNominalTopUP.setBackground(Color.DARK_GRAY);
        top_up.add(isiNominalTopUP);

        pembayaran.setBounds(20,100, 160, 23);
        pembayaran.setForeground(Color.WHITE);
        top_up.add(pembayaran);
        isiPembayaran.setBounds(180,100, 160, 23);
        isiPembayaran.setForeground(Color.WHITE);
        isiPembayaran.setBackground(Color.decode("#717D7E"));
        top_up.add(isiPembayaran);

        btnBayar.setBounds(20,140, 140, 23);
        btnBayar.setForeground(Color.WHITE);
        btnBayar.setBackground(Color.decode("#717D7E"));
        top_up.add(btnBayar);

        btnBack.setBounds(200,140, 140, 23);
        btnBack.setForeground(Color.WHITE);
        btnBack.setBackground(Color.decode("#717D7E"));
        top_up.add(btnBack);

        
        btnBayar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double topUpAmount = Double.parseDouble(isiNominalTopUP.getText()); 
                boolean insert = con.updateWallet(user, topUpAmount);
                if (insert) {
                    JOptionPane.showMessageDialog(null, "Top Up successful");
                    new HomeUser(user);
                    top_up.dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Top Up failed");
                }
            }
        });
        top_up.setVisible(true);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new HomeUser(user);
                top_up.setVisible(false);
            }
        });

        top_up.setVisible(true);

    }
}