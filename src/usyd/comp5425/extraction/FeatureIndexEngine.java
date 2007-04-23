/*
 * FeatureIndexEngine.java
 *
 * Created on 14 April 2007, 16:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.extraction;

import java.io.File;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * @author Yuezhong Zhang
 */
public class FeatureIndexEngine {
    public  ExecutorService service;
    public  FileSearchThread  thread;
    public BlockingQueue queue;
    /** Creates a new instance of FeatureIndexEngine */
    public FeatureIndexEngine() {
        queue = new LinkedBlockingQueue(300);
        service = Executors.newFixedThreadPool(10);
        
    }
    public void start(){
        stop();
        for(int i=0;i<5; i++){
            service.execute(new FeatureExtractThread("FeatureExtractThread " + i, queue));
        }
        thread = new FileSearchThread("FileSearchThread",queue);
        service.execute(thread);
    }
    public void stop(){
        service.shutdownNow();
    }
    public void add(File file, boolean includeSubFolder){
        thread.addFile(file,includeSubFolder);
    }
}
