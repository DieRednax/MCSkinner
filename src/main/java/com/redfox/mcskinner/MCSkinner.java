package com.redfox.mcskinner;

import com.redfox.mcskinner.ui.MainFrame;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class MCSkinner {
    public static MainFrame mainFrame;
    public static void main(String[] args) {

        try {
            mainFrame = new MainFrame();
        } catch (IOException e) {
            System.err.println("Image retrieving exception: ");
            e.printStackTrace();
        }
    }
}
