package baseFrontEnd;

import javax.swing.*;

import java.awt.CardLayout;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import baseFrontEnd.Screens.IntroScreen;

public class Wrapper {
    public static Screen activeScreen;
    public static JFrame appFrame;

    public static List<User> users;
    public static User activeUser;

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            appFrame = new JFrame("PayLink");
            appFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            appFrame.setSize(640, 480);
            appFrame.setLocationRelativeTo(null);
            appFrame.setLayout(new CardLayout());
            appFrame.setVisible(true);

            users = new ArrayList<>();
            users.add(new User("Dummy", "Dummy@Dummy.com", BigDecimal.valueOf(0)));

            // start with IntroScreen
            setActiveScreen(new IntroScreen());
        });
    }

    public static void setActiveScreen(Screen newScreen) {
        if (activeScreen != null) {
            appFrame.remove(activeScreen);
            activeScreen.clean();
        }

        activeScreen = newScreen;
        appFrame.add(activeScreen);
        appFrame.revalidate();
        appFrame.repaint();
    }
}
