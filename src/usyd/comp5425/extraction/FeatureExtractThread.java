/*
 * FeatureExtractThread.java
 *
 * Created on 14 April 2007, 16:40
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.extraction;

import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Yuezhong Zhang
 */
public class FeatureExtractThread  implements Runnable {
    
    /** Creates a new instance of FeatureExtractThread */
    public FeatureExtractThread(String name, BlockingQueue queue) {
    }
    
    /**
     * Holds value of property stop.
     */
    private boolean stop;
    
    /**
     * Getter for property stop.
     * @return Value of property stop.
     */
    public boolean isStop() {
        return this.stop;
    }
    
    /**
     * Setter for property stop.
     * @param stop New value of property stop.
     */
    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public void run() {
    }
    
}
