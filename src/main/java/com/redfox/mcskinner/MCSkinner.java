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

        String json = new SkinPackGen(test,"test").genSkinsJSON();
        System.out.println(json);
    }
}
