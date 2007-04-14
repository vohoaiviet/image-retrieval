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
    private ImageAppFrame frame;
    private ImageFileFilter filter = new ImageFileFilter();
    /** Creates a new instance of IndexActionHandler */
    public IndexActionHandler(ImageAppFrame frame) {
        this.frame = frame;
        ActionManager.getInstance().registerActionHandler(this);
    }
    @Action("open-command")
    public void handleIndexAction(){
        JFileChooser jfc = frame.getFilechooser();
        jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        if(jfc.showOpenDialog(frame)== JFileChooser.APPROVE_OPTION){
            File folder  = jfc.getSelectedFile();
            if(folder != null){
                System.setProperty("indexFile",folder.getAbsolutePath());
                folder = null;
            }else {
                return;
            }
        }else {
            return;
        }
        jfc = null;
        SwingWorker worker = new SwingWorker<Object, File>(){
            protected Object doInBackground() throws Exception {
                System.out.println("start executing");
                File folder = new File(System.getProperty("indexFile"));
                System.getProperties().remove("indexFile");
                if(folder.isDirectory()){
                    File [] files = folder.listFiles(filter);
                    System.out.println("files=" + files.length);
                    if(files.length > 0) {
                        System.out.println("start runing");
                        logger.fine("start processing");
                        FeatureExtractManager manager = new FeatureExtractManager();
                        DataTap  tap = DataTapFactory.createDataTap();
                        for(File file : files){
                            publish(file);
                            logger.fine("start processing " + file);
                            Collection<FeatureInfo> features = manager.extractFeature(file);
                            for(FeatureInfo info : features)
                                tap.add(info);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                }
                return null;
            }
            protected void process(File file){
                frame.setStatusText("Processing :" + file.getAbsolutePath());
            }
            
        };
        worker.execute();
        
    }
    public void handleCancelAction(){
        
    }
}
