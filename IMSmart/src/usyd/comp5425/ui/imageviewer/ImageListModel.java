/*
 * ImageListModel.java
 *
 * Created on 24 April 2007, 15:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.ui.imageviewer;

import java.util.ArrayList;
import java.util.List;
import javax.swing.AbstractListModel;
import usyd.comp5425.query.QueryResult;

/**
 *
 * @author Yuezhong Zhang
 */
public class ImageListModel extends AbstractListModel {
    
    private ArrayList<QueryResult> list;
    private int pageSize;
    private int pageOffset;
    
    public ImageListModel() {
        this.pageSize   = 50;
        this.pageOffset = 0;
        list = new ArrayList<QueryResult> ();
    }
    
    public int getSize() {
        return Math.min(getPageSize(), list.size());
    }
    
    public Object getElementAt(int row) {
        int realRow = row + (getPageOffset() * getPageSize());
        return list.get(realRow);
    }
    public int getPageOffset() {
        return pageOffset;
    }
    
    public void setPageOffset(int pageOffset) {
        this.pageOffset = pageOffset;
    }
    
    public int getPageSize() {
        return pageSize;
    }
    
    public void setPageSize(int s) {
        if (s == pageSize) { return; }
        int oldPageSize = pageSize;
        pageSize = s;
        pageOffset = (oldPageSize * pageOffset) / pageSize;
        fireListDataChanged();
    }
    public int getPageCount(){
        return (int)Math.ceil((double)list.size() / pageSize);
    }
    public int getRealRowCount() {
        return  list.size();
    }
    public void pageUp(){
        if (pageOffset > 0) {
            pageOffset--;
            fireListDataChanged();
        }
    }
    public void pageDown(){
        if (pageOffset < getPageCount() - 1) {
            pageOffset++;
            fireListDataChanged();
        }
    }
    public boolean canPageUp(){
        if(getPageOffset()==0)
            return false;
        return true;
    }
    public boolean canPageDown(){
        return getPageOffset() < getPageCount()-1 ;
    }
    private void fireListDataChanged(){
        fireIntervalRemoved(this, 0, getSize()-1);
        fireIntervalAdded(this, 0, getSize()-1);
    }
    public void clear() {
        list.clear();
        fireIntervalRemoved(this, 0, getSize());
    }
    public void add(QueryResult obj){
        list.add(obj);
        fireListDataChanged();
        
    }
    public void add(List<QueryResult> rs){
        list.addAll(rs);
        fireListDataChanged();
    }
}
