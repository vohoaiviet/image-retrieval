/*
 * AverageRGBModule.java
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
 * @author Yuezhong Zhang SID:305275631
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
        vector.add(round(((double)red    / size) ));
        vector.add(round(((double)green / size) ));
        vector.add(round(((double)blue / size) ));
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
