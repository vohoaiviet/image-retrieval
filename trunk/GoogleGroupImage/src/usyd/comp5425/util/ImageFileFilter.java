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
import java.io.FilenameFilter;

/**
 *
 * @author Yuezhong Zhang
 */
public class ImageFileFilter implements FileFilter {
    
    /** Creates a new instance of ImageFileFilter */
    public ImageFileFilter() {
    }

    public boolean accept(File file, String name) {
        if(file.isFile()){
            name = name.toUpperCase();
            if(name.endsWith(".JPG") || name.endsWith(".JPEG") || name.endsWith("GIF")){
                return true;
            }
        }
        return false;
    }

    public boolean accept(File file) {
        if(file.isFile()){
           String name = file.getName();
           if(name.endsWith("jpg"))
               return true;
        }
        return false;
    }

}
