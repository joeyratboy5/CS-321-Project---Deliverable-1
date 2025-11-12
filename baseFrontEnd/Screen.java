package baseFrontEnd;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public abstract class Screen extends JPanel implements ActionListener {
    protected boolean isRunning = true;

    public Screen() {
        setLayout(null); // manually control component positions if needed
        initialize();
    }

    /**
     * For performing any necessary initialization logic.
     */
    protected abstract void initialize();

    /**
     * Called when this screen is being closed or replaced.
     */
    public void clean() {
        removeAll();
    }

    /**
     * Switch to another screen.
     */
    public void switchTo(Screen nextScreen) {
        Wrapper.setActiveScreen(nextScreen);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
    	
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
    }
}
