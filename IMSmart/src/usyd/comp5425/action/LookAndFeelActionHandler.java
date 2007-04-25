/*
 * LookAndFeelActionHandler.java
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
import java.awt.Cursor;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import usyd.comp5425.ui.ImageAppFrame;

/**
 *
 * @author Yuezhong Zhang SID:305275631
 */
public class LookAndFeelActionHandler{
    ImageAppFrame frame;
    /** Creates a new instance of LookAndFeelActionHandler */
    public LookAndFeelActionHandler(ImageAppFrame frame ) {
        this.frame = frame;
        ActionManager.getInstance().registerActionHandler(this);
        ActionManager.getInstance().setSelected("SubstanceBusinessBlackSteelLookAndFeel", true);
    }
    @Action("SubstanceBusinessLookAndFeel")
    public void hanleSubstanceBusinessLookAndFeel(boolean bool){
        changeLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessLookAndFeel");
    }
    @Action("SubstanceBusinessBlackSteelLookAndFeel")
    public void handleSubstanceBusinessBlackSteelLookAndFeel(boolean bool){
        this.changeLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessBlackSteelLookAndFeel");
    }
    @Action("SubstanceCremeLookAndFeel")
    public void handleSubstanceCremeLookAndFeel(boolean bool){
        this.changeLookAndFeel("org.jvnet.substance.skin.SubstanceCremeLookAndFeel");
    }
    @Action("SubstanceSaharaLookAndFeel")
    public void handleSubstanceSaharaLookAndFeel(boolean bool){
        this.changeLookAndFeel("org.jvnet.substance.skin.SubstanceSaharaLookAndFeel");
    }
    @Action("SubstanceModerateLookAndFeel")
    public void handleSubstanceModerateLookAndFeel(boolean bool){
        this.changeLookAndFeel("org.jvnet.substance.skin.SubstanceModerateLookAndFeel");
    }
    @Action("SubstanceOfficeSilver2007LookAndFeel")
    public void handleSubstanceOfficeSilver2007LookAndFeel(boolean bool){
        this.changeLookAndFeel("org.jvnet.substance.skin.SubstanceOfficeSilver2007LookAndFeel");
    }
    
    public void changeLookAndFeel(String className){
        try {
            UIManager.setLookAndFeel(className);
            frame.setCursor( Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
            SwingUtilities.updateComponentTreeUI(frame);
            frame.validate();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        } catch (InstantiationException ex) {
            ex.printStackTrace();
        } catch (UnsupportedLookAndFeelException ex) {
            ex.printStackTrace();
        }finally{
            frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
        }
    }
}

