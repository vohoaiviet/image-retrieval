/*
 * GlobalColorHistogram.java
 *
 * Created on 31 March 2007, 15:39
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.image;

import java.util.Vector;

/**
 *
 * @author Yuezhong Zhang
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
            vector.add(new Double(histogram[i]/size));
        }
        histogram = null;
        return vector;
    }
    
    public double getThreshold() {
        return 0.05d;
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
