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
import puzzle.Condition;
import puzzle.Symbol;

/**
 *
 * @author Kurumin
 */
public class Room {
    
    private static double[] ZERO = {0.0, 0.0, 0.0};
    
    private int index;
    private String id;
    private double[] coord;
    
    private List<Door> doors;
    private List<Symbol> symbols;
    private Condition condition;
    private double intensity;
    
    private String lore;
    private String info;
    private String firstTimeText;

    public Room(String id) {
        this.id = id;
        index = -1;
        coord = ZERO;
        doors = new ArrayList<>();
        symbols = new ArrayList<>();
        condition = new Condition();
        intensity = 0.0;
        lore = "";
        info = "";
        firstTimeText = "";
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double[] getCoord() {
        return coord;
    }

    public void setCoord(double[] coord) {
        this.coord = coord;
    }
    
    public void setCoord(double x, double y, double z) {
        coord = new double[3];
        coord[0] = x;
        coord[1] = y;
        coord[2] = z;
    }

    public List<Door> getDoors() {
        return doors;
    }

    public void setDoors(List<Door> doors) {
        this.doors = doors;
    }
    
    public void addDoor(Door door){
        this.doors.add(door);
    }
    
    public boolean removeDoor(Door door){
        return this.doors.remove(door);
    }
    
    public Door removeDoor(int idx){
        return this.doors.remove(idx);
    }

    public List<Symbol> getSymbols() {
        return symbols;
    }

    public void setSymbols(List<Symbol> symbols) {
        this.symbols = symbols;
    }
    
    public void addSymbol(Symbol symbol){
        this.symbols.add(symbol);
    }
    
    public boolean removeSymbol(Symbol symbol){
        return this.symbols.remove(symbol);
    }
    
    public Symbol removeSymbol(int idx){
        return this.symbols.remove(idx);
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    public double getIntensity() {
        return intensity;
    }

    public void setIntensity(double intensity) {
        this.intensity = intensity;
    }

    public String getLore() {
        return lore;
    }

    public void setLore(String lore) {
        this.lore = lore;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getFirstTimeText() {
        return firstTimeText;
    }

    public void setFirstTimeText(String firstTimeText) {
        this.firstTimeText = firstTimeText;
    }
}
