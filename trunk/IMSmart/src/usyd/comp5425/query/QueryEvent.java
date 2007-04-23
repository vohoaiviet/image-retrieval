/*
 * QueryEvent.java
 *
 * Created on 16 April 2007, 22:31
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.query;

/**
 *
 * @author Yuezhong Zhang
 */
public class QueryEvent{
    private String filename;
    public QueryEvent(){
        
    }
    public QueryEvent(String filename){
        this.filename = filename;
    }
    public String getFilename(){
        return this.filename;
    }
    public void setFilename(String filename){
        this.filename = filename;
    }
}
