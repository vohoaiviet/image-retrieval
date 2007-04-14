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
import usyd.comp5425.action.GeneralActionHandler;
import usyd.comp5425.action.IndexActionHandler;
import usyd.comp5425.action.QueryActionHandler;
import usyd.comp5425.ui.ImageAppFrame;

/**
 *
 * @author Yuezhong Zhang
 */
public class Application {
    public static void main(String args[]) {
        System.out.println("setup");
        System.setProperty("derby.user","yuezhong");
        System.setProperty("derby.password","yuezhong");
        System.setProperty("derby.url","jdbc:derby:myDB");
        System.setProperty("derby.system.home","C:\\Sun");
        try {
            
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
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
    }
    public static void initActionHandle(ImageAppFrame frame){
        GeneralActionHandler gah = new GeneralActionHandler(frame);
        QueryActionHandler qh = new QueryActionHandler(frame);
        IndexActionHandler iah = new IndexActionHandler(frame);
    }
    
}
