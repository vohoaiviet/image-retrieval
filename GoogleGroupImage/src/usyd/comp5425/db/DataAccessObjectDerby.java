/*
 * DataAccessObjectDerby.java
 *
 * Created on 12 April 2007, 21:08
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.db;

import java.io.File;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Yuezhong Zhang
 */
public class DataAccessObjectDerby extends DataAccessObject {
    public DataAccessObjectDerby() {
        super();
        initDb();
        connectionPool = new  ConnectionPool(
                System.getProperty("derby.url"),
                System.getProperty("derby.user"),
                System.getProperty("derby.password"),
                "org.apache.derby.jdbc.EmbeddedDriver");
    }
    public void initDb() {
        String name = System.getProperty("derby.url");
        int index = name.lastIndexOf(":");
        name = name.substring(index+1);
        File file = new File(
                System.getProperty("derby.system.home"),
                name);
        System.out.printf("database=%s%n",file.getAbsolutePath());
        Connection conn = null;
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver").newInstance();
            StringBuffer url = new StringBuffer(System.getProperty("derby.url"));
            if(!file.exists()){
                url.append(";create=true");
                System.out.println("database not exist, creating database");
            }
            conn = DriverManager.getConnection(
                    url.toString(),
                    System.getProperty("derby.user"),
                    System.getProperty("derby.password"));
            initTable(conn);
            System.out.println("database is ready ...");
            conn.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
    }
    public void initTable(Connection conn)throws Exception{
        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            // Specify the type of object; in this case we want tables
            String[] types = {"TABLE"};
            ResultSet resultSet = dbmd.getTables(null, null, "%", types);
            // Get the table names
            while (resultSet.next()) {
                // Get the table name
                String tableName = resultSet.getString(3);
                if(tableName.equalsIgnoreCase(names[4])){
                    System.out.println("table is ready ...");
                    return;
                }
            }
            resultSet.close();
            resultSet = null;
            dbmd = null;
        } catch (SQLException ex) {
            throw ex;
        }
        try {
            System.out.println("creating table ....");
            String text ="create table {4}({0} int generated always as identity, {1} varchar(50),{2} varchar(50), {3} varchar(30000))";
            pstmt = conn.prepareStatement(format(text,names));
            pstmt.executeUpdate();
            pstmt.close();
            text = null;
            System.out.println("table is ready ...");
        } catch (SQLException sqlex) {
            System.out.println("failed to create table \n"+ sqlex.getMessage());
            throw sqlex;
        }finally{
            pstmt = null;
        }
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
