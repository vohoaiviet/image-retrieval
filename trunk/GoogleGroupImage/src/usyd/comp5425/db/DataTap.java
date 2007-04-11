/*
 * DataTap.java
 *
 * Created on 12 April 2007, 00:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.db;

import java.util.Collection;

/**
 *
 * @author Yuezhong Zhang
 */
public interface DataTap {
    
    public Collection getAllFeaturesID();
    public Collection getAllFeaturesIDBy(String featureName);
    public FeatureInfo getFeatureBy(int id, String featureName);
    public FeatureInfo getFeatureBy(int id);
    public Collection<FeatureInfo> getFeaturesBy(String image);
    
    public int add(FeatureInfo feature);
    public boolean remove(int id);
    public boolean remove(FeatureInfo feature);
    public boolean remove(String image);
}
