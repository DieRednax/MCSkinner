package com.redfox.mcskinner.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.redfox.mcskinner.MCSkinner.mainFrame;

public class SaveAsFrame extends JFrame implements ActionListener {
    String selectedSaveType = "importMC";
    String strTFSelectedDirText = "";

    Container contentRoot;
    CardLayout cardLayout;

    JPanel jpChooseSaveType = new JPanel(new BorderLayout(10, 10));
    JPanel jpCSTCenterGrid = new JPanel(new GridLayout(3, 1));

    ButtonGroup typeOptions = new ButtonGroup();
    JRadioButton jrImportMC = new JRadioButton("Import to Minecraft");
    JRadioButton jrExportMCPack = new JRadioButton("Export to *.mcpack file");
    JRadioButton jrSaveDirectory = new JRadioButton("Save to directory");

    JPanel jpCSTSouthButtons = new JPanel(new BorderLayout(10, 10));
    JButton jbNext = new JButton("Next >");

    JPanel jpImportMC = new JPanel(new BorderLayout(10, 10));
    JPanel jpExportMCPack = new JPanel(new BorderLayout(10, 10));
    JPanel jpSaveDirectory = new JPanel(new BorderLayout(10, 10));

    public SaveAsFrame() {
        jrImportMC = new JRadioButton(mainFrame.languageModules.get("saf.jr.import_mc"));
        jrExportMCPack = new JRadioButton(mainFrame.languageModules.get("saf.jr.export_mcp"));
        jrSaveDirectory = new JRadioButton(mainFrame.languageModules.get("saf.jr.save_d"));

        jbNext = new JButton(mainFrame.languageModules.get("saf.jb.next"));

        this.setSize(500, 300);
        this.setIconImage(mainFrame.appIcon.getImage());
        this.setTitle(mainFrame.languageModules.get("saf.title.title"));
        this.setResizable(true);
        this.setLocationRelativeTo(mainFrame);
        contentRoot = this.getContentPane();

        typeOptions.add(jrImportMC);
        typeOptions.add(jrExportMCPack);
        typeOptions.add(jrSaveDirectory);

        jpCSTCenterGrid.add(jrImportMC);
        jpCSTCenterGrid.add(jrExportMCPack);
        jpCSTCenterGrid.add(jrSaveDirectory);

//        jrImportMC.setToolTipText("Imports your skin-pack right into Minecraft!");
//        jrExportMCPack.setToolTipText("Exports your skin-pack to a .mcpack file. (open to import into minecraft)");
//        jrSaveDirectory.setToolTipText("Saves your skin-pack as a raw folder (useful if you want to make changes)");

        jrImportMC.addActionListener(this);
        jrExportMCPack.addActionListener(this);
        jrSaveDirectory.addActionListener(this);

        jrImportMC.setActionCommand("importMC");
        jrExportMCPack.setActionCommand("exportMCP");
        jrSaveDirectory.setActionCommand("saveD");

        jbNext.setPreferredSize(new Dimension(100, 55));

        jbNext.addActionListener(this);
        jpCSTSouthButtons.add(jbNext, BorderLayout.SOUTH);

        jpCSTSouthButtons.setPreferredSize(new Dimension(100, 60));

        jpChooseSaveType.add(jpCSTSouthButtons, BorderLayout.SOUTH);
        jpChooseSaveType.add(jpCSTCenterGrid, BorderLayout.CENTER);


        cardLayout = new CardLayout(10, 10);
        contentRoot.setLayout(cardLayout);

        contentRoot.add("chooseSaveType", jpChooseSaveType);
        contentRoot.add("importMC", jpImportMC);
        contentRoot.add("exportMCP", jpExportMCPack);
        contentRoot.add("saveD", jpSaveDirectory);

        for (int i = 0; i < 3; i++) {
            JPanel jpCenterBorder = new JPanel(new BorderLayout(10, 10));
            JPanel jpNorthLabel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            jpNorthLabel.setPreferredSize(new Dimension(100, 70));
            JLabel jlLabel = new JLabel();
            JPanel jpSouth = new JPanel(new BorderLayout(10, 10));
            JPanel jpSouthButtons = new JPanel(new GridLayout(1, 3));
            JPanel jpSouthNorth = new JPanel();
            jpSouthNorth.setPreferredSize(new Dimension(100, 50));
            JButton jbBack = new JButton(mainFrame.languageModules.get("saf.jb.back"));
            jbBack.addActionListener((ActionEvent e) -> cardLayout.show(contentRoot, "chooseSaveType"));
            JPanel jpMiddlePanel = new JPanel();
            JButton jbApply = new JButton(mainFrame.languageModules.get("saf.jb.apply"));
//            jbApply.addActionListener(mainFrame);
//            jbApply.setActionCommand("saveAsFrame.jbApply");

            jpNorthLabel.add(jlLabel);

            jpSouthButtons.add(jbBack);
            jpSouthButtons.add(jpMiddlePanel);
            jpSouthButtons.add(jbApply);

            jpSouth.add(jpSouthButtons, BorderLayout.SOUTH);
            jpSouth.add(jpSouthNorth, BorderLayout.NORTH);

            JButton jbChoosePathOfGen = new JButton(mainFrame.openFileIcon);
            JTextField tfChoosePathOfGen = new JTextField(mainFrame.settings.get("defaultsaveloc"));
            JFileChooser fcChoosePathOfGen = new JFileChooser();

            jbApply.addActionListener((ActionEvent e) -> {
                strTFSelectedDirText = tfChoosePathOfGen.getText();

                if (selectedSaveType.equals("importMC")) {
                    mainFrame.saveSkinPack();
                } else {
                    if (saveAsFrameCorrect(strTFSelectedDirText, "path of generation")) {
                        mainFrame.saveSkinPack();
                    }
                }
            });

            switch (i) {
                case 0:
                    jlLabel.setText(mainFrame.languageModules.get("saf.jl.label.imc"));

                    jpImportMC.add(jpCenterBorder, BorderLayout.CENTER);
                    jpImportMC.add(jpNorthLabel, BorderLayout.NORTH);
                    jpImportMC.add(jpSouth, BorderLayout.SOUTH);
                    break;
                case 1:
                    jlLabel.setText(mainFrame.languageModules.get("saf.jl.label.emcp"));

                    jbChoosePathOfGen.addActionListener((ActionEvent e) -> {
                         tfChoosePathOfGen.setText(mainFrame.getAddSkinFrame().selectFile(fcChoosePathOfGen, "MCSkinner: Choose path of generation", "*.mcpack", "mcpack"));
                    });

                    jpCenterBorder.add(tfChoosePathOfGen, BorderLayout.CENTER);
                    jpCenterBorder.add(jbChoosePathOfGen, BorderLayout.EAST);
                    jpExportMCPack.add(jpCenterBorder, BorderLayout.CENTER);
                    jpExportMCPack.add(jpNorthLabel, BorderLayout.NORTH);
                    jpExportMCPack.add(jpSouth, BorderLayout.SOUTH);
                    break;
                case 2:
                    jlLabel.setText(mainFrame.languageModules.get("saf.jl.label.sd"));

                    jbChoosePathOfGen.addActionListener((ActionEvent e) -> {
                        tfChoosePathOfGen.setText(mainFrame.selectDir(fcChoosePathOfGen, "MCSkinner: Choose path of generation"));
                    });

                    jpCenterBorder.add(tfChoosePathOfGen, BorderLayout.CENTER);
                    jpCenterBorder.add(jbChoosePathOfGen, BorderLayout.EAST);
                    jpSaveDirectory.add(jpCenterBorder, BorderLayout.CENTER);
                    jpSaveDirectory.add(jpNorthLabel, BorderLayout.NORTH);
                    jpSaveDirectory.add(jpSouth, BorderLayout.SOUTH);
            }
        }

        for (Component jTextComponent : mainFrame.getAllLabels(contentRoot)) {
            jTextComponent.setFont(new Font(mainFrame.settings.get("font"), Font.PLAIN, Integer.parseInt(mainFrame.settings.get("size"))));
        }

        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("importMC")
            || e.getActionCommand().equals("exportMCP")
            || e.getActionCommand().equals("saveD")) {
            selectedSaveType = e.getActionCommand();
        } else if (e.getSource() == jbNext) {
            switch (selectedSaveType) {
                case "importMC":
                    cardLayout.show(contentRoot, "importMC");
                    break;
                case "exportMCP":
                    cardLayout.show(contentRoot, "exportMCP");
                    break;
                case "saveD":
                    cardLayout.show(contentRoot, "saveD");
            }
        }
    }
    public String getStrTFSelectedDirText() {
        return this.strTFSelectedDirText;
    }
    private boolean saveAsFrameCorrect(String str, String strWarnName) {
        if (!(str.equals("> This can't be empty") || str.isEmpty())) {
            return true;
        } else {
            mainFrame.warning(this, "You must insert a " + strWarnName);
            return false;
        }
    }
}
