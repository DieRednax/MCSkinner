package com.redfox.mcskinner.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static com.redfox.mcskinner.MCSkinner.mainFrame;

public class SettingsFrame extends JFrame implements ActionListener {
    Container contentRoot;

    String[] themes = {
            languageModule("mf.jm.light"), languageModule("mf.mi.light_extra"),
            languageModule("mf.jm.dark"), languageModule("mf.mi.dark_extra")
    };
    String [] languages = {
            "English",
            "Afrikaans",
            "Chinese-Mandarin"
    };

    JButton jbCancel = new JButton(languageModule("sf.jb.cancel"));
    JButton jbApply = new JButton(languageModule("sf.jb.apply"));

    JPanel jpSouthPanel = new JPanel();

    JTabbedPane tpMenu = new JTabbedPane(JTabbedPane.LEFT);

    JPanel jpAppearance = new JPanel(new BorderLayout(10, 10));
    JPanel jpLanguage = new JPanel(new BorderLayout(10, 10));
    JPanel jpOther = new JPanel(new BorderLayout(10, 10));

    JPanel jpAppearanceN = new JPanel();
    JPanel jpLanguageN = new JPanel();
    JPanel jpOtherN = new JPanel();

    JPanel jpAppearanceS = new JPanel();
    JPanel jpLanguageS = new JPanel();
    JPanel jpOtherS = new JPanel();

    JLabel jlAppearance = new JLabel(languageModule("sf.jl.appearance"));
    JLabel jlLanguage = new JLabel(languageModule("sf.jl.language"));
    JLabel jlOther = new JLabel(languageModule("sf.jl.other"));

    //jpAppearance
    JPanel jpAppearanceC = new JPanel(new GridLayout(0, 2));
    JPanel jpAppearanceE = new JPanel(new GridLayout(0, 1));

    JLabel jlTheme = new JLabel(languageModule("sf.jl.theme"));
    JComboBox<String> cbTheme = new JComboBox<>(themes);

    JButton jbThemeReset = new JButton(languageModule("sf.jb.reset"));

    //jpLanguage
    JPanel jpLanguageC = new JPanel(new GridLayout(0, 2));
    JPanel jpLanguageE = new JPanel(new GridLayout(0, 1));

    JLabel jlSelectLanguage = new JLabel(languageModule("sf.jl.language"));
    JComboBox<String> cbSelectLanguage = new JComboBox<>(languages);

    JButton jbSelectLanguageReset = new JButton(languageModule("sf.jb.reset"));

    //jpOther
    JPanel jpOtherC = new JPanel(new GridLayout(0, 2));
    JPanel jpOtherE = new JPanel(new GridLayout(0, 1));

    JLabel jlDefaultSaveLoc = new JLabel(languageModule("mf.mi.default_save_loc"));
    JPanel jpDefaultSaveLoc = new JPanel(new BorderLayout(1, 1));
    JTextField tfDefaultSaveLoc = new JTextField("");
    JButton jbDefaultSaveLoc = new JButton(mainFrame.openFileIcon);
    JFileChooser fcDefaultSaveLoc = new JFileChooser();

    JButton jbDefaultSaveLocReset = new JButton(languageModule("sf.jb.reset"));

    public SettingsFrame() {
        this.setSize(600, 360);
        this.setIconImage(mainFrame.appIcon.getImage());
        this.setTitle(languageModule("sf.title.title"));
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

        switch (mainFrame.settings.get("theme")) {
            case "rich_light":
                cbTheme.setSelectedIndex(1);
                break;
            case "dark":
                cbTheme.setSelectedIndex(2);
                break;
            case "rich_dark":
                cbTheme.setSelectedIndex(3);
                break;
            default:
                cbTheme.setSelectedIndex(0);
        }

        switch (mainFrame.settings.get("language")) {
            case "Afrikaans":
                cbSelectLanguage.setSelectedIndex(1);
                break;
            case "Chinese-Mandarin":
                cbSelectLanguage.setSelectedIndex(2);
                break;
            default:
                cbSelectLanguage.setSelectedIndex(0);
        }

        tfDefaultSaveLoc.setText(mainFrame.settings.get("defaultsaveloc"));

        jpAppearanceC.add(jlTheme);
        jpAppearanceC.add(cbTheme);
        jpAppearanceE.add(jbThemeReset);

        jpLanguageC.add(jlSelectLanguage);
        jpLanguageC.add(cbSelectLanguage);
        jpLanguageE.add(jbSelectLanguageReset);

        jpOtherC.add(jlDefaultSaveLoc);
        jpOtherC.add(jpDefaultSaveLoc);
        jpOtherE.add(jbDefaultSaveLocReset);

        jbThemeReset.addActionListener(this);
        jbSelectLanguageReset.addActionListener(this);
        jbDefaultSaveLocReset.addActionListener(this);

        jpAppearanceN.add(jlAppearance);
        jpLanguageN.add(jlLanguage);
        jpOtherN.add(jlOther);

        jpAppearanceS.setPreferredSize(new Dimension(0, 185)); // h - 62 -> add new setting
        jpLanguageS.setPreferredSize(new Dimension(0, 185)); // h - 62 -> add new setting
        jpOtherS.setPreferredSize(new Dimension(0, 185)); // h - 62 -> add new setting

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

        tpMenu.addTab(languageModule("sf.tn.appearance"), jpAppearance);
        tpMenu.addTab(languageModule("sf.tn.language"), jpLanguage);
        tpMenu.addTab(languageModule("sf.tn.other"), jpOther);

        jpSouthPanel.add(jbApply);
        jpSouthPanel.add(jbCancel);

        contentRoot.add(tpMenu);
        contentRoot.add(jpSouthPanel, BorderLayout.SOUTH);
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == jbThemeReset) {
            cbTheme.setSelectedIndex(0);
        } else if (e.getSource() == jbSelectLanguageReset) {
            cbSelectLanguage.setSelectedIndex(0);
        } else if (e.getSource() == jbDefaultSaveLocReset) {
            tfDefaultSaveLoc.setText("");
        } else if (e.getSource() == jbDefaultSaveLoc) {
            tfDefaultSaveLoc.setText(mainFrame.selectDir(fcDefaultSaveLoc, "MCSkinner: Choose default path of generation"));
        } else if (e.getSource() == jbCancel) {
            this.dispose();
        } else if (e.getSource() == jbApply) {
            String[] themesForS = {
                    "light",
                    "rich_light",
                    "dark",
                    "rich_dark"
            };

            mainFrame.settings.put("theme", themesForS[cbTheme.getSelectedIndex()]);
            mainFrame.settings.put("language", languages[cbSelectLanguage.getSelectedIndex()].toLowerCase().replaceAll("\\s", "_"));
            mainFrame.settings.put("defaultsaveloc", tfDefaultSaveLoc.getText());

            mainFrame.updateSettingsJson(mainFrame.settings, "settings.json");
            mainFrame.settingsChangesMessage();
            this.dispose();
        }
    }
    private String languageModule(String key) {
        return mainFrame.languageModules.get(key);
    }
}
