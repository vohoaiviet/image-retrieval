/*
 * DataAccessObjectDerby.java
 *
 * Created on 12 April 2007, 21:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.db;

import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Yuezhong Zhang
 */
public class DataAccessObjectDerby extends DataAccessObject {
    
    /** Creates a new instance of DataAccessObjectDerby */
    public DataAccessObjectDerby() {
        super();
        ConnectionPool connPool = new  ConnectionPool(
                System.getProperty("jdbc.url"),
                System.getProperty("jdbc.user"),
                System.getProperty("jdbc.password"),
                "org.apache.derby.jdbc.EmbeddedDriver");
        this.setConnectionPool(connPool);
    }
    public void close(){
        try {
            this.getConnectionPool().close();
            DriverManager.getConnection("jdbc:derby:;shutdown=true");
        } catch (SQLException ex) {
            //ex.printStackTrace();
        }
    }
}
