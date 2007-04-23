/*
 * FeatureExtractionTest.java
 *
 * Created on 14 April 2007, 19:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.extraction;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Yuezhong Zhang
 */
public class FeatureExtractionTest {
    
    /** Creates a new instance of FeatureExtractionTest */
    public FeatureExtractionTest() {
    }
    public static void main(String[] args) {
        try {
            
            BufferedImage image = ImageIO.read(new File("C:\\4detkz7.jpg"));
            System.out.println("read");
            int [] rgb = image.getRGB(0,0,image.getWidth(),image.getHeight(),null,0,image.getWidth());
            System.out.println(rgb);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        
    }
    
}
