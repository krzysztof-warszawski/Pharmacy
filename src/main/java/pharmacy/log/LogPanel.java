package pharmacy.log;

import pharmacy.Main;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class LogPanel extends JPanel {

    private JLabel pharmacyName, usernameLabel, passwordLabel;
    private JButton loginButton;
    private JCheckBox checkPasswordBox;
    private JTextField usernameTextField;
    private JPasswordField passwordTextField;

    private BufferedImage img;

    public LogPanel(){
        setLayout(null);
        try {
            img = ImageIO.read(getClass().getResource("/LogoAndBackground.png")
            );
        } catch (IOException e) {
            e.printStackTrace();
        }

        pharmacyName = new JLabel("PHARMACY", SwingConstants.CENTER);
        pharmacyName.setBounds(200, 65, 300,50);
        pharmacyName.setFont(pharmacyName.getFont().deriveFont(50f));

        usernameLabel = new JLabel("username: ");
        usernameLabel.setBounds(110, 312, 150, 50);
        usernameLabel.setFont(usernameLabel.getFont().deriveFont(20f));

        passwordLabel = new JLabel("password: ");
        passwordLabel.setBounds(110, 372, 150, 50);
        passwordLabel.setFont(passwordLabel.getFont().deriveFont(20f));

        usernameTextField = new JTextField();
        usernameTextField.setBounds(225, 320, 250, 40);
        usernameTextField.setFont(usernameTextField.getFont().deriveFont(15f));

        passwordTextField = new JPasswordField();
        passwordTextField.setBounds(225, 380, 250, 40);
        passwordTextField.setFont(passwordTextField.getFont().deriveFont(15f));

        checkPasswordBox = new JCheckBox("check password");
        checkPasswordBox.setBounds(480,380,150,40);
        checkPasswordBox.addActionListener(e -> {
            if (checkPasswordBox.isSelected()) {
                passwordTextField.setEchoChar('\u0000');
            } else {
                passwordTextField.setEchoChar('*');
            }
        });

        loginButton = new JButton("Log in");
        loginButton.setBounds(275, 500, 150, 50);
        loginButton.setFont(loginButton.getFont().deriveFont(20f));
        loginButton.addActionListener(e -> {
            if (!Main.pharmacyApp.logging(usernameTextField.getText(), String.valueOf(passwordTextField.getPassword()))) {
                JOptionPane.showMessageDialog(null,"Incorrect credentials","Incorrect credentials", 2);
            }
            usernameTextField.setText("");
            passwordTextField.setText("");
        });

        add(checkPasswordBox);
        add(pharmacyName);
        add(usernameLabel);
        add(passwordLabel);
        add(passwordTextField);
        add(usernameTextField);
        add(loginButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(img, 0, 0, getWidth(), getHeight(), null);
    }

    public JLabel getPharmacyName() {
        return pharmacyName;
    }

    public void setPharmacyName(JLabel pharmacyName) {
        this.pharmacyName = pharmacyName;
    }

    public JLabel getUsernameLabel() {
        return usernameLabel;
    }

    public void setUsernameLabel(JLabel usernameLabel) {
        this.usernameLabel = usernameLabel;
    }

    public JLabel getPasswordLabel() {
        return passwordLabel;
    }

    public void setPasswordLabel(JLabel passwordLabel) {
        this.passwordLabel = passwordLabel;
    }

    public JButton getLoginButton() {
        return loginButton;
    }

    public void setLoginButton(JButton loginButton) {
        this.loginButton = loginButton;
    }

    public JTextField getUsernameTextField() {
        return usernameTextField;
    }

    public void setUsernameTextField(JTextField usernameTextField) {
        this.usernameTextField = usernameTextField;
    }

    public JTextField getPasswordTextField() {
        return passwordTextField;
    }

    public void setPasswordTextField(JPasswordField passwordTextField) {
        this.passwordTextField = passwordTextField;
    }
}