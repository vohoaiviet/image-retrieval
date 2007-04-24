/*
 * ResultListCellRenderer.java
 *
 * Created on 24 April 2007, 12:03
 */

package usyd.comp5425.ui.imageviewer;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import javax.swing.ImageIcon;
import javax.swing.JList;
import javax.swing.ListCellRenderer;
import usyd.comp5425.query.QueryResult;

/**
 *
 * @author  Yuezhong Zhang
 */
public class ResultListCellRenderer extends javax.swing.JPanel  implements ListCellRenderer {
    /** Creates new form ResultListCellRenderer */
    public ResultListCellRenderer() {
        initComponents();
    }
    
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc=" Generated Code ">//GEN-BEGIN:initComponents
    private void initComponents() {
        imageLabel = new javax.swing.JLabel();
        rankSlider = new javax.swing.JSlider();
        nameLabel = new javax.swing.JLabel();

        setLayout(new java.awt.BorderLayout());

        setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(imageLabel, java.awt.BorderLayout.CENTER);

        rankSlider.setMaximum(10);
        rankSlider.setValue(0);
        StrokedShapeSliderUI sui = new StrokedShapeSliderUI();
        sui.setPrimaryColor(Color.YELLOW);
        rankSlider.setUI(sui);
        add(rankSlider, java.awt.BorderLayout.NORTH);

        nameLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        add(nameLabel, java.awt.BorderLayout.SOUTH);

    }// </editor-fold>//GEN-END:initComponents
    
    public Component getListCellRendererComponent(
            JList list,
            Object value,            // value to display
            int index,               // cell index
            boolean isSelected,      // is the cell selected
            boolean cellHasFocus)    // the list and the cell have the focus
    {
        QueryResult result = (QueryResult)value;
        imageLabel.setIcon(new ImageIcon(result.getBufferedImage()));
        nameLabel.setText(getName(result.getImage()));
        if(rankingEnabled){
            int val = (int)(result.getDistance() * 10);
            rankSlider.setValue(val);
            rankSlider.setToolTipText(val +"%");
        }
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
    public String getName(String image){
        return image.substring(image.lastIndexOf(File.separatorChar)+1);
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel imageLabel;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JSlider rankSlider;
    // End of variables declaration//GEN-END:variables
    
    private boolean rankingEnabled = true;
    
    /**
     * Getter for property rankingEnabled.
     * @return Value of property rankingEnabled.
     */
    public boolean isRankingEnabled() {
        return rankingEnabled;
    }
    
    /**
     * Setter for property rankingEnabled.
     * @param rankingEnabled New value of property rankingEnabled.
     */
    public void setRankingEnabled(boolean rankingEnabled) {
        this.rankingEnabled = rankingEnabled;
        this.rankSlider.setVisible(this.rankingEnabled);
    }
    
}
