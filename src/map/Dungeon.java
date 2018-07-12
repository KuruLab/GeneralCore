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
package map;

import util.Tree;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author Kurumin
 */
public class Dungeon {
    
    private String id;
    private Tree<Level> levels;
    private HashMap<Integer, List<Level>> tierMap;

    public Dungeon(String id) {
        this.id = id;
        this.levels = new Tree<>(null);
        this.tierMap = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Tree<Level> getLevels() {
        return levels;
    }

    public void setLevels(Tree<Level> levels) {
        this.levels = levels;
    }

    public HashMap<Integer, List<Level>> getTierMap() {
        return tierMap;
    }

    public void setTierMap(HashMap<Integer, List<Level>> tierMap) {
        this.tierMap = tierMap;
    }
    
    @Override
    public String toString(){
        return "Dungeon: "+id+"\n"+levels.toString();
    }
    
}
