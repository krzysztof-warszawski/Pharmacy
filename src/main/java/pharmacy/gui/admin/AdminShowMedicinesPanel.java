package pharmacy.gui.admin;

import pharmacy.utils.GetCurrentDate;
import pharmacy.service.AdminOperations;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static pharmacy.Main.mainFrame;

public class AdminShowMedicinesPanel extends JPanel {

    private JLabel loggedNameLabel, dateLabel, allMedicinesLabel;
    private JButton logOutButton, deleteMedButton, addMedButton, editMedButton, readMedButton;
    private JList<String> medicineList;
    private JScrollPane medicineListScroller;
    private AdminAddMedicinePanel adminAddMedicinePanel;
    private AdminReadUserPanel adminReadUserPanel;
    private AdminUpdateUserPanel adminUpdateUserPanel;
    private GetCurrentDate getCurrentDate = new GetCurrentDate();
    private BufferedImage img;

    public AdminShowMedicinesPanel(AdminOperations adminOperations) {
        setLayout(null);
        try {
            img = ImageIO.read(getClass().getResource("/background.png")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        loggedNameLabel = new JLabel(adminOperations.getUserInitData().getFirstName(), SwingConstants.CENTER);
        loggedNameLabel.setBounds(555, 15, 80, 50);
        loggedNameLabel.setFont(loggedNameLabel.getFont().deriveFont(15f));

        dateLabel = new JLabel(getCurrentDate.getCurrentDate());
        dateLabel.setBounds(50, 15, 100, 50);
        dateLabel.setFont(dateLabel.getFont().deriveFont(15f));

        logOutButton = new JButton("Log out");
        logOutButton.setBounds(555, 55, 80, 30);
        logOutButton.setFont(logOutButton.getFont().deriveFont(12f));
        logOutButton.addActionListener(e -> {
            mainFrame.logout();
        });

        allMedicinesLabel = new JLabel("MEDICINES: ", SwingConstants.CENTER);
        allMedicinesLabel.setBounds(100, 50, 500, 50);
        allMedicinesLabel.setFont(allMedicinesLabel.getFont().deriveFont(15f));

        medicineList = new JList(adminOperations.getEmployeeList());
        medicineList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        medicineList.setFont(medicineList.getFont().deriveFont(15f));

        medicineListScroller = new JScrollPane();
        medicineListScroller.setViewportView(medicineList);
        medicineListScroller.setBounds(225, 100, 250, 250);

        addMedButton = new JButton("ADD");
        addMedButton.setBounds(155, 370, 90, 40);
        addMedButton.setFont(addMedButton.getFont().deriveFont(13f));
        addMedButton.addActionListener(e -> {
            adminAddMedicinePanel = new AdminAddMedicinePanel(adminOperations);
            mainFrame.panelSwitchOver(adminAddMedicinePanel);
        });

        readMedButton = new JButton("DETAILS");
        readMedButton.setBounds(255, 370, 90, 40);
        readMedButton.setFont(dateLabel.getFont().deriveFont(13f));
        readMedButton.addActionListener(e -> {
            adminOperations.setUpdateUser(medicineList.getSelectedIndex());
            adminReadUserPanel = new AdminReadUserPanel(adminOperations);
            mainFrame.panelSwitchOver(adminReadUserPanel);
        });

        editMedButton = new JButton("EDIT");
        editMedButton.setBounds(355, 370, 90, 40);
        editMedButton.setFont(dateLabel.getFont().deriveFont(13f));
        editMedButton.addActionListener(e -> {
            adminOperations.setUpdateUser(medicineList.getSelectedIndex());
            adminUpdateUserPanel = new AdminUpdateUserPanel(adminOperations);
            mainFrame.panelSwitchOver(adminUpdateUserPanel);
        });

        deleteMedButton = new JButton("DELETE");
        deleteMedButton.setBounds(455, 370, 90, 40);
        deleteMedButton.setFont(dateLabel.getFont().deriveFont(13f));
        deleteMedButton.addActionListener(e -> {
            adminOperations.removeUser(medicineList.getSelectedIndex());
            medicineList = new JList(adminOperations.getEmployeeList());
            medicineList.setFont(medicineList.getFont().deriveFont(15f));
            medicineListScroller.setViewportView(medicineList);
        });

        add(loggedNameLabel);
        add(dateLabel);
        add(allMedicinesLabel);
        add(logOutButton);
        add(deleteMedButton);
        add(editMedButton);
        add(addMedButton);
        add(readMedButton);
        add(medicineListScroller);

    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }
}