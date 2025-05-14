package com.redfox.mcskinner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class SkinPackGen {
    private ArrayList<HashMap<String, String>> skins;
    private String name;
    private String author;
    private String description;
    private String version;
    private String mcVersion;
    private String genDirectory;
    private String namePref;
    private ArrayList<String> skinNames;
    private ArrayList<String> images;
    private String langFileName;
    public SkinPackGen(ArrayList<HashMap<String, String>> skins,
                       String name, String author, String description, String version, String mcVersion,
                       String genDirectory,
                       String langFileName) {
        this.skins = skins;
        this.name = name;
        this.author = author;
        this.description = description;
        this.version = version;
        this.mcVersion = mcVersion;
        this.genDirectory = genDirectory;

        this.namePref = "RF_mcs__nr_" + name.replaceAll("\\s", "_").substring(0, 3);
        this.skinNames = new ArrayList<>();
        this.images = new ArrayList<>();

        this.langFileName = langFileName;
    }
    public void genSkinPackFiles() {
        String skinsJSON = genSkinsJSON();
        String langJSON = genLangJSON();
        String defLang = genDefLangFile();
        String manifestJSON = genManifestJSON();

        File skinsJSONF = new File(genDirectory + "/" + name + "/skins.json");
        File langJSONF = new File(genDirectory + "/" + name + "/texts/languages.json");
        File defLangF = new File(genDirectory + "/" + name + "/texts/" + langFileName);
        File manifestJSONF = new File(genDirectory + "/" + name + "/manifest.json");

        writeFile(skinsJSON, skinsJSONF);
        writeFile(langJSON, langJSONF);
        writeFile(defLang, defLangF);
        writeFile(manifestJSON, manifestJSONF);

        for (String image : images) {
            Path sourcePath = Paths.get(image);
            Path destinationPath = Paths.get(genDirectory + "/" + name) ;

            copyFile(sourcePath, destinationPath);
        }
    }
    public String genManifestJSON() {
        StringBuilder manifestJSON = new StringBuilder();
        manifestJSON.append("{\n");

        manifestJSON.append(
                        "\t\"format_version\": 2,\n" +
                        "\t\"metadata\": {\n" +
                        "\t\t\"authors\": [\n" +
                        "\t\t\t\"MCSkinner user: " + author + "\"\n" +
                        "\t\t],\n" +
                        "\t\t\"generated_with\": {\n" +
                        "\t\t\t\"MCSkinner\": [\n" +
                        "\t\t\t\t\"Made by RedFox\"\n" +
                        "\t\t\t]\n" +
                        "\t\t}\n" +
                        "\t},\n" +
                        "\t\"header\": {\n" +
                        "\t\t\"name\": \"" + name + "\",\n" +
                        "\t\t\"description\": \"" + description + " - generated with MCSkinner (by RedFox)\",\n" +
                        "\t\t\"min_engine_version\": [" + mcVersion + "],\n" +
                        "\t\t\"uuid\": \"" + UUID.randomUUID() + "\",\n" +
                        "\t\t\"version\": [" + version + "]\n" +
                        "\t},\n" +
                        "\t\"modules\": [\n" +
                        "\t\t{\n" +
                        "\t\t\t\"type\": \"skin_pack\",\n" +
                        "\t\t\t\"uuid\": \"" + UUID.randomUUID() + "\",\n" +
                        "\t\t\t\"version\": [" + version + "]\n" +
                        "\t\t}\n" +
                        "\t]\n"
        );

        manifestJSON.append("}");

        return manifestJSON.toString();
    }
    public String genSkinsJSON() {
        StringBuilder skinsJSON = new StringBuilder();
        int cursor = 0;

        skinsJSON.append("{\n");
        skinsJSON.append(
                appendStatement("\t", "geometry", "skinpacks/skins.json")
        ).append(",\n");

        skinsJSON.append("\t\"skins\": [\n");
        for (HashMap<String, String> skin : skins) {
            cursor++;

            skinsJSON.append("\t\t{\n");

            for (String key : skin.keySet()) {
                switch (key) {
                    case "name":
                        skinsJSON.append(
                                appendStatement("\t\t\t","localization_name",
                                        skin.get(key).toLowerCase().replaceAll("\\s", "_"))
                        ).append(",\n");

                        skinNames.add(skin.get(key));
                        break;
                    case "geo":
                        String geo;

                        if (skin.get(key).equals("classic")) {
                            geo = "geometry.humanoid.custom";
                        } else if (skin.get(key).equals("slim")) {
                            geo = "geometry.humanoid.customSlim";
                        } else geo = skin.get(key);

                        skinsJSON.append(
                                appendStatement("\t\t\t","geometry", geo)
                        ).append(",\n");
                        break;
                    case "texture":
                        skinsJSON.append(
                                appendStatement("\t\t\t","texture",
                                        skin.get(key).replace(new File(skin.get(key)).getParent(), "")
                                                .replace("/", "")
                                                .replace("\\", ""))
                        ).append(",\n");

                        images.add(skin.get(key));
                        break;
                    case "cape":
                        skinsJSON.append(
                                appendStatement("\t\t\t","cape",
                                        skin.get(key).replace(new File(skin.get(key)).getParent(), "")
                                                .replace("/", "")
                                                .replace("\\", ""))
                        ).append(",\n");

                        images.add(skin.get(key));
                        break;
                }
            }
            skinsJSON.append(
                    appendStatement("\t\t\t","type", "free")
            ).append("\n");

            if (cursor < skins.size()) {
                skinsJSON.append("\t\t},\n");
            } else if (cursor == skins.size()) {
                skinsJSON.append("\t\t}\n");
            }
        }
        skinsJSON.append("\t],\n");

        skinsJSON.append(
                appendStatement("\t", "serialize_name", namePref)
        ).append(",\n");
        skinsJSON.append(
                appendStatement("\t", "localization_name", namePref)
        ).append("\n");
        skinsJSON.append("}");

        return skinsJSON.toString();
    }
    public String genDefLangFile() {
        StringBuilder langFile = new StringBuilder();

        langFile.append("skinpack." + namePref + "=" + name).append("\n");
        for (String skinName : skinNames) {
            langFile.append("skin." + namePref + "." + skinName.toLowerCase().replaceAll("\\s", "_") + "=" + skinName).append("\n");
        }

        return langFile.toString();
    }
    public String genLangJSON() {
        return " [\n\t\"" + langFileName.replaceAll("\\.lang", "") + "\"\n]";
    }

    private void writeFile(String input, File file) {
        File parentDirectory = file.getParentFile();
        if (parentDirectory != null && !parentDirectory.exists()) {
            parentDirectory.mkdirs();
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file))) {
            bw.write(input);
        } catch (IOException e) {
            System.err.println("Error writing to file " + file + ": " + e.getMessage());
        }
    }
    private void copyFile(Path sourcePath, Path destinationPath) {
        try {
            Files.copy(sourcePath, destinationPath.resolve(sourcePath.getFileName()), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            System.err.println("Error copying file " + sourcePath + " to destination " + destinationPath + ": " + e.getMessage());
            e.printStackTrace();
        }
    }

    //json gen
    private String appendStatement( String tabsPref, String left, String right) {
        return tabsPref + "\"" + left + "\": \"" + right + "\"";
    }
    private <T> String appendStatementRightNonSTR(String tabsPref, String left, T right) {
        return tabsPref + "\"" + left + "\": " + right;
    }
}
