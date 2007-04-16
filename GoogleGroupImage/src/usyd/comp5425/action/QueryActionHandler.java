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
    
    private ImageAppFrame frame;
    private  QueryManager manager;
    private  DefaultListModel model;
    private  LRUCache<String,BufferedImage> cache;
    /** Creates a new instance of QueryActionHandler */
    public QueryActionHandler(ImageAppFrame frame) {
        this.frame = frame;
        ActionManager.getInstance().registerActionHandler(this);
        manager = QueryManager.getInstance();
        manager.addQueryListener(this);
        JThumbnailPanel panel =(JThumbnailPanel) frame.getPanel(frame.THUMBNAIL_PANEL);
        model = panel.getListModel();
        panel = null;
        cache = new LRUCache<String,BufferedImage>(200);
        ImageIO.setCacheDirectory(new File(System.getProperty("user.home")));
        ImageIO.setUseCache(true);
    }
    @Action("query-command")
    public void handleQueryAction(){
        SwingWorker worker = new SwingWorker<Object, Object>(){
            @Override
            protected Object doInBackground() throws Exception {
                QueryFormPanel panel =(QueryFormPanel) frame.getPanel(frame.QUERY_FROM_PANEL);
                String path = panel.getSampleFileField().getText();
                if(path.length()!=0){
                    manager.query(new File(path));
                }
                panel = null;
                path  = null;
                return null;
            }
        };
        worker.execute();
    }
    @Action("browse-command")
    public void handleOpenSample(){
        JFileChooser jfc = frame.getFilechooser();
        if(jfc.showOpenDialog(frame) == JFileChooser.APPROVE_OPTION){
            File file = jfc.getSelectedFile();
            QueryFormPanel panel =(QueryFormPanel) frame.getPanel(frame.QUERY_FROM_PANEL);
            panel.getSampleFileField().setText(file.getAbsolutePath());
            panel = null;
        }
    }
    
    public void queryStarted(String text) {
        model.clear();
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                frame.getProgressPane().setText("Querying is in progress...");
                frame.getProgressPane().start();
                frame.setVisiblePanel(frame.THUMBNAIL_PANEL);
            }
        });
    }
    
    public void queryFinished(String text) {
        EventQueue.invokeLater(new Runnable(){
            public void run(){
                frame.getProgressPane().stop();
            }
        });
    }
    
    public void itemFound(final String text) {
        SwingWorker worker = new SwingWorker<BufferedImage, Object>(){
            @Override
            protected BufferedImage doInBackground() throws Exception {
                BufferedImage image = null;
                try {
                    image = cache.get(text);
                    if(image == null){
                        image = ImageIO.read(new File(text));
                        image = GraphicsUtilities.createThumbnailFast(image,128, 100);
                        cache.put(text,image);
                    }
                    return image;
                } catch (IOException ex) {
                    ex.printStackTrace();
                    return image;
                }
            }
            @Override
            public void done(){
                try {
                    BufferedImage image = get();
                    if(image !=null)
                        model.addElement(get());
                    image = null;
                } catch (ExecutionException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        };
        worker.execute();
    }
}
