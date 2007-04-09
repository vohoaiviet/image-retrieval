/*
 * ImageFeature.java
 *
 * Created on 10 April 2007, 00:10
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.db;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 * Entity class ImageFeature
 *
 * @author Yuezhong Zhang
 */
@Entity
public class ImageFeature implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    /** Creates a new instance of ImageFeature */
    public ImageFeature() {
    }
    
    /**
     * Gets the id of this ImageFeature.
     * @return the id
     */
    public Integer getId() {
        return this.id;
    }
    
    /**
     * Sets the id of this ImageFeature to the specified value.
     * @param id the new id
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * Returns a hash code value for the object.  This implementation computes
     * a hash code value based on the id fields in this object.
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (this.id != null ? this.id.hashCode() : 0);
        return hash;
    }
    
    /**
     * Determines whether another object is equal to this ImageFeature.  The result is
     * <code>true</code> if and only if the argument is not null and is a ImageFeature object that
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ImageFeature)) {
            return false;
        }
        ImageFeature other = (ImageFeature)object;
        if (this.id != other.id && (this.id == null || !this.id.equals(other.id))) return false;
        return true;
    }
    
    /**
     * Returns a string representation of the object.  This implementation constructs
     * that representation based on the id fields.
     * @return a string representation of the object.
     */
    @Override
    public String toString() {
        return "usyd.comp5425.db.ImageFeature[id=" + id + "]";
    }
    
    /**
     * Holds value of property path.
     */
    @Column(name="RELATIVE_PATH")
    private String path;
    
    /**
     * Getter for property path.
     * @return Value of property path.
     */
    public String getPath() {
        return this.path;
    }
    
    /**
     * Setter for property path.
     * @param path New value of property path.
     */
    public void setPath(String path) {
        this.path = path;
    }
    
    /**
     * Holds value of property featureID.
     */
    @Column(name="FEATURE_ID")
    private String featureID;
    
    /**
     * Getter for property featureID.
     * @return Value of property featureID.
     */
    public String getFeatureID() {
        return this.featureID;
    }
    
    /**
     * Setter for property featureID.
     * @param featureID New value of property featureID.
     */
    public void setFeatureID(String featureID) {
        this.featureID = featureID;
    }
    
    /**
     * Holds value of property vector.
     */
    @OneToMany(cascade=javax.persistence.CascadeType.ALL, mappedBy="imageFeature")
    private Collection<DoubleValue> vector;
    
    /**
     * Getter for property vector.
     * @return Value of property vector.
     */
    public Collection<DoubleValue> getVector() {
        return this.vector;
    }
    
    /**
     * Setter for property vector.
     * @param vector New value of property vector.
     */
    public void setVector(Collection<DoubleValue> vector) {
        this.vector = vector;
    }
    
    
    
}
