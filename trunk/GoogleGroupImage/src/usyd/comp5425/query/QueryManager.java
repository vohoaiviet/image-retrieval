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
import java.util.Collection;
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
    public void query(File file){
        if (file == null)
            return;
        QueryEvent event = new QueryEvent();
        this.fireQueryListenerQueryStarted(event);
        BufferedImage image = null;
        try {
            image = ImageIO.read(file);
            if(image == null){
                this.fireQueryListenerQueryFinished(event);
                return;
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        DataTap dt = DataTapFactory.createDataTap();
        FeatureModule module = FeatureModuleFactory.getInstance().getFeatureModule("AverageRGB");
        Vector<Double> features = module.getFeatureVector(image);
        image = null;
        Collection<Integer> ids = dt.getAllFeaturesIDBy(module.getName());
        for(Integer i : ids ){
            FeatureInfo info = dt.getFeatureBy(i);
            double diff = module.compareFeatureVector(features, info.getVector());
            if(diff < module.getThreshold()){
                this.fireQueryListenerItemFound(new QueryEvent(info.getImage()));
            }
            info = null;
        }
        this.fireQueryListenerQueryFinished(event);
        ids = null;
        features = null;
        module = null;
        dt = null;
        
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
    private void fireQueryListenerQueryStarted(usyd.comp5425.query.QueryEvent event) {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i]==usyd.comp5425.query.QueryListener.class) {
                ((usyd.comp5425.query.QueryListener)listeners[i+1]).queryStarted(event);
            }
        }
    }
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireQueryListenerQueryFinished(usyd.comp5425.query.QueryEvent event) {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i]==usyd.comp5425.query.QueryListener.class) {
                ((usyd.comp5425.query.QueryListener)listeners[i+1]).queryFinished(event);
            }
        }
    }
    
    /**
     * Notifies all registered listeners about the event.
     *
     * @param event The event to be fired
     */
    private void fireQueryListenerItemFound(usyd.comp5425.query.QueryEvent event) {
        if (listenerList == null) return;
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i]==usyd.comp5425.query.QueryListener.class) {
                ((usyd.comp5425.query.QueryListener)listeners[i+1]).itemFound(event);
            }
        }
    }
    
}