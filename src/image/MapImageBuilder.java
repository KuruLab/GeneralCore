/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package image;

import config.GeneralConfig;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import org.graphstream.graph.Edge;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.ui.geom.Point3;
import static org.graphstream.ui.graphicGraph.GraphPosLengthUtils.nodePointPosition;
import puzzle.Condition;
import puzzle.Symbol;
import util.GraphStreamUtil;

/**
 *
 * @author andre
 */
public class MapImageBuilder {
    private String filename;
    private BufferedImage bImg;
    public MapImageBuilder(String file){
        this.filename = file;
    }
    
    public BufferedImage buildImage(Graph graph){
        double border = GeneralConfig.borderSize;
        double width = 0;
        double height = 0;
        double maxX = 0;
        int xIndex = 0;
        double maxY = 0;
        int yIndex = 0;
        // first, find the bounds
        for (int i = 0; i < graph.getNodeCount(); i++) {
            Node node = graph.getNode(i);
            Point3 xyz = nodePointPosition(node); 
            if (xyz.x > maxX) {
                maxX = xyz.x;
                xIndex = i;
            }
            if (xyz.y > maxY) {
                maxY = xyz.y;
                yIndex = i;
            }
        }
        width  = maxX + ((double) graph.getNode(xIndex).getAttribute("width") / 2.0) + border;
        height = maxY + ((double) graph.getNode(yIndex).getAttribute("height") / 2.0) + border;
        System.out.println("Img size: "+width+" x "+height);
        bImg = new BufferedImage((int)width, (int)height, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g = bImg.createGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, (int)width, (int)height);
        for(Edge edge : graph.getEachEdge()){
            drawEdge(g, edge);
        }
        for(Node node : graph.getEachNode()){
            drawNode(g, node, (int) height);
        }
        
        bImg = flipImage(bImg);
        g = bImg.createGraphics();
        for(Node node : graph.getEachNode()){
            drawLabel(g, node, (int) maxY, border);
        }
        
        try {
            ImageIO.write(bImg, "png", new File(filename));
        } catch (IOException ex) {
            Logger.getLogger(MapImageBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        return bImg;
    }
    
    public void drawNode(Graphics2D g, Node node, int height){
        Point3 xyz = nodePointPosition(node);
        int x = (int) (xyz.x - (double) node.getAttribute("width")/2.0);
        //int y = (int) (height - xyz.y + (double) node.getAttribute("height")/2.0);
        int y = (int) (xyz.y - (double) node.getAttribute("height")/2.0);
        double w = (double) node.getAttribute("width");
        double h = (double) node.getAttribute("height");
        Symbol symbol = node.getAttribute("symbol");
        if(symbol == null){
           g.setColor(Color.blue); 
        }
        else{
            if(symbol.isNothinig())
                g.setColor(Color.blue);
            if(symbol.isStart())
                g.setColor(Color.cyan);
            if(symbol.isBoss())
                g.setColor(Color.red);
            if(symbol.getValue()>0)
                g.setColor(Color.yellow);
        }
        g.fillRect(x, y, (int)w, (int)h);
        g.setColor(Color.white);
        g.drawRect(x, y, (int)w, (int)h);
    }
    
    public void drawEdge(Graphics2D g, Edge edge){
        GraphStreamUtil util = new GraphStreamUtil();
        Point3[] points = util.getFourRotatedEdgePoints(edge);
        g.setColor(Color.BLUE);
        int[] xPoints = {(int)points[0].x, (int)points[1].x, (int)points[2].x, (int)points[3].x};
        int[] yPoints = {(int)points[0].y, (int)points[1].y, (int)points[2].y, (int)points[3].y};
        g.fillPolygon(xPoints, yPoints, 4);
        g.setColor(Color.white);
        g.drawLine((int)points[0].x, (int)points[0].y, (int)points[1].x, (int)points[1].y);
        g.drawLine((int)points[1].x, (int)points[1].y, (int)points[2].x, (int)points[2].y);
        g.drawLine((int)points[2].x, (int)points[2].y, (int)points[3].x, (int)points[3].y);
        g.drawLine((int)points[3].x, (int)points[3].y, (int)points[0].x, (int)points[0].y);
    }
    
    public void drawLabel(Graphics2D g, Node node, int h, double border){
        Point3 xyz = nodePointPosition(node);
        Symbol symbol = node.getAttribute("symbol");
        if(symbol != null){
            if(!symbol.isNothinig())
                g.setColor(Color.blue);
            else
                g.setColor(Color.white);
            g.setFont(new Font("Verdana", Font.PLAIN, 9));

            String label = "";
            if(symbol.isKey()){
                label += symbol.toString();
            }
            else{
                Condition condition = node.getAttribute("condition");
                if(condition != null && condition.getKeyLevel()>0 )
                    label += condition.toString();
            }
            //    label += "*"+symbol.toString()+"*";

            int w = (int)(double) node.getAttribute("width");
            int s = (int)((double) node.getAttribute("height")/2.0);
            int x = (int) ((xyz.x) - w/2.0) + 2;
            int y = (int) (h - (xyz.y) + border + s + 2);

            g.drawString(label, x, y);
        }
    }
    
    public BufferedImage flipImage(BufferedImage image){
        // Flip the image vertically
        AffineTransform tx = AffineTransform.getScaleInstance(1, -1);
        tx.translate(0, -image.getHeight(null));
        AffineTransformOp op = new AffineTransformOp(tx, AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
        image = op.filter(image, null);
        return image;
    }
    
}
