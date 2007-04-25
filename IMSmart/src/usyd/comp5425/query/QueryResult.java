/*
 * QueryResult.java
 *
 *  Copyright (C) 2007 COMP5425 Multimedia Storage, Retrieval and Delivery
 *  The School of Information Technology
 *  The University of Sydney
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */
package usyd.comp5425.query;

import java.awt.image.BufferedImage;

/**
 *
 * @author Yuezhong Zhang  SID:305275631
 */
public class QueryResult implements Rankable{
    
    /** Creates a new instance of QueryResult */
    public QueryResult() {
    }
    public QueryResult(String image, double distance){
        this.image = image;
        this.distance = distance;
    }
    /**
     * Holds value of property image.
     */
    private String image;
    
    /**
     * Getter for property image.
     * @return Value of property image.
     */
    public String getImage() {
        return this.image;
    }
    
    /**
     * Setter for property image.
     * @param image New value of property image.
     */
    public void setImage(String image) {
        this.image = image;
    }
    
    /**
     * Holds value of property distance.
     */
    private double distance;
    
    /**
     * Getter for property distance.
     * @return Value of property distance.
     */
    public double getDistance() {
        return this.distance;
    }
    
    /**
     * Setter for property distance.
     * @param distance New value of property distance.
     */
    public void setDistance(double distance) {
        this.distance = distance;
    }
    public int compareTo(Object obj) {
        QueryResult another = (QueryResult) obj;
        if((this.distance - another.distance) < 0.0d)
            return 1;
        else if((this.distance - another.distance )>0.0d)
            return -1;
        else
            return 0;
    }
    @Override
    public boolean equals(Object obj){
        QueryResult another = (QueryResult) obj;
        return this.image.equalsIgnoreCase(another.image);
    }

    /**
     * Holds value of property bufferedImage.
     */
    private BufferedImage bufferedImage;

    /**
     * Getter for property bufferedImage.
     * @return Value of property bufferedImage.
     */
    public BufferedImage getBufferedImage() {
        return this.bufferedImage;
    }

    /**
     * Setter for property bufferedImage.
     * @param bufferedImage New value of property bufferedImage.
     */
    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }
}
