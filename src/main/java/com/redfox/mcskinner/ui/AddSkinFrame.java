package com.redfox.mcskinner.ui;

import com.formdev.flatlaf.FlatLightLaf;

import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import static com.redfox.mcskinner.MCSkinner.mainFrame;

public class AddSkinFrame extends JFrame implements ActionListener {
    public boolean cape = false;


    private JPanel jpCenterGrid = new JPanel(new GridLayout(3, 2));
    private JLabel jlName = new JLabel("Name: ");
    private JLabel jlGeo = new JLabel("Geometry: ");
//    private JLabel jlGeoPath = new JLabel("Geometry Path: ");
    private JLabel jlTexture = new JLabel("Texture: ");
    private JLabel jlCape = new JLabel("Cape Texture: ");
    public JTextField tfName = new JTextField();
    public JComboBox<String> cbGeo = new JComboBox<>(new String[]{"Classic", "Slim"});
//    public JTextField tfGeoPath = new JTextField();
    private JPanel jpTexture = new JPanel(new BorderLayout(1, 1));
    public JTextField tfTexture = new JTextField(">");
    private JButton jbSelectTexture = new JButton();
    private JPanel jpCape = new JPanel(new BorderLayout(1, 1));
    public JTextField tfCape = new JTextField(">");
    private JButton jbSelectCape = new JButton();
    private JFileChooser fcSelectTexture = new JFileChooser();
    private JFileChooser fcSelectCape = new JFileChooser();

    private JPanel jpLowButtons = new JPanel(new BorderLayout(2, 2));
    private JButton jbAddCape = new JButton("Add Cape");
    public JButton jbApply = new JButton("Add Skin");
    public AddSkinFrame() {
        this.setSize(500, 300);
//        this.setIconImage(programIcon.getImage());
        this.setTitle("MCSkinner");
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.setLayout(new BorderLayout(10, 10));

        jpCenterGrid.add(jlName);
        jpCenterGrid.add(tfName);

        jpCenterGrid.add(jlGeo);
        jpCenterGrid.add(cbGeo);

        jbSelectTexture.addActionListener(this);
        jbSelectCape.addActionListener(this);

        jbSelectTexture.setIcon(mainFrame.openFileIcon);
        jbSelectCape.setIcon(mainFrame.openFileIcon);

        jpTexture.add(tfTexture, BorderLayout.CENTER);
        jpTexture.add(jbSelectTexture, BorderLayout.EAST);
        jpCenterGrid.add(jlTexture);
        jpCenterGrid.add(jpTexture);

        jbAddCape.setPreferredSize(new Dimension(1, 48));
        jbApply.setPreferredSize(new Dimension(1, 48));

        jpLowButtons.add(jbAddCape, BorderLayout.NORTH);
        jpLowButtons.add(jbApply, BorderLayout.SOUTH);

        jbAddCape.addActionListener(this);
        jbApply.addActionListener(mainFrame);

        jpLowButtons.setPreferredSize(new Dimension(1, 100));

        this.add(jpLowButtons, BorderLayout.SOUTH);
        this.add(jpCenterGrid, BorderLayout.CENTER);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbAddCape) {
            this.setSize(500, 350);

            jpCenterGrid.setLayout(new GridLayout(4, 3));

            jpCape.add(tfCape, BorderLayout.CENTER);
            jpCape.add(jbSelectCape, BorderLayout.EAST);
            jpCenterGrid.add(jlCape);
            jpCenterGrid.add(jpCape);

            cape = true;
        } else if (e.getSource() == jbSelectTexture) {
            tfTexture.setText(selectFile(fcSelectTexture, "MCSkinner: Select Skin-Texture", "*.png", "png"));
        } else if (e.getSource() == jbSelectCape) {
            tfCape.setText(selectFile(fcSelectCape, "MCSkinner: Select Skin Cape-Texture", "*.png", "png"));
        }
    }
    public String selectFile(JFileChooser fileSelector, String dialogTitle, String fileExtensionFilterDescription, String fileExtensionFilterExtension) {
        fileSelector.setDialogTitle(dialogTitle);
        fileSelector.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileSelector.setFileFilter(new FileNameExtensionFilter(fileExtensionFilterDescription, fileExtensionFilterExtension));

        int response = fileSelector.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            File selectedFileGenPath = fileSelector.getSelectedFile();
            return selectedFileGenPath.getAbsolutePath();
        } else return "> This can't be empty";
    }
}
