/*
 * AverageRGBModule.java
 *
 * Created on 31 March 2007, 15:15
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
public class AverageRGBModule extends FeatureModule {
    
    /** Creates a new instance of AverageRGBModule */
    public AverageRGBModule() {
    }
    
    public Vector<Double> getFeatureVector(int[] inImg, int height, int width, int[] maskImg, int flag, double feature, int featureDimension) {
        
        Vector<Double> vector = new Vector<Double>(3);
        int size = height * width;
        int red   = 0;
        int green = 0;
        int blue  = 0;
        for(int i =0; i<size; i++){
            red   += inImg[i] >> 16 & 0xff;
            green += inImg[i] >> 8 &  0xff;
            blue  += inImg[i] & 0xff;
        }
        vector.add(((double)red    / size) );
        vector.add(((double)green / size) );
        vector.add(((double)blue / size) );
        return vector;
    }
    
    public double getThreshold() {
        return 22.0d;
    }
    
    public int getFeatureLength() {
        return 3;
    }
    
    public String getName() {
        return "Average RGB";
    }
    
    public String getCategory() {
        return "Color";
    }
    
    public String getDescription() {
        return "Average the RGB values of an image";
    }
    
}
