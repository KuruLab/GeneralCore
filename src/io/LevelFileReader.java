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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Logger;
import map.Door;
import map.Room;
import map.Level;
import map.Stair;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import puzzle.Condition;
import puzzle.Symbol;

/**
 *
 * @author Kurumin
 */
public class LevelFileReader extends RawFileReader{
    
    public LevelFileReader(String _filename){
        super(_filename);
    }
    
    public Level parseJson(){
        String levelID = "unamed level";
        Level level = new Level(levelID);
        try {
            String rawJSon = readRawString();
            System.out.println("Parsing \""+filename+"\"");
            //System.out.println(rawJSon);
            JSONParser parser = new JSONParser(); 
            JSONObject obj = (JSONObject) parser.parse(rawJSon);
            
            levelID = (String) obj.get("graph");
            level.setId(levelID);
            
            JSONArray nodeArray = (JSONArray) obj.get("nodes");
            
            for(int i = 0; i < nodeArray.size(); i++){
                JSONObject nodeObj = (JSONObject) nodeArray.get(i);
                String nodeID = (String) nodeObj.get("id");
                
                Double itnsty = (Double) nodeObj.get("intensity");
                String symbol = (String) nodeObj.get("symbol");
                String cond = (String) nodeObj.get("condition");
                JSONArray pos = (JSONArray) nodeObj.get("position");
                
                Double x = (Double) pos.get(0);
                Double y = (Double) pos.get(1);
                Double z = (Double) pos.get(2);
                
                Symbol sym = new Symbol(symbol);
                Condition con = new Condition(new Symbol(cond));

                Room room = new Room(nodeID);
                room.setIndex(i);
                room.setCoord(x.doubleValue(), y.doubleValue(), z.doubleValue());
                room.setIntensity(itnsty);
                room.addSymbol(sym);
                room.setCondition(con);
                
                improveLevelSymbols(sym, room, level);
                level.addRoom(room);
            }
            
            JSONArray edgeArray = (JSONArray) obj.get("edges");
            for(int i = 0; i < edgeArray.size(); i++){
                JSONObject edgeObj = (JSONObject) edgeArray.get(i);
                String edgeID = (String) edgeObj.get("id");
                String from = (String) edgeObj.get("a");
                String to = (String) edgeObj.get("b");
                String symbol = (String) edgeObj.get("symbol");
                
                Room roomFrom = level.getRoom(from);
                Room roomTo = level.getRoom(to);
                if(roomFrom == null || roomTo == null)
                    System.err.println("Error: room not found!");
                
                Door door = new Door(edgeID);
                door.setA(roomFrom);
                door.setB(roomTo);
                door.setCondition(new Condition(new Symbol(symbol)));
                door.setOpen(false);
                
                roomFrom.addDoor(door);
                roomTo.addDoor(door);
            }
        } catch (ParseException ex) {
            Logger.getLogger(getClass().getName(), ex.getMessage());
        }
        return level;
    }
    
    private void improveLevelSymbols(Symbol symbol, Room room, Level level) {
        if (symbol.isStart()) {
            level.setStart(room);
        } else if (symbol.isBoss()) {
            level.setBoss(room);
        } else if (symbol.isKey()) {
            level.addKey(symbol);
        } else if (symbol.isStair()){
            //level.addStair(new Stair());
        }
    }
}
