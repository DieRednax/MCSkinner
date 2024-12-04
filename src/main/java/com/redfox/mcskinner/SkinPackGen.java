package com.redfox.mcskinner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SkinPackGen {
    private ArrayList<HashMap<String, String>> skins;
    private String name;
    private String namePref;
    public SkinPackGen(ArrayList<HashMap<String, String>> skins, String name) {
        this.skins = skins;
        this.name = name;

        this.namePref = "RF_mcs_nr_" + name.replaceAll("\\s", "_").substring(0, 3);
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
                        break;
                    case "cape":
                        skinsJSON.append(
                                appendStatement("\t\t\t","cape",
                                        skin.get(key).replace(new File(skin.get(key)).getParent(), "")
                                                .replace("/", "")
                                                .replace("\\", ""))
                        ).append(",\n");
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

    //json gen
    private String appendStatement( String tabsPref, String left, String right) {
        return tabsPref + "\"" + left + "\": \"" + right + "\"";
    }
}
