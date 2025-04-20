package com.redfox.mcskinner.ui;

import com.formdev.flatlaf.*;
import com.formdev.flatlaf.intellijthemes.*;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatArcDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneDarkIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatAtomOneLightIJTheme;
import com.formdev.flatlaf.intellijthemes.materialthemeuilite.FlatGitHubDarkIJTheme;
import com.formdev.flatlaf.themes.*;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jthemedetecor.OsThemeDetector;
import com.redfox.mcskinner.MCSkinner;
import com.redfox.mcskinner.SkinPackGen;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.prefs.Preferences;

public class MainFrame extends JFrame implements ActionListener {
    private ArrayList<HashMap<String, String>> skins = new ArrayList<>();


    private AddSkinFrame addSkinFrame;

    public InputStream openFileInputStream = MCSkinner.class.getResourceAsStream("/mcskinner/icons/file-open-2-64.png");
    public final ImageIcon openFileIcon  = new ImageIcon(openFileInputStream.readAllBytes());

    private InputStream appIconInputStream = MCSkinner.class.getResourceAsStream("/mcskinner/icons/icon.png");
    public final ImageIcon appIcon = new ImageIcon(appIconInputStream.readAllBytes());


    public HashMap<String, String> settings = new HashMap<>();
    public HashMap<String, String> languageModules = new HashMap<>();

    private CardLayout cardLayout;
    private Container contentRoot;

    private JMenuBar mbMain = new JMenuBar();
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
    private JButton jbNewSkinPack;// = new JButton("Create new SkinPack");

    private JPanel jpCenterGrid = new JPanel(new GridLayout(6, 2));
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
                case "light_extra":
                    FlatAtomOneLightIJTheme.setup();
                    break;
                case "dark":
                    FlatDarkLaf.setup();
                    break;
                case "dark_extra":
                    FlatOneDarkIJTheme.setup();
            }
        } else {
            if (settings.get("theme").equals("light")) {
                FlatMacLightLaf.setup();
            } else FlatMacDarkLaf.setup();
        }

        tfFileGenPath = new JTextField(settings.get("defaultsaveloc"));

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
        jbNewSkinPack = new JButton(languageModules.get("mf.jb.new_skin_pack"));
        jlName = new JLabel(languageModules.get("mf.jl.name"));
        jlDescription = new JLabel(languageModules.get("mf.jl.description"));
        jlAuthor = new JLabel(languageModules.get("mf.jl.author"));
        jlVersion = new JLabel(languageModules.get("mf.jl.version"));
        jlMCVersion = new JLabel(languageModules.get("mf.jl.mc_version"));
        jlFileGenPath = new JLabel(languageModules.get("mf.jl.file_gen_path"));
        jbAddSkin = new JButton(languageModules.get("mf.jb.add_skin"));
        jbApply = new JButton(languageModules.get("mf.jb.apply"));

        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(700, 520);
        this.setIconImage(appIcon.getImage());
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
                }
            });
            jmLanguage.add(miTempLanguage);
        }

        jmSettings.add(jmTheme);
        jmSettings.add(jmLanguage);
        jmSettings.add(miChooseDefaultSaveLoc);
        jmSettings.add(miOpenSettings);

        mbMain.add(jmSettings);

        this.setJMenuBar(mbMain);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == miLight) {
            settings.put("theme", "light");
            updateSettingsJson(settings, "settings.json");
            settingsChangesMessage();
        } else if (e.getSource() == miLightExtra) {
            settings.put("theme", "light_extra");
            updateSettingsJson(settings, "settings.json");
            settingsChangesMessage();
        } else if (e.getSource() == miDark) {
            settings.put("theme", "dark");
            updateSettingsJson(settings, "settings.json");
            settingsChangesMessage();
        } else if (e.getSource() == miDarkExtra) {
            settings.put("theme", "dark_extra");
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
        } else if (e.getSource() == jbNewSkinPack) {
            cardLayout.next(contentRoot);
            this.setTitle(languageModules.get("mf.title.title"));

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
            } else tfFileGenPath.setText("> This can't be empty");
        } else if (e.getSource() == jbAddSkin) {
            addSkinFrame = new AddSkinFrame();
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
        } else if (e.getSource() == jbApply) {
            String version = tfVersion1.getText() + ", " + tfVersion2.getText() + ", " + tfVersion3.getText();
            String mcVersion;
            if (Integer.parseInt(tfMCVersion1.getText()) <= 1
                && Integer.parseInt(tfMCVersion2.getText()) <= 21
                && Integer.parseInt(tfMCVersion3.getText())  <= 71) {
                mcVersion = tfMCVersion1.getText() + ", " + tfMCVersion2.getText() + ", " + tfMCVersion3.getText();


                if (mainFrameCorrect(tfFileGenPath, "path of generation")
                    && mainFrameCorrect(tfName, "skin-pack name")
                    && mainFrameCorrect(tfDescription, "skin-pack description")
                    && mainFrameCorrect(tfAuthor, "skin-pack author")) {
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
            } else {
                warning(this, "The MC Version must be 1 21 60 or lower");
            }
        }
    }
    private void updateSettingsJson(HashMap<String, String> settings, String fileName) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fw = new FileWriter(fileName)) {
            gson.toJson(settings, fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void warning(Component parent, String message) {
        JOptionPane.showMessageDialog(parent, message, "MCSkinner: warning", JOptionPane.WARNING_MESSAGE);
    }
    private void info(Component parent, String message) {
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
}
