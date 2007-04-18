/*
 * Application.java
 *
 * Created on 9 April 2007, 15:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425;

import javax.swing.UIManager;
import org.jvnet.substance.SubstanceLookAndFeel;
import usyd.comp5425.action.GeneralActionHandler;
import usyd.comp5425.action.IndexActionHandler;
import usyd.comp5425.action.QueryActionHandler;
import usyd.comp5425.db.DataTapFactory;
import usyd.comp5425.ui.ImageAppFrame;

/**
 *
 * @author Yuezhong Zhang
 */
public class Application {
    public static void main(String args[]) {
        
        try {
            UIManager.setLookAndFeel("org.jvnet.substance.SubstanceLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                ImageAppFrame frame = new ImageAppFrame();
                initActionHandle(frame);
                frame.setVisible(true);
            }
        });
        System.out.println("setup");
        System.setProperty("derby.user","yuezhong");
        System.setProperty("derby.password","yuezhong");
        System.setProperty("derby.url","jdbc:derby:myDB");
        System.setProperty("derby.system.home",System.getProperty("user.dir"));
        DataTapFactory.createDataTap();
    }
    public static void initActionHandle(ImageAppFrame frame){
        GeneralActionHandler gah = new GeneralActionHandler(frame);
        QueryActionHandler qh = new QueryActionHandler(frame);
        IndexActionHandler iah = new IndexActionHandler(frame);
    }
    
}
