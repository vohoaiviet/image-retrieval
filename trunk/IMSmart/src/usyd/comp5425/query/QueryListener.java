/*
 * QueryListener.java
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

import java.util.EventListener;
import java.util.List;

/**
 *
 * @author Yuezhong Zhang SID:305275631
 */
public interface QueryListener extends EventListener{
    
    public void queryStarted(String text);
    public void queryFinished(String text);
    public void itemFound(List<QueryResult> list);
}
