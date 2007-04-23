/*
 * GeometricMoment.java
 *
 * Created on 31 March 2007, 16:25
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.image;

import java.awt.Dimension;
import java.util.Vector;

/**
 *
 * @author Yuezhong Zhang
 */
public class GeometricMoment extends ShapeModule{
    private final int DEFAULT_P_VALUE = 1;	// Default value of order p
    private final int DEFAULT_Q_VALUE = 1;	// Default value of order q
    
    /** Creates a new instance of GeometricMoment */
    public GeometricMoment() {
    }
    
    public Vector<Double> getFeatureVector(int[] inImg, int height, int width, int[] maskImg, int flag, double feature, int featureDimension) {
        Dimension size = new Dimension(width,height);
        int pixels[] = getProcessedImage(inImg, height,width, size);
        double weight[] = objectWeightPoint(pixels, size);
        return (findGeometricMoment(pixels, size, weight, DEFAULT_P_VALUE, DEFAULT_Q_VALUE));
        
    }
    /** Find Geometric Moment
     * @param pixels pixel array containing Black & White ONLY
     * @param size size of the image the pixel array is representing
     * @param weight object weight
     * @param p order in x coordinate
     * @param q order in y coordinate
     * @return Vector containing the geometric moment
     */
    private Vector<Double> findGeometricMoment(int pixels[], Dimension size, double weight[], int pValue, int qValue) {
        double moment = 0;
        Vector<Double> geometricMoment = new Vector<Double>(1);
        for (int j=0; j<size.height; j++)
            for (int i=0; i<size.width; i++)
                if (pixels[j*size.width + i] == FOREGROUND)
                    moment += Math.pow(i-weight[0],pValue) * Math.pow(j-weight[1],qValue);
        
        geometricMoment.add(round( moment ));
        return (geometricMoment);
    }
    public double getThreshold() {
        return 880.0d;
    }
    
    public int getFeatureLength() {
        return 1;
    }
    
    public String getName() {
        return "Geometric Moment";
    }
    
    public String getCategory() {
        return "Shape";
    }
    
    public String getDescription() {
        return "Computes the Geometric moment of the image";
    }
    
}
