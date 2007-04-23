/*
 * FileSearchThread.java
 *
 * Created on 14 April 2007, 17:01
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.extraction;

import java.io.File;
import java.util.Hashtable;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;

/**
 *
 * @author Yuezhong Zhang
 */
public class FileSearchThread implements Runnable {
    private BlockingQueue queue;
    // private BlockingQueue files;
    private String name;
    private Hashtable<File,Boolean> files = new Hashtable<File,Boolean>();
    public  LinkedList<File> list = new LinkedList<File>();
    public  boolean includeSubFolder = false;
    /** Creates a new instance of FileSearchThread */
    public FileSearchThread(String name, BlockingQueue queue) {
        this.name = name;
        this.queue = queue;
    }
    public void run(){
        while(true){
            if(Thread.currentThread().isInterrupted()){
                return;
            }
            if(list.isEmpty()){
                try {
                    wait();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            File file = list.removeFirst();
            if(file !=null){
                File [] files = file.listFiles();
                for(File f: files){
                    if(f.isFile()){
                        try {
                            queue.put(f);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                            return;
                        }
                    }else {
                        list.add(f);
                    }
                }
            }
            
        }
    }
    public void addFile(File file, boolean includeSubFolder){
        if(file.isDirectory())
            list.add(file);
        this.includeSubFolder = includeSubFolder;
        notifyAll();
    }
}
