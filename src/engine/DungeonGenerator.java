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
package engine;

import io.LevelFileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import map.Door;
import map.Dungeon;
import map.Level;
import map.Room;
import map.Stair;
import puzzle.Condition;
import puzzle.Symbol;

/**
 *
 * @author Kurumin
 */
public class DungeonGenerator implements Runnable{    
    
    public static String folder = "." + File.separator + "data" + File.separator + "dungeons" + File.separator + "";
    private String dungeonName;
    private int maxTiers = 1;
            
    @Override
    public void run() {
        List<Level> levelList = getAllLevelsList();
        Collections.shuffle(levelList);
        
        Dungeon dungeon = new Dungeon(dungeonName);
        
        int currentTier = 1;
        while(currentTier <= maxTiers && !levelList.isEmpty()){
            
            if(currentTier > 1){
                // adjust the level stair to allow backtrack
            }
            
            Level level = levelList.remove(0);
            System.out.println("Working on "+level.getId());
            level.setTier(currentTier);
            
            Room bossRoom = level.getBoss();
            System.out.println("Room "+bossRoom.getId()+" is the boss.");
            Condition condition = bossRoom.getCondition();
            Symbol stairsKey = new Symbol(condition.getKeyLevel()+1);
            bossRoom.addSymbol(stairsKey);
            
            Room stairsRoom = new Room("s");
            stairsRoom.setCondition(new Condition(stairsKey));
            
            Door toStairs = new Door("to stairs");
            toStairs.setA(bossRoom);
            toStairs.setB(stairsRoom);
            toStairs.setCondition(new Condition(stairsKey));
            
            bossRoom.addDoor(toStairs);
            stairsRoom.addDoor(toStairs);
            
            if(levelList.isEmpty()){
                System.out.println("No more levels.");
            }
            else if(currentTier == maxTiers){
                System.out.println("No more tiers.");
            }
            else{
                Level nextLevel = levelList.get(0);
                Stair stair = new Stair(level.getId()+" - "+nextLevel.getId());
                stair.setLower(nextLevel);
                stair.setUpper(level);
                stair.setFrom(stairsRoom);
                stair.setTo(nextLevel.getStart()); // fix this
                level.addStair(stair);
            }
            currentTier++;
        }
    }
    
    private List<Level> getAllLevelsList(){
        ArrayList<Level> levelList = new ArrayList<>();
        
        File levelDirectory = new File(LevelGenerator.folder);
        File[] levelArray = levelDirectory.listFiles();
        
        System.out.println("Loading level list...");
        
        if (levelArray.length > 0) {
            for (int i = 0; i < levelArray.length; i++) {
                if(levelArray[i].isDirectory()){
                    File mapFile = new File(levelArray[i], "map_"+levelArray[i].getName()+".json");
                    System.out.print("Checking if \""+levelArray[i]+"\\\" is valid... ");
                    if(mapFile.exists()){
                        System.out.println("ok!");
                        LevelFileReader levelReader = new LevelFileReader(mapFile.getPath());
                        Level level = levelReader.parseJson();
                        levelList.add(level);
                    }
                    else{
                        System.err.println("warning: "+levelArray[i]+" is not a valid folder.");
                    }
                }
            }
        }
        return levelList;
    }
    
}
