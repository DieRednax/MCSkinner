package com.redfox.mcskinner;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

public class SettingsHandler {
    private File settingsJson;

    private HashMap<String, String> settings;
    private HashMap<String, String> languageModules;

    public SettingsHandler() {
        settings = new HashMap<>();
        languageModules = new HashMap<>();
        try { settingsJson = new File(new File(SettingsHandler.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath()).getParent(), "settings.json"); } catch (URISyntaxException e) { e.printStackTrace(); }
    }

    public void initialiseJSONFiles() {
        try {
            if (settingsJson.exists()) {
                Gson gson = new Gson();
                FileReader fr = new FileReader(settingsJson);
                settings = gson.fromJson(fr, HashMap.class);
            } else {
                settings.put("theme", "light");
                settings.put("defaultsaveloc", "");
                settings.put("language", "English");
                settings.put("font", "Open Sans");
                settings.put("size", "12");
                settings.put("gen_as_cur_lang", "false");
                settings.put("defaultimporttype", "");
                updateSettingsJson();
            }

            InputStream languageJSONIn = getClass().getClassLoader().getResourceAsStream("mcskinner/support_files/" + settings.get("language") + ".json");

            if (languageJSONIn != null) {
                Gson gsonLanguageModules = new Gson();
                InputStreamReader ir = new InputStreamReader(languageJSONIn, StandardCharsets.UTF_8);
                languageModules = gsonLanguageModules.fromJson(ir, HashMap.class);

                languageJSONIn.close();
            } else {
                JOptionPane.showMessageDialog(null, "No language file found", "MCSkinner: error", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
        } catch (IOException e) {
            System.err.println("Stacktrace: " + e.getMessage());
        }
    }

    //setters and getters
    public void setSetting(String settingKey, String settingValue) {
        settings.put(settingKey, settingValue);
        updateSettingsJson();
    }
    public String getSettingValue(String settingKey) {
        return settings.get(settingKey);
    }

    //common funcs
    public void doCompFonts(Container container) {
        for (Component jTextComponent : Statics.getAllNamedComponents(container)) {
            jTextComponent.setFont(new Font(this.getSettingValue("font"), Font.PLAIN, Integer.parseInt(this.getSettingValue("size"))));
        }
    }

    //lang setters and getters
    public String getCompLangName(String languageKey) {
        return languageModules.get(languageKey);
    }
    public void setCompLangName(Component nameableComp, String languageKey) {
        nameableComp.setName(languageModules.get(languageKey));
    }

    //privates
    private void updateSettingsJson() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try (FileWriter fw = new FileWriter(settingsJson)) {
            gson.toJson(settings, fw);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
