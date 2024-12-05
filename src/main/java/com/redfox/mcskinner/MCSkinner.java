package com.redfox.mcskinner;

import com.redfox.mcskinner.ui.MainFrame;

import java.util.ArrayList;
import java.util.HashMap;

public class MCSkinner {
    public static void main(String[] args) {

        new MainFrame();

        /*
        HashMap<String, String> testSkin = new HashMap<>();
            testSkin.put("name", "test Skin");
            testSkin.put("geo", "classic");
            testSkin.put("texture", "src\\main\\resources\\test\\bionic.png");
            testSkin.put("cape", "src\\main\\resources\\test\\Bee.png");
        ArrayList<HashMap<String, String>> test = new ArrayList<>();
        test.add(testSkin);
        test.add(testSkin);

        SkinPackGen testPackGen = new SkinPackGen(test,
                "test s_1", "RedFox - Die Rednax","test","1, 0, 0", "1, 21",
                "src\\main\\resources\\test");

        String json = testPackGen.genSkinsJSON();
        System.out.println("skins.json: \n" + json + "\n");

        String lang = testPackGen.genDefLangFile();
        System.out.println("en_US.lang: \n" + lang + "\n");

        String langJSON = testPackGen.genLangJSON();
        System.out.println("languages.json: \n" + langJSON + "\n");

        String manifestJSON = testPackGen.genManifestJSON();
        System.out.println("manifest.json: \n" + manifestJSON);
        testPackGen.genSkinPackFiles();
*/

    }
}
