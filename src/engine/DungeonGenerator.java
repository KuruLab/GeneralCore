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
    
    public static final int
            RANDOM_LINE = 1,
            RANDOM_TREE = 2;
    
    public static String folder = "." + File.separator + "data" + File.separator + "dungeons" + File.separator + "";
    
    private String dungeonName;
    private int maxTiers;
    private int method;
    private boolean finished = false;
    
    private Dungeon generatedDungeon;

    public DungeonGenerator(String dungeonName, int maxTiers, int method) {
        this.dungeonName = dungeonName;
        this.maxTiers = maxTiers;
        this.method = method;
    }
    
    @Override
    public void run() {
        finished = false;
        switch(method){
            case DungeonGenerator.RANDOM_LINE:
                randomLineGenerator();
                break;
            case DungeonGenerator.RANDOM_TREE:
                randomTreeGenerator();
                break;    
            default:
                randomLineGenerator();
        }
        finished = true;
    }
    
    private void randomLineGenerator(){
        List<Level> levelList = getAllLevelsList();
        Collections.shuffle(levelList);
        
        generatedDungeon = new Dungeon(dungeonName);
        
        int currentTier = 1;
        Tree<Level> tree = null;
        while(currentTier <= maxTiers && !levelList.isEmpty()){
            
            Level level = levelList.remove(0);
            level.setTier(currentTier);  
            System.out.println("Adding "+level.getId()+" to the dungeon.");
            if(currentTier == 1){
                tree = new Tree<>(level);
            }
            else{
                tree = tree.setAsParent(level);
            }
            
            if(levelList.isEmpty()){
                System.out.println("No more levels.");
            }
            else if(currentTier == maxTiers){
                System.out.println("No more tiers.");
            }
            currentTier++;
        }
        generatedDungeon.setLevels(tree);
        fixDungeonStairs(generatedDungeon);
        System.out.println(generatedDungeon);
    }
    
    private void randomTreeGenerator(){
        // not today...
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
    
    public void fixDungeonStairs(Dungeon dungeon){
        Tree<Level> tree = dungeon.getLevels();
        recursiveFix(tree);
    }
    
    private void recursiveFix(Tree<Level> tree){
        Level level = tree.getHead();
        System.out.println("Fixing "+level.getId());
        
        Tree<Level> parentTree = tree.getParent();
        if(parentTree != null){ // if is not the root
            Level parent = parentTree.getHead();
            
            Room bossRoom = level.getBoss();
            System.out.println("Room "+level.getStart().getId()+" is the start.");
            System.out.println("Room "+bossRoom.getId()+" is the boss.");
            
            // add the key to stairs
            Condition condition = bossRoom.getCondition();
            Symbol stairsKey = new Symbol(condition.getKeyLevel()+1);
            bossRoom.addSymbol(stairsKey);
            
            // create a new room -> add the stairs room
            Room stairsRoom = new Room("s");
            stairsRoom.setCondition(new Condition(stairsKey));
            
            // link the rooms with a door
            Door toStairs = new Door("to stairs");
            toStairs.setA(bossRoom);
            toStairs.setB(stairsRoom);
            toStairs.setCondition(new Condition(stairsKey));
            bossRoom.addDoor(toStairs);
            stairsRoom.addDoor(toStairs);
            
            // create the stairs linking the levels
            Stair stair = new Stair("\""+level.getId()+"\":\""+stairsRoom.getId()+"\", \""+parent.getId()+"\":\""+parent.getStart().getId()+"\"");
            stair.setLower(level);
            stair.setUpper(parent);
            stair.setFrom(stairsRoom);
            stair.setTo(parent.getStart()); // fix this latter?
            level.addStair(stair);
            parent.addStair(stair);
            System.out.println("Stair created: "+stair);
        }
        for(Tree<Level> leaf : tree.getSubTrees()){
            recursiveFix(leaf);
        }
        
        

        

        

        
                
                
        
    }

    public String getDungeonName() {
        return dungeonName;
    }

    public void setDungeonName(String dungeonName) {
        this.dungeonName = dungeonName;
    }

    public int getMaxTiers() {
        return maxTiers;
    }

    public void setMaxTiers(int maxTiers) {
        this.maxTiers = maxTiers;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public boolean isFinished() {
        return finished;
    }

    public void setFinished(boolean finished) {
        this.finished = finished;
    }

    public Dungeon getGeneratedDungeon() {
        return generatedDungeon;
    }

    public void setGeneratedDungeon(Dungeon generatedDungeon) {
        this.generatedDungeon = generatedDungeon;
    }
    
}
