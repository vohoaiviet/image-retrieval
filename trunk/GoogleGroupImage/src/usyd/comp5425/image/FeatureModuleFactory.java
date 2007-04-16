/*
 * FeatureExtractionFactory.java
 *
 * Created on 31 March 2007, 16:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.image;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 *
 * @author Yuezhong Zhang
 */
public class FeatureModuleFactory {
    private  static FeatureModuleFactory factory;
    private Hashtable<String,FeatureModule> features = new Hashtable<String,FeatureModule>();
    private Properties props = new Properties();
    private FeatureModuleFactory() {
        
        props.setProperty("AverageRGB","usyd.comp5425.image.AverageRGBModule");
        props.setProperty("Cooccurence","usyd.comp5425.image.CooccurenceModule");
        props.setProperty("Local_Color_Histogram","usyd.comp5425.image.LocalColorHistogram");
        props.setProperty("Global_Color_Histogram","usyd.comp5425.image.GlobalColorHistogram");
        props.setProperty("Geometric_Moment","usyd.comp5425.image.GeometricMoment");
        
    }
    public static FeatureModuleFactory getInstance(){
        if(factory == null){
            factory = new FeatureModuleFactory();
        }
        return factory;
    }
    public FeatureModule getFeatureModule(String name){
        FeatureModule module = null;
        String className = props.getProperty(name);
        if(className == null){
            return null;
        }
        module = features.get(className);
        if(module == null){
            try {
                Class c =  Class.forName(className);
                module =  (FeatureModule) c.newInstance();
                features.put(className,module);
            } catch (Exception ex) {
                return null;
            }
        }
        return  module;
    }
    public Enumeration getModulesName(){
        return props.propertyNames();
    }
    public int getNumberOfMoudle(){
        return props.size();
    }
    
}
