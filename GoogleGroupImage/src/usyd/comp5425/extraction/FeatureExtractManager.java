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
import java.util.logging.Level;
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
    public FeatureExtractManager() {
        logger.setLevel(Level.OFF);
        //ImageIO.setUseCache(true);
        // ImageIO.setCacheDirectory(new File(System.getProperty("user.home")));
    }
    public Collection <FeatureInfo> extractFeature(File file){
        logger.info("processing file " + file.getAbsolutePath());
        FeatureModuleFactory factory = FeatureModuleFactory.getInstance();
        Vector<FeatureInfo> features = new Vector<FeatureInfo>(factory.getNumberOfMoudle());
        BufferedImage image = readImage(file);
        if(image == null)
            return features;
        for (Enumeration e = factory.getModulesName(); e.hasMoreElements() ;) {
            FeatureModule module =(FeatureModule) e.nextElement();
            logger.info("process is with " + module.getName());
            FeatureInfo info = new FeatureInfo();
            info.setFeatureName(module.getName());
            info.setImage(file.getPath());
            info.setVector(module.getFeatureVector(image));
            System.out.println(info.getVector().toString());
            features.add(info);
            info = null;
            module = null;
        }
        image = null;
        factory = null;
        logger.info("return features");
        features.trimToSize();
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
        //info.setImage(file.getPath());
        info.setVector(module.getFeatureVector(pixels, image.getHeight(),image.getWidth(),new int [0],0 ,0.1,module.getFeatureLength()));
        return info;
    }
    public BufferedImage readImage(File file){
        logger.info("read file " + file.getAbsolutePath());
        try {
            return ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public BufferedImage readImage(URL url){
        logger.info("read file " + url);
        try {
            return   ImageIO.read(url);
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
