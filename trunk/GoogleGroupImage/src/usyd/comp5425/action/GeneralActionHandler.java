/*
 * GeneralActionHandler.java
 *
 * Created on 8 April 2007, 01:46
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.action;

import com.sun.jaf.ui.Action;
import com.sun.jaf.ui.ActionManager;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import usyd.comp5425.db.DataTapFactory;
import usyd.comp5425.ui.AboutPanel;
import usyd.comp5425.ui.ImageAppFrame;
import usyd.comp5425.ui.PaintPanel;

/**
 *
 * @author Yuezhong Zhang
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
    }
    @Action("statusbar-command")
    public void handleStatusBarAction(boolean flag){
        frame.getStatusBar().setVisible(flag);
    }
    @Action("show-viewer-command")
    public void handleShowViewerCommand(boolean flag) {
        frame.setVisiblePanel(frame.THUMBNAIL_PANEL);
    }
    @Action("show-query-command")
    public void handleShowQueryFormCommand(boolean flag) {
        frame.setVisiblePanel(frame.QUERY_FROM_PANEL);
    }
    @Action("show-painter-command")
    public void handleShowPainterCommand(boolean flag) {
        frame.setVisiblePanel(frame.PAINT_PANEL);
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
                    ImageIO.write(image,"png",f);
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
}
