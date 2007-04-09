/*
 * StatusBar.java
 *
 * Created on 8 April 2007, 01:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.ui;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.FontMetrics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.AbstractButton;
import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.MenuElement;

/**
 *
 * @author Yuezhong Zhang
 */
public class StatusBar extends JPanel {
    
    
    private JLabel label;
    private Dimension preferredSize;
    
    private MouseHandler handler;
    
    public StatusBar()  {
        this.setLayout(new FlowLayout(FlowLayout.LEFT));
        this.setBorder(BorderFactory.createEtchedBorder());
        
        // Set a large blank label to set the preferred size.
        label = new JLabel("                                                                                        ");
        preferredSize = new Dimension(getWidth(label.getText()), 2 * getFontHeight());
        
        this.add(label);
    }
    
    /**
     * Returns the string width
     * @param s the string
     * @return the string width
     */
    protected int getWidth(String s) {
        FontMetrics fm = this.getFontMetrics(this.getFont());
        if (fm == null) {
            return 0;
        }
        return fm.stringWidth(s);
    }
    
    /**
     * Returns the height of a line of text
     * @return the height of a line of text
     */
    protected int getFontHeight() {
        FontMetrics fm = this.getFontMetrics(this.getFont());
        if (fm == null) {
            return 0;
        }
        return fm.getHeight();
    }
    
    /**
     * Returns the perferred size
     * @return the preferred size
     */
    public Dimension getPreferredSize() {
        return preferredSize;
    }
    
    /**
     * Sets non-transient status bar message
     * @param message the message to display on the status bar
     */
    public void setMessage(String message) {
        label.setText(message);
    }
    
    /**
     * Custom mouse handler which will listen to mouse
     * events on action based components and send the
     * value of the LONG_DESCRIPTION to the status bar.
     */
    private class MouseHandler extends MouseAdapter {
        
        public void mouseExited(MouseEvent evt) {
            setMessage("");
        }
        
        /**
         * Takes the LONG_DESCRIPTION of the Action based components
         * and sends them to the Status bar
         */
        public void mouseEntered(MouseEvent evt) {
            if (evt.getSource() instanceof AbstractButton)  {
                AbstractButton button = (AbstractButton)evt.getSource();
                Action action = button.getAction();
                if (action != null)  {
                    String message = (String)action.getValue(Action.LONG_DESCRIPTION);
                    setMessage(message);
                }
            }
        }
    }
    
    /**
     * Helper method to recursively register all MenuElements with
     * a mouse listener.
     */
    public void registerListener(MenuElement[] elements) {
        if (handler == null) {
            handler = new MouseHandler();
        }
        
        for (int i = 0; i < elements.length; i++) {
            if (elements[i] instanceof JMenuItem) {
                ((JMenuItem)elements[i]).addMouseListener(handler);
            }
            registerListener(elements[i].getSubElements());
        }
    }
    
    /**
     * Helper method to register all components with a mouse listener.
     */
    public void registerListener(Component[] components) {
        if (handler == null) {
            handler = new MouseHandler();
        }
        
        for (int i = 0; i < components.length; i++) {
            if (components[i] instanceof AbstractButton) {
                ((AbstractButton)components[i]).addMouseListener(handler);
            }
        }
    }
} // end class StatusBar