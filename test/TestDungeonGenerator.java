
import engine.DungeonGenerator;
import engine.LevelGenerator;
import io.DungeonFileReader;
import io.DungeonFileWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import map.Dungeon;

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

/**
 *
 * @author Kurumin
 */
public class TestDungeonGenerator {
    
    public static void main(String args[]){
        try {
            LevelGenerator.folder = "D:\\Kurumin\\Documents\\NetBeansProjects\\TIoDClient\\data\\levels\\";
            DungeonGenerator.folder = "D:\\Kurumin\\Documents\\NetBeansProjects\\TIoDClient\\data\\dungeons\\";
            
            String dungeonID = "Testing Dungeon";
            
            DungeonGenerator dg = new DungeonGenerator(dungeonID, 3, DungeonGenerator.RANDOM_LINE);
            Thread thread = new Thread(dg);
            thread.start();
            thread.join();
            
            Dungeon dungeon = dg.getGeneratedDungeon();
            DungeonFileWriter dfw = new DungeonFileWriter(dungeon);
            
            String path = DungeonGenerator.folder + dungeon.getId()+".json";
            
            dfw.exportJSONData(path);
            
            DungeonFileReader dfr = new DungeonFileReader(path);
            Dungeon readDungeon = dfr.parseJson();
            System.out.println(readDungeon);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(TestDungeonGenerator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
