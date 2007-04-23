/*
 * ImageCellRenderer.java
 *
 * Created on 7 April 2007, 14:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.ui.imageviewer;

import java.awt.Component;
import java.awt.image.BufferedImage;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListCellRenderer;

/**
 *
 * @author Yuezhong Zhang
 */
public class ImageCellRenderer extends JLabel implements ListCellRenderer  {
    
    /** Creates a new instance of ImageCellRenderer */
    public ImageCellRenderer() {
        setOpaque(true);
        this.setBorder(BorderFactory.createEmptyBorder(5,5,5,5));
    }
    
    public Component getListCellRendererComponent(
            JList list,
            Object value,            // value to display
            int index,               // cell index
            boolean isSelected,      // is the cell selected
            boolean cellHasFocus)    // the list and the cell have the focus
    {
        setIcon(new ImageIcon((BufferedImage)value));
        if (isSelected) {
            setBackground(list.getSelectionBackground());
            setForeground(list.getSelectionForeground());
        } else {
            setBackground(list.getBackground());
            setForeground(list.getForeground());
        }
        setEnabled(list.isEnabled());
        return this;
    }
    
}
