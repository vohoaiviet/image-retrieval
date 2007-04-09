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
import java.util.ArrayList;
import java.util.List;
import org.jdesktop.swingworker.SwingWorker;
import usyd.comp5425.query.QueryManager;
import usyd.comp5425.ui.ImageAppFrame;
import usyd.comp5425.ui.imageviewer.ImageListModel;

/**
 *
 * @author Yuezhong Zhang
 */
public class QueryActionHandler {
    
    private ImageAppFrame frame;
    /** Creates a new instance of QueryActionHandler */
    public QueryActionHandler(ImageAppFrame frame) {
        this.frame = frame;
        ActionManager.getInstance().registerActionHandler(this);
    }
    @Action("query-command")
    public void handleQueryAction(){
        frame.getProgressPane().setText("Start searching ...");
        frame.getProgressPane().start();
        SwingWorker worker = new SwingWorker<List, Object>(){
            @Override
            protected List doInBackground() throws Exception {
                QueryManager manager = QueryManager.getInstance();
                List list = manager.query(null);
                if(list == null){
                    System.out.println("lst is null");
                    return new ArrayList();
                }
                return list;
            }
            @Override
            protected void done() {
                try {
                    ImageListModel model = frame.getThumbnailPanel().getListModel();
                    model.clear();
                    model.add(get());
                    model = null;
                    frame.setVisiblePanel(frame.THUMBNAIL_PANEL);
                } catch (Exception ignore) {
                    ignore.printStackTrace();
                    frame.setStatusText("Failed to query image");
                }finally{
                     frame.getProgressPane().stop();
                }
            }
        };
        worker.execute();
    }
}
