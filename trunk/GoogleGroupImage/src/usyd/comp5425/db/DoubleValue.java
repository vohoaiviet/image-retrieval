/*
 * DoubleValue.java
 *
 * Created on 10 April 2007, 01:16
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.db;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

/**
 * Entity class DoubleValue
 *
 * @author Yuezhong Zhang
 */
@Entity
public class DoubleValue implements Serializable {
    
    @ManyToOne
    private ImageFeature imageFeature;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    
    /** Creates a new instance of DoubleValue */
    public DoubleValue() {
    }
    
    /**
     * Gets the id of this DoubleValue.
     * @return the id
     */
    public Integer getId() {
        return this.id;
    }
    
    /**
     * Sets the id of this DoubleValue to the specified value.
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
     * Determines whether another object is equal to this DoubleValue.  The result is
     * <code>true</code> if and only if the argument is not null and is a DoubleValue object that
     * has the same id field values as this object.
     * @param object the reference object with which to compare
     * @return <code>true</code> if this object is the same as the argument;
     * <code>false</code> otherwise.
     */
    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DoubleValue)) {
            return false;
        }
        DoubleValue other = (DoubleValue)object;
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
        return "usyd.comp5425.db.DoubleValue[id=" + id + "]";
    }
    
    /**
     * Holds value of property value.
     */
    @Column(name="FEATURE_VALUE")
    private double value;
    
    /**
     * Getter for property value.
     * @return Value of property value.
     */
    public double getValue() {
        return this.value;
    }
    
    /**
     * Setter for property value.
     * @param value New value of property value.
     */
    public void setValue(double value) {
        this.value = value;
    }
    public ImageFeature getImageFeature() {
        return imageFeature;
    }
    
    public void setImageFeature(ImageFeature imageFeature) {
        this.imageFeature = imageFeature;
    }
    
}
