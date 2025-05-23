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
    private JLabel jlName = new JLabel(mainFrame.languageModules.get("asf.jl.name"));
    private JLabel jlGeo = new JLabel(mainFrame.languageModules.get("asf.jl.geo"));
//    private JLabel jlGeoPath = new JLabel("Geometry Path: ");
    private JLabel jlTexture = new JLabel(mainFrame.languageModules.get("asf.jl.texture"));
    private JLabel jlCape = new JLabel(mainFrame.languageModules.get("asf.jl.cape"));
    public JTextField tfName = new JTextField("");
    public JComboBox<String> cbGeo = new JComboBox<>(new String[]{"Classic", "Slim"});
//    public JTextField tfGeoPath = new JTextField();
    private JPanel jpTexture = new JPanel(new BorderLayout(1, 1));
    public JTextField tfTexture = new JTextField("");
    private JButton jbSelectTexture = new JButton();
    private JPanel jpCape = new JPanel(new BorderLayout(1, 1));
    public JTextField tfCape = new JTextField("");
    private JButton jbSelectCape = new JButton();
    private JFileChooser fcSelectTexture = new JFileChooser();
    private JFileChooser fcSelectCape = new JFileChooser();

    private JPanel jpLowButtons = new JPanel(new BorderLayout(2, 2));
    private JButton jbAddCape = new JButton(mainFrame.languageModules.get("asf.jb.add_cape"));
    public JButton jbApply = new JButton(mainFrame.languageModules.get("asf.jb.apply"));
    public AddSkinFrame() {
        this.setSize(500, 300);
        this.setIconImage(mainFrame.appIcon.getImage());
        this.setTitle(mainFrame.languageModules.get("asf.title.title"));
        this.setResizable(true);
        this.setLocationRelativeTo(mainFrame);
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

        for (Component jTextComponent : mainFrame.getAllLabels(this)) {
            jTextComponent.setFont(new Font(mainFrame.settings.get("font"), Font.PLAIN, Integer.parseInt(mainFrame.settings.get("size"))));
        }

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
    public static <T extends JFrame> String selectFileStatic(T parent, JFileChooser fileSelector, String dialogTitle, String fileExtensionFilterDescription, String fileExtensionFilterExtension) {
        fileSelector.setDialogTitle(dialogTitle);
        fileSelector.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileSelector.setFileFilter(new FileNameExtensionFilter(fileExtensionFilterDescription, fileExtensionFilterExtension));

        int response = fileSelector.showOpenDialog(parent);
        if (response == JFileChooser.APPROVE_OPTION) {
            File selectedFileGenPath = fileSelector.getSelectedFile();
            return selectedFileGenPath.getAbsolutePath();
        } else return "> This can't be empty";
    }
}
