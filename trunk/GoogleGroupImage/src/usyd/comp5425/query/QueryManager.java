/*
 * QueryManager.java
 *
 * Created on 7 April 2007, 14:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.query;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import usyd.comp5425.util.GraphicsUtilities;

/**
 *
 * @author Yuezhong Zhang
 */
public class QueryManager {
    private final static QueryManager manager = new QueryManager();
    /** Creates a new instance of QueryManager */
    private QueryManager() {
    }
    public static QueryManager getInstance(){
        return manager;
    }
    public List query(Object query){
        List list = new ArrayList();
        File  file = new File("C:\\myfiles");
        if(!file.exists())
            return list;
        File [] files = file.listFiles();
        for(File tmp : files){
            if(tmp.getName().endsWith(".JPG")){
                try {
                    BufferedImage image =  GraphicsUtilities.loadCompatibleImage(tmp.toURL());
                    image =  GraphicsUtilities.createThumbnailFast(image,160);
                    list.add(image);
                    image = null;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
        System.out.println("list =" +list.size());
        return list;
    }
}