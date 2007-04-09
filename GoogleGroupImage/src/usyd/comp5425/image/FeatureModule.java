/*
 * FeatureExtraction.java
 *
 * Created on 31 March 2007, 14:56
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
public abstract class FeatureModule {
    public abstract Vector <Double> getFeatureVector(int [] inImg, int height, int width, int [] maskImg, int flag, double feature, int featureDimension);
    public abstract double getThreshold();
    public abstract int getFeatureLength();
    public abstract String getName();
    public abstract String getCategory();
    public abstract String getDescription();
    
    
    
    /**
     *Compare two vectors of Double value. this implementation use L2 distance measure
     *
     **This approach again works in vector space similar to the matching coefficient and
     *the dice coefficient, however the similarity measure is not judged from the angle
     * as in cosine rule but rather the direct euclidean distance between the vector inputs.
     * Below is the standard Euclidean distance formula between vectors q and r.
     *
     * enc (q,r ) = squareRoot ( sum( (( v1(y) -v2)) * (( v1(y) -v2))) power 1/2
     *
     * @see http://www.dcs.shef.ac.uk/~sam/stringmetrics.html#dist
     * @param v1  vector1
     * @param v2  vector2
     * @return    a double number indicates how close these two vectors.
     */
    public double compareFeatureVector(Vector<Double> v1, Vector<Double> v2) {
        int size = v1.size();
        if(size != v2.size()){
            return 99999.9;
        }
        double total = 0.0d;
        double first = 0.0d;
        double second = 0.0d;
        for(int i =0; i <size; i++ ){
            first = v1.get(i);
            second = v2.get(i);
            total += (first - second) * (first - second);
        }
        return Math.sqrt(total / size);
    }
    
    
    
}
