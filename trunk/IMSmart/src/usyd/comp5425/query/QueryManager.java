/*
 * QueryManager.java
 *
 * Created on 7 April 2007, 14:58
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.query;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import javax.imageio.ImageIO;
import usyd.comp5425.db.DataTap;
import usyd.comp5425.db.DataTapFactory;
import usyd.comp5425.db.FeatureInfo;
import usyd.comp5425.image.FeatureModule;
import usyd.comp5425.image.FeatureModuleFactory;

/**
 *
 * @author Yuezhong Zhang
 */
public class QueryManager {
    private final static QueryManager manager = new QueryManager();
    private QueryManager() {
    }
    public static QueryManager getInstance(){
        return manager;
    }
    public void query(Vector<String> vector,File file){
        if (file == null)
            return;
        this.fireQueryListenerQueryStarted(null);
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
            if(image == null){
                this.fireQueryListenerQueryFinished(null);
                return;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        DataTap dt = DataTapFactory.createDataTap();
        Vector<List<QueryResult>> vect = new Vector<List<QueryResult>>();
        for(String name : vector){
            System.out.println("query with " + name);
            List<QueryResult> list = new ArrayList<QueryResult>();
            FeatureModule module = FeatureModuleFactory.getInstance().getFeatureModule(name);
            Vector<Double> v =  module.getFeatureVector(image);
            synchronized(dt){
                Collection<Integer> ids = dt.getAllFeaturesIDBy(name);
                for(Integer i : ids){
                    FeatureInfo info = dt.getFeatureBy(i);
                    double diff = module.compareFeatureVector(v,info.getVector());
                    if(diff < module.getThreshold()){
                        double percentDiff = Math.abs(module.getThreshold() - diff) /module.getThreshold();
                        list.add(new QueryResult(info.getImage(),percentDiff));
                    }
                    info = null;
                    try {
                        Thread.sleep(300L);
                    } catch (InterruptedException ex) {
                        ex.printStackTrace();
                    }
                }
                ids = null;
            }
            v = null;
            module = null;
            vect.add(list);
        }
        List<QueryResult> tmp = new LinkedList<QueryResult>();
        for(List l : vect){
            if(l.size()>tmp.size())
                tmp = l;
        }
        vect.remove(tmp);
        List<QueryResult> resultList = new ArrayList<QueryResult>();
        for(QueryResult result : tmp){
            boolean ok = false;
            int count = 0;
            for(List<QueryResult> l : vect){
                if(l.contains(result)){
                    count++;
                }
            }
            if(count == vect.size()){
                resultList.add(result);
            }
        }
        this.fireQueryListenerItemFound(resultList);
        this.fireQueryListenerQueryFinished(null);
        tmp = null;
        vect = null;
        System.gc();
    }
    public void luckyQuery(){
        this.fireQueryListenerQueryStarted(null);
        int size = 100;
        List<QueryResult> resultList = new ArrayList<QueryResult>();
        DataTap dt = DataTapFactory.createDataTap();
        List nameList = FeatureModuleFactory.getInstance().getModulesNameList();
        int index = getRandomNumber(nameList.size()-1);
        synchronized(dt){
            ArrayList<Integer> ids = (ArrayList) dt.getAllFeaturesIDBy((String) nameList.get(index));
            nameList = null;
            if(size >=ids.size())
                size = ids.size() -1;
            for (int i = 0; i < size; i++) {
                int pos = (int) (Math.random() * (ids.size()-10));
                FeatureInfo info =  dt.getFeatureBy(ids.get(pos));
                resultList.add(new QueryResult(info.getImage(),0.0d));
            }
            
            ids.clear();
            ids = null;
        }
        fireQueryListenerItemFound(resultList);
        this.fireQueryListenerQueryFinished(null);
        
    }
    public int getRandomNumber(int max){
        return  Math.abs( (int)(Math.random() * max));
    }
    /**
     * Utility field used by event firing mechanism.
     */
    private javax.swing.event.EventListenerList listenerList =  null;
    
    /**
     * Registers QueryListener to receive events.
     * @param listener The listener to register.
     */
    public synchronized void addQueryListener(usyd.comp5425.query.QueryListener listener) {
        if (listenerList == null ) {
            listenerList = new javax.swing.event.EventListenerList();
        }
        listenerList.add(usyd.comp5425.query.QueryListener.class, listener);
    }
    
    /**
     * Removes QueryListener from the list of listeners.
     * @param listener The listener to remove.
     */
    public synchronized void removeQueryListener(usyd.comp5425.query.QueryListener listener) {
        listenerList.remove(usyd.comp5425.query.QueryListener.class, listener);
    }
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireQueryListenerQueryStarted(String text) {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i]==usyd.comp5425.query.QueryListener.class) {
                ((usyd.comp5425.query.QueryListener)listeners[i+1]).queryStarted(text);
            }
        }
    }
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireQueryListenerQueryFinished(String text) {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i]==usyd.comp5425.query.QueryListener.class) {
                ((usyd.comp5425.query.QueryListener)listeners[i+1]).queryFinished(text);
            }
        }
    }
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireQueryListenerItemFound(List<QueryResult> resultList) {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i]==usyd.comp5425.query.QueryListener.class) {
                ((usyd.comp5425.query.QueryListener)listeners[i+1]).itemFound(resultList);
            }
        }
    }
    
}