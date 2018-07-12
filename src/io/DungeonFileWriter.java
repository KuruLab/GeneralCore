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

import util.Tree;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Logger;
import map.Dungeon;
import map.Level;

/**
 *
 * @author Kurumin
 */
public class DungeonFileWriter {
     
    private Dungeon dungeon;

    public DungeonFileWriter(Dungeon dungeon) {
        this.dungeon = dungeon;
    }
    
    public void exportJSONData(String path){
        
        Tree<Level> tree = dungeon.getLevels();
        try {
            PrintWriter pw = new PrintWriter(path);
            pw.printf("{%n");

            pw.printf("\"dungeonID\": \"%s\",%n", dungeon.getId());
            pw.printf("\"levels\": [%n");
            
            recursiveLevelPrint(pw, tree);
            
            pw.printf("],%n");
            pw.printf("}%n");
            pw.flush();
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(getClass().getName(), ex.getMessage());
        }
    }
    
    public void recursiveLevelPrint(PrintWriter pw, Tree<Level> tree){
         printLevelInformation(pw, tree);
         List<Tree<Level>> subtrees = (List<Tree<Level>>) tree.getSubTrees();
         for(Tree<Level> subtree : subtrees){
             recursiveLevelPrint(pw, subtree);
         }
    }
    
    public void printLevelInformation(PrintWriter pw, Tree<Level> tree){
        pw.printf("\t{%n");
        pw.printf("\t\"levelID\": \"%s\",%n", tree.getHead().getId());
        pw.printf("\t\"tier\": %s,%n", tree.getHead().getTier());
        //pw.printf("\t{%n");
        Tree parent = tree.getParent();
        if(parent == null)
            pw.printf("\t\"parent\": \"null\",%n");
        else
            pw.printf("\t\"parent\": \"%s\",%n", tree.getParent().getHead().getId());
        pw.printf("\t\"leafs\": [%n");
        List<Tree<Level>> subtrees = (List<Tree<Level>>) tree.getSubTrees();
        for (Tree<Level> subtree : subtrees) {
            pw.printf("\t\t\"%s\",%n", subtree.getHead().getId());
        }
        pw.printf("\t\t]%n");
        //pw.printf("\t}%n");
        pw.printf("\t},%n");
    }
    
}
