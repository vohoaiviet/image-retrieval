/*
 * ImageFileFilter.java
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

package usyd.comp5425.util;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Yuezhong Zhang  SID:305275631
 */
public class ImageFileFilter implements FileFilter {
    
    /** Creates a new instance of ImageFileFilter */
    private String exts [] =  {".jpg",".png",".jpeg",".gif"};
    public ImageFileFilter() {
    }
    
    public boolean accept(File file) {
        if(file.isDirectory())
            return true;
        if(file.isFile()){
            String name = file.getName().toLowerCase();
            for(String ext : exts){
                if(name.endsWith(ext))
                    return true;
            }
            
        }
        return false;
    }
}
