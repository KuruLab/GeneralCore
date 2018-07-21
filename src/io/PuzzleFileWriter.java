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

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.logging.Logger;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;

/**
 *
 * @author Kurumin
 */
public class PuzzleFileWriter extends JsonFileWriter{

    public PuzzleFileWriter(Graph graph, String folder, String filename) {
        super(graph, folder, "puzzle", filename);
    }
    
    public PuzzleFileWriter(Graph graph, String folder, String filename, String version) {
        super(graph, folder, "puzzle", filename, version);
    }

    public void exportPuzzleJSON(String generator, boolean useHash) {
        Hashtable coordHash = parseCoordsToID(graph);
        try {
            PrintWriter pw = new PrintWriter(file);
            pw.printf("{%n");
            if (useHash) {
                pw.printf("\"graph\": \"%s\",%n", graph.hashCode());
            } else {
                pw.printf("\"graph\": \"%s\",%n", filename);
            }
            pw.printf("\"generator\": \"%s\",%n", generator);
            pw.printf("\"puzzle_runtime\": %s,%n", (long) graph.getAttribute("puzzle_runtime"));
            pw.printf("\"puzzle_fitness\": %.6f,%n", (double) graph.getAttribute("puzzle_fitness"));
            pw.printf("\"puzzle_path\": \"%s\",%n", (String) graph.getAttribute("puzzle_path"));
            //pw.printf("\"ui.stylesheet\": \"%s\",%n", "url('media/stylesheet.css')");
            pw.printf("\"nodes\": [%n");
            for (int i = 0; i < graph.getNodeCount(); i++) {
                Node node = graph.getNode(i);
                pw.printf("\t{%n");
                pw.printf("\t\t\"id\": \"%s\",%n", coordHash.get(node.getId()));
                
                pw.printf("\t\t\"intensity\": %.6f,%n", (double) node.getAttribute("ui.color"));
                pw.printf("\t\t\"symbol\": \"%s\",%n", node.getAttribute("symbol").toString());
                pw.printf("\t\t\"condition\": \"%s\"%n", node.getAttribute("condition").toString());
                pw.printf("\t}");
                if (i != graph.getNodeCount() - 1) {
                    pw.printf(",");
                }
                pw.printf("%n");
            }
            pw.printf("],%n");
            pw.printf("\"edges\": [%n");
            for (int i = 0; i < graph.getEdgeCount(); i++) {
                Edge edge = graph.getEdge(i);
                String coord1 = edge.getId().substring(0, edge.getId().indexOf("_"));
                String coord2 = edge.getId().substring(edge.getId().indexOf("_") + 1);
                String id = coordHash.get(coord1) + "_" + coordHash.get(coord2);
                pw.printf("\t{%n");
                pw.printf("\t\t\"id\": \"%s\",%n", id);
                pw.printf("\t\t\"symbol\": \"%s\"%n", edge.getAttribute("symbol").toString());
                pw.printf("\t}");
                if (i != graph.getEdgeCount() - 1) {
                    pw.printf(",");
                }
                pw.printf("%n");
            }
            pw.printf("]%n");
            pw.printf("}%n");
            pw.flush();
            pw.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LevelFileWriter.class.getName(), ex.getMessage());
        }
    }
}
