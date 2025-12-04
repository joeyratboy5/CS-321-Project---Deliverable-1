package baseFrontEnd.Screens;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.math.BigDecimal;

import baseFrontEnd.Screen;
import baseFrontEnd.User;
import baseFrontEnd.Wrapper;

public class IntroScreen extends Screen {

    private static final long serialVersionUID = -4967001973343568430L;

    private JButton submitButton;
    private JTextField nameTextField;
    private JTextField emailTextField;

     @Override
    protected void initialize() {
        setLayout(null);

        // Name label and field
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(200, 125, 200, 25);

        nameTextField = new JTextField();
        nameTextField.setBounds(200, 150, 200, 30);

        // Email label and field (moved down)
        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(200, 190, 200, 25);  // Changed from 165 to 190

        emailTextField = new JTextField();
        emailTextField.setBounds(200, 215, 200, 30);  // Changed from 190 to 215

        // Submit button (moved down)
        submitButton = new JButton("Submit");
        submitButton.setBounds(250, 265, 100, 35);  // Changed from 240 to
    
        submitButton.addActionListener(e -> Submit());

        // Add all components
        add(nameLabel);
        add(nameTextField);
        add(emailLabel);
        add(emailTextField);
        add(submitButton);
    }
    
    protected void Submit() {
        String nameText = nameTextField.getText();
        String emailText = emailTextField.getText();

        if(nameText.length() > 0 && emailText.length() > 0) {
            // Check if user already exists (LOGIN)
            User existingUser = null;
            for (User u : Wrapper.users) {
                if (u.email.equalsIgnoreCase(emailText)) {
                    existingUser = u;
                    break;
                }
            }

            if (existingUser != null) {
                // User exists - LOGIN
                Wrapper.activeUser = existingUser;
            } else {
                // New user - SIGNUP
                Wrapper.activeUser = new User(nameText, emailText, BigDecimal.valueOf(2000));
                Wrapper.users.add(Wrapper.activeUser);
            }
        }

        switchTo(new AccountScreen());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("Welcome to PayLink!", 250, 120);
    }
}
