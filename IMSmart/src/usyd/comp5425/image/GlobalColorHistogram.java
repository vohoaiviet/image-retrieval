/*
 * GlobalColorHistogram.java
 *
 *  Copyright (C) 2007 COMP5425 Multimedia Storage, Retrieval and Delivery
 *  The School of Information Technology
 *  The University of Sydney
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package usyd.comp5425.image;

import java.util.Vector;

/**
 *
 * @author Yuezhong Zhang  SID:305275631
 */
public class GlobalColorHistogram extends FeatureModule{
    
    /** Creates a new instance of GlobalColorHistogram */
    public GlobalColorHistogram() {
    }
    
    public Vector<Double> getFeatureVector(int[] inImg, int height, int width, int[] maskImg, int flag, double feature, int featureDimension) {
        Vector<Double> vector = new Vector<Double>(64);
        int size = width * height;
        double[] histogram = new double[64];
        //initalize it with 0.0
        for(int i=0 ; i<64 ; i++){
            histogram[i] = 0.0;
        }
        int index;
        for(int i=0 ; i<size ; i++){
            index = (inImg[i]>>18 & 0x30) | (inImg[i]>>12 & 0xc) | (inImg[i]>>6 & 0x3);
            histogram[index] += 1.0;
        }
        
        for(int i=0 ; i<64 ; i++){
            vector.add(round(histogram[i]/size));
        }
        histogram = null;
        return vector;
    }
    
    public double getThreshold() {
        return 0.37d;
    }
    
    public int getFeatureLength() {
        return 64;
    }
    
    public String getName() {
        return "Global Color Histogram";
    }
    
    public String getCategory() {
        return "Color";
    }
    
    public String getDescription() {
        return "Compute global color histogram for an image";
    }
    
}
