/*
 * ImageAppFrame.java
 *
 * Created on 7 April 2007, 14:34
 */

package usyd.comp5425.ui;

import com.sun.jaf.ui.ActionManager;
import com.sun.jaf.ui.UIFactory;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.io.IOException;
import java.net.URL;
import javax.swing.*;
import usyd.comp5425.ui.imageviewer.JThumbnailPanel;

/**
 *
 * @author  Yuezhong Zhang
 */
public class ImageAppFrame extends JFrame {
    
    /** Creates new form ImageAppFrame */
    public ImageAppFrame() {
        initActions();
        initComponents();
        this.setTitle("IMSmart v1.0");
        this.setSize(850,600);
        this.setProgressPane(new ProgressGlassPane());
        this.setLocationRelativeTo(null);
    }
    private void initActions() {
        URL actionURL = ImageAppFrame.class.getResource("resources/actions-demo.xml");
        manager = ActionManager.getInstance();
        try {
            manager.loadActions(actionURL);
        } catch (IOException ioe) {
            System.out.println("ERROR parsing: " + ioe);
        }
    }
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        UIFactory factory = UIFactory.getInstance();
        JMenuBar menubar = factory.createMenuBar("main-menu");
        if (menubar != null) {
            setJMenuBar(menubar);
        }
        JToolBar toolbar = factory.createToolBar("main-toolbar");
        if (toolbar != null) {
            toolbar.setFloatable(false);
            getContentPane().add(toolbar, BorderLayout.NORTH);
        }

        mainPanel = new javax.swing.JPanel();
        queryFormPanel1 = new usyd.comp5425.ui.QueryFormPanel();
        paintPanel1 = new usyd.comp5425.ui.PaintPanel();
        jThumbnailPanel1 = new usyd.comp5425.ui.imageviewer.JThumbnailPanel();
        statusBar1 = new usyd.comp5425.ui.StatusBar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        mainPanel.setLayout(new java.awt.CardLayout());

        mainPanel.add(queryFormPanel1, "queryFormPanel");

        mainPanel.add(paintPanel1, "paintPanel");

        mainPanel.add(jThumbnailPanel1, "thumbnailPanel");

        getContentPane().add(mainPanel, java.awt.BorderLayout.CENTER);

        statusBar1.setBorder(javax.swing.BorderFactory.createEtchedBorder());
        statusBar1.registerListener(menubar.getSubElements());
        statusBar1.registerListener(toolbar.getComponents());
        getContentPane().add(statusBar1, java.awt.BorderLayout.SOUTH);

    }// </editor-fold>//GEN-END:initComponents
    public void setVisiblePanel(String name){
        CardLayout layout = (CardLayout)mainPanel.getLayout();
        layout.show(mainPanel,name);
    }
    public JPanel getPanel(String name){
        if(name.equalsIgnoreCase(this.QUERY_FROM_PANEL))
            return this.queryFormPanel1;
        else if(name.equalsIgnoreCase(this.THUMBNAIL_PANEL)){
            return this.jThumbnailPanel1;
        }else if(name.equalsIgnoreCase(this.PAINT_PANEL))
            return this.paintPanel1;
        return null;
    }
    public JThumbnailPanel getThumbnailPanel(){
        return jThumbnailPanel1;
    }
    public StatusBar getStatusBar(){
        return statusBar1;
    }
    public void setStatusText(String text){
        this.statusBar1.setMessage(text);
    }
    public ProgressGlassPane getProgressPane() {
        return progressPane;
    }
    public void setProgressPane(ProgressGlassPane progressPane) {
        this.progressPane = progressPane;
        this.setGlassPane(this.progressPane);
    }
    public static final String QUERY_FROM_PANEL = "queryFormPanel";
    public static final String PAINT_PANEL = "paintPanel";
    public static final String THUMBNAIL_PANEL="thumbnailPanel";
    private ActionManager manager;
    private ProgressGlassPane progressPane;
    private JFileChooser filechooser = new JFileChooser();
    public JFileChooser getFilechooser() {
        filechooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        return filechooser;
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private usyd.comp5425.ui.imageviewer.JThumbnailPanel jThumbnailPanel1;
    private javax.swing.JPanel mainPanel;
    private usyd.comp5425.ui.PaintPanel paintPanel1;
    private usyd.comp5425.ui.QueryFormPanel queryFormPanel1;
    private usyd.comp5425.ui.StatusBar statusBar1;
    // End of variables declaration//GEN-END:variables
    
}
