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

import java.util.ArrayList;
import java.util.List;
import puzzle.Symbol;

/**
 *
 * @author Kurumin
 */
public class Level {
    
    private String id;
    private int tier;
    private List<Room> rooms;
    private List<Stair> stairs;
    private List<Symbol> keys;
    private Room start;
    private Room boss;

    public Level(String id) {
        this.id = id;
        tier = 1;
        rooms = new ArrayList<>();
        stairs = new ArrayList<>();
        keys = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getTier() {
        return tier;
    }

    public void setTier(int tier) {
        this.tier = tier;
    }

    public List<Room> getRooms() {
        return rooms;
    }
    
    public Room getRoom(String roomID){
        for(Room room : rooms){
            if(room.getId().equals(roomID))
                return room;
        }
        return null;
    }

    public void setRooms(List<Room> rooms) {
        this.rooms = rooms;
    }
    
    public void addRoom(Room room){
        this.rooms.add(room);
    }
    
    public boolean removeRoom(Room room){
        return this.rooms.remove(room);
    }
    
    public Room removeRoom(int idx){
        return this.rooms.remove(idx);
    }

    public List<Stair> getStairs() {
        return stairs;
    }

    public void setStairs(List<Stair> stairs) {
        this.stairs = stairs;
    }
    
    public void addStair(Stair stair){
        this.stairs.add(stair);
    }
    
    public boolean removeStair(Stair stair){
        return this.stairs.remove(stair);
    }
    
    public Stair removeStair(int idx){
        return this.stairs.remove(idx);
    }

    public List<Symbol> getKeys() {
        return keys;
    }

    public void setKeys(List<Symbol> keys) {
        this.keys = keys;
    }
    
    public void addKey(Symbol key){
        this.keys.add(key);
    }
    
    public boolean removeKey(Symbol key){
        return this.keys.remove(key);
    }
    
    public Symbol removeKey(int idx){
        return this.keys.remove(idx);
    }

    public Room getStart() {
        return start;
    }

    public void setStart(Room start) {
        this.start = start;
    }

    public Room getBoss() {
        return boss;
    }

    public void setBoss(Room boss) {
        this.boss = boss;
    }
    
    @Override
    public String toString(){
        return id+" - T: "+tier;
    }
    
}
