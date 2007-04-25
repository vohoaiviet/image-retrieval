/*
 * GeneralActionHandler.java
 *
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
import java.math.BigDecimal;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import usyd.comp5425.db.DataTapFactory;
import usyd.comp5425.query.QueryResult;
import usyd.comp5425.ui.AboutPanel;
import usyd.comp5425.ui.ImageAppFrame;
import usyd.comp5425.ui.PaintPanel;
import usyd.comp5425.ui.QueryFormPanel;
import usyd.comp5425.ui.imageviewer.ImageListModel;
import usyd.comp5425.ui.imageviewer.JThumbnailPanel;

/**
 *
 * @author Yuezhong Zhang SID:305275631
 */
public class GeneralActionHandler {
    
    /**
     * Creates a new instance of GeneralActionHandler
     */
    private ImageAppFrame frame;
    public GeneralActionHandler(ImageAppFrame frame) {
        this.frame = frame;
        ActionManager.getInstance().registerActionHandler(this);
        ActionManager.getInstance().setEnabled("save-command", false);
        ActionManager.getInstance().setEnabled("new-command", false);
        ActionManager.getInstance().setSelected("ranking-command",true);
        ActionManager.getInstance().setSelected("statusbar-command",true);
        ActionManager.getInstance().setSelected("show-query-command",true);
        
    }
    
    @Action("show-query-command")
    public void handleShowQueryFormCommand(boolean flag) {
        frame.setVisiblePanel(frame.QUERY_FROM_PANEL);
        ActionManager.getInstance().setEnabled("new-command", false);
    }
    @Action("show-painter-command")
    public void handleShowPainterCommand(boolean flag) {
        frame.setVisiblePanel(frame.PAINT_PANEL);
        ActionManager.getInstance().setEnabled("new-command", true);
    }
    
    @Action("show-viewer-command")
    public void handleShowViewerCommand(boolean flag) {
        frame.setVisiblePanel(frame.THUMBNAIL_PANEL);
        ActionManager.getInstance().setEnabled("new-command", false);
    }
    
    @Action("exit-command")
    public void handleExit(){
        DataTapFactory.close();
        System.exit(0);
    }
    @Action("about-command")
    public void handleAbout(){
        JOptionPane.showMessageDialog(frame,new AboutPanel());
    }
    @Action("save-command")
    public void handleSave(){
        JFileChooser jfc = new JFileChooser();
        if(jfc.showSaveDialog(frame)== jfc.APPROVE_OPTION){
            File f = jfc.getSelectedFile();
            PaintPanel  pp =(PaintPanel) frame.getPanel(frame.PAINT_PANEL);
            BufferedImage image = pp.getImage();
            if(image !=null)
                try {
                    ImageIO.write(image,"jpg",f);
                    frame.setStatusText("saved image to " + f.getAbsolutePath());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
        }
        jfc = null;
    }
    @Action("new-command")
    public void handleNew(){
        PaintPanel  pp =(PaintPanel) frame.getPanel(frame.PAINT_PANEL);
        boolean created =  pp.createNewPaintBoard();
        ActionManager.getInstance().setEnabled("save-command",created);
    }
    @Action("ranking-command")
    public void hanldeRankingAction(boolean bool){
        JThumbnailPanel panel =(JThumbnailPanel) frame.getPanel(frame.THUMBNAIL_PANEL);
        panel.setRankingEnabled(bool);
    }
    @Action("statusbar-command")
    public void handleStatusBarAction(boolean flag){
        frame.getStatusBar().setVisible(flag);
    }
    @Action("view-result-command")
    public void handleVieResult(){
        handleQueryResult();
    }
    @Action("query-result-command")
    public void handleQueryResult(){
        JThumbnailPanel tpanel =(JThumbnailPanel) frame.getPanel(frame.THUMBNAIL_PANEL);
        QueryResult result = (QueryResult) tpanel.getImageList().getSelectedValue();
        QueryFormPanel qpanel =(QueryFormPanel) frame.getPanel(frame.QUERY_FROM_PANEL);
        qpanel.setSampleFile(new File(result.getImage()));
        frame.setVisiblePanel(frame.QUERY_FROM_PANEL);
        tpanel = null;
        qpanel = null;
    }
    @Action("vote-result-command")
    public void hanldeVoteResult(){
        JThumbnailPanel tpanel =(JThumbnailPanel) frame.getPanel(frame.THUMBNAIL_PANEL);
        QueryResult result = (QueryResult) tpanel.getImageList().getSelectedValue();
        double value = result.getDistance();
        double round = getScaled(value,1);
        if(value == round && value !=1.0)
            round += 0.1;
        result.setDistance(round);
        tpanel.getImageList().repaint();
    }
    
    
    public static  double getScaled(double value, int scale) {
        double result = value; //default: unscaled
        //use BigDecimal String constructor as this is the only exact way for double values
        result = new BigDecimal(""+value).setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
        
        return result;
    }
    
    @Action("pageup-command")
    public void handlePageUp(){
        JThumbnailPanel tpanel =(JThumbnailPanel) frame.getPanel(frame.THUMBNAIL_PANEL);
        ImageListModel model = tpanel.getListModel();
        if(model.canPageUp()){
            model.pageUp();
        }
        tpanel.changePageButtonState();
    }
    @Action("pagedown-command")
    public void handlePageDown(){
        JThumbnailPanel tpanel =(JThumbnailPanel) frame.getPanel(frame.THUMBNAIL_PANEL);
        ImageListModel model =  tpanel.getListModel();
        
        if(model.canPageDown()){
            model.pageDown();
            
        }
        tpanel.changePageButtonState();
    }
}
