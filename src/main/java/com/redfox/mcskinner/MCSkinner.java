package com.redfox.mcskinner;

import com.redfox.mcskinner.ui.MainFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MCSkinner {
    public static MainFrame mainFrame;
    public static SettingsHandler settingsHandler = new SettingsHandler();
    public static void main(String[] args) {
        settingsHandler.initialiseJSONFiles();

        try {
            mainFrame = new MainFrame(settingsHandler);
        } catch (IOException e) {
            System.err.println("Image retrieving exception: ");
            e.printStackTrace();
        }
    }
}
