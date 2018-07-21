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

import java.util.logging.Logger;
import org.graphstream.graph.Graph;
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
public class PuzzleFileReader extends JsonFileReader{
    
    public PuzzleFileReader(String _folder, String _filename) {
        super(_folder, "puzzle", _filename);
    }
    
    public PuzzleFileReader(String _folder, String _filename, String _version) {
        super(_folder, "puzzle", _filename, _version);
    }
    
    public void improveGraph(Graph graph){
        try {
            String rawJSon = readRawString();
            JSONParser parser = new JSONParser(); 
            JSONObject obj = (JSONObject) parser.parse(rawJSon);
            Long runtime = (long) obj.get("puzzle_runtime");
            Double fitness = (double) obj.get("puzzle_fitness");
            graph.addAttribute("puzzle_runtime", runtime);
            graph.addAttribute("puzzle_fitness", fitness); 
            graph.addAttribute("puzzle_path", (String) obj.get("puzzle_path"));
            /*String[] path = new String[pathArray.size()];
            for(int i = 0; i < pathArray.size(); i++){
                path[i] = (String) pathArray.get(i);
            }
            graph.addAttribute("puzzle_path", path);*/
            
            JSONArray nodeArray = (JSONArray) obj.get("nodes");
            for(int i = 0; i < nodeArray.size(); i++){
                JSONObject nodeObj = (JSONObject) nodeArray.get(i);
                String nodeID = (String) nodeObj.get("id");
                Double itnsty = (Double) nodeObj.get("intensity");
                String symbol = (String) nodeObj.get("symbol");
                String cond = (String) nodeObj.get("condition");
                
                graph.getNode(nodeID).addAttribute("ui.color", itnsty);
                
                Symbol sym = new Symbol(symbol);
                Condition con = new Condition(new Symbol(cond));
                
                graph.getNode(nodeID).addAttribute("symbol", sym);
                graph.getNode(nodeID).addAttribute("condition", con);
            }
            
            JSONArray edgeArray = (JSONArray) obj.get("edges");
            for(int i = 0; i < edgeArray.size(); i++){
                JSONObject edgeObj = (JSONObject) edgeArray.get(i);
                String edgeID = (String) edgeObj.get("id");
                String symbol = (String) edgeObj.get("symbol");
                Symbol sym = new Symbol(symbol);
                graph.getEdge(edgeID).addAttribute("symbol", sym);
            }
         } catch (ParseException ex) {
            Logger.getLogger(getClass().getName(), ex.getMessage());
        }
    }
    
}
