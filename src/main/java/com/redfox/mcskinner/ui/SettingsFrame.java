package com.redfox.mcskinner.ui;

import com.redfox.mcskinner.Statics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.redfox.mcskinner.MCSkinner.mainFrame;
import static com.redfox.mcskinner.MCSkinner.settingsHandler;

public class SettingsFrame extends JFrame implements ActionListener {
    Container contentRoot;

    String[] themes = {
            settingsHandler.getCompLangName("mf.jm.light"), settingsHandler.getCompLangName("mf.mi.light_extra"),
            settingsHandler.getCompLangName("mf.jm.dark"), settingsHandler.getCompLangName("mf.mi.dark_extra")
    };
    String[] fonts = {
            "Open Sans", "Bradley Hand ITC",
            "Algerian", "Blackadder ITC",
            "Minecraft"
    };
    String[] languages = {
            "English",
            "Afrikaans",
            "Chinese-Mandarin"
    };
    boolean generateAsCurrentLang = false;
    String[] importTypes = {
            settingsHandler.getCompLangName("sf.cb_i.import_mc"),
            settingsHandler.getCompLangName("sf.cb_i.import_mcp"),
            settingsHandler.getCompLangName("sf.cb_i.import_d"),
            settingsHandler.getCompLangName("sf.cb_i_importT_none")
    };

    JButton jbCancel = new JButton(settingsHandler.getCompLangName("sf.jb.cancel"));
    JButton jbApply = new JButton(settingsHandler.getCompLangName("sf.jb.apply"));

    JPanel jpSouthPanel = new JPanel();

    JTabbedPane tpMenu = new JTabbedPane(JTabbedPane.LEFT);

    JPanel jpAppearance = new JPanel(new BorderLayout(2, 2));
    JPanel jpLanguage = new JPanel(new BorderLayout(2, 2));
    JPanel jpOther = new JPanel(new BorderLayout(2, 2));

    JPanel jpAppearanceN = new JPanel();
    JPanel jpLanguageN = new JPanel();
    JPanel jpOtherN = new JPanel();

    JPanel jpAppearanceS = new JPanel();
    JPanel jpLanguageS = new JPanel();
    JPanel jpOtherS = new JPanel();

    JLabel jlAppearance = new JLabel(settingsHandler.getCompLangName("sf.jl.appearance"));
    JLabel jlLanguage = new JLabel(settingsHandler.getCompLangName("sf.jl.language"));
    JLabel jlOther = new JLabel(settingsHandler.getCompLangName("sf.jl.other"));

    //jpAppearance
    JPanel jpAppearanceC = new JPanel(new GridLayout(0, 2));
    JPanel jpAppearanceE = new JPanel(new GridLayout(0, 1));

    JLabel jlTheme = new JLabel(settingsHandler.getCompLangName("sf.jl.theme"));
    JComboBox<String> cbTheme = new JComboBox<>(themes);
    JButton jbThemeReset = new JButton(settingsHandler.getCompLangName("sf.jb.reset"));

    JLabel jlFont = new JLabel(settingsHandler.getCompLangName("sf.jl.font"));
    JComboBox<String> cbFont = new JComboBox<>(fonts);
    JButton jbFontReset = new JButton(settingsHandler.getCompLangName("sf.jb.reset"));

    JLabel jlTextSize = new JLabel(settingsHandler.getCompLangName("sf.jl.text_size"));
    JSpinner jsTextSize = new JSpinner(new SpinnerNumberModel(12, 1, 70, 2));
    JButton jbTextSizeReset = new JButton(settingsHandler.getCompLangName("sf.jb.reset"));

    //jpLanguage
    JPanel jpLanguageC = new JPanel(new GridLayout(0, 2));
    JPanel jpLanguageE = new JPanel(new GridLayout(0, 1));

    JLabel jlSelectLanguage = new JLabel(settingsHandler.getCompLangName("sf.jl.language"));
    JComboBox<String> cbSelectLanguage = new JComboBox<>(languages);
    JButton jbSelectLanguageReset = new JButton(settingsHandler.getCompLangName("sf.jb.reset"));

    JLabel jlGenerateAsCurrentLang = new JLabel(settingsHandler.getCompLangName("sf.jl.gen_as_cur_lang"));
    JCheckBox jcGenerateAsCurrentLang = new JCheckBox();
    JButton jbGenerateAsCurrentLangReset = new JButton(settingsHandler.getCompLangName("sf.jb.reset"));

    //jpOther
    JPanel jpOtherC = new JPanel(new GridLayout(0, 2));
    JPanel jpOtherE = new JPanel(new GridLayout(0, 1));

