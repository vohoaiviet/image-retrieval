/*
 * QueryActionHandler.java
 *
 * Created on 9 April 2007, 15:11
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.action;

import com.sun.jaf.ui.Action;
import com.sun.jaf.ui.ActionManager;
import java.awt.EventQueue;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.List;
import java.util.concurrent.ExecutionException;
import javax.imageio.ImageIO;
import javax.swing.DefaultListModel;
import javax.swing.JFileChooser;
import org.jdesktop.swingworker.SwingWorker;
import usyd.comp5425.query.QueryListener;
import usyd.comp5425.query.QueryManager;
import usyd.comp5425.ui.ImageAppFrame;
import usyd.comp5425.ui.QueryFormPanel;
import usyd.comp5425.ui.imageviewer.JThumbnailPanel;
import usyd.comp5425.util.GraphicsUtilities;
import usyd.comp5425.util.LRUCache;

/**
 *
 * @author Yuezhong Zhang
 */
public class QueryActionHandler implements QueryListener{
    
    private  ImageAppFrame frame;
    private  JThumbnailPanel thumbPanel;
    private  QueryManager manager;
    private  LRUCache<String,BufferedImage> cache;
    private  String pattern = "Found image about {0} in {1} seconds";
    private  long  startTime;
    
    public QueryActionHandler(ImageAppFrame frame) {
        this.frame = frame;
        ActionManager.getInstance().registerActionHandler(this);
        manager = QueryManager.getInstance();
        manager.addQueryListener(this);
        cache = new LRUCache<String,BufferedImage>(200);
        ImageIO.setCacheDirectory(new File(System.getProperty("user.home")));
        ImageIO.setUseCache(true);
        thumbPanel =(JThumbnailPanel) frame.getPanel(frame.THUMBNAIL_PANEL);
    }
    @Action("query-command")
    public void handleQueryAction(){
        SwingWorker worker = new SwingWorker<Object, Object>(){
            @Override
            protected Object doInBackground() throws Exception {
                QueryFormPanel panel =(QueryFormPanel) frame.getPanel(frame.QUERY_FROM_PANEL);
                File file = panel.getSampleFile();
                if(file !=null) {
                    manager.query(panel.getSelectedFeatures(),file);
                }
                panel = null;
                return null;
            }
        };
        worker.execute();
    }
    @Action("browse-command")
    public void handleOpenSample(){
        JFileChooser jfc = new JFileChooser();
        if(jfc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION){
            File file = jfc.getSelectedFile();
            QueryFormPanel panel =(QueryFormPanel) frame.getPanel(frame.QUERY_FROM_PANEL);
            panel.setSampleFile(file);
            panel = null;
        }
        jfc = null;
    }
    
    public void queryStarted(String text) {
        startTime = System.currentTimeMillis();
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                thumbPanel.getListModel().clear();
                frame.getProgressPane().setText("Querying is in progress...");
                frame.getProgressPane().start();
                frame.setVisiblePanel(frame.THUMBNAIL_PANEL);
            }
        });
    }
    public void queryFinished(String text) {
    }
    @Action("imlucky-command")
    public void handleLuckyBrowse(){
        ActionManager.getInstance().setEnabled("imlucky-command", false);
        ActionManager.getInstance().setEnabled("query-command", false);
        SwingWorker worker = new SwingWorker<Object, Object>(){
            @Override
            protected Object doInBackground() throws Exception {
                manager.luckyQuery();
                return null;
            }
            @Override
            public void done(){
                ActionManager.getInstance().setEnabled("imlucky-command", true);
                ActionManager.getInstance().setEnabled("query-command", true);
            }
        };
        worker.execute();
    }
    
    public void itemFound(final List<String> list) {
        SwingWorker worker = new SwingWorker<DefaultListModel, String>(){
            @Override
            protected DefaultListModel doInBackground() throws Exception {
                StringBuffer userdir = new StringBuffer(System.getProperty("user.dir"));
                userdir.append(File.separatorChar);
                userdir.append("images");
                userdir.append(File.separatorChar);
                DefaultListModel model = new DefaultListModel();
                File file;
                for(String text : list){
                    BufferedImage image = null;
                    synchronized(cache) {
                        if(text.indexOf(":")>0)
                            file = new File(text);
                        else
                            file = new File(userdir.toString(),text);
                        image = cache.get(file.getAbsolutePath());
                        publish(file.toString());
                        if(image == null){
                            try {
                                if(text.indexOf(":")>0)
                                    image = ImageIO.read(new File(text));
                                else
                                    image = ImageIO.read(new File(userdir.toString(),text));
                                image = GraphicsUtilities.createThumbnailFast(image,128, 100);
                                cache.put(file.getAbsolutePath(),image);
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            }
                        }
                        if(image !=null)
                            model.addElement(image);
                    }
                }
                list.clear();
                userdir = null;
                System.gc();
                return model;
            }
            @Override
            protected void done(){
                try {
                    frame.getProgressPane().stop();
                    thumbPanel.setListModel(get());
                    int size = thumbPanel.getListModel().size();
                    long duration = (System.currentTimeMillis() - startTime) /1000;
                    String text = MessageFormat.format(pattern, new Object []{size,duration});
                    frame.setStatusText(text);
                    System.out.println(text);
                    
                } catch (ExecutionException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
            @Override
            protected void process(List<String> txts){
                for(String txt : txts){
                    frame.setStatusText("Found image :" + txt);
                }
            }
        };
        worker.execute();
    }
}
