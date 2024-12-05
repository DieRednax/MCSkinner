package com.redfox.mcskinner.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class MainFrame extends JFrame implements ActionListener {
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
    private JPanel jpNewSkinPack = new JPanel(new BorderLayout());
    public MainFrame() {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700, 400);
//        this.setIconImage(programIcon.getImage());
        this.setTitle("MCSkinner");
        this.setResizable(true);
        this.setLocationRelativeTo(null);

        contentRoot = this.getContentPane();

        jbNewSkinPack.addActionListener(this);
        jbNewSkinPack.setPreferredSize(new Dimension(500, 70));

        jpHomePanelN.setPreferredSize(new Dimension(100, 140));
        jpHomePanelS.setPreferredSize(new Dimension(100, 140));
        jpHomePanelW.setPreferredSize(new Dimension(100, 140));
        jpHomePanelE.setPreferredSize(new Dimension(100, 140));

        //testing:
        jpHomePanelN.setBackground(Color.green);
        jpHomePanelS.setBackground(Color.red);
        jpHomePanelW.setBackground(Color.blue);
        jpHomePanelE.setBackground(Color.yellow);
        //

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
        } else if (e.getSource() == jbSelctFileGenPath) {
            fcSelectFileGenPath.setDialogTitle("MCSkinner: Choose generation directory");
            fcSelectFileGenPath.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

            int respoonse = fcSelectFileGenPath.showOpenDialog(null);
            if (respoonse == JFileChooser.APPROVE_OPTION) {
                File selectedFileGenPath = fcSelectFileGenPath.getSelectedFile();
                tfFileGenPath.setText(selectedFileGenPath.getAbsolutePath());
            }
        }
    }
}
