package com.redfox.mcskinner.ui;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.themes.*;
import com.redfox.mcskinner.MCSkinner;
import com.redfox.mcskinner.SkinPackGen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;

public class MainFrame extends JFrame implements ActionListener {
    private ArrayList<HashMap<String, String>> skins = new ArrayList<>();


    private AddSkinFrame addSkinFrame;

    public InputStream openFileInputStream = MCSkinner.class.getResourceAsStream("/mcskinner/icons/file-open-2-64.png");
    public final ImageIcon openFileIcon  = new ImageIcon(openFileInputStream.readAllBytes());

    private CardLayout cardLayout;
    private Container contentRoot;

    private JPanel jpHomePanel = new JPanel(new BorderLayout());
    private JPanel jpHomePanelN = new JPanel();
    private JPanel jpHomePanelS = new JPanel();
    private JPanel jpHomePanelW = new JPanel();
    private JPanel jpHomePanelE = new JPanel();
    private JButton jbNewSkinPack = new JButton("Create new SkinPack");

    private JPanel jpCenterGrid = new JPanel(new GridLayout(6, 2));
    private JLabel jlName = new JLabel("Name: ");
    private JLabel jlDescription = new JLabel("Description: ");
    private JLabel jlAuthor = new JLabel("Author: ");
    private JLabel jlVersion = new JLabel("Version: ");
    private JLabel jlMCVersion = new JLabel("MC Version: ");
    private JLabel jlFileGenPath = new JLabel("Path of generation: ");
    private JTextField tfName = new JTextField();
    private JTextField tfDescription = new JTextField();
    private JTextField tfAuthor = new JTextField();
    private JPanel jpVersion = new JPanel(new GridLayout(1, 3));
    private JTextField tfVersion1 = new JTextField("1");
    private JTextField tfVersion2 = new JTextField("0");
    private JTextField tfVersion3 = new JTextField("0");
    private JPanel jpMCVersion = new JPanel(new GridLayout(1, 3));
    private JTextField tfMCVersion1 = new JTextField("1");
    private JTextField tfMCVersion2 = new JTextField("21");
    private JTextField tfMCVersion3 = new JTextField("50");
    private JPanel jpFileGenPath = new JPanel(new BorderLayout(2, 2));
    private JTextField tfFileGenPath = new JTextField();
    private JButton jbSelctFileGenPath = new JButton();
    private JFileChooser fcSelectFileGenPath = new JFileChooser();

    private JPanel jpLowButtons = new JPanel(new BorderLayout(60, 60));
    private JButton jbAddSkin = new JButton("Add Skin");
    private JButton jbApply = new JButton("Generate Skin-Pack");
    private JPanel jpNewSkinPack = new JPanel(new BorderLayout());
    public MainFrame() throws IOException {
        String os = System.getProperty("os.name").toLowerCase();
        if (!(os.contains("mac") || os.contains("darwin"))) {
            FlatLightLaf.setup();
//            FlatDarkLaf.setup();
        } else FlatMacLightLaf.setup(); //FlatMacDarkLaf.setup();

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700, 500);
//        this.setIconImage(programIcon.getImage());
        this.setTitle("MCSkinner");
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        contentRoot = this.getContentPane();

        jbNewSkinPack.addActionListener(this);
        jbNewSkinPack.setPreferredSize(new Dimension(500, 70));

        jpHomePanelN.setPreferredSize(new Dimension(100, 190));
        jpHomePanelS.setPreferredSize(new Dimension(100, 190));
        jpHomePanelW.setPreferredSize(new Dimension(100, 190));
        jpHomePanelE.setPreferredSize(new Dimension(100, 190));

        jpHomePanel.add(jbNewSkinPack, BorderLayout.CENTER);
        jpHomePanel.add(jpHomePanelN, BorderLayout.NORTH);
        jpHomePanel.add(jpHomePanelS, BorderLayout.SOUTH);
        jpHomePanel.add(jpHomePanelW, BorderLayout.WEST);
        jpHomePanel.add(jpHomePanelE, BorderLayout.EAST);

        jpVersion.add(tfVersion1);
        jpVersion.add(tfVersion2);
        jpVersion.add(tfVersion3);

        jpMCVersion.add(tfMCVersion1);
        jpMCVersion.add(tfMCVersion2);
        jpMCVersion.add(tfMCVersion3);

        jbSelctFileGenPath.addActionListener(this);
        jbSelctFileGenPath.setIcon(openFileIcon);

        jpFileGenPath.add(tfFileGenPath, BorderLayout.CENTER);
        jpFileGenPath.add(jbSelctFileGenPath, BorderLayout.EAST);

