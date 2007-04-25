/*
 * ColorMomentFeatureModule.java
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
public class ColorMomentFeatureModule extends FeatureModule{
    int numChannel = 3;
    
    double	w1 = 1,	// The weights of the 3 moments in the similarity function
            w2 = 1,	// Default values are all 1
            w3 = 1;	// i.e. equal weighting
    /** Creates a new instance of ColorMomentFeatureModule */
    public ColorMomentFeatureModule() {
    }
    
    public Vector<Double> getFeatureVector(int[] inImg, int height, int width, int[] maskImg, int flag, double feature, int featureDimension) {
        
        Vector<Double> colorMoments = new Vector<Double>(9);
        int size = height * width;
        double[] pixels  = int2double(inImg);
        
        
        double numPixelsAtChannel[] = new double[numChannel];
        for (int i=0; i<numChannel; i++)
            numPixelsAtChannel[i] = 0;
        
        for (int i=0; i<size; i++) {
            for (int j=0; j<numChannel; j++)
                numPixelsAtChannel[j] += pixels[i*3+j];	//Might need to change if not using RGB
        }
        
        // The Average Moments: Ei
        for (int j=0; j<numChannel; j++)
            colorMoments.add(round((double)numPixelsAtChannel[j] / (double)size));
        
        // Calculation of the second moment: variance
        double tempTotal = 0.0;
        for (int j=0; j<numChannel; j++) {
            tempTotal = 0.0;
            for (int i=0; i<size; i++)
                tempTotal +=	(pixels[i*3+j] - ((Double)colorMoments.elementAt(j)).doubleValue()) *
                        (pixels[i*3+j] - ((Double)colorMoments.elementAt(j)).doubleValue());
            // The Variance Moments: Oi
            colorMoments.add(round( Math.sqrt(tempTotal / (double)size) ));
        }
        
        // Calculation of the third moment: skewness
        for (int j=0; j<numChannel; j++) {
            tempTotal = 0.0;
            for (int i=0; i<size; i++)
                tempTotal +=	(pixels[i*3+j] - ((Double)colorMoments.elementAt(j)).doubleValue()) *
                        (pixels[i*3+j] - ((Double)colorMoments.elementAt(j)).doubleValue()) *
                        (pixels[i*3+j] - ((Double)colorMoments.elementAt(j)).doubleValue());
            // The Skewness Moments: Si
            int polarity = 1;
            if (tempTotal < 0)
                polarity = -1;
            colorMoments.add(round( polarity * Math.pow(polarity * tempTotal / (double)size , (1.0/3.0))));
        }
        pixels = null;
        return (colorMoments);
        
    }
    public static double[] int2double(int[] in) {
        double[] result = new double[in.length*3];
        for (int i=0; i<in.length; i++) {
            result[i*3] = ((in[i]>>16&0xff) / 255.0);
            result[i*3+1] = ((in[i]>>8&0xff) / 255.0);
            result[i*3+2] = ((in[i]&0xff) / 255.0);
        }
        return (result);
    }
    @Override
    public double compareFeatureVector(Vector<Double> a, Vector<Double> b) {
        double similarity = 0;
        
        //Array storing the 3 types of moments
        double Ea[] = new double[numChannel];
        double Oa[] = new double[numChannel];
        double Sa[] = new double[numChannel];
        double Eb[] = new double[numChannel];
        double Ob[] = new double[numChannel];
        double Sb[] = new double[numChannel];
        
        // Retrieving the values from the vectors
        for (int i=0; i<numChannel; i++) {
            Ea[i] = ((Double)a.elementAt(i)).doubleValue();
            Oa[i] = ((Double)a.elementAt(i+numChannel)).doubleValue();
            Sa[i] = ((Double)a.elementAt(i+numChannel*2)).doubleValue();
            
            Eb[i] = ((Double)b.elementAt(i)).doubleValue();
            Ob[i] = ((Double)b.elementAt(i+numChannel)).doubleValue();
            Sb[i] = ((Double)b.elementAt(i+numChannel*2)).doubleValue();
        }
        
        for (int i=0; i<numChannel; i++)
            similarity += ( w1 * Math.abs(Ea[i]-Eb[i]) + w2 * Math.abs(Oa[i]-Ob[i]) +
                    w3 * Math.abs(Sa[i]-Sb[i]) );
        
        return (similarity);
    }
    public double getThreshold() {
        return 0.9;
    }
    
    public int getFeatureLength() {
        return 9;
    }
    
    public String getName() {
        return "Color Moment";
    }
    
    public String getCategory() {
        return "Color";
    }
    
    public String getDescription() {
        return "Color Moment";
    }
}
