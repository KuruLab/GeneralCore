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

import config.GeneralConfig;
import java.util.logging.Logger;
import map.Door;
import map.Room;
import map.Level;
import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.DefaultGraph;
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
public class LevelFileReader extends JsonFileReader{
    
    public LevelFileReader(String _folder, String _filename){
        super(_folder, "level", _filename);
    }
    
    public Graph parseJsonToGraph(){
        String graphID = "-1";
        DefaultGraph graph = null;
        try {
            String rawJSon = readRawString();
            //System.out.println(rawJSon);
            JSONParser parser = new JSONParser(); 
            JSONObject obj = (JSONObject) parser.parse(rawJSon);
            
            graphID = (String) obj.get("graph");
            
            JSONArray nodeArray = (JSONArray) obj.get("nodes");
            
            graph = new DefaultGraph(graphID);
            graph.addAttribute("ui.stylesheet", "url('"+GeneralConfig.media+"')");
            Boolean antialias = (Boolean) obj.get("ui.antialias");
            if(antialias)
                graph.addAttribute("ui.antialias");
            Boolean quality = (Boolean) obj.get("ui.quality");
            if(quality)
                graph.addAttribute("ui.quality");
            
            String size = (String) obj.get("size");
            Double gWidth  = Double.parseDouble(size.split("x")[0]);
            Double gHeight = Double.parseDouble(size.split("x")[1]);
            Double border = (double)((long) obj.get("border"));
            Long runtime = (long) obj.get("runtime");
            //Double fitness = (double) obj.get("fitness");
            graph.addAttribute("runtime", runtime);
            graph.addAttribute("width", gWidth);
            graph.addAttribute("height", gHeight);
            graph.addAttribute("border", border);
            
            for(int i = 0; i < nodeArray.size(); i++){
                JSONObject nodeObj = (JSONObject) nodeArray.get(i);
                String nodeID = (String) nodeObj.get("id");
                Double width = (Double) nodeObj.get("width");
                Double height = (Double) nodeObj.get("height");
                //Double itnsty = (Double) nodeObj.get("intensity");
                //String symbol = (String) nodeObj.get("symbol");
                //String cond = (String) nodeObj.get("condition");
                JSONArray pos = (JSONArray) nodeObj.get("position");
                
                Double x = (Double) pos.get(0);
                Double y = (Double) pos.get(1);
                Double z = (Double) pos.get(2);
                
                graph.addNode(nodeID);
                graph.getNode(i).addAttribute("width",  width.doubleValue());
                graph.getNode(i).addAttribute("height", height.doubleValue());
                graph.getNode(i).addAttribute("xyz", x.doubleValue(), y.doubleValue(), z.doubleValue());
                //graph.getNode(i).addAttribute("ui.color", itnsty);
                
                //Symbol sym = new Symbol(symbol);
                //Condition con = new Condition(new Symbol(cond));
                
                //graph.getNode(i).addAttribute("symbol", sym);
                //graph.getNode(i).addAttribute("condition", con);
            }
            
            JSONArray edgeArray = (JSONArray) obj.get("edges");
            for(int i = 0; i < edgeArray.size(); i++){
                JSONObject edgeObj = (JSONObject) edgeArray.get(i);
                String edgeID = (String) edgeObj.get("id");
                String from = (String) edgeObj.get("a");
                String to = (String) edgeObj.get("b");
                //String symbol = (String) edgeObj.get("symbol");
                
                graph.addEdge(edgeID, to, from);
                
                //Symbol sym = new Symbol(symbol);
                //graph.getEdge(i).addAttribute("symbol", sym);
            }
        } catch (ParseException ex) {
            Logger.getLogger(getClass().getName(), ex.getMessage());
        }
        return graph;
    }
    
    public Level parseJsonToLevel(){
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
                
                //Double itnsty = (Double) nodeObj.get("intensity");
                //String symbol = (String) nodeObj.get("symbol");
                //String cond = (String) nodeObj.get("condition");
                //String lore = (String) nodeObj.get("lore");
                //String info = (String) nodeObj.get("info");
                //String firstTime = (String) nodeObj.get("first_time");
                JSONArray pos = (JSONArray) nodeObj.get("position");
                
                Double x = (Double) pos.get(0);
                Double y = (Double) pos.get(1);
                Double z = (Double) pos.get(2);
                
                //Symbol sym = new Symbol(symbol);
                //Condition con = new Condition(new Symbol(cond));

                Room room = new Room(nodeID);
                room.setIndex(i);
                room.setCoord(x.doubleValue(), y.doubleValue(), z.doubleValue());
                //room.setIntensity(itnsty);
                //room.addSymbol(sym);
                //room.setCondition(con);
                //room.setLore(lore);
                //room.setInfo(info);
                //room.setFirstTimeText(firstTime);
                
                //improveLevelSymbols(sym, room, level);
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
