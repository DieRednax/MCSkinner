package com.redfox.mcskinner;

import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UncheckedIOException;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Statics {
    //io
    static public void copyFile(Path sourcePath, Path destinationPath) {
        try {
            Files.copy(sourcePath, destinationPath.resolve(sourcePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error copying file " + sourcePath + " to destination " + destinationPath + ": " + e.getMessage());
            System.err.println("Stacktrace: " + e);
        }
    }
    static public void copyDir(File sourceDir, File destDir) {
        try {
            FileUtils.copyDirectory(sourceDir, destDir);
        } catch (IOException e) {
            System.err.println("Stacktrace: " + e);
        }
    }
    static public void zipFile(Path sourceDir, Path destFile) {
        try(ZipOutputStream zipOutputStream = new ZipOutputStream(Files.newOutputStream(destFile))) {
            Files.walk(sourceDir)
                    .filter(path -> !Files.isDirectory(path))
                    .forEach(path -> {
                        ZipEntry zipEntry = new ZipEntry(sourceDir.relativize(path).toString());
                        try {
                            zipOutputStream.putNextEntry(zipEntry);
                            Files.copy(path, zipOutputStream);
                            zipOutputStream.closeEntry();
                        } catch (IOException ex) {
                            throw new UncheckedIOException(ex);
                        }
                    });
        } catch (IOException e) {
            System.err.println("Stacktrace: " + e);
        }
    }
    static public void unZipFile(Path zipFilePath, Path outputDir) {
        try (ZipInputStream zis = new ZipInputStream(Files.newInputStream(zipFilePath))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                Path newFilePath = outputDir.resolve(entry.getName());
                if (entry.isDirectory()) {
                    Files.createDirectories(newFilePath);
                } else {
                    Files.createDirectories(newFilePath.getParent()); // make sure parent folders exist
                    try (OutputStream os = Files.newOutputStream(newFilePath)) {
                        byte[] buffer = new byte[1024];
                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            os.write(buffer, 0, len);
                        }
                    }
                }
                zis.closeEntry();
            }
        } catch (IOException e) {
            System.err.println("Stacktrace: " + e);
        }
    }

    //ui
    public static ArrayList<Component> getAllNamedComponents(Container container) {
        ArrayList<Component> jTextComponents = new ArrayList<>();
        Component[] components = container.getComponents();

        for (Component component : components) {
            if (hasText(component)) {
                jTextComponents.add(component);
//            } else if (component instanceof JTabbedPane || component instanceof JComboBox<?>) {
//                jTextComponents.add(component);
            } else if (component instanceof Container) {
                jTextComponents.addAll(getAllNamedComponents((Container) component));
            }
        }

        return jTextComponents;
    }
    public static String selectDir(Component parent, JFileChooser fileSelector, String dialogTitle) {
        fileSelector.setDialogTitle(dialogTitle);
        fileSelector.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileSelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int response = fileSelector.showOpenDialog(parent);
        if (response == JFileChooser.APPROVE_OPTION) {
            File selectedFileGenPath = fileSelector.getSelectedFile();
            return selectedFileGenPath.getAbsolutePath();
        } else return "> This can't be empty";
    }
    public static String selectFile(Component parent, JFileChooser fileSelector, String dialogTitle, String fileExtensionFilterDescription, String fileExtensionFilterExtension) {
        fileSelector.setDialogTitle(dialogTitle);
        fileSelector.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileSelector.setFileFilter(new FileNameExtensionFilter(fileExtensionFilterDescription, fileExtensionFilterExtension));

        int response = fileSelector.showOpenDialog(parent);
        if (response == JFileChooser.APPROVE_OPTION) {
            File selectedFileGenPath = fileSelector.getSelectedFile();
            return selectedFileGenPath.getAbsolutePath();
        } else return "> This can't be empty";
    }
    public static void warning(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "MCSkinner: warning", JOptionPane.WARNING_MESSAGE);
    }
    public static void info(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "MCSkinner: info", JOptionPane.INFORMATION_MESSAGE);
    }

    //privates
    public static boolean hasText(Component component) {
        try {
            // Check if the component has a getText() method and returns non-null, non-empty string
            Method getTextMethod = component.getClass().getMethod("getText");
            Object result = getTextMethod.invoke(component);
            return result instanceof String && !((String) result).isEmpty();
        } catch (Exception e) {
            // getText doesn't exist or isn't accessible – ignore
            return false;
        }
    }
}
