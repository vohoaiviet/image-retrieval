/*
 * QueryListener.java
 *
 * Created on 16 April 2007, 22:30
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.query;

import java.util.EventListener;

/**
 *
 * @author Yuezhong Zhang
 */
public interface QueryListener extends EventListener{
    
    public void queryStarted(QueryEvent event);
    public void queryFinished(QueryEvent event);
    public void itemFound(QueryEvent event);
}
