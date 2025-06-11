package com.redfox.mcskinner.ui;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneLightIJTheme;
import com.formdev.flatlaf.themes.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jthemedetecor.OsThemeDetector;
import com.redfox.mcskinner.MCSkinner;
import com.redfox.mcskinner.SettingsHandler;
import com.redfox.mcskinner.SkinPackGen;
import com.redfox.mcskinner.Statics;

import org.apache.commons.io.FileUtils;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class MainFrame extends JFrame implements ActionListener, WindowListener {
    private ArrayList<HashMap<String, String>> skins = new ArrayList<>();
    private String importType = "importMCP";


    private AddSkinFrame addSkinFrame;
    private SaveAsFrame saveAsFrame;
    private SettingsFrame settingsFrame;

    public InputStream openFileInputStream = MCSkinner.class.getResourceAsStream("/mcskinner/icons/file-open-2-64.png");
    public final ImageIcon openFileIcon  = new ImageIcon(openFileInputStream.readAllBytes());

    private InputStream appIconInputStream = MCSkinner.class.getResourceAsStream("/mcskinner/icons/icon.png");
    public final ImageIcon appIcon = new ImageIcon(appIconInputStream.readAllBytes());


    private SettingsHandler settingsHandler = MCSkinner.settingsHandler;

    private String name = "";
    private String description = "";
    private String author = "";
    private String version = "";
    private String mcVersion = "";

    private CardLayout cardLayout;
    private Container contentRoot;

    private JMenuBar mbMain = new JMenuBar();
    private JMenu jmFile = new JMenu(settingsHandler.getCompLangName("mf.jm.file"));;
    private JMenuItem miNewSkinpack = new JMenuItem(settingsHandler.getCompLangName("mf.mi.new_skinpack"));
    private JMenuItem miImportSkinpack = new JMenuItem(settingsHandler.getCompLangName("mf.mi.import_skinpack"));
    private JMenu jmSettings = new JMenu(settingsHandler.getCompLangName("mf.jm.settings"));// = new JMenu("Settings");
    private JMenuItem miOpenSettings = new JMenuItem(settingsHandler.getCompLangName("mf.mi.open_settings"));// = new JMenuItem("Open Settings");
    private JMenu jmTheme = new JMenu(settingsHandler.getCompLangName("mf.jm.theme"));// = new JMenu("Theme");
    private JMenu jmLight = new JMenu(settingsHandler.getCompLangName("mf.jm.light"));
    private JMenuItem miLight = new JMenuItem(settingsHandler.getCompLangName("mf.mi.light"));// = new JMenuItem("Light");
    private JMenuItem miLightExtra = new JMenuItem(settingsHandler.getCompLangName("mf.mi.light_extra"));
    private JMenu jmDark = new JMenu(settingsHandler.getCompLangName("mf.jm.dark"));
    private JMenuItem miDark = new JMenuItem(settingsHandler.getCompLangName("mf.mi.dark"));// = new JMenuItem("Dark");
    private JMenuItem miDarkExtra = new JMenuItem(settingsHandler.getCompLangName("mf.mi.dark_extra"));
    private JMenuItem miSystemTheme = new JMenuItem(settingsHandler.getCompLangName("mf.mi.sys_theme"));
    private JMenuItem miChooseDefaultSaveLoc = new JMenuItem(settingsHandler.getCompLangName("mf.mi.default_save_loc"));// = new JMenuItem("Default save location...");
    private JFileChooser fcChooseDefaultSaveLoc = new JFileChooser();
    private JMenu jmLanguage = new JMenu(settingsHandler.getCompLangName("mf.jm.language"));// = new JMenu("Language");
    private ArrayList<String> languages = new ArrayList<>();

    private JPanel jpHomePanel = new JPanel(new BorderLayout());
    private JPanel jpHomePanelN = new JPanel();
    private JPanel jpHomePanelS = new JPanel();
    private JPanel jpHomePanelW = new JPanel();
    private JPanel jpHomePanelE = new JPanel();
    private JPanel jpHomeCenterGrid = new JPanel(new GridLayout(2, 1));
    private JButton jbNewSkinPack = new JButton(settingsHandler.getCompLangName("mf.jb.new_skin_pack"));// = new JButton("Create new SkinPack");
    private JButton jbImportSkinPack = new JButton(settingsHandler.getCompLangName("mf.jb.import_skin_pack"));

    private JPanel jpImportSkinPack = new JPanel(new BorderLayout(10, 10));
    private JPanel jpISPCenterGrid = new JPanel(new GridLayout(3, 1));

    private ButtonGroup ispButtons = new ButtonGroup();
    private JRadioButton jrImportMC = new JRadioButton(settingsHandler.getCompLangName("mf.jr.import_mc"));
    private  JRadioButton jrImportMCP = new JRadioButton(settingsHandler.getCompLangName("mf.jr.import_mcp"));
    private JRadioButton jrImportD = new JRadioButton(settingsHandler.getCompLangName("mf.jr.import_d"));

    private JPanel jpISPLowButtons = new JPanel(new BorderLayout(10, 10));
    private JButton jbISPNext = new JButton(settingsHandler.getCompLangName("mf.jb.isp_next"));

    private JPanel jpImportMC = new JPanel(new BorderLayout(10, 10));
    private JPanel jpImportMCP = new JPanel(new BorderLayout(10, 10));
    private JPanel jpImportD = new JPanel(new BorderLayout(10, 10));

    private JPanel jpCenterGrid = new JPanel(new GridLayout(5, 2));
    private JLabel jlName = new JLabel(settingsHandler.getCompLangName("mf.jl.name"));// = new JLabel("Name: ");
    private JLabel jlDescription = new JLabel(settingsHandler.getCompLangName("mf.jl.description"));// = new JLabel("Description: ");
    private JLabel jlAuthor = new JLabel(settingsHandler.getCompLangName("mf.jl.author"));// = new JLabel("Author: ");
    private JLabel jlVersion = new JLabel(settingsHandler.getCompLangName("mf.jl.version"));// = new JLabel("Version: ");
    private JLabel jlMCVersion = new JLabel(settingsHandler.getCompLangName("mf.jl.mc_version"));// = new JLabel("MC Version: ");
    private JLabel jlFileGenPath;// = new JLabel("Path of generation: ");
    private JTextField tfName = new JTextField("");
    private JTextField tfDescription = new JTextField("");
    private JTextField tfAuthor = new JTextField("");
    private JPanel jpVersion = new JPanel(new GridLayout(1, 3));
    private JTextField tfVersion1 = new JTextField("1");
    private JTextField tfVersion2 = new JTextField("0");
    private JTextField tfVersion3 = new JTextField("0");
    private JPanel jpMCVersion = new JPanel(new GridLayout(1, 3));
    private JTextField tfMCVersion1 = new JTextField("1");
    private JTextField tfMCVersion2 = new JTextField("21");
    private JTextField tfMCVersion3 = new JTextField("80");

    private JPanel jpLowButtons = new JPanel(new BorderLayout(60, 60));
    private JButton jbAddSkin = new JButton(settingsHandler.getCompLangName("mf.jb.add_skin"));// = new JButton("Add Skin");
    private JButton jbApply = new JButton(settingsHandler.getCompLangName("mf.jb.apply"));// = new JButton("Generate Skin-Pack");
    private JPanel jpNewSkinPack = new JPanel(new BorderLayout());
    public MainFrame(SettingsHandler settingsHandler, String inputMCPackString) throws IOException {
        this.settingsHandler = settingsHandler;

        String os = System.getProperty("os.name").toLowerCase();
        if (!(os.contains("mac") || os.contains("darwin"))) {
            switch (settingsHandler.getSettingValue("theme")) {
                case "light":
                    FlatLightLaf.setup();
                    break;
                case "rich_light":
                    FlatAtomOneLightIJTheme.setup();
                    break;
                case "dark":
                    FlatDarkLaf.setup();
                    break;
                case "rich_dark":
                    FlatOneDarkIJTheme.setup();
            }
        } else {
            if (settingsHandler.getSettingValue("theme").equals("light")) {
                FlatMacLightLaf.setup();
            } else FlatMacDarkLaf.setup();
        }


        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700, 460);
        this.setIconImage(appIcon.getImage());
        this.setTitle("MCSkinner");
        this.setResizable(true);
        this.setLocationRelativeTo(null);
        this.addWindowListener(this);
        contentRoot = this.getContentPane();

        jbNewSkinPack.addActionListener(this);
        jbNewSkinPack.setPreferredSize(new Dimension(500, 60));

        jbImportSkinPack.addActionListener(this);
        jbImportSkinPack.setPreferredSize(new Dimension(500, 60));

        jpHomePanelN.setPreferredSize(new Dimension(100, 130)); //160
        jpHomePanelS.setPreferredSize(new Dimension(100, 130)); //160
        jpHomePanelW.setPreferredSize(new Dimension(100, 190));
        jpHomePanelE.setPreferredSize(new Dimension(100, 190));

        jpHomeCenterGrid.add(jbNewSkinPack);
        jpHomeCenterGrid.add(jbImportSkinPack);

        jpHomePanel.add(jpHomeCenterGrid, BorderLayout.CENTER);
        jpHomePanel.add(jpHomePanelN, BorderLayout.NORTH);
        jpHomePanel.add(jpHomePanelS, BorderLayout.SOUTH);
        jpHomePanel.add(jpHomePanelW, BorderLayout.WEST);
        jpHomePanel.add(jpHomePanelE, BorderLayout.EAST);

        jbISPNext.addActionListener(this);

        ispButtons.add(jrImportMC);
        ispButtons.add(jrImportMCP);
        ispButtons.add(jrImportD);

        jpISPCenterGrid.add(jrImportMCP);
        jpISPCenterGrid.add(jrImportMC);
        jpISPCenterGrid.add(jrImportD);

        jpISPLowButtons.add(jbISPNext);

        jpImportSkinPack.add(jpISPCenterGrid, BorderLayout.CENTER);
        jpImportSkinPack.add(jpISPLowButtons, BorderLayout.SOUTH);

        jrImportMC.addActionListener(this);
        jrImportMCP.addActionListener(this);
        jrImportD.addActionListener(this);

        for (int i = 0; i < 3; i++) {
            JPanel jpCenterBorder = new JPanel(new BorderLayout(10, 10));
            JPanel jpNorthLabel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            JPanel jpSouth = new JPanel(new BorderLayout(10, 10));
            JPanel jpSouthButtons = new JPanel(new GridLayout(1, 3));
            JPanel jpSouthNorth = new JPanel();

            JLabel jlLabel = new JLabel();

            JButton jbBack = new JButton(settingsHandler.getCompLangName("mf.jb.isp_back"));
            JPanel jpMiddlePanel = new JPanel();
            JButton jbImport = new JButton(settingsHandler.getCompLangName("mf.jb.isp_import"));

            jpNorthLabel.setPreferredSize(new Dimension(100, 110));
            jpSouthNorth.setPreferredSize(new Dimension(100, 160));

            jpNorthLabel.add(jlLabel);

            jpSouthButtons.add(jbBack);
            jpSouthButtons.add(jpMiddlePanel);
            jpSouthButtons.add(jbImport);

            jpSouth.add(jpSouthButtons, BorderLayout.SOUTH);
            jpSouth.add(jpSouthNorth, BorderLayout.NORTH);


            JButton jbChooseInGameSkinPack = new JButton(settingsHandler.getCompLangName("mf.jb.choose_in_game_skinpack"));
            JButton jbChoosePathOfGen = new JButton(openFileIcon);
            JTextField tfChoosePathOfGen = new JTextField();
            JFileChooser fcChoosePathOfGen = new JFileChooser();

            jbBack.addActionListener((ActionEvent e) -> cardLayout.show(contentRoot, "ImportSkinPack"));

            AtomicReference<String> tempSTRChoosePathOfGen = new AtomicReference<>("");
            jbImport.addActionListener((ActionEvent e) -> {
                String strChoosePathOfGen = "";

                switch (importType) {
                    case "importD":
                        if (!(tfChoosePathOfGen.getText().isEmpty() || tfChoosePathOfGen.getText().equals("> This can't be empty"))) {
                            strChoosePathOfGen = tfChoosePathOfGen.getText();
                        } else Statics.warning(this, "You must choose a mcpack file");
                        break;
                    case "importMCP":
                        if (!(tfChoosePathOfGen.getText().isEmpty() || tfChoosePathOfGen.getText().equals("> This can't be empty"))) {
                            if (!(tfChoosePathOfGen.getText().toLowerCase().endsWith(".mcpack"))) {
                                strChoosePathOfGen = tfChoosePathOfGen.getText() + ".mcpack";
                            } else strChoosePathOfGen = tfChoosePathOfGen.getText();

                            String newFilePath = System.getProperty("java.io.tmpdir") + "MCSkinner/" + UUID.randomUUID();
                            Statics.unZipFile(Paths.get(strChoosePathOfGen), Paths.get(newFilePath));
                            strChoosePathOfGen = newFilePath;
                        } else Statics.warning(this, "You must choose a mcpack file");
                        break;
                    case "importMC":
                        if (!tempSTRChoosePathOfGen.get().isEmpty()) {
                            strChoosePathOfGen = tempSTRChoosePathOfGen.get();
                        } else Statics.warning(this, "You must choose a skinpack");
                }

                importSkinPackFromDir(strChoosePathOfGen);
            });
            switch (i) {
                case 0:
                    jlLabel.setText(settingsHandler.getCompLangName("mf.jl.import_mc.label"));

                    jbChooseInGameSkinPack.addActionListener((ActionEvent e) -> {
                        fcChoosePathOfGen.setDialogTitle("MCSkinner: Choose path of generation");
                        fcChoosePathOfGen.setCurrentDirectory(new File(System.getProperty("user.home") + "\\AppData\\Local\\Packages\\Microsoft.MinecraftUWP_8wekyb3d8bbwe\\LocalState\\games\\com.mojang\\skin_packs"));
                        fcChoosePathOfGen.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

                        int response = fcChoosePathOfGen.showOpenDialog(this);
                        if (response == JFileChooser.APPROVE_OPTION) {
                            File selectedFileGenPath = fcChoosePathOfGen.getSelectedFile();
                            tempSTRChoosePathOfGen.set(selectedFileGenPath.getAbsolutePath());
                        } else tempSTRChoosePathOfGen.set("");
                    });

                    jpCenterBorder.add(jbChooseInGameSkinPack, BorderLayout.CENTER);
                    jpImportMC.add(jpCenterBorder, BorderLayout.CENTER);
                    jpImportMC.add(jpNorthLabel, BorderLayout.NORTH);
                    jpImportMC.add(jpSouth, BorderLayout.SOUTH);
                    break;
                case 1:
                    jlLabel.setText(settingsHandler.getCompLangName("mf.jl.import_mcp.label"));

                    jbChoosePathOfGen.addActionListener((ActionEvent e) -> {
                        tfChoosePathOfGen.setText(Statics.selectFile(this, fcChoosePathOfGen, "MCSkinner: Choose path of generation", "*.mcpack", "mcpack"));
                    });

                    jpCenterBorder.add(tfChoosePathOfGen, BorderLayout.CENTER);
                    jpCenterBorder.add(jbChoosePathOfGen, BorderLayout.EAST);
                    jpImportMCP.add(jpCenterBorder, BorderLayout.CENTER);
                    jpImportMCP.add(jpNorthLabel, BorderLayout.NORTH);
                    jpImportMCP.add(jpSouth, BorderLayout.SOUTH);
                    break;
                case 2:
                    jlLabel.setText(settingsHandler.getCompLangName("mf.jl.import_d.label"));

                    jbChoosePathOfGen.addActionListener((ActionEvent e) -> {
                        tfChoosePathOfGen.setText(Statics.selectDir(this, fcChoosePathOfGen, "MCSkinner: Choose path of generation"));
                    });

                    jpCenterBorder.add(tfChoosePathOfGen, BorderLayout.CENTER);
                    jpCenterBorder.add(jbChoosePathOfGen, BorderLayout.EAST);
                    jpImportD.add(jpCenterBorder, BorderLayout.CENTER);
                    jpImportD.add(jpNorthLabel, BorderLayout.NORTH);
                    jpImportD.add(jpSouth, BorderLayout.SOUTH);
            }
        }

        jpVersion.add(tfVersion1);
        jpVersion.add(tfVersion2);
        jpVersion.add(tfVersion3);

        jpMCVersion.add(tfMCVersion1);
        jpMCVersion.add(tfMCVersion2);
        jpMCVersion.add(tfMCVersion3);

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


        jpCenterGrid.setVisible(true);

        jbAddSkin.addActionListener(this);
        jbApply.addActionListener(this);

        jbAddSkin.setPreferredSize(new Dimension(1, 68));
        jbApply.setPreferredSize(new Dimension(1, 68));

        jpLowButtons.setPreferredSize(new Dimension(50, 140));
        jpLowButtons.add(jbAddSkin, BorderLayout.NORTH);
        jpLowButtons.add(jbApply, BorderLayout.SOUTH);

        jpNewSkinPack.add(jpLowButtons, BorderLayout.SOUTH);

        jpNewSkinPack.add(jpCenterGrid, BorderLayout.CENTER);

        cardLayout = new CardLayout(10, 10);
        contentRoot.setLayout(cardLayout);

        contentRoot.add("Home", jpHomePanel);
        contentRoot.add("ImportSkinPack", jpImportSkinPack);
        contentRoot.add("ImportMC", jpImportMC);
        contentRoot.add("ImportMCP", jpImportMCP);
        contentRoot.add("ImportD", jpImportD);
        contentRoot.add("NewSkinPack", jpNewSkinPack);

        miNewSkinpack.addActionListener(this);
        miImportSkinpack.addActionListener(this);

        miLight.addActionListener(this);
        miLightExtra.addActionListener(this);
        miDark.addActionListener(this);
        miDarkExtra.addActionListener(this);
        miSystemTheme.addActionListener(this);

        jmLight.add(miLight);
        jmLight.add(miLightExtra);
        jmDark.add(miDark);
        jmDark.add(miDarkExtra);

        jmTheme.add(jmLight);
        jmTheme.add(jmDark);
        jmTheme.add(miSystemTheme);

        miChooseDefaultSaveLoc.addActionListener(this);
        miOpenSettings.addActionListener(this);

        languages.add("System Default");
        languages.add("English");
        languages.add("Afrikaans");
        languages.add("Chinese-Mandarin");
        for (String language : languages) {
            JMenuItem miTempLanguage = new JMenuItem(language);
            miTempLanguage.addActionListener((ActionEvent e) -> {
                if (!language.equals("System Default")) {
                    settingsHandler.setSetting("language", language);
                    settingsChangesMessage();
                } else {
                    switch (Locale.getDefault().getLanguage()) {
                        case "en":
                            settingsHandler.setSetting("language", "English");
                            break;
                        case "af":
                            settingsHandler.setSetting("language", "Afrikaans");
                            break;
                        case "zh":
                            settingsHandler.setSetting("language", "Chinese-Mandarin");
                            break;
                        default:
                            settingsHandler.setSetting("language", "English");
                            Statics.info(this, "System default language unavailable.\nChoosing English instead");
                    }

                    settingsChangesMessage();
                }
            });
            jmLanguage.add(miTempLanguage);
        }

        jmFile.add(miNewSkinpack);
        jmFile.add(miImportSkinpack);

        jmSettings.add(jmTheme);
        jmSettings.add(jmLanguage);
        jmSettings.add(miChooseDefaultSaveLoc);
        jmSettings.add(miOpenSettings);

        mbMain.add(jmFile);
        mbMain.add(jmSettings);

        this.setJMenuBar(mbMain);

        settingsHandler.doCompFonts(contentRoot);

        if (inputMCPackString != null) {
            String newFilePath = System.getProperty("java.io.tmpdir") + "MCSkinner/" + UUID.randomUUID();
            Statics.unZipFile(Paths.get(inputMCPackString), Paths.get(newFilePath));
            importSkinPackFromDir(newFilePath);
        }

        SwingUtilities.updateComponentTreeUI(this);
        this.setVisible(true);


    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == miNewSkinpack) {
            cardLayout.show(contentRoot, "NewSkinPack");

            SwingUtilities.updateComponentTreeUI(this);
            this.setSize(700, 550);
        } else if (e.getSource() == miImportSkinpack) {
            cardLayout.show(contentRoot, "ImportSkinPack");

            switch (settingsHandler.getSettingValue("defaultimporttype")) {
                case "importMC":
                    jrImportMC.setSelected(true);
                    break;
                case "importMCP":
                    jrImportMCP.setSelected(true);
                    break;
                case "importD":
                    jrImportD.setSelected(true);
            }

            SwingUtilities.updateComponentTreeUI(this);
        } else if (e.getSource() == jrImportMC) {
            importType = "importMC";
        } else if (e.getSource() == jrImportMCP) {
            importType = "importMCP";
        } else if (e.getSource() == jrImportD) {
            importType = "importD";
        } else if (e.getSource() == jbISPNext) {
            switch (importType) {
                case "importMC":
                    if (!(System.getProperty("os.name").toLowerCase().contains("mac") || System.getProperty("os.name").toLowerCase().contains("darwin"))) {
                        cardLayout.show(contentRoot, "ImportMC");
                    } else Statics.warning(this, "Minecraft bedrock isn't supported on mac");

                    System.out.println("importMC");
                    break;
                case "importMCP":
                    cardLayout.show(contentRoot, "ImportMCP");
                    System.out.println("importMCP");
                    break;
                case "importD":
                    cardLayout.show(contentRoot, "ImportD");
                    System.out.println("importD");
            }
        } else if (e.getSource() == miLight) {
            settingsHandler.setSetting("theme", "light");
            settingsChangesMessage();
        } else if (e.getSource() == miLightExtra) {
            settingsHandler.setSetting("theme", "rich_light");
            settingsChangesMessage();
        } else if (e.getSource() == miDark) {
            settingsHandler.setSetting("theme", "dark");
            settingsChangesMessage();
        } else if (e.getSource() == miDarkExtra) {
            settingsHandler.setSetting("theme", "rich_dark");
            settingsChangesMessage();
        } else if (e.getSource() == miSystemTheme) {
            if (OsThemeDetector.getDetector().isDark()) {
                settingsHandler.setSetting("theme", "dark");
            } else settingsHandler.setSetting("theme", "light");
            settingsChangesMessage();

        } else if (e.getSource() == miChooseDefaultSaveLoc) {
            String defaultSaveLoc = "";

            SwingUtilities.updateComponentTreeUI(fcChooseDefaultSaveLoc);
            fcChooseDefaultSaveLoc.invalidate();
            fcChooseDefaultSaveLoc.validate();
            fcChooseDefaultSaveLoc.repaint();

            fcChooseDefaultSaveLoc.setDialogTitle("MCSkinner: Choose default generation directory");
            fcChooseDefaultSaveLoc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            fcChooseDefaultSaveLoc.setCurrentDirectory(new File(System.getProperty("user.home")));

            int response = fcChooseDefaultSaveLoc.showOpenDialog(this);
            if (response == JFileChooser.APPROVE_OPTION) {
                File selectedFileGenPath = fcChooseDefaultSaveLoc.getSelectedFile();
                defaultSaveLoc = selectedFileGenPath.getAbsolutePath();
            }

            settingsHandler.setSetting("defaultsaveloc", defaultSaveLoc);
            settingsChangesMessage();
        } else if (e.getSource() == miOpenSettings) {
            settingsFrame = new SettingsFrame();
        } else if (e.getSource() == jbImportSkinPack) {
            cardLayout.show(contentRoot, "ImportSkinPack");

            switch (settingsHandler.getSettingValue("defaultimporttype")) {
                case "importMC":
                    jrImportMC.setSelected(true);
                    break;
                case "importMCP":
                    jrImportMCP.setSelected(true);
                    break;
                case "importD":
                    jrImportD.setSelected(true);
            }

            SwingUtilities.updateComponentTreeUI(this);
        } else if (e.getSource() == jbNewSkinPack) {
            cardLayout.show(contentRoot, "NewSkinPack");
            this.setTitle(settingsHandler.getCompLangName("mf.title.title"));

            this.setSize(700, 550);

            SwingUtilities.updateComponentTreeUI(this);
        } else if (e.getSource() == jbAddSkin) {
            addSkinFrame = new AddSkinFrame();
        } else if (e.getSource() == jbApply) {
            version = tfVersion1.getText() + ", " + tfVersion2.getText() + ", " + tfVersion3.getText();
            if (Integer.parseInt(tfMCVersion1.getText()) <= 1
                && Integer.parseInt(tfMCVersion2.getText()) <= 21
                && Integer.parseInt(tfMCVersion3.getText())  <= 82) {
                mcVersion = tfMCVersion1.getText() + ", " + tfMCVersion2.getText() + ", " + tfMCVersion3.getText();

                if (mainFrameCorrect(tfName, "skin-pack name")
                    && mainFrameCorrect(tfDescription, "skin-pack description")
                    && mainFrameCorrect(tfAuthor, "skin-pack author")) {

                    name = tfName.getText();
                    description = tfDescription.getText();
                    author = tfAuthor.getText();

                    saveAsFrame = new SaveAsFrame();

                }
            } else {
                Statics.warning(this, "The MC Version must be 1 21 82 or lower");
            }
        } else if (e.getSource() == addSkinFrame.jbApply) {

            boolean nameCorrect;
            boolean textureCorrect;
            boolean capeCorrect;
            HashMap<String, String> skin = new HashMap<>();
            skin.put("geo", addSkinFrame.cbGeo.getItemAt(addSkinFrame.cbGeo.getSelectedIndex()));

            if (!(addSkinFrame.tfName.getText().equals("> This can't be empty") || addSkinFrame.tfName.getText().isEmpty())) {
                skin.put("name", addSkinFrame.tfName.getText());
                nameCorrect = true;
            } else {
                Statics.warning(addSkinFrame, "You must insert a skin name");
                nameCorrect = false;
            }
            if (!(addSkinFrame.tfTexture.getText().equals("> This can't be empty") || addSkinFrame.tfTexture.getText().isEmpty())) {
                skin.put("texture", addSkinFrame.tfTexture.getText());
                textureCorrect = true;
            } else {
                Statics.warning(addSkinFrame, "You must insert a skin texture (*.png)");
                textureCorrect = false;
            }
            if (addSkinFrame.cape) {
                if (!(addSkinFrame.tfCape.getText().equals("> This can't be empty") || addSkinFrame.tfCape.getText().isEmpty())) {
                    skin.put("cape", addSkinFrame.tfCape.getText());
                    capeCorrect = true;
                } else {
                    Statics.warning(addSkinFrame, "You must insert a cape texture (*.png)");
                    capeCorrect = false;
                }
            } else capeCorrect = true;

            if (textureCorrect && capeCorrect && nameCorrect) {
                for (String key : skin.keySet()) {
                    System.out.println(key + ": " + skin.get(key));
                }

                skins.add(skin);
                addSkinFrame.dispose();
            }
        }
    }

    public void settingsChangesMessage() {
        Statics.info(this, "You must restart MCSkinner to apply your changes");
    }
    private boolean mainFrameCorrect(JTextField tf, String tfWarnName) {
        if (!(tf.getText().equals("> This can't be empty") || tf.getText().isEmpty())) {
            return true;
        } else {
            Statics.warning(this, "You must insert a " + tfWarnName);
            return false;
        }
    }

    public void saveSkinPack() {
        String langFileName;
        if (settingsHandler.getSettingValue("gen_as_cur_lang").equals("true")) {
            langFileName = switch (settingsHandler.getSettingValue("language")) {
                case "Afrikaans" -> "af_ZA.lang";
                case "Chinese-Mandarin" -> "zh_CN.lang";
                default -> "en_US.lang";
            };
        } else langFileName = "en_US.lang";

        SkinPackGen skinPackGen = new SkinPackGen(skins,
                name, author, description, version, mcVersion,
                System.getProperty("java.io.tmpdir") + "MCSkinner", langFileName);

        String json = skinPackGen.genSkinsJSON();
        System.out.println("skins.json: \n" + json + "\n");

        String lang = skinPackGen.genDefLangFile();
        System.out.println("en_US.lang: \n" + lang + "\n");

        String langJSON = skinPackGen.genLangJSON();
        System.out.println("languages.json: \n" + langJSON + "\n");

        String manifestJSON = skinPackGen.genManifestJSON();
        System.out.println("manifest.json: \n" + manifestJSON);
        skinPackGen.genSkinPackFiles();

        cardLayout.show(contentRoot, "Home");

        switch (saveAsFrame.selectedSaveType) {
            case "importMC":
                if (!System.getProperty("os.name").toLowerCase().contains("mac") || !System.getProperty("os.name").toLowerCase().contains("darwin")) {
                    Statics.copyDir(new File(System.getProperty("java.io.tmpdir") + "MCSkinner/" + name), new File(System.getProperty("user.home") + "\\AppData\\Local\\Packages\\Microsoft.MinecraftUWP_8wekyb3d8bbwe\\LocalState\\games\\com.mojang\\skin_packs\\" + name));
                    System.out.println("Generated skin-pack at: " + System.getProperty("user.home") + "\\AppData\\Local\\Packages\\Microsoft.MinecraftUWP_8wekyb3d8bbwe\\LocalState\\games\\com.mojang\\skin_packs" + name);
                } else
                    Statics.warning(saveAsFrame, "Minecraft bedrock is not supported on MacOS, so you cannot import this pack into the game");
                saveAsFrame.dispose();
                break;
            case "exportMCP":
                String mcpackPath = saveAsFrame.getStrTFSelectedDirText();

                if (mcpackPath.toLowerCase().endsWith(".mcpack")) {
                    Statics.zipFile(Paths.get(System.getProperty("java.io.tmpdir") + "MCSkinner/" + name), Paths.get(saveAsFrame.getStrTFSelectedDirText()));
                } else {
                    Statics.zipFile(Paths.get(System.getProperty("java.io.tmpdir") + "MCSkinner/" + name), Paths.get(saveAsFrame.getStrTFSelectedDirText() + "/" + name + ".mcpack"));
                }

                saveAsFrame.dispose();
                break;
            case "saveD":
                Statics.copyDir(new File(System.getProperty("java.io.tmpdir") + "MCSkinner/" + name), new File(saveAsFrame.getStrTFSelectedDirText() + "/" + name));
                saveAsFrame.dispose();
        }

        this.setSize(700, 460);
    }
    public void importSkinPack(File manifestFile, File skinsFile, ArrayList<String> images) {
        try {
            tfName.setText(SkinPackGen.getSkinpackName(manifestFile));
            tfDescription.setText(SkinPackGen.getSkinpackDescription(manifestFile));
            tfAuthor.setText(SkinPackGen.getSkinpackAuthor(manifestFile));

            tfVersion1.setText(Integer.toString(SkinPackGen.getSkinpackVersion(manifestFile)[0]));
            tfVersion2.setText(Integer.toString(SkinPackGen.getSkinpackVersion(manifestFile)[1]));
            tfVersion3.setText(Integer.toString(SkinPackGen.getSkinpackVersion(manifestFile)[2]));

            tfMCVersion1.setText(Integer.toString(SkinPackGen.getSkinpackMCVersion(manifestFile)[0]));
            tfMCVersion2.setText(Integer.toString(SkinPackGen.getSkinpackMCVersion(manifestFile)[1]));
            tfMCVersion3.setText(Integer.toString(SkinPackGen.getSkinpackMCVersion(manifestFile)[2]));


            skins = SkinPackGen.getSkinpackSkins(skinsFile, images);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void importSkinPackFromDir(String dirPath) {
        List<String> images = new ArrayList<>();

        try {
            images = Files.walk(Paths.get(dirPath))
                    .filter(p -> Files.isRegularFile(p) && p.toString().toLowerCase().endsWith(".png"))
                    .map(p -> p.toAbsolutePath().toString())
                    .collect(Collectors.toList());
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        importSkinPack(new File(dirPath + "/manifest.json"), new File(dirPath + "/skins.json"), (ArrayList<String>) images);

        cardLayout.show(contentRoot, "NewSkinPack");
        this.setSize(700, 550);
    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        try {
            Files.walk(new File(System.getProperty("java.io.tmpdir"), "MCSkinner").toPath())
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        } catch (IOException ex) {
            System.err.println("Stacktrace: " + ex.getMessage());
        }
        System.out.println(System.getProperty("java.io.tmpdir") + "MCSkinner" + " deleted");
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
