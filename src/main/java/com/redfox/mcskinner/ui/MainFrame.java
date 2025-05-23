package com.redfox.mcskinner.ui;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneLightIJTheme;
import com.formdev.flatlaf.themes.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jthemedetecor.OsThemeDetector;
import com.redfox.mcskinner.MCSkinner;
import com.redfox.mcskinner.SkinPackGen;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class MainFrame extends JFrame implements ActionListener {
    private ArrayList<HashMap<String, String>> skins = new ArrayList<>();
    private String importType = "importMCP";


    private AddSkinFrame addSkinFrame;
    private SaveAsFrame saveAsFrame;
    private SettingsFrame settingsFrame;

    public InputStream openFileInputStream = MCSkinner.class.getResourceAsStream("/mcskinner/icons/file-open-2-64.png");
    public final ImageIcon openFileIcon  = new ImageIcon(openFileInputStream.readAllBytes());

    private InputStream appIconInputStream = MCSkinner.class.getResourceAsStream("/mcskinner/icons/icon.png");
    public final ImageIcon appIcon = new ImageIcon(appIconInputStream.readAllBytes());


    public HashMap<String, String> settings = new HashMap<>();
    public HashMap<String, String> languageModules = new HashMap<>();

    private String name = "";
    private String description = "";
    private String author = "";
    private String version = "";
    private String mcVersion = "";

    private CardLayout cardLayout;
    private Container contentRoot;

    private JMenuBar mbMain = new JMenuBar();
    private JMenu jmFile;
    private JMenuItem miNewSkinpack;
    private JMenuItem miImportSkinpack;
    private JMenu jmSettings;// = new JMenu("Settings");
    private JMenuItem miOpenSettings;// = new JMenuItem("Open Settings");
    private JMenu jmTheme;// = new JMenu("Theme");
    private JMenu jmLight;
    private JMenuItem miLight;// = new JMenuItem("Light");
    private JMenuItem miLightExtra;
    private JMenu jmDark;
    private JMenuItem miDark;// = new JMenuItem("Dark");
    private JMenuItem miDarkExtra;
    private JMenuItem miSystemTheme;
    private JMenuItem miChooseDefaultSaveLoc;// = new JMenuItem("Default save location...");
    private JFileChooser fcChooseDefaultSaveLoc = new JFileChooser();
    private JMenu jmLanguage;// = new JMenu("Language");
    private ArrayList<String> languages = new ArrayList<>();

    private JPanel jpHomePanel = new JPanel(new BorderLayout());
    private JPanel jpHomePanelN = new JPanel();
    private JPanel jpHomePanelS = new JPanel();
    private JPanel jpHomePanelW = new JPanel();
    private JPanel jpHomePanelE = new JPanel();
    private JPanel jpHomeCenterGrid = new JPanel(new GridLayout(2, 1));
    private JButton jbNewSkinPack;// = new JButton("Create new SkinPack");
    private JButton jbImportSkinPack;

    private JPanel jpImportSkinPack = new JPanel(new BorderLayout(10, 10));
    private JPanel jpISPCenterGrid = new JPanel(new GridLayout(3, 1));

    private ButtonGroup ispButtons = new ButtonGroup();
    private JRadioButton jrImportMC;
    private  JRadioButton jrImportMCP;
    private JRadioButton jrImportD;

    private JPanel jpISPLowButtons = new JPanel(new BorderLayout(10, 10));
    private JButton jbISPNext;

    private JPanel jpImportMC = new JPanel(new BorderLayout(10, 10));
    private JPanel jpImportMCP = new JPanel(new BorderLayout(10, 10));
    private JPanel jpImportD = new JPanel(new BorderLayout(10, 10));

    private JPanel jpCenterGrid = new JPanel(new GridLayout(5, 2));
    private JLabel jlName;// = new JLabel("Name: ");
    private JLabel jlDescription;// = new JLabel("Description: ");
    private JLabel jlAuthor;// = new JLabel("Author: ");
    private JLabel jlVersion;// = new JLabel("Version: ");
    private JLabel jlMCVersion;// = new JLabel("MC Version: ");
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
    private JTextField tfMCVersion3 = new JTextField("71");
    private JPanel jpFileGenPath = new JPanel(new BorderLayout(2, 2));
    private JTextField tfFileGenPath;
    private JButton jbSelctFileGenPath = new JButton();
    private JFileChooser fcSelectFileGenPath = new JFileChooser();

    private JPanel jpLowButtons = new JPanel(new BorderLayout(60, 60));
    private JButton jbAddSkin;// = new JButton("Add Skin");
    private JButton jbApply;// = new JButton("Generate Skin-Pack");
    private JPanel jpNewSkinPack = new JPanel(new BorderLayout());
    public MainFrame() throws IOException {
        initialiseJSONFiles();

        String os = System.getProperty("os.name").toLowerCase();
        if (!(os.contains("mac") || os.contains("darwin"))) {
            switch (settings.get("theme")) {
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
            if (settings.get("theme").equals("light")) {
                FlatMacLightLaf.setup();
            } else FlatMacDarkLaf.setup();
        }

        jmFile = new JMenu(languageModules.get("mf.jm.file"));
        miNewSkinpack = new JMenuItem(languageModules.get("mf.mi.new_skinpack"));
        miImportSkinpack = new JMenuItem(languageModules.get("mf.mi.import_skinpack"));
        jmSettings = new JMenu(languageModules.get("mf.jm.settings"));
        miOpenSettings = new JMenuItem(languageModules.get("mf.mi.open_settings"));
        jmTheme = new JMenu(languageModules.get("mf.jm.theme"));
        jmLight = new JMenu(languageModules.get("mf.jm.light"));
        miLight = new JMenuItem(languageModules.get("mf.mi.light"));
        miLightExtra = new JMenuItem(languageModules.get("mf.mi.light_extra"));
        jmDark = new JMenu(languageModules.get("mf.jm.dark"));
        miDark = new JMenuItem(languageModules.get("mf.mi.dark"));
        miDarkExtra = new JMenuItem(languageModules.get("mf.mi.dark_extra"));
        miSystemTheme = new JMenuItem(languageModules.get("mf.mi.sys_theme"));
        miChooseDefaultSaveLoc = new JMenuItem(languageModules.get("mf.mi.default_save_loc"));
        jmLanguage = new JMenu(languageModules.get("mf.jm.language"));
        jbImportSkinPack = new JButton(languageModules.get("mf.mi.import_skinpack"));
        jrImportMCP = new JRadioButton(languageModules.get("mf.jr.import_mcp"));
        jrImportMC = new JRadioButton(languageModules.get("mf.jr.import_mc"));
        jrImportD = new JRadioButton(languageModules.get("mf.jr.import_d"));
        jbISPNext = new JButton(languageModules.get("mf.jb.isp_next"));
        jbNewSkinPack = new JButton(languageModules.get("mf.jb.new_skin_pack"));
        jbImportSkinPack = new JButton(languageModules.get("mf.jb.import_skin_pack"));
        jlName = new JLabel(languageModules.get("mf.jl.name"));
        jlDescription = new JLabel(languageModules.get("mf.jl.description"));
        jlAuthor = new JLabel(languageModules.get("mf.jl.author"));
        jlVersion = new JLabel(languageModules.get("mf.jl.version"));
        jlMCVersion = new JLabel(languageModules.get("mf.jl.mc_version"));
        jlFileGenPath = new JLabel(languageModules.get("mf.jl.file_gen_path"));
        jbAddSkin = new JButton(languageModules.get("mf.jb.add_skin"));
        jbApply = new JButton(languageModules.get("mf.jb.apply"));

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700, 460);
        this.setIconImage(appIcon.getImage());
        this.setTitle("MCSkinner");
        this.setResizable(true);
        this.setLocationRelativeTo(null);
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

            JButton jbBack = new JButton(languageModules.get("mf.jb.isp_back"));
            JPanel jpMiddlePanel = new JPanel();
            JButton jbImport = new JButton(languageModules.get("mf.jb.isp_import"));

            jpNorthLabel.setPreferredSize(new Dimension(100, 110));
            jpSouthNorth.setPreferredSize(new Dimension(100, 160));

            jpNorthLabel.add(jlLabel);

            jpSouthButtons.add(jbBack);
            jpSouthButtons.add(jpMiddlePanel);
            jpSouthButtons.add(jbImport);

            jpSouth.add(jpSouthButtons, BorderLayout.SOUTH);
            jpSouth.add(jpSouthNorth, BorderLayout.NORTH);


            JButton jbChooseInGameSkinPack = new JButton(languageModules.get("mf.jb.choose_in_game_skinpack"));
            JButton jbChoosePathOfGen = new JButton(openFileIcon);
            JTextField tfChoosePathOfGen = new JTextField();
            JFileChooser fcChoosePathOfGen = new JFileChooser();

            jbBack.addActionListener((ActionEvent e) -> cardLayout.show(contentRoot, "ImportSkinPack"));

            AtomicReference<String> tempSTRChoosePathOfGen = new AtomicReference<>("");
            jbImport.addActionListener((ActionEvent e) -> {
                String strChoosePathOfGen = "";
                List<String> images = new ArrayList<>();

                switch (importType) {
                    case "importD":
                        if (!(tfChoosePathOfGen.getText().isEmpty() || tfChoosePathOfGen.getText().equals("> This can't be empty"))) {
                            strChoosePathOfGen = tfChoosePathOfGen.getText();
                        } else warning(this, "You must choose a mcpack file");
                        break;
                    case "importMCP":
                        if (!(tfChoosePathOfGen.getText().isEmpty() || tfChoosePathOfGen.getText().equals("> This can't be empty"))) {
                            if (!(tfChoosePathOfGen.getText().toLowerCase().endsWith(".mcpack"))) {
                                strChoosePathOfGen = tfChoosePathOfGen.getText() + ".mcpack";
                            } else strChoosePathOfGen = tfChoosePathOfGen.getText();

                            String newFilePath = "temp/" + UUID.randomUUID();
                            unZipFile(Paths.get(strChoosePathOfGen), Paths.get(newFilePath));
                            strChoosePathOfGen = newFilePath;
                        } else warning(this, "You must choose a mcpack file");
                        break;
                    case "importMC":
                        if (!tempSTRChoosePathOfGen.get().isEmpty()) {
                            strChoosePathOfGen = tempSTRChoosePathOfGen.get();
                        } else warning(this, "You must choose a skinpack");
                }

                try {
                    images = Files.walk(Paths.get(strChoosePathOfGen))
                            .filter(p -> Files.isRegularFile(p) && p.toString().toLowerCase().endsWith(".png"))
                            .map(p -> p.toAbsolutePath().toString())
                            .collect(Collectors.toList());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

                importSkinPack(new File(strChoosePathOfGen + "/manifest.json"), new File(strChoosePathOfGen + "/skins.json"), (ArrayList<String>) images);

                cardLayout.show(contentRoot, "NewSkinPack");
                this.setSize(700, 550);
            });
            switch (i) {
                case 0:
                    jlLabel.setText(languageModules.get("mf.jl.import_mc.label"));

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
                    jlLabel.setText(languageModules.get("mf.jl.import_mcp.label"));

                    jbChoosePathOfGen.addActionListener((ActionEvent e) -> {
                        tfChoosePathOfGen.setText(selectFile(fcChoosePathOfGen, "MCSkinner: Choose path of generation", "*.mcpack", "mcpack"));
                    });

                    jpCenterBorder.add(tfChoosePathOfGen, BorderLayout.CENTER);
                    jpCenterBorder.add(jbChoosePathOfGen, BorderLayout.EAST);
                    jpImportMCP.add(jpCenterBorder, BorderLayout.CENTER);
                    jpImportMCP.add(jpNorthLabel, BorderLayout.NORTH);
                    jpImportMCP.add(jpSouth, BorderLayout.SOUTH);
                    break;
                case 2:
                    jlLabel.setText(languageModules.get("mf.jl.import_d.label"));

                    jbChoosePathOfGen.addActionListener((ActionEvent e) -> {
                        tfChoosePathOfGen.setText(selectDir(fcChoosePathOfGen, "MCSkinner: Choose path of generation"));
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

        jbSelctFileGenPath.addActionListener(this);
        jbSelctFileGenPath.setIcon(openFileIcon);

//        jpFileGenPath.add(tfFileGenPath, BorderLayout.CENTER);
//        jpFileGenPath.add(jbSelctFileGenPath, BorderLayout.EAST);

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
                    settings.put("language", language);
                    updateSettingsJson(settings, "settings.json");
                    settingsChangesMessage();
                } else {
                    switch (Locale.getDefault().getLanguage()) {
                        case "en":
                            settings.put("language", "English");
                            break;
                        case "af":
                            settings.put("language", "Afrikaans");
                            break;
                        case "zh":
                            settings.put("language", "Chinese-Mandarin");
                            break;
                        default:
                            settings.put("language", "English");
                            info(this, "System default language unavailable.\nChoosing English instead");
                    }
                    updateSettingsJson(settings, "settings.json");
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

        for (Component jTextComponent : getAllLabels(contentRoot)) {
            jTextComponent.setFont(new Font(settings.get("font"), Font.PLAIN, Integer.parseInt(settings.get("size"))));
        }

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

            switch (settings.get("defaultimporttype")) {
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
                    } else warning(this, "Minecraft bedrock isn't supported on mac");

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
            settings.put("theme", "light");
            updateSettingsJson(settings, "settings.json");
            settingsChangesMessage();
        } else if (e.getSource() == miLightExtra) {
            settings.put("theme", "rich_light");
            updateSettingsJson(settings, "settings.json");
            settingsChangesMessage();
        } else if (e.getSource() == miDark) {
            settings.put("theme", "dark");
            updateSettingsJson(settings, "settings.json");
            settingsChangesMessage();
        } else if (e.getSource() == miDarkExtra) {
            settings.put("theme", "rich_dark");
            updateSettingsJson(settings, "settings.json");
            settingsChangesMessage();
        } else if (e.getSource() == miSystemTheme) {
            if (OsThemeDetector.getDetector().isDark()) {
                settings.put("theme", "dark");
            } else settings.put("theme", "light");
            updateSettingsJson(settings, "settings.json");
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

            settings.put("defaultsaveloc", defaultSaveLoc);
            updateSettingsJson(settings, "settings.json");
            settingsChangesMessage();
        } else if (e.getSource() == miOpenSettings) {
            settingsFrame = new SettingsFrame();

        } else if (e.getSource() == jbImportSkinPack) {
            cardLayout.show(contentRoot, "ImportSkinPack");

            switch (settings.get("defaultimporttype")) {
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
            this.setTitle(languageModules.get("mf.title.title"));

            this.setSize(700, 550);

            SwingUtilities.updateComponentTreeUI(this);
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
            } else tfFileGenPath.setText("> This can't be empty");
        } else if (e.getSource() == jbAddSkin) {
            addSkinFrame = new AddSkinFrame();
        } else if (e.getSource() == jbApply) {
            version = tfVersion1.getText() + ", " + tfVersion2.getText() + ", " + tfVersion3.getText();
            if (Integer.parseInt(tfMCVersion1.getText()) <= 1
                && Integer.parseInt(tfMCVersion2.getText()) <= 21
                && Integer.parseInt(tfMCVersion3.getText())  <= 71) {
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
                warning(this, "The MC Version must be 1 21 70 or lower");
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
                warning(addSkinFrame, "You must insert a skin name");
                nameCorrect = false;
            }
            if (!(addSkinFrame.tfTexture.getText().equals("> This can't be empty") || addSkinFrame.tfTexture.getText().isEmpty())) {
                skin.put("texture", addSkinFrame.tfTexture.getText());
                textureCorrect = true;
            } else {
                warning(addSkinFrame, "You must insert a skin texture (*.png)");
                textureCorrect = false;
            }
            if (addSkinFrame.cape) {
                if (!(addSkinFrame.tfCape.getText().equals("> This can't be empty") || addSkinFrame.tfCape.getText().isEmpty())) {
                    skin.put("cape", addSkinFrame.tfCape.getText());
                    capeCorrect = true;
                } else {
                    warning(addSkinFrame, "You must insert a cape texture (*.png)");
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
    public void updateSettingsJson(HashMap<String, String> settings, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fw = new FileWriter(fileName)) {
            gson.toJson(settings, fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void warning(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "MCSkinner: warning", JOptionPane.WARNING_MESSAGE);
    }
    public void info(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "MCSkinner: info", JOptionPane.INFORMATION_MESSAGE);
    }

    public void settingsChangesMessage() {
        info(this, "You must restart MCSkinner to apply your changes");
    }
    private boolean mainFrameCorrect(JTextField tf, String tfWarnName) {
        if (!(tf.getText().equals("> This can't be empty") || tf.getText().isEmpty())) {
            return true;
        } else {
            warning(this, "You must insert a " + tfWarnName);
            return false;
        }
    }

    public void initialiseJSONFiles() {
        if (new File("settings.json").exists()) {
            Gson gson = new Gson();
            try (FileReader fr = new FileReader("settings.json")) {
                settings = gson.fromJson(fr, HashMap.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            settings.put("theme", "light");
            settings.put("defaultsaveloc", "");
            settings.put("language", "English");
            settings.put("font", "Open Sans");
            settings.put("size", "12");
            settings.put("gen_as_cur_lang", "false");
            settings.put("defaultimporttype", "");
            updateSettingsJson(settings, "settings.json");
        }

        InputStream languageJSONIn = getClass().getClassLoader().getResourceAsStream("mcskinner/support_files/" + settings.get("language") + ".json");

        if (languageJSONIn != null) {
            Gson gsonLanguageModules = new Gson();
            try (InputStreamReader ir = new InputStreamReader(languageJSONIn, StandardCharsets.UTF_8)) {
                languageModules = gsonLanguageModules.fromJson(ir, HashMap.class);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(this, "No language file found", "MCSkinner: error", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }
    public AddSkinFrame getAddSkinFrame() {
        return addSkinFrame;
    }
    public SettingsFrame getSettingsFrame() {
        return settingsFrame;
    }

    public String selectDir(JFileChooser fileSelector, String dialogTitle) {
        fileSelector.setDialogTitle(dialogTitle);
        fileSelector.setCurrentDirectory(new File(System.getProperty("user.home")));
        fileSelector.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int response = fileSelector.showOpenDialog(this);
        if (response == JFileChooser.APPROVE_OPTION) {
            File selectedFileGenPath = fileSelector.getSelectedFile();
            return selectedFileGenPath.getAbsolutePath();
        } else return "> This can't be empty";
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

    private void copyFile(Path sourcePath, Path destinationPath) {
        try {
            Files.copy(sourcePath, destinationPath.resolve(sourcePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error copying file " + sourcePath + " to destination " + destinationPath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }
    private void copyDir(File sourceDir, File destDir) {
        try {
            FileUtils.copyDirectory(sourceDir, destDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void zipFile(Path sourceDir, Path destFile) {
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
            e.printStackTrace();
        }
    }
    private void unZipFile(Path zipFilePath, Path outputDir) {
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
            e.printStackTrace();
        }
    }
    public void saveSkinPack() {
        String langFileName;
        if (settings.get("gen_as_cur_lang").equals("true")) {
            langFileName = switch (settings.get("language")) {
                case "Afrikaans" -> "af_ZA.lang";
                case "Chinese-Mandarin" -> "zh_CN.lang";
                default -> "en_US.lang";
            };
        } else langFileName = "en_US.lang";

        SkinPackGen skinPackGen = new SkinPackGen(skins,
                name, author, description, version, mcVersion,
                "temp", langFileName);

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
                    copyDir(new File("temp/" + name), new File(System.getProperty("user.home") + "\\AppData\\Local\\Packages\\Microsoft.MinecraftUWP_8wekyb3d8bbwe\\LocalState\\games\\com.mojang\\skin_packs\\" + name));
                    System.out.println("Generated skin-pack at: " + System.getProperty("user.home") + "\\AppData\\Local\\Packages\\Microsoft.MinecraftUWP_8wekyb3d8bbwe\\LocalState\\games\\com.mojang\\skin_packs" + name);
                } else
                    warning(saveAsFrame, "Minecraft bedrock is not supported on MacOS, so you cannot import this pack into the game");
                saveAsFrame.dispose();
                break;
            case "exportMCP":
                String mcpackPath = saveAsFrame.getStrTFSelectedDirText();

                if (mcpackPath.toLowerCase().endsWith(".mcpack")) {
                    zipFile(Paths.get("temp/" + name), Paths.get(saveAsFrame.getStrTFSelectedDirText()));
                } else {
                    zipFile(Paths.get("temp/" + name), Paths.get(saveAsFrame.getStrTFSelectedDirText() + "/" + name + ".mcpack"));
                }

                saveAsFrame.dispose();
                break;
            case "saveD":
                copyDir(new File("temp/" + name), new File(saveAsFrame.getStrTFSelectedDirText() + "/" + name));
                saveAsFrame.dispose();
        }

        this.setSize(700, 460);
        try {
            FileUtils.deleteDirectory(new File("temp"));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
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

    public ArrayList<Component> getAllLabels(Container container) {
        ArrayList<Component> jTextComponents = new ArrayList<>();
        Component[] components = container.getComponents();

        for (Component component : components) {
            if (hasText(component)) {
                jTextComponents.add(component);
//            } else if (component instanceof JTabbedPane || component instanceof JComboBox<?>) {
//                jTextComponents.add(component);
            } else if (component instanceof Container) {
                jTextComponents.addAll(getAllLabels((Container) component));
            }
        }

        return jTextComponents;
    }
    private boolean hasText(Component component) {
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
