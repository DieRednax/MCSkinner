package com.redfox.mcskinner;

import java.util.ArrayList;
import java.util.HashMap;

public class MCSkinner {
    public static void main(String[] args) {
        HashMap<String, String> testSkin = new HashMap<>();
            testSkin.put("name", "test Skin");
            testSkin.put("geo", "classic");
            testSkin.put("texture", "C:\\Users\\<user>\\OneDrive\\Minecraft\\Resource packs\\Custom Capes By BionicBen.zip\\Custom Capes By BionicBen\\bionic.png"); //replace <user>
            testSkin.put("cape", "C:\\Users\\<user>\\OneDrive\\Minecraft\\Resource packs\\Custom Capes By BionicBen.zip\\Custom Capes By BionicBen\\Bee.png"); //replace <user>
        ArrayList<HashMap<String, String>> test = new ArrayList<>();
        test.add(testSkin);
        test.add(testSkin);

        SkinPackGen testPackGen = new SkinPackGen(test,"test");

        String json = testPackGen.genSkinsJSON();
        System.out.println("skins.json: \n" + json + "\n");

        String lang = testPackGen.genDefLangFile();
        System.out.println("en_US.lang: \n" + lang + "\n");

        String langJSON = testPackGen.genLangJSON();
        System.out.println("languages.json: \n" + langJSON);
    }
}
