package com.redfox.mcskinner.ui;

import com.redfox.mcskinner.Statics;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import static com.redfox.mcskinner.MCSkinner.mainFrame;
import static com.redfox.mcskinner.MCSkinner.settingsHandler;

public class AddSkinFrame extends JFrame implements ActionListener {
    public boolean cape = false;


    private JPanel jpCenterGrid = new JPanel(new GridLayout(3, 2));
    private JLabel jlName = new JLabel(settingsHandler.getCompLangName("asf.jl.name"));
    private JLabel jlGeo = new JLabel(settingsHandler.getCompLangName("asf.jl.geo"));
//    private JLabel jlGeoPath = new JLabel("Geometry Path: ");
    private JLabel jlTexture = new JLabel(settingsHandler.getCompLangName("asf.jl.texture"));
    private JLabel jlCape = new JLabel(settingsHandler.getCompLangName("asf.jl.cape"));
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
    private JButton jbAddCape = new JButton(settingsHandler.getCompLangName("asf.jb.add_cape"));
    public JButton jbApply = new JButton(settingsHandler.getCompLangName("asf.jb.apply"));
    public AddSkinFrame() {
        this.setSize(500, 300);
        this.setIconImage(mainFrame.appIcon.getImage());
        this.setTitle(settingsHandler.getCompLangName("asf.title.title"));
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

        settingsHandler.doCompFonts(this);

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
            tfTexture.setText(Statics.selectFile(this, fcSelectTexture, "MCSkinner: Select Skin-Texture", "*.png", "png"));
        } else if (e.getSource() == jbSelectCape) {
            tfCape.setText(Statics.selectFile(this, fcSelectCape, "MCSkinner: Select Skin Cape-Texture", "*.png", "png"));
        }
    }
}
