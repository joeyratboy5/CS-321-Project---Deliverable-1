package baseFrontEnd.Screens;

import javax.swing.*;
import java.awt.*;
import java.math.BigDecimal;

import baseFrontEnd.Screen;
import baseFrontEnd.User;
import baseFrontEnd.Wrapper;

public class AccountScreen extends Screen {

    private static final long serialVersionUID = -4967001973343568430L;

    private JLabel nameText;
    private JLabel accountBalanceText;
    private JPanel backgroundPanel;
    private JButton transferButton;
    private JButton exitButton;

    private boolean isTransferActive = false;
    private JDialog activeTransferScreen;
    
    private NotificationService notificationService;

    @Override
    protected void initialize() {
    	notificationService = new NotificationService();
        setLayout(null);
        setBackground(new Color(190, 200, 190));
        
        JOptionPane.showMessageDialog(this, 
                "Welcome to our protoype! Send your funds to dummy@dummy.com for a demonstration.");

        backgroundPanel = new JPanel();
        backgroundPanel.setBounds(0, 0, 640, 480);
        backgroundPanel.setBackground(new Color(190, 200, 190));
        backgroundPanel.setLayout(null);
        add(backgroundPanel);

        if (Wrapper.activeUser != null) {
            nameText = new JLabel("Name: " + Wrapper.activeUser.name);
            accountBalanceText = new JLabel("Balance: $" + Wrapper.activeUser.balance);
        } else {
            nameText = new JLabel("No user active.");
            accountBalanceText = new JLabel("");
        }

        nameText.setBounds(220, 140, 300, 30);
        accountBalanceText.setBounds(220, 200, 300, 30);

        backgroundPanel.add(nameText);
        backgroundPanel.add(accountBalanceText);

        transferButton = new JButton("Make Transfer");
        transferButton.setBounds(240, 260, 150, 40);
        transferButton.addActionListener(e -> MakeTransfer());
        backgroundPanel.add(transferButton);

        exitButton = new JButton("Exit");
        exitButton.setBounds(240, 320, 150, 40);
        exitButton.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION);

            if (confirm == JOptionPane.YES_OPTION) {
                Wrapper.activeUser = null;
                switchTo(new IntroScreen());
            }
        });
        backgroundPanel.add(exitButton);
    }

    private void MakeTransfer() {
        if (isTransferActive) return; // prevent multiple popups
        isTransferActive = true;

        activeTransferScreen = new JDialog((Frame) SwingUtilities.getWindowAncestor(this), "Transfer Money", true);
        activeTransferScreen.setSize(350, 250);
        activeTransferScreen.setLayout(null);

        JLabel emailLabel = new JLabel("Recipient Email:");
        emailLabel.setBounds(30, 30, 120, 25);
        JTextField emailField = new JTextField();
        emailField.setBounds(160, 30, 150, 25);

        JLabel amountLabel = new JLabel("Amount:");
        amountLabel.setBounds(30, 70, 120, 25);
        JTextField amountField = new JTextField();
        amountField.setBounds(160, 70, 150, 25);

        JButton confirmButton = new JButton("Send");
        confirmButton.setBounds(110, 130, 120, 35);
        confirmButton.addActionListener(e -> {
            String recipientEmail = emailField.getText().trim();
            String amtText = amountField.getText().trim();

            if (recipientEmail.isEmpty() || amtText.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill in all fields.");
                return;
            }

            try {
                double amt = Double.parseDouble(amtText);
                if (amt <= 0) {
                    JOptionPane.showMessageDialog(this, "Amount must be positive.");
                    return;
                }

                // Find recipient user
                User recipient = null;
                for (User u : Wrapper.users) {
                    if (u.email.equalsIgnoreCase(recipientEmail)) {
                        recipient = u;
                        break;
                    }
                }

                if (recipient == null) {
                    JOptionPane.showMessageDialog(this, "No user found with that email.");
                    return;
                }

                User sender = Wrapper.activeUser;
                if (BigDecimal.valueOf(amt).compareTo(sender.balance) > 0) {
                    JOptionPane.showMessageDialog(this, "Insufficient funds. You have $" + sender.balance);
                    return;
                }

                // Record transaction
                Transaction.perform(sender, recipient, BigDecimal.valueOf(amt), notificationService);

                // Notify both users
                NotificationService notifier = new NotificationService();
                notifier.notifyUser("You sent $" + amt + " to " + recipient.name);
                notifier.notifyUser("You received $" + amt + " from " + sender.name);

                accountBalanceText.setText("Balance: $" + String.format("%.2f", sender.balance));

                JOptionPane.showMessageDialog(this,
                    "Transferred $" + String.format("%.2f", amt) +
                    " to " + recipient.name + " (" + recipient.email + ")!");

                activeTransferScreen.dispose();
                isTransferActive = false;


            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid amount.");
            }
        });

        activeTransferScreen.add(emailLabel);
        activeTransferScreen.add(emailField);
        activeTransferScreen.add(amountLabel);
        activeTransferScreen.add(amountField);
        activeTransferScreen.add(confirmButton);
        activeTransferScreen.setLocationRelativeTo(this);
        activeTransferScreen.setVisible(true);
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
