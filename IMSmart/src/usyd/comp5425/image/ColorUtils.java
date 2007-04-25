/*
 * ColorUtils.java
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

package usyd.comp5425.image;

/**
 *
 * @author Yuezhong Zhang   SID:305275631
 */
public class ColorUtils {
    
    /** Creates a new instance of ColorUtils */
    public ColorUtils() {
    }
    public static int [] rgbToGrayScale(int [] rgb, int grayscaleLevel){
        int result [] = new int[rgb.length];
        int i = 0;
        
        for (i = 0; i < rgb.length; i++) {
            // Y = 0.299R + 0.587G + 0.114B
            result[i] = (int)((0.299 * (rgb[i]>>16 & 0xff) + 0.587 * (rgb[i]>>8 & 0xff)
            + 0.114 * (rgb[i] & 0xff)) / 256 * grayscaleLevel + 0.5);
        }
        return result;
    }
}
