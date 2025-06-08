package com.redfox.mcskinner;

import com.redfox.mcskinner.ui.MainFrame;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

public class MCSkinner {
    public static MainFrame mainFrame;
    public static SettingsHandler settingsHandler = new SettingsHandler();
    public static void main(String[] args) {
        settingsHandler.initialiseJSONFiles();

        String inputMCPack = null;
        if (args.length == 1) {
            File inputMCPackFile = new File(args[0]);
            if (inputMCPackFile.exists() && args[0].toLowerCase().endsWith(".mcpack")) {
                inputMCPack = inputMCPackFile.getAbsolutePath();
            }
        }

        try {
            mainFrame = new MainFrame(settingsHandler, inputMCPack);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                Path tmpDir = Path.of(System.getProperty("java.io.tmpdir"), "MCSkinner");
                if (Files.exists(tmpDir)) {
                    try {
                        Files.walk(tmpDir)
                                .sorted(Comparator.reverseOrder())
                                .map(Path::toFile)
                                .forEach(file -> {
                                    if (!file.delete()) {
                                        System.err.println("Could not delete: " + file.getAbsolutePath());
                                    }
                                });
                    } catch (IOException e) {
                        System.err.println("Stacktrace: " + e.getMessage());
                    }
                    System.out.println("Temp directory cleaned up.");
                }
            }));
        } catch (IOException e) {
            System.err.println("Image retrieving exception: " + e.getMessage());
        }
    }
}