    JLabel jlDefaultSaveLoc = new JLabel(settingsHandler.getCompLangName("mf.mi.default_save_loc"));
    JPanel jpDefaultSaveLoc = new JPanel(new BorderLayout(1, 1));
    JTextField tfDefaultSaveLoc = new JTextField("");
    JButton jbDefaultSaveLoc = new JButton(mainFrame.openFileIcon);
    JFileChooser fcDefaultSaveLoc = new JFileChooser();
    JButton jbDefaultSaveLocReset = new JButton(settingsHandler.getCompLangName("sf.jb.reset"));

    JLabel jlDefaultImportType = new JLabel(settingsHandler.getCompLangName("sf.jl.default_import_type"));
    JComboBox<String> cbDefaultImportType = new JComboBox<>(importTypes);
    JButton jbDefaultImportTypeReset = new JButton(settingsHandler.getCompLangName("sf.jb.reset"));

    public SettingsFrame() {
        this.setSize(600, 360);
        this.setIconImage(mainFrame.appIcon.getImage());
        this.setTitle(settingsHandler.getCompLangName("sf.title.title"));
        this.setResizable(true);
        this.setLocationRelativeTo(mainFrame);
        contentRoot = this.getContentPane();
        contentRoot.setLayout(new BorderLayout(10, 10));

        jbApply.addActionListener(this);
        jbCancel.addActionListener(this);

        jbDefaultSaveLoc.setPreferredSize(new Dimension(60, 0));
        jbDefaultSaveLoc.addActionListener(this);
        jpDefaultSaveLoc.add(tfDefaultSaveLoc, BorderLayout.CENTER);
        jpDefaultSaveLoc.add(jbDefaultSaveLoc, BorderLayout.EAST);

        jcGenerateAsCurrentLang.addActionListener(this);

        switch (settingsHandler.getSettingValue("theme")) {
            case "rich_light" -> cbTheme.setSelectedIndex(1);
            case "dark" -> cbTheme.setSelectedIndex(2);
            case "rich_dark" -> cbTheme.setSelectedIndex(3);
            default -> cbTheme.setSelectedIndex(0);
        }
        boolean fontsIMatch = false;
        for (int i = 0; i < fonts.length; i++) {
            if (fonts[i].equals(settingsHandler.getSettingValue("font"))) {
                cbFont.setSelectedIndex(i);
                fontsIMatch = true;
            }
        }
        if (!fontsIMatch) {
            cbFont.setSelectedIndex(0);
        }
        jsTextSize.setValue(Integer.parseInt(settingsHandler.getSettingValue("size")));

        switch (settingsHandler.getSettingValue("language")) {
            case "Afrikaans" -> cbSelectLanguage.setSelectedIndex(1);
            case "Chinese-Mandarin" -> cbSelectLanguage.setSelectedIndex(2);
            default -> cbSelectLanguage.setSelectedIndex(0);
        }
        jcGenerateAsCurrentLang.setSelected(Boolean.parseBoolean(settingsHandler.getSettingValue("gen_as_cur_lang")));
        generateAsCurrentLang = Boolean.parseBoolean(settingsHandler.getSettingValue("gen_as_cur_lang"));

        tfDefaultSaveLoc.setText(settingsHandler.getSettingValue("defaultsaveloc"));
        switch (settingsHandler.getSettingValue("defaultimporttype")) {
            case "importMC" -> cbDefaultImportType.setSelectedIndex(0);
            case "importMCP" -> cbDefaultImportType.setSelectedIndex(1);
            case "importD" -> cbDefaultImportType.setSelectedIndex(2);
            default -> cbDefaultImportType.setSelectedIndex(3);
        }

        jpAppearanceC.add(jlTheme);
        jpAppearanceC.add(cbTheme);
        jpAppearanceE.add(jbThemeReset);
        jpAppearanceC.add(jlFont);
        jpAppearanceC.add(cbFont);
        jpAppearanceE.add(jbFontReset);
        jpAppearanceC.add(jlTextSize);
        jpAppearanceC.add(jsTextSize);
        jpAppearanceE.add(jbTextSizeReset);

        jpLanguageC.add(jlSelectLanguage);
        jpLanguageC.add(cbSelectLanguage);
        jpLanguageE.add(jbSelectLanguageReset);
        jpLanguageC.add(jlGenerateAsCurrentLang);
        jpLanguageC.add(jcGenerateAsCurrentLang);
        jpLanguageE.add(jbGenerateAsCurrentLangReset);

        jpOtherC.add(jlDefaultSaveLoc);
        jpOtherC.add(jpDefaultSaveLoc);
        jpOtherE.add(jbDefaultSaveLocReset);
        jpOtherC.add(jlDefaultImportType);
        jpOtherC.add(cbDefaultImportType);
        jpOtherE.add(jbDefaultImportTypeReset);

        jbThemeReset.addActionListener(this);
        jbFontReset.addActionListener(this);
        jbTextSizeReset.addActionListener(this);
        jbSelectLanguageReset.addActionListener(this);
        jbGenerateAsCurrentLangReset.addActionListener(this);
        jbDefaultSaveLocReset.addActionListener(this);
        jbDefaultImportTypeReset.addActionListener(this);

        jpAppearanceN.add(jlAppearance);
        jpLanguageN.add(jlLanguage);
        jpOtherN.add(jlOther);

        jpAppearanceS.setPreferredSize(new Dimension(0, 61)); // h - 62 -> add new setting
        jpLanguageS.setPreferredSize(new Dimension(0, 123)); // h - 62 -> add new setting
        jpOtherS.setPreferredSize(new Dimension(0, 123)); // h - 62 -> add new setting

        jpAppearanceE.setPreferredSize(new Dimension(65, 0));
        jpLanguageE.setPreferredSize(new Dimension(65, 0));
        jpOtherE.setPreferredSize(new Dimension(65, 0));

        jpAppearance.add(jpAppearanceN, BorderLayout.NORTH);
        jpLanguage.add(jpLanguageN, BorderLayout.NORTH);
        jpOther.add(jpOtherN, BorderLayout.NORTH);

        jpAppearance.add(jpAppearanceC, BorderLayout.CENTER);
        jpLanguage.add(jpLanguageC, BorderLayout.CENTER);
        jpOther.add(jpOtherC, BorderLayout.CENTER);

        jpAppearance.add(jpAppearanceE, BorderLayout.EAST);
        jpLanguage.add(jpLanguageE, BorderLayout.EAST);
        jpOther.add(jpOtherE, BorderLayout.EAST);

        jpAppearance.add(jpAppearanceS, BorderLayout.SOUTH);
        jpLanguage.add(jpLanguageS, BorderLayout.SOUTH);
        jpOther.add(jpOtherS, BorderLayout.SOUTH);

        tpMenu.addTab(settingsHandler.getCompLangName("sf.tn.appearance"), jpAppearance);
        tpMenu.addTab(settingsHandler.getCompLangName("sf.tn.language"), jpLanguage);
        tpMenu.addTab(settingsHandler.getCompLangName("sf.tn.other"), jpOther);

        jpSouthPanel.add(jbApply);
        jpSouthPanel.add(jbCancel);

        contentRoot.add(tpMenu);
        contentRoot.add(jpSouthPanel, BorderLayout.SOUTH);

        settingsHandler.doCompFonts(contentRoot);

        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbThemeReset) {
            cbTheme.setSelectedIndex(0);
        } else if (e.getSource() == jbFontReset) {
            cbFont.setSelectedIndex(0);
        } else if (e.getSource() == jbTextSizeReset) {
            jsTextSize.setValue(12);
        } else if (e.getSource() == jbSelectLanguageReset) {
            cbSelectLanguage.setSelectedIndex(0);
        } else if (e.getSource() == jbGenerateAsCurrentLangReset) {
            jcGenerateAsCurrentLang.setSelected(false);
        } else if (e.getSource() == jcGenerateAsCurrentLang) {
            generateAsCurrentLang = jcGenerateAsCurrentLang.isSelected();
        } else if (e.getSource() == jbDefaultSaveLocReset) {
            tfDefaultSaveLoc.setText("");
        } else if (e.getSource() == jbDefaultImportTypeReset) {
            cbDefaultImportType.setSelectedIndex(3);
        }

