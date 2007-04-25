/*
 * LocalColorHistogram.java
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
public class LocalColorHistogram extends FeatureModule{
    
    /** Creates a new instance of LocalColorHistogram */
    public LocalColorHistogram() {
    }
    
    public Vector<Double> getFeatureVector(int[] inImg, int height, int width, int[] maskImg, int flag, double feature, int featureDimension) {
        int gridCount = 4;
        int row = gridCount * gridCount;
        Vector<Double> localColorHistogram = new Vector<Double>(row * 64);
        int size    =  width * height;
        int i = 0;
        int j = 0;
        int k = 0;
        double[][] histogram = new double[row][64];
        //initialize all values to 0.0
        for(i=0 ; i<row ; i++){
            histogram[i] = new double[64];
            for(j=0 ; j<64 ; j++){
                histogram[i][j] = 0.0d;
            }
        }
        for(i=0 ; i<size ; i++){
            k = (inImg[i]>>18 & 0x30) | (inImg[i]>>12 & 0xc) | (inImg[i]>>6 & 0x3);
            j =	getGrid(i, width, height, gridCount);
            histogram[j][k] += 1.0;
        }
        for(i=0 ; i<gridCount ; i++)
            for(j=0 ; j<64 ; j++)
                localColorHistogram.add(round(((double)histogram[i][j]/(size/gridCount))));
        histogram = null;
        return localColorHistogram;
        
    }
    private int getGrid(double j, double width, double height, int gridCount){
        double localWidth = width/gridCount;
        double localHeight = height/gridCount;
        int cell = (int)(j/width/localHeight)*gridCount + (int)((j % width)/localWidth);
        return cell;
    }
    public double getThreshold() {
        return 0.35d;
    }
    
    public int getFeatureLength() {
        return 1024;
    }
    
    public String getName() {
        return "Local Color Histogram";
    }
    
    public String getCategory() {
        return "Color";
    }
    
    public String getDescription() {
        return "Compute color histogram for an image";
    }
    
}
