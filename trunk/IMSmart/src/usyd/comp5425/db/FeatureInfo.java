/*
 * FeatureInfo.java
 *
 *  Copyright (C) 2007 COMP5425 Multimedia Storage, Retrieval and Delivery
 *  The School of Information Technology
 *  The University of Sydney
 *
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.
 */

package usyd.comp5425.db;

import java.io.Serializable;
import java.util.Vector;

/**
 *
 * @author Yuezhong Zhang SID:305275631
 */
public class FeatureInfo implements Serializable {
    
    /** Creates a new instance of FeatureInfo */
    public FeatureInfo() {
    }

    /**
     * Holds value of property id.
     */
    private int id;

    /**
     * Getter for property id.
     * @return Value of property id.
     */
    public int getId() {
        return this.id;
    }

    /**
     * Setter for property id.
     * @param id New value of property id.
     */
    public void setId(int id) {
        this.id = id;
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
     * Holds value of property featureName.
     */
    private String featureName;

    /**
     * Getter for property featureName.
     * @return Value of property featureName.
     */
    public String getFeatureName() {
        return this.featureName;
    }

    /**
     * Setter for property featureName.
     * @param featureName New value of property featureName.
     */
    public void setFeatureName(String featureName) {
        this.featureName = featureName;
    }

    /**
     * Holds value of property vector.
     */
    private Vector<Double> vector;

    /**
     * Getter for property vector.
     * @return Value of property vector.
     */
    public Vector<Double> getVector() {
        return this.vector;
    }

    /**
     * Setter for property vector.
     * @param vector New value of property vector.
     */
    public void setVector(Vector<Double> vector) {
        this.vector = vector;
    }

    
}
