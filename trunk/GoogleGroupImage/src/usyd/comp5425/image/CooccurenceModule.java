/*
 * CooccurenceModule.java
 *
 * Created on 31 March 2007, 15:45
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
public class CooccurenceModule  extends FeatureModule{
    final int GRAY_LEVEL = 256,	// number of grayscale level (8 bits)
            NUM_TYPE = 5,	// number of analyze method for cooccurence matrix
            NUM_SHIFT = 4;	// number of XY shift
    final int[][] XYSHIFT = {{1,1},{4,4},{9,9},{16,16}};
    
    /** Creates a new instance of CooccurenceModule */
    public CooccurenceModule() {
    }
    
    public Vector<Double> getFeatureVector(int[] inImg, int height, int width, int[] maskImg, int flag, double feature, int featureDimension) {
        double coMatrix[][] = new double[NUM_SHIFT][];
        Vector<Double> v = new Vector<Double>(this.getFeatureLength());
        int i;
        
        // Get the cooccurence matrices for the default parameters
        for (i = 0; i < NUM_SHIFT; i++) {
            coMatrix[i] = getCooccurenceMatrix(inImg,height,width, XYSHIFT[i][0], XYSHIFT[i][1]);
        }
        // By using the cooccurence matrices to calculate the feature vector
        // using different type of statistical analysis
        for (i = 0; i < NUM_SHIFT; i++) {
            // maximum probability
            v.add(round(analyseMatrix(coMatrix[i], 1)));
            // first order element difference moment
            v.add(round(analyseMatrix(coMatrix[i], 2)));
            // first order inverse element difference moment
            v.add(round(analyseMatrix(coMatrix[i], 5)));
            // entropy
            v.add(round(analyseMatrix(coMatrix[i], 8)));
            // uniformity
            v.add(round(analyseMatrix(coMatrix[i], 9)));
        }
        
        return v;
        
        
    }
    private double[] getCooccurenceMatrix(int [] pixels,int height,int width, int xshift, int yshift) {
        int i, j, total = 0;
        int gray1, gray2;
        //int width = image.getWidth(), height = image.getHeight();
        //int pixels[] = image.getPackedRGBPixel();
        int[] gray_pixels = ColorUtils.rgbToGrayScale(pixels, GRAY_LEVEL);
        double coMatrix[] = new double[GRAY_LEVEL * GRAY_LEVEL];
        
        // initialize the cooccurence matrix
        for (i = 0; i < GRAY_LEVEL; i++) {
            for (j = 0; j < GRAY_LEVEL; j++) {
                coMatrix[i*GRAY_LEVEL+j] = 0;
            }
        }
        
        // for each pixel compare the gray level of another pixel
        for (i = 0; i < height; i++) {
            for (j = 0; j < width; j++) {
                if (!((j + xshift >= width) || (j + xshift < 0) ||
                        (i + yshift >= height) || (i + yshift < 0))) {
                    // Get the gray level of the first pixel
                    gray1 = gray_pixels[i*width + j];
                    // Get the gray level of the second pixel
                    gray2 = gray_pixels[(i+yshift) * width + j + xshift];
                    // update the cooccurence matrix
                    coMatrix[gray1*GRAY_LEVEL+gray2]++;
                    total++;
                }
            }
        }
        
        // Convert the number of occurence in the matrix to joint probability
        for (i = 0; i < GRAY_LEVEL; i++) {
            for (j = 0; j < GRAY_LEVEL; j++) {
                coMatrix[i*GRAY_LEVEL+j] = (double)coMatrix[i*GRAY_LEVEL+j] / (double)total;
            }
        }
        
        return coMatrix;
    }
    /*
     * This method analyzes the matrix using the inputted type of statistical method
     *
     * @param coMatrix the cooccurence matrix
     * @param type
     * 1 maximum probability
     * 2 first order element difference moment
     * 3 second order element difference moment
     * 4 third order element difference moment
     * 5 first order inverse element difference moment
     * 6 second order inverse element difference moment
     * 7 third order inverse element difference moment
     * 8 entropy
     * 9 uniformity
     * @return a double value representing corresponding moment
     */
    public double analyseMatrix(double[] coMatrix, int type) {
        double sum = 0;
        int i,j;
        switch (type) {
            // maximum probability
            case 1:
                for (i = 0; i < GRAY_LEVEL; i++) {
                    for (j = 0; j < GRAY_LEVEL; j++) {
                        if (coMatrix[i*GRAY_LEVEL+j]>sum) {
                            sum = coMatrix[i*GRAY_LEVEL+j];
                        }
                    }
                }
                break;
                
                // first order element difference moment
            case 2:
                for (i = 0; i < GRAY_LEVEL; i++) {
                    for (j = 0; j < 256; j++) {
                        sum += coMatrix[i * GRAY_LEVEL + j] * Math.abs(i-j);
                    }
                }
                break;
                
                // second order element difference moment
            case 3:
                for (i = 0; i < GRAY_LEVEL; i++) {
                    for (j = 0; j < GRAY_LEVEL; j++) {
                        sum += coMatrix[i*GRAY_LEVEL+j] * Math.pow(Math.abs(i-j), 2);
                    }
                }
                break;
                
                // third order element difference moment
            case 4:
                for (i = 0; i < GRAY_LEVEL; i++) {
                    for (j = 0; j < GRAY_LEVEL; j++) {
                        sum += coMatrix[i*256+j] * Math.pow(Math.abs(i-j), 3);
                    }
                }
                break;
                
                // first order inverse element difference moment
            case 5:
                for (i = 0; i < GRAY_LEVEL; i++) {
                    for (j = 0; j < GRAY_LEVEL; j++) {
                        sum += coMatrix[i*GRAY_LEVEL+j] / (1 + Math.abs(i-j));
                    }
                }
                break;
                
                // second order inverse element difference moment
            case 6:
                for (i = 0; i < GRAY_LEVEL; i++) {
                    for (j = 0; j < GRAY_LEVEL; j++) {
                        sum += coMatrix[i*GRAY_LEVEL+j] / (1 + Math.pow(Math.abs(i-j),2));
                    }
                }
                break;
                
                // third order inverse element difference moment
            case 7:
                for (i = 0; i < GRAY_LEVEL; i++) {
                    for (j = 0; j < GRAY_LEVEL; j++) {
                        sum += coMatrix[i*GRAY_LEVEL+j] / (1 + Math.pow(Math.abs(i-j), 3));
                    }
                }
                break;
                
                // entropy
            case 8:
                for (i = 0; i < GRAY_LEVEL; i++) {
                    for (j = 0; j < GRAY_LEVEL; j++) {
                        if (coMatrix[i * GRAY_LEVEL + j] != 0) {
                            sum += coMatrix[i*GRAY_LEVEL+j] * Math.log(coMatrix[i*GRAY_LEVEL+j]);
                        }
                    }
                }
                sum = -sum;
                break;
                
                // uniformity
            case 9:
                for (i = 0; i < GRAY_LEVEL; i++) {
                    for (j = 0; j < GRAY_LEVEL; j++) {
                        sum += coMatrix[i*GRAY_LEVEL+j] * coMatrix[i*GRAY_LEVEL+j];
                    }
                }
                break;
                
            default:
                break;
        }
        
        return sum;
    }
    
    public double getThreshold() {
        return 4.0d;
    }
    
    public int getFeatureLength() {
        return NUM_TYPE * NUM_SHIFT;
    }
    
    public String getName() {
        return "Co-occurrence";
    }
    
    public String getCategory() {
        return "Texture";
    }
    
    public String getDescription() {
        return "Feature extraction by analysing the cooccurence matrix.";
    }
    
}
