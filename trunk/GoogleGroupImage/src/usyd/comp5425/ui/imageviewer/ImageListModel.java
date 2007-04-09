/*
 * ImageListModel.java
 *
 * Created on 7 April 2007, 14:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.ui.imageviewer;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;

/**
 *
 * @author Yuezhong Zhang
 */
public class ImageListModel extends AbstractListModel{
    
    /** Creates a new instance of ImageListModel */
    private ArrayList list = new ArrayList();
    public ImageListModel() {
    }
    public int getSize() {
        return list.size();
    }
    
    public Object getElementAt(int index) {
        return list.get(index);
    }
    public void add(Object obj){
        this.list.add(obj);
        int index = list.size();
        super.fireIntervalAdded(this,list.size()-1,list.size());
    }
    public void add(List list){
        int index = list.size();
        this.list.addAll(list);
        super.fireIntervalAdded(this,this.list.size() -list.size(),this.list.size());
    }
    public void remove(Object obj){
        this.list.remove(obj);
        super.fireIntervalRemoved(this,list.size()-1,list.size());
    }
    public void clear(){
        this.list.clear();
        super.fireContentsChanged(this,0,0);
    }
}
