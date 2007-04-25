/*
 * ConnectionPool.java
 *
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

/**
 *
 * @author Yuezhong Zhang SID:305275631
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;

public class ConnectionPool{
    
    private ArrayList<Connection> freePool;
    private String url;
    private String username;
    private String password;
    private String dbDriverName;
    
    public ConnectionPool(){
        freePool = new ArrayList<Connection>();
    }
    
    public ConnectionPool(String connectURL, String uName,
            String pWord, String driverName){
        this();
        url = connectURL;
        username = uName;
        password = pWord;
        dbDriverName = driverName;
        createConnections(url,username,password,dbDriverName);
    }
    
    public synchronized Connection getConnection(){
        if(freePool.size() == 0){
            //None Available
            //Create a new set of Connections
            createConnections(url,username,password,dbDriverName);
        }
        //Last connection in the Array so that we save time on re-arranging the Array
        return ((Connection)freePool.remove(freePool.size() - 1));
    }
    
    public void closeConnection(Connection con){
        freePool.add(con);
    }
    
    private void createConnections
            (String connectURL, String uName, String pWord, String driverName){
        try{
            Class.forName(driverName).newInstance();
            for(int i=0; i<2; ++i){
                Connection con = DriverManager.getConnection(connectURL,uName,pWord);
                freePool.add(con);
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
    public void close(){
        for(Connection con : freePool){
            try {
                con.close();
            } catch (SQLException ex) {
                // ex.printStackTrace();
            }
        }
        freePool = null;
    }
}

