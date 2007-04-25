/*
 * Application.java
 *
 *  Copyright (C) 2007 COMP5425 Multimedia Storage, Retrieval and Delivery
 *  The School of Information Technology
 *  The University of Sydney
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package usyd.comp5425;

import java.awt.EventQueue;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.UIManager;
import usyd.comp5425.action.GeneralActionHandler;
import usyd.comp5425.action.IndexActionHandler;
import usyd.comp5425.action.LookAndFeelActionHandler;
import usyd.comp5425.action.QueryActionHandler;
import usyd.comp5425.db.DataTapFactory;
import usyd.comp5425.ui.ImageAppFrame;

/**
 *
 * @author Yuezhong Zhang  SID:305275631
 */
public class Application {
    public static void main(String args[]) {
        System.setProperty("sun.awt.noerasebackground", "true");
        System.setProperty("app.version", "1.0");
        try {
            JFrame.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("setup");
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                ImageAppFrame frame = new ImageAppFrame();
                initActionHandle(frame);
                frame.setVisible(true);
            }
        });
        
        File file = new File(System.getProperty("user.dir"), "images");
        System.out.println("Image folder " + file.getAbsolutePath());
        if(!file.exists()){
            file.mkdir();
            System.out.println("created images directory");
        }
        file = null;
        System.setProperty("derby.user","admin");
        System.setProperty("derby.password","adminadmin");
        System.setProperty("derby.url","jdbc:derby:myDB");
        System.setProperty("derby.system.home",System.getProperty("user.dir"));
        DataTapFactory.createDataTap();
        ImageIO.setUseCache(true);
        ImageIO.setCacheDirectory(new File(System.getProperty("user.home")));
        System.out.println("IMSmart v"+ System.getProperty("app.version") +" is ready");
    }
    public static void initActionHandle(ImageAppFrame frame){
        GeneralActionHandler gah = new GeneralActionHandler(frame);
        QueryActionHandler qh = new QueryActionHandler(frame);
        IndexActionHandler iah = new IndexActionHandler(frame);
        LookAndFeelActionHandler lfh = new LookAndFeelActionHandler(frame);
    }
    
}
