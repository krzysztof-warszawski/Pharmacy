package dev.kris.pharmacy.log;

import javax.swing.*;

public class LogFrame extends JFrame {

    public LogFrame() {
        setSize(1000,1000);
        setResizable(false);
        setTitle("Log in");
        setLocationRelativeTo(null);

        LogPanel logPanel = new LogPanel();

        add(logPanel);
    }

}
