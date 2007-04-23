/*
 * ImageFileFilter.java
 *
 * Created on 14 April 2007, 18:02
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.util;

import java.io.File;
import java.io.FileFilter;

/**
 *
 * @author Yuezhong Zhang
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
