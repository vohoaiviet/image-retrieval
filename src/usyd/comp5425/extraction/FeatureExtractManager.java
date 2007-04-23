/*
 * FeatureExtractManager.java
 *
 * Created on 14 April 2007, 15:45
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.extraction;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Vector;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import usyd.comp5425.db.FeatureInfo;
import usyd.comp5425.image.FeatureModule;
import usyd.comp5425.image.FeatureModuleFactory;

/**
 *
 * @author Yuezhong Zhang
 */
public class FeatureExtractManager {
    private Logger logger = Logger.getLogger(FeatureExtractManager.class.getName());
    private int length;
    public FeatureExtractManager() {
        //logger.setLevel(Level.OFF);
        StringBuffer sb = new StringBuffer(System.getProperty("user.dir"));
        sb.append(File.separatorChar);
        sb.append("images");
        sb.append(File.separatorChar);
        length = sb.length();
        sb = null;
        
    }
    public Collection <FeatureInfo> extractFeature(File file){
        FeatureModuleFactory factory = FeatureModuleFactory.getInstance();
        Vector<FeatureInfo> features = new Vector<FeatureInfo>(factory.getNumberOfMoudle());
        BufferedImage image = readImage(file);
        if(image == null)
            return features;
        int [] pixels = getRGBPixels(image);
        String relative = file.getAbsolutePath().substring(length);
        for (Enumeration e = factory.getModulesName(); e.hasMoreElements() ;) {
            String moduleName = (String) e.nextElement();
            FeatureModule module = factory.getFeatureModule(moduleName);
            FeatureInfo info = new FeatureInfo();
            info.setFeatureName(moduleName);
            info.setImage(relative);
            info.setVector(module.getFeatureVector(pixels, image.getHeight(),image.getWidth(),new int [0],0 ,0.1,module.getFeatureLength()));
            features.add(info);
            info = null;
            module = null;
        }
        pixels = null;
        image = null;
        factory = null;
        features.trimToSize();
        System.gc();
        return features;
    }
    public FeatureInfo extractFeature(File file, String featureName){
        FeatureModuleFactory factory = FeatureModuleFactory.getInstance();
        BufferedImage image = readImage(file);
        if(image == null)
            return null;
        int [] pixels = getRGBPixels(image);
        FeatureModule module =(FeatureModule) factory.getFeatureModule(featureName);
        factory = null;
        image = null;
        FeatureInfo info = new FeatureInfo();
        info.setFeatureName(module.getName());
        String relative = file.getAbsolutePath().substring(length);
        info.setImage(relative);
        info.setVector(module.getFeatureVector(pixels, image.getHeight(),image.getWidth(),new int [0],0 ,0.1,module.getFeatureLength()));
        return info;
    }
    public BufferedImage readImage(File file){
        try {
            return ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public BufferedImage readImage(URL url){
        try {
            return  ImageIO.read(url);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public int [] getRGBPixels(BufferedImage image){
        int rgb [] = image.getRGB(0,0,image.getWidth(),image.getHeight(),null,0,image.getWidth());
        return rgb;
    }
}
