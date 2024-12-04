package com.redfox.mcskinner;

import java.util.ArrayList;
import java.util.HashMap;

public class SkinPackGen {
    private ArrayList<HashMap<String, Object>> skins;
    private String name;
    public SkinPackGen(ArrayList<HashMap<String, Object>> skins, String name) {
        this.skins = skins;
        this.name = name;
    }
}
