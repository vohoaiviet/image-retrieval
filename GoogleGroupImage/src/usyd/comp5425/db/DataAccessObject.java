

/*
 * DataAccessObject.java
 *
 * Created on 12 April 2007, 00:44
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author Yuezhong Zhang
 */
public abstract class DataAccessObject implements DataTap{
    private ConnectionPool connectionPool;
    private static final String TABLE_NAME    = "FEATURE_TABLE";
    private static final String ID_COLUMN     = "ID";
    private static final String NAME_COLUMN   = "FEATURE_NAME";
    private static final String IMAGE_COLUMN  = "IMAGE";
    private static final String VECTOR_COLUMN = "VECTOR";
    private static String names [] = {"ID","FEATURE_NAME","IMAGE","VECTOR","FEATURE_TABLE"};
    PreparedStatement pstmt;
    public DataAccessObject() {
    }
    public Collection<Integer> getAllFeaturesID(){
        Collection<Integer> results = new LinkedList<Integer>();
        try {
            Connection con = connectionPool.getConnection();
            pstmt = con.prepareStatement(format("SELECT {0} FROM {4}",names));
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                results.add(rs.getInt(1));
            }
            connectionPool.closeConnection(con);
            rs.close();
            pstmt.close();
            rs  = null;
            pstmt = null;
            con = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return results;
        }
        return results;
    }
    public Collection getAllFeaturesIDBy(String featureName){
        Collection<Integer> results = new LinkedList<Integer>();
        try {
            Connection con = connectionPool.getConnection();
            pstmt = con.prepareStatement(format("SELECT {0} FROM {4} WHERE {1}=?",names));
            pstmt.setString(1,featureName);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                results.add(rs.getInt(1));
            }
            connectionPool.closeConnection(con);
            rs.close();
            pstmt.close();
            rs  = null;
            pstmt = null;
            con = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return results;
        }
        return results;
    }
    public FeatureInfo getFeatureBy(int id, String featureName){
        FeatureInfo info = null;
        try {
            Connection con = connectionPool.getConnection();
            pstmt = con.prepareStatement(format("SELECT * FROM {4} WHERE {0}=? AND {1}=?",names));
            pstmt.setInt(1,id);
            pstmt.setString(2,featureName);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                info = new FeatureInfo();
                info.setId(rs.getInt(1));
                info.setFeatureName(rs.getString(2));
                info.setImage(rs.getString(3));
                info.setVector(convert(rs.getString(4)));
            }
            connectionPool.closeConnection(con);
            rs.close();
            pstmt.close();
            rs  = null;
            pstmt = null;
            con = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return null;
    }
    public FeatureInfo getFeatureBy(int id){
        FeatureInfo info = null;
        try {
            Connection con = connectionPool.getConnection();
            pstmt = con.prepareStatement(format("SELECT * FROM {4} WHERE {0}=?",names));
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                info = new FeatureInfo();
                info.setId(rs.getInt(1));
                info.setFeatureName(rs.getString(2));
                info.setImage(rs.getString(3));
                info.setVector(convert(rs.getString(4)));
            }
            connectionPool.closeConnection(con);
            rs.close();
            pstmt.close();
            rs  = null;
            pstmt = null;
            con = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
        return info;
    }
    public Collection<FeatureInfo> getFeaturesBy(String image){
        Collection<FeatureInfo> results = new LinkedList<FeatureInfo>();
        try {
            Connection con = connectionPool.getConnection();
            pstmt = con.prepareStatement(format("SELECT * FROM {4} WHERE {2}=?",names));
            pstmt.setString(1,image);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                FeatureInfo info = new FeatureInfo();
                info.setId(rs.getInt(1));
                info.setFeatureName(rs.getString(2));
                info.setImage(rs.getString(3));
                info.setVector(convert(rs.getString(4)));
                results.add(info);
            }
            connectionPool.closeConnection(con);
            rs.close();
            pstmt.close();
            rs  = null;
            pstmt = null;
            con = null;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return results;
        }
        return results;
    }
    
    public boolean add(FeatureInfo feature){
        try {
            Connection con  = connectionPool.getConnection();
            pstmt = con.prepareStatement(format("INSERT INTO {4} ({1},{2},{3}) VALUES (?,?,?)",names),Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1,feature.getFeatureName());
            pstmt.setString(2,feature.getImage());
            pstmt.setString(3,convert(feature.getVector()));
            int row = pstmt.executeUpdate();
            if(row ==1){
                ResultSet rs = pstmt.getGeneratedKeys();
                if(rs.next());
                int id = rs.getInt(1);
                feature.setId(id);
                connectionPool.closeConnection(con);
                rs.close();
                rs = null;
                pstmt.close();
                pstmt = null;
                return true;
            }
        } catch (SQLException ex) {
            return false;
        }
        return false;
    }
    public boolean update(FeatureInfo feature){
        try {
            Connection con  = connectionPool.getConnection();
            pstmt = con.prepareStatement(format("UPDATE {4} SET {1}=?,{2}=?,{3}=? WHERE {0}=?",names));
            pstmt.setString(1,feature.getFeatureName());
            pstmt.setString(2,feature.getImage());
            pstmt.setString(3,convert(feature.getVector()));
            pstmt.setInt(4,feature.getId());
            int row = pstmt.executeUpdate();
            connectionPool.closeConnection(con);
            pstmt.close();
            pstmt = null;
            return row ==1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
    }
    public boolean remove(int id){
        try {
            Connection con  = connectionPool.getConnection();
            pstmt = con.prepareStatement(format("DELETE FROM {4} WHERE {0}=?",names));
            pstmt.setInt(1,id);
            int row = pstmt.executeUpdate();
            connectionPool.closeConnection(con);
            pstmt.close();
            pstmt = null;
            return row ==1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        
    }
    public boolean remove(FeatureInfo feature){
        return remove(feature.getId());
    }
    public boolean remove(String image){
        try {
            Connection con  = connectionPool.getConnection();
            pstmt = con.prepareStatement(format("DELETE FROM {4} WHERE {2}=?",names));
            pstmt.setString(1,image);
            int row = pstmt.executeUpdate();
            connectionPool.closeConnection(con);
            pstmt.close();
            pstmt = null;
            return row ==1;
        } catch (SQLException ex) {
            ex.printStackTrace();
            return false;
        }
        
    }
    private Vector<Double> convert(String str){
        StringTokenizer tokenizer = new StringTokenizer(str,",");
        Vector<Double> v = new Vector<Double>(tokenizer.countTokens());
        while(tokenizer.hasMoreElements()){
            v.add(Double.parseDouble((String)tokenizer.nextElement()));
        }
        tokenizer = null;
        return v;
    }
    private String convert(Vector<Double> v){
        StringBuffer sb = new StringBuffer();
        for (Enumeration e = v.elements() ; e.hasMoreElements();) {
            sb.append(e.nextElement());
            sb.append(",");
        }
        return sb.toString();
    }
    private String format(String txt, Object [] args){
        return MessageFormat.format(txt,args);
    }
    public void setConnectionPool(ConnectionPool pool){
        this.connectionPool = pool;
    }
    public ConnectionPool getConnectionPool(){
        return this.connectionPool;
    }
}
