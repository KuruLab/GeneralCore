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
package experiments;

import java.io.File;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import org.graphstream.graph.Graph;

/**
 *
 * @author Kurumin
 */
public abstract class AbstractExperiment implements Experiment{
    
    public int current;
    public int executions;
    public Graph[] graph;
    
    @Override
    public void verifyFolder(String path){
        File folder = new File(path);
        if(!folder.exists())
            folder.mkdir();
        ArrayList<File> results = new ArrayList<>();
        for(int i = 0; i < folder.listFiles().length; i++){
            if(folder.listFiles()[i].isDirectory())
                results.add(folder.listFiles()[i]);
        }
        if(results.size() > 0){
            int remaining = Math.max(0, executions - results.size());
            JOptionPane.showMessageDialog(null, "There are already "+results.size()+" results in the speficied folder out of "+executions+" required.", "Atention!", JOptionPane.WARNING_MESSAGE);
            String opt = JOptionPane.showInputDialog("Choose an option:\n"
                    + "[0] - Proceed anyway (new "+executions+" executions).\n"
                    + "[1] - Proceed just "+remaining+" more times.\n"
                    + "[2] - Only read existing results.\n"
                    + "[3] - Exit.");
            if(opt.compareTo("0")==0){
                current = 0;
            }
            else if(opt.compareTo("1")==0){
                current = results.size();
            }
            else if(opt.compareTo("2")==0){
                current = executions;
            }
            else{
                System.exit(0);
            }
        }
    }
}
