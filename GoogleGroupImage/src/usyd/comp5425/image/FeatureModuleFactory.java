/*
 * FeatureExtractionFactory.java
 *
 * Created on 31 March 2007, 16:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.image;

import java.util.Hashtable;

/**
 *
 * @author Yuezhong Zhang
 */
public class FeatureModuleFactory {
    public final static FeatureModuleFactory factory = new FeatureModuleFactory();
    private Hashtable<String,String> props = new Hashtable<String,String>();
    /** Creates a new instance of FeatureExtractionFactory */
    private FeatureModuleFactory() {
        props.put("AverageRGB","usyd.comp5425.image.AverageRGBModule");
        props.put("Co-occurence","usyd.comp5425.image.CooccurenceModule");
        props.put("Local Color Histogram","usyd.comp5425.image.LocalColorHistogram");
        props.put("Global Color Histogram", "usyd.comp5425.image.GlobalColorHistogram");
        props.put("Geometric Moment", "usyd.comp5425.image.GeometricMoment");
    }
    public FeatureModuleFactory getInstance(){
        return factory;
    }
    public FeatureModule getFeatureModule(String name){
        String className = props.get(name);
        if(className == null) {
            return null;
        }else{
            try {
                Class c =  Class.forName(className);
                return (FeatureModule) c.newInstance();
            } catch (Exception ex) {
                return null;
            }
        }
    }
    public String [] getAvailableModuleNames(){
        return (String []) props.keySet().toArray();
    }
    
}
