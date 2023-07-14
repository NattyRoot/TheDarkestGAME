package tdg;


import tdg.config.ConfigurationReader;
import tdg.ui.GamePanel;

import javax.swing.*;

import static tdg.config.ConfigurationKey.*;

public class TheDarkestGAME {

    public static void main(String[] args) {
        System.out.println("App version: " + ConfigurationReader.getString(APPLICATION_VERSION));

        JFrame window = new JFrame();

        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setResizable(false);
        window.setTitle(ConfigurationReader.getString(APPLICATION_NAME));

        GamePanel gamePanel = new GamePanel();

        window.add(gamePanel);
        window.pack();

        window.setLocationRelativeTo(null);

        window.setVisible(true);

        gamePanel.startGameThread();
    }
}
