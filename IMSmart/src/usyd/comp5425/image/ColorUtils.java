/*
 * ColorUtils.java
 *
 * Created on 31 March 2007, 15:49
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.image;

/**
 *
 * @author Yuezhong Zhang
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
