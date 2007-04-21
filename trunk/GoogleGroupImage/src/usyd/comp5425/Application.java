/*
 * Application.java
 *
 * Created on 9 April 2007, 15:18
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425;

import de.javasoft.plaf.synthetica.SyntheticaBlackMoonLookAndFeel;
import de.javasoft.plaf.synthetica.SyntheticaLookAndFeel;
import java.io.File;
import javax.imageio.ImageIO;
import javax.swing.UIManager;
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
            //   UIManager.setLookAndFeel("org.jvnet.substance.SubstanceLookAndFeel");
            UIManager.setLookAndFeel(new SyntheticaBlackMoonLookAndFeel());
            SyntheticaLookAndFeel.setAntiAliasEnabled(true);
            SyntheticaLookAndFeel.setExtendedFileChooserEnabled(false);
            SyntheticaLookAndFeel.setRememberFileChooserPreferences(false);
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
        System.setProperty("derby.user","admin");
        System.setProperty("derby.password","adminadmin");
        System.setProperty("derby.url","jdbc:derby:myDB");
        System.setProperty("derby.system.home",System.getProperty("user.dir"));
        DataTapFactory.createDataTap();
        StringBuffer sb = new StringBuffer(System.getProperty("user.dir"));
        sb.append(File.separatorChar);
        sb.append("images");
        sb.append(File.separatorChar);
        System.out.printf("Image folder:%s%n",sb);
        File file = new File(sb.toString());
        if(!file.exists()){
            file.mkdir();
        }
        file = null;
        ImageIO.setCacheDirectory(new File(System.getProperty("user.home")));
        ImageIO.setUseCache(true);
        System.out.println("IMSmart v1.0 is ready");
    }
    public static void initActionHandle(ImageAppFrame frame){
        GeneralActionHandler gah = new GeneralActionHandler(frame);
        QueryActionHandler qh = new QueryActionHandler(frame);
        IndexActionHandler iah = new IndexActionHandler(frame);
    }
    
}
