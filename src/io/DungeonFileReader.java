/*
 * Copyright (C) 2018 Kurumin
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package io;

import engine.LevelGenerator;
import engine.Tree;
import java.io.File;
import java.util.HashMap;
import java.util.logging.Logger;
import map.Dungeon;
import map.Level;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author Kurumin
 */
public class DungeonFileReader extends RawFileReader {
    
    private HashMap<String, Level> loadedLevels;
    
    public DungeonFileReader(String _filename) {
        super(_filename);
        this.loadedLevels = new HashMap<>();
    }
    
    public Dungeon parseJson(){
        String dungeonID = "unamed dungeon";
        Dungeon dungeon = new Dungeon(dungeonID);
        try {
            String rawJSon = readRawString();
            System.out.println("Parsing \""+filename+"\"");
            //System.out.println(rawJSon);
            JSONParser parser = new JSONParser(); 
            JSONObject obj = (JSONObject) parser.parse(rawJSon);
            
            dungeonID = (String) obj.get("dungeonID");
            dungeon.setId(dungeonID);
            
            Tree<Level> tree = null;
            
            JSONArray levelArray = (JSONArray) obj.get("levels");
            for(int i = 0; i < levelArray.size(); i++){
                JSONObject levelObj = (JSONObject) levelArray.get(i);
                String levelID = (String) levelObj.get("levelID");
                load(levelID);
                Level level = loadedLevels.get(levelID);
                if(tree == null){
                    tree = new Tree<>(level);
                }
                
                String parentID = (String) levelObj.get("parent");
                if(!parentID.equals("null")){
                    load(parentID);
                    Level parent = loadedLevels.get(parentID);
                    tree.addLeaf(parent, level);
                }
                
                Long tier = ((Long) levelObj.get("tier"));
                level.setTier(tier.intValue());
                
                JSONArray leafArray = (JSONArray) obj.get("leafs");
                if(leafArray != null){
                    for(int j = 0; j < leafArray.size(); j++){
                        String leafID = (String) leafArray.get(j);
                        load(leafID);
                        tree.addLeaf(level, loadedLevels.get(leafID));
                    }
                }
            }
            dungeon.setLevels(tree);

        } catch (ParseException ex) {
            Logger.getLogger(DungeonFileReader.class.getName(), ex.getMessage());
        }
        return dungeon;
    }
    
    public void load(String levelID) {
        if (!loadedLevels.containsKey(levelID)) {
            String dirName = LevelGenerator.folder + levelID + File.separator;
            File dirFile = new File(dirName);
            if (dirFile.isDirectory()) {
                File mapFile = new File(dirFile, "map_" + levelID + ".json");
                System.out.print("Checking if " + levelID + " is valid... ");
                if (mapFile.exists()) {
                    System.out.println("ok!");
                    LevelFileReader levelReader = new LevelFileReader(mapFile.getPath());
                    Level level = levelReader.parseJson();
                    loadedLevels.put(levelID, level);
                } else {
                    System.err.println("warning: " + levelID + " is not a valid folder.");
                }
            }
        }
    }
    
}
