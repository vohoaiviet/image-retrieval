/*
 * PersistenceException.java
 *
 * Created on 13 April 2007, 06:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.fetch;

/**
 *
 * @author Yuezhong Zhang
 */
public class PersistenceException extends Exception{
    
    /** Creates a new instance of PersistenceException */
    public PersistenceException(String text) {
        super(text);
    }
    public PersistenceException(Exception e){
        super(e);
    }
    public PersistenceException(String text, Exception e){
        super(text,e);
    }
    
}