        else if (e.getSource() == jbDefaultSaveLoc) {
            tfDefaultSaveLoc.setText(Statics.selectDir(this, fcDefaultSaveLoc, "MCSkinner: Choose default path of generation"));
        } else if (e.getSource() == jbCancel) {
            this.dispose();
        } else if (e.getSource() == jbApply) {
            String[] themesForS = {
                    "light",
                    "rich_light",
                    "dark",
                    "rich_dark"
            };

            settingsHandler.setSetting("theme", themesForS[cbTheme.getSelectedIndex()]);
            settingsHandler.setSetting("font", fonts[cbFont.getSelectedIndex()]);
            settingsHandler.setSetting("size", String.valueOf((int) jsTextSize.getValue()));
            settingsHandler.setSetting("language", languages[cbSelectLanguage.getSelectedIndex()]);
            settingsHandler.setSetting("gen_as_cur_lang", Boolean.toString(generateAsCurrentLang));
            settingsHandler.setSetting("defaultsaveloc", tfDefaultSaveLoc.getText());
            settingsHandler.setSetting("defaultimporttype", switch (cbDefaultImportType.getSelectedIndex()) {
                case 0 -> "importMC";
                case 1 -> "importMCP";
                case 2 -> "importD";
                default -> "";
            });

            this.dispose();
            mainFrame.settingsChangesMessage();
        }
    }
}
