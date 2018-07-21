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

import java.io.File;
import java.util.Hashtable;
import org.graphstream.graph.Graph;

/**
 *
 * @author Kurumin
 */
public class JsonFileWriter {
    protected Graph graph;
    protected String folder;
    protected String filename;
    protected File file;

    public JsonFileWriter(Graph graph, String folder, String type, String filename) {
        this.graph = graph;
        this.folder = folder;
        this.filename = filename;
        
        File upperDir = new File(folder);
        if(!upperDir.exists())
            upperDir.mkdir();
        
        File lowerDir = new File(upperDir, filename);
        if(!lowerDir.exists())
            lowerDir.mkdir();
        
        String finalname = type + "_" + filename + ".json";
        this.file = new File(lowerDir, finalname);
    }
    
    public JsonFileWriter(Graph graph, String folder, String type, String filename, String version) {
        this.graph = graph;
        this.folder = folder;
        this.filename = filename;
        
        File upperDir = new File(folder);
        if(!upperDir.exists())
            upperDir.mkdir();
        
        File lowerDir = new File(upperDir, filename);
        if(!lowerDir.exists())
            lowerDir.mkdir();
        
        String finalname = type + "_" + filename + "_" + version + ".json";
        this.file = new File(lowerDir, finalname);
    }
    
    protected Hashtable<String, Integer> parseCoordsToID(Graph graph) {
        Hashtable<String, Integer> hash = new Hashtable<>();
        for (int i = 0; i < graph.getNodeCount(); i++) {
            Integer id = new Integer(i);
            String coord = graph.getNode(i).getId();
            if (hash.containsKey(coord)) {
                System.out.println("Error: hash already contains coord " + coord);
            } else {
                hash.put(coord, id);
            }
        }
        return hash;
    }
}
