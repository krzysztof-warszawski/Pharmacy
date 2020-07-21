package pharmacy.gui.admin;

import pharmacy.service.MedicineService;
import pharmacy.service.MedicineServiceImpl;
import pharmacy.service.UserProfileService;
import pharmacy.utils.CurrentDate;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

import static pharmacy.Main.mainFrame;

public class AdminShowMedicinesPanel extends JPanel {

    private JLabel userNameLabel, dateLabel, allMedicinesLabel;
    private JButton logOutButton, deleteMedButton, addMedButton, editMedButton, readMedButton, backToMenu;
    private JList<String> medicineList;
    private JScrollPane medicineListScroller;
    private AdminAddMedicinePanel adminAddMedicinePanel;
    private AdminReadMedicinePanel adminReadMedicinePanel;
    private AdminUpdateMedicinePanel adminUpdateMedicinePanel;
    private BufferedImage img;

    private CurrentDate currentDate = new CurrentDate();
    private MedicineService medicineService = new MedicineServiceImpl();

    public AdminShowMedicinesPanel() {
        setLayout(null);
        try {
            img = ImageIO.read(getClass().getResource("/background.png")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        userNameLabel = new JLabel(UserProfileService.getFirstName(), SwingConstants.CENTER);
        userNameLabel.setBounds(555, 15, 80, 50);
        userNameLabel.setFont(userNameLabel.getFont().deriveFont(15f));

        dateLabel = new JLabel(currentDate.getCurrentDate());
        dateLabel.setBounds(50, 15, 100, 50);
        dateLabel.setFont(dateLabel.getFont().deriveFont(15f));

        logOutButton = new JButton("LOG OUT");
        logOutButton.setBounds(555, 55, 80, 30);
        logOutButton.setFont(logOutButton.getFont().deriveFont(12f));
        logOutButton.addActionListener(e -> {
            mainFrame.logout();
        });

        backToMenu = new JButton("MENU");
        backToMenu.setBounds(555, 85, 80, 30);
        backToMenu.setFont(backToMenu.getFont().deriveFont(12f));
        backToMenu.addActionListener(e -> {
            mainFrame.panelSwitchOver(new AdminMenuPanel());
        });

        allMedicinesLabel = new JLabel("MEDICINES: ", SwingConstants.CENTER);
        allMedicinesLabel.setBounds(100, 50, 500, 50);
        allMedicinesLabel.setFont(allMedicinesLabel.getFont().deriveFont(15f));

        medicineList = new JList(medicineService.getMedicineNameList());
        medicineList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        medicineList.setFont(medicineList.getFont().deriveFont(15f));

        medicineListScroller = new JScrollPane();
        medicineListScroller.setViewportView(medicineList);
        medicineListScroller.setBounds(225, 100, 250, 250);

        addMedButton = new JButton("ADD");
        addMedButton.setBounds(155, 370, 90, 40);
        addMedButton.setFont(addMedButton.getFont().deriveFont(13f));
        addMedButton.addActionListener(e -> {
            adminAddMedicinePanel = new AdminAddMedicinePanel(medicineService);
            mainFrame.panelSwitchOver(adminAddMedicinePanel);
        });

        readMedButton = new JButton("DETAILS");
        readMedButton.setBounds(255, 370, 90, 40);
        readMedButton.setFont(dateLabel.getFont().deriveFont(13f));
        readMedButton.addActionListener(e -> {
            if (medicineList.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null,"Nothing is selected!","Information", 1);
            } else {
                medicineService.findMedicineData(medicineList.getSelectedIndex());
                adminReadMedicinePanel = new AdminReadMedicinePanel(medicineService);
                mainFrame.panelSwitchOver(adminReadMedicinePanel);
            }
        });

        editMedButton = new JButton("EDIT");
        editMedButton.setBounds(355, 370, 90, 40);
        editMedButton.setFont(dateLabel.getFont().deriveFont(13f));
        editMedButton.addActionListener(e -> {
            if (medicineList.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null,"Nothing is selected!","Information", 1);
            } else {
                medicineService.findMedicineData(medicineList.getSelectedIndex());
                adminUpdateMedicinePanel = new AdminUpdateMedicinePanel(medicineService);
                mainFrame.panelSwitchOver(adminUpdateMedicinePanel);
            }
        });

        deleteMedButton = new JButton("DELETE");
        deleteMedButton.setBounds(455, 370, 90, 40);
        deleteMedButton.setFont(dateLabel.getFont().deriveFont(13f));
        deleteMedButton.addActionListener(e -> {
            if (medicineList.isSelectionEmpty()) {
                JOptionPane.showMessageDialog(null,"Nothing is selected!","Information", 1);
            } else {
                medicineService.removeMedicine(medicineList.getSelectedIndex());
                medicineList = new JList(medicineService.getMedicineNameList());
                medicineList.setFont(medicineList.getFont().deriveFont(15f));
                medicineListScroller.setViewportView(medicineList);
            }
        });

        add(userNameLabel);
        add(dateLabel);
        add(allMedicinesLabel);
        add(logOutButton);
        add(backToMenu);
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