        jpCenterGrid.add(jlName);
        jpCenterGrid.add(tfName);

        jpCenterGrid.add(jlDescription);
        jpCenterGrid.add(tfDescription);

        jpCenterGrid.add(jlAuthor);
        jpCenterGrid.add(tfAuthor);

        jpCenterGrid.add(jlVersion);
        jpCenterGrid.add(jpVersion);

        jpCenterGrid.add(jlMCVersion);
        jpCenterGrid.add(jpMCVersion);

        jpCenterGrid.add(jlFileGenPath);
        jpCenterGrid.add(jpFileGenPath);

        jpCenterGrid.setVisible(true);

        jbAddSkin.addActionListener(this);
        jbApply.addActionListener(this);

        jbAddSkin.setPreferredSize(new Dimension(1, 58));
        jbApply.setPreferredSize(new Dimension(1, 58));

        jpLowButtons.setPreferredSize(new Dimension(50, 120));
        jpLowButtons.add(jbAddSkin, BorderLayout.NORTH);
        jpLowButtons.add(jbApply, BorderLayout.SOUTH);

        jpNewSkinPack.add(jpLowButtons, BorderLayout.SOUTH);

        jpNewSkinPack.add(jpCenterGrid, BorderLayout.CENTER);

        cardLayout = new CardLayout(10, 10);
        contentRoot.setLayout(cardLayout);

        contentRoot.add("NewSkinPack", jpHomePanel);
        contentRoot.add("Home", jpNewSkinPack);

        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbNewSkinPack) {
            cardLayout.next(contentRoot);
            this.setTitle("MCSkinner: Create new skin-pack");

            this.setSize(700, 550);

            SwingUtilities.updateComponentTreeUI(this);
            this.invalidate();
            this.validate();
            this.repaint();
        } else if (e.getSource() == jbSelctFileGenPath) {
            SwingUtilities.updateComponentTreeUI(fcSelectFileGenPath);
            fcSelectFileGenPath.invalidate();
            fcSelectFileGenPath.validate();
            fcSelectFileGenPath.repaint();

            fcSelectFileGenPath.setDialogTitle("MCSkinner: Choose generation directory");
            fcSelectFileGenPath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fcSelectFileGenPath.setCurrentDirectory(new File(System.getProperty("user.home")));

            int response = fcSelectFileGenPath.showOpenDialog(this);
            if (response == JFileChooser.APPROVE_OPTION) {
                File selectedFileGenPath = fcSelectFileGenPath.getSelectedFile();
                tfFileGenPath.setText(selectedFileGenPath.getAbsolutePath());
            }
        } else if (e.getSource() == jbAddSkin) {
            addSkinFrame = new AddSkinFrame();
        } else if (e.getSource() == addSkinFrame.jbApply) {

            HashMap<String, String> skin = new HashMap<>();
            skin.put("name", addSkinFrame.tfName.getText());
            skin.put("geo", addSkinFrame.cbGeo.getItemAt(addSkinFrame.cbGeo.getSelectedIndex()));
            skin.put("texture", addSkinFrame.tfTexture.getText());
            if (addSkinFrame.cape) {
                skin.put("cape", addSkinFrame.tfCape.getText());
            }

            for (String key : skin.keySet()) {
                System.out.println(key + ": " + skin.get(key));
            }

            skins.add(skin);
            addSkinFrame.dispose();
        } else if (e.getSource() == jbApply) {
            String version = tfVersion1.getText() + ", " + tfVersion2.getText() + ", " + tfVersion3.getText();
            String mcVersion = tfMCVersion1.getText() + ", " + tfMCVersion2.getText() + ", " + tfMCVersion3.getText();

            SkinPackGen skinPackGen = new SkinPackGen(skins,
                    tfName.getText(), tfAuthor.getText(),tfDescription.getText(),version, mcVersion,
                    tfFileGenPath.getText());

            String json = skinPackGen.genSkinsJSON();
            System.out.println("skins.json: \n" + json + "\n");

            String lang = skinPackGen.genDefLangFile();
            System.out.println("en_US.lang: \n" + lang + "\n");

            String langJSON = skinPackGen.genLangJSON();
            System.out.println("languages.json: \n" + langJSON + "\n");

            String manifestJSON = skinPackGen.genManifestJSON();
            System.out.println("manifest.json: \n" + manifestJSON);
            skinPackGen.genSkinPackFiles();

            cardLayout.next(contentRoot);
            this.setTitle("MCSkinner");

            this.setSize(700, 500);
        }
    }
}
