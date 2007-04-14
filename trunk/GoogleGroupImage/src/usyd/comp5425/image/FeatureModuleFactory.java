/*
 * FeatureExtractionFactory.java
 *
 * Created on 31 March 2007, 16:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.image;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Properties;

/**
 *
 * @author Yuezhong Zhang
 */
public class FeatureModuleFactory {
    private final static FeatureModuleFactory factory = new FeatureModuleFactory();
    private Hashtable<String,FeatureModule> features = new Hashtable<String,FeatureModule>();
    private Properties props = new Properties();
    private FeatureModuleFactory() {
        try {
            InputStream is = getClass().getClassLoader().getSystemResourceAsStream("modules.properties");
            props.load(is);
            is.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public static FeatureModuleFactory getInstance(){
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
