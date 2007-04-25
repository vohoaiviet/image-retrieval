/*
 * DataTap.java
 *
 *
 *  Copyright (C) 2007 COMP5425 Multimedia Storage, Retrieval and Delivery
 *  The School of Information Technology
 *  The University of Sydney
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package usyd.comp5425.db;

import java.util.Collection;

/**
 *
 * @author Yuezhong Zhang SID:305275631
 */
public interface DataTap {
    
    public Collection<Integer> getAllFeaturesID();
    public Collection<Integer> getAllFeaturesIDBy(String featureName);
    public FeatureInfo getFeatureBy(int id, String featureName);
    public FeatureInfo getFeatureBy(int id);
    public Collection<FeatureInfo> getFeaturesBy(String image);
    
    public boolean add(FeatureInfo feature);
    public boolean remove(int id);
    public boolean remove(FeatureInfo feature);
    public boolean remove(String image);
    public boolean exists(String image);
}
