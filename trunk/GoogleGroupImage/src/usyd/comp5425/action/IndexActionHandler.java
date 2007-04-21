/*
 * IndexActionHandler.java
 *
 * Created on 14 April 2007, 17:37
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.action;

import com.sun.jaf.ui.Action;
import com.sun.jaf.ui.ActionManager;
import java.io.File;
import java.util.Collection;
import java.util.LinkedList;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import org.jdesktop.swingworker.SwingWorker;
import usyd.comp5425.db.DataTap;
import usyd.comp5425.db.DataTapFactory;
import usyd.comp5425.db.FeatureInfo;
import usyd.comp5425.extraction.FeatureExtractManager;
import usyd.comp5425.ui.ImageAppFrame;
import usyd.comp5425.util.ImageFileFilter;

/**
 *
 * @author Yuezhong Zhang
 */
public class IndexActionHandler {
    private Logger logger = Logger.getLogger(IndexActionHandler.class.getName());
    private ImageFileFilter filter = new ImageFileFilter();
    private ImageAppFrame frame;
    private SwingWorker worker;
    private LinkedList<File> list;
    private boolean started = false;
    /** Creates a new instance of IndexActionHandler */
    public IndexActionHandler(ImageAppFrame frame) {
        this.frame = frame;
        ActionManager.getInstance().registerActionHandler(this);
        list = new LinkedList<File>();
    }
    @Action("open-command")
    public void handleIndexAction(){
        JFileChooser jfc = new JFileChooser();
        jfc.setFileSelectionMode(jfc.DIRECTORIES_ONLY);
        if(jfc.showOpenDialog(frame)== JFileChooser.APPROVE_OPTION){
            File file  = jfc.getSelectedFile();
            list.add(file);
        }
        if(worker == null && (!list.isEmpty())){
            worker = new SwingWorker<Object,Object>(){
                protected Object doInBackground() throws Exception {
                    System.out.println("start indexing images");
                    ActionManager.getInstance().setEnabled("open-command",false);
                    FeatureExtractManager manager = new FeatureExtractManager();
                    DataTap  tap = DataTapFactory.createDataTap();
                    while(true){
                        if(list.isEmpty() || worker.isCancelled())
                            break;
                        File file = list.removeFirst();
                        if(file.isDirectory()){
                            File files [] = file.listFiles(filter);
                            for(File f : files)
                                list.addLast(f);
                        }else{
                            boolean  failed = false;
                            Collection<FeatureInfo> features = manager.extractFeature(file);
                            for(FeatureInfo info : features){
                                boolean isAdded = tap.add(info);
                                if(!isAdded) {
                                    failed = true;
                                }
                            }
                            if(!failed){
                                System.out.println("Successfully indexed "+ file);
                            }else {
                                
                                System.out.println("Failed to index "+ file);
                            }
                        }
                        Thread.sleep(200L);
                    }
                    System.out.println("finished");
                    return null;
                }
                @Override
                public void done(){
                    
                    worker = null;
                    ActionManager.getInstance().setEnabled("open-command",true);
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
}
