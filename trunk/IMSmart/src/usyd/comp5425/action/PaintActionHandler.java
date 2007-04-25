/*
 * PaintActionHandler.java
 *
 *  Copyright (C) 2007 COMP5425 Multimedia Storage, Retrieval and Delivery
 *  The School of Information Technology
 *  The University of Sydney
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package usyd.comp5425.action;

import java.io.File;
import javax.imageio.ImageIO;

/**
 *
 * @author Yuezhong Zhang SID:305275631
 */
public class PaintActionHandler {
    
    /** Creates a new instance of PaintActionHandler */
    public PaintActionHandler() {
    }
    public void handleSave(){
        
        
        
        File file = new File("images", "test14.jpng");
      //  ImageIO.write(bufi, "png", file);
    }
}
