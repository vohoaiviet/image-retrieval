/*
 * ConnectionPool.java
 *
 * Created on 12 April 2007, 00:42
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionPool{
    
    private ArrayList _freePool;
    private String _url;
    private String _username;
    private String _password;
    private String _dbDriverName;
    
    public ConnectionPool(){
        _freePool = new ArrayList();
    }
    
    public ConnectionPool(String connectURL, String uName,
            String pWord, String driverName){
        this();
        _url = connectURL;
        _username = uName;
        _password = pWord;
        _dbDriverName = driverName;
        _createConnections(_url,_username,_password,_dbDriverName);
    }
    
    public synchronized Connection getConnection(){
        if(_freePool.size() == 0){
            //None Available
            //Create a new set of Connections
            _createConnections(_url,_username,_password,_dbDriverName);
        }
        //Last connection in the Array so that we save time on re-arranging the Array
        return ((Connection)_freePool.remove(_freePool.size() - 1));
    }
    
    public void closeConnection(Connection con){
        System.out.println("Current size of Pool: "+_freePool.size());
        _freePool.add(con);
        System.out.println("Size of Pool after connection closed: "+
                _freePool.size());
    }
    
    private void _createConnections
            (String connectURL, String uName, String pWord, String driverName){
        try{
            Class.forName(driverName).newInstance();
            for(int i=0; i<10; ++i){
                Connection con = DriverManager.getConnection
                        (connectURL,uName,pWord);
                _freePool.add(con);
            }
        }catch(ClassNotFoundException ex){
            System.out.println(ex);
        }catch(InstantiationException ex){
            System.out.println(ex);
        }catch(IllegalAccessException ex){
            System.out.println(ex);
        }catch(SQLException ex){
            System.out.println(ex);
        }
    }
}

