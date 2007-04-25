/*
 * IndexActionHandler.java
 
 *  Copyright (C) 2007 COMP5425 Multimedia Storage, Retrieval and Delivery
 *  The School of Information Technology
 *  The University of Sydney
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package usyd.comp5425.action;

import com.sun.jaf.ui.Action;
import com.sun.jaf.ui.ActionManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import org.jdesktop.swingworker.SwingWorker;
import usyd.comp5425.db.DataTap;
import usyd.comp5425.db.DataTapFactory;
import usyd.comp5425.db.FeatureInfo;
import usyd.comp5425.image.FeatureModule;
import usyd.comp5425.image.FeatureModuleFactory;
import usyd.comp5425.ui.ImageAppFrame;
import usyd.comp5425.util.ImageFileFilter;

/**
 *
 * @author Yuezhong Zhang SID:305275631
 */
public class IndexActionHandler {
    private Logger logger = Logger.getLogger(IndexActionHandler.class.getName());
    private ImageFileFilter filter = new ImageFileFilter();
    private ImageAppFrame frame;
    private SwingWorker worker;
    private LinkedList<File> list;
    private boolean started = false;
    private int length;
    private StringBuffer userdir;
    /** Creates a new instance of IndexActionHandler */
    public IndexActionHandler(ImageAppFrame frame) {
        this.frame = frame;
        ActionManager.getInstance().registerActionHandler(this);
        list = new LinkedList<File>();
        userdir = new StringBuffer(System.getProperty("user.dir"));
        userdir.append(File.separatorChar);
        userdir.append("images");
        userdir.append(File.separatorChar);
    }
    @Action("open-command")
    public void handleIndexAction(){
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(jfc.FILES_AND_DIRECTORIES);
        if(jfc.showOpenDialog(frame)== JFileChooser.APPROVE_OPTION){
            File file  = jfc.getSelectedFile();
            list.add(file);
            file = null;
        }
        jfc = null;
        if(worker == null && (!list.isEmpty())){
            ActionManager.getInstance().setEnabled("open-command",false);
            worker = new SwingWorker<Object,String>(){
                protected Object doInBackground() throws Exception {
                    System.out.println("start indexing images");
                    DataTap  tap = DataTapFactory.createDataTap();
                    while(true){
                        if(list.isEmpty() || worker.isCancelled()){
                            break;
                        }
                        File file = list.removeFirst();
                        if(file.isDirectory()){
                            File files [] = file.listFiles(filter);
                            for(File f : files)
                                list.addLast(f);
                        }else{
                            boolean exist = false;
                            synchronized(tap){
                                exist = tap.exists(getImagePath(file));
                            }
                            if(!exist){
                                boolean  failed = false;
                                publish("Processing file " + file.getName());
                                for(FeatureInfo info : extractFeature(file)){
                                    synchronized(tap){
                                        boolean isAdded = tap.add(info);
                                        if(!isAdded) {
                                            failed = true;
                                        }
                                    }
                                    info = null;
                                }
                                if(!failed){
                                    publish("Successfully indexed "+ file.getName());
                                }else {
                                    publish("Failed to index "+ file.getName());
                                }
                            }else {
                                publish("features already in database " + file.getName());
                            }
                        }
                        try {
                            Thread.sleep(300L);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                    }
                    System.out.println("Finished");
                    return null;
                }
                @Override
                protected  void done(){
                    worker = null;
                    frame.setStatusText("Finished");
                    ActionManager.getInstance().setEnabled("open-command",true);
                }
                @Override
                protected  void process(List<String> chunks){
                    for(String text : chunks){
                        frame.setStatusText(text);
                    }
                }
            };
            worker.execute();
        }
    }
    public void handleCancelAction(){
        list.clear();
        if(worker !=null){
            worker.cancel(true);
            worker = null;
        }
    }
    public Collection <FeatureInfo> extractFeature(File file){
        FeatureModuleFactory factory = FeatureModuleFactory.getInstance();
        Vector<FeatureInfo> features = new Vector<FeatureInfo>(factory.getNumberOfMoudle());
        BufferedImage image = readImage(file);
        if(image == null)
            return features;
        for (Enumeration e = factory.getModulesName(); e.hasMoreElements() ;) {
            String moduleName = (String) e.nextElement();
            frame.setStatusText("Extract features with " + moduleName);
            FeatureModule module = factory.getFeatureModule(moduleName);
            FeatureInfo info = new FeatureInfo();
            info.setFeatureName(moduleName);
            info.setImage(getImagePath(file));
            info.setVector(module.getFeatureVector(image));
            features.add(info);
            moduleName = null;
            info = null;
            module = null;
        }
        image = null;
        factory = null;
        features.trimToSize();
        return features;
    }
    public BufferedImage readImage(File file){
        try {
            return ImageIO.read(file);
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    public String getImagePath(File file){
        String filename = file.getAbsolutePath();
        if(filename.indexOf(userdir.toString())> -1 )
            return filename.substring(userdir.length());
        else
            return  filename;
    }
    
}
