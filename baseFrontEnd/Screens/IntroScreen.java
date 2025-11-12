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

        nameTextField = new JTextField("insert name here");
        nameTextField.setBounds(200, 150, 200, 30);

        emailTextField = new JTextField("insert email here");
        emailTextField.setBounds(200, 190, 200, 30);

        submitButton = new JButton("Submit");
        submitButton.setBounds(250, 240, 100, 35);

        submitButton.addActionListener(e -> {
            String name = nameTextField.getText();
            String email = emailTextField.getText();
            JOptionPane.showMessageDialog(this, 
                "Welcome, " + name + " (" + email + ")!");
        });

        add(nameTextField);
        add(emailTextField);
        add(submitButton);
        submitButton.addActionListener(e -> Submit());
    }
    
    protected void Submit() {
    	String nameText = nameTextField.getText();
    	String emailText = emailTextField.getText();
    	
    	if(nameText.length() > 0 && emailText.length() > 0) {
    		Wrapper.activeUser = new User(nameText, emailText, BigDecimal.valueOf(2000)); // start with 2000 $
    	}
    	
    	switchTo(new AccountScreen());
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("Welcome to PayLink!", 250, 120);
    }
}
