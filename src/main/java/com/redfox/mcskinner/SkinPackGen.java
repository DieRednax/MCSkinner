package com.redfox.mcskinner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class SkinPackGen {
    private ArrayList<HashMap<String, String>> skins;
    private String name;
    public SkinPackGen(ArrayList<HashMap<String, String>> skins, String name) {
        this.skins = skins;
        this.name = name;
    }
    public String genSkinsJSON() {
        StringBuilder skinsJSON = new StringBuilder();
        skinsJSON.append("[\n");

        for (HashMap<String, String> skin : skins) {
            skinsJSON.append("\t{\n");

            int cursor = 0;
            for (String key : skin.keySet()) {
                cursor++;
                switch (key) {
                    case "name":
                        skinsJSON.append(
                                appendStatement("localization_name",
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
                                appendStatement("geometry", geo)
                        ).append(",\n");
                        break;
                    case "texture":
                        skinsJSON.append(
                                appendStatement("texture",
                                        skin.get(key).replace(new File(skin.get(key)).getParent(), "")
                                                .replace("/", "")
                                                .replace("\\", ""))
                        ).append(",\n");
                        break;
                    case "cape":
                        skinsJSON.append(
                                appendStatement("cape",
                                        skin.get(key).replace(new File(skin.get(key)).getParent(), "")
                                                .replace("/", "")
                                                .replace("\\", ""))
                        ).append(",\n");
                        break;
                }

//                if (cursor < skin.keySet().size()) {
//                    skinsJSON.append(",\n");
//                } else if (cursor == skin.keySet().size()) {
//                    skinsJSON.append("\n");
//                }
            }
            skinsJSON.append(
                    appendStatement("type", "free")
            ).append("\n");
        }

        skinsJSON.append("\t},\n");

        skinsJSON.append("]");

        return skinsJSON.toString();
    }

    //json gen
    private String appendStatement(String left, String right) {
        return "\t\t\"" + left + "\": \"" + right + "\"";
    }
}
