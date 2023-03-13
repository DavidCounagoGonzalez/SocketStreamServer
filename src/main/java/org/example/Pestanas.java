package org.example;

import javax.swing.*;
import java.awt.*;


public class Pestanas extends JPanel{

    public static JTextArea chat;
    public static JScrollPane scroll;
    private JButton btnCer;

    public Pestanas(String title){
        setLayout(null);
        chat = new JTextArea();
        chat.setEditable(false);
        chat.setFont(new Font("TimesRoman", Font.TRUETYPE_FONT, 15));
        scroll = new JScrollPane(chat);
        scroll.setBorder(BorderFactory.createMatteBorder(2,2,2,2, Color.BLACK));
        scroll.setBounds(0,0,365,222);
        add(scroll);
        setOpaque(false);
        btnCer = new JButton();
        btnCer.setPreferredSize(new java.awt.Dimension(10, 10));
        btnCer.setBounds(365, 100, 50,30);
        btnCer.setFont(new Font("TimesRoman", Font.BOLD, 10));
        btnCer.setForeground(Color.RED);
        btnCer.setText("X");
        //Listener para cierre de tabs con acceso estatico al `JTabbedPane`
        btnCer.addActionListener(e -> InterfazServer.tabPanel.removeTabAt(InterfazServer.tabPanel.indexOfTab(title)));
        add(btnCer);


    }

}
