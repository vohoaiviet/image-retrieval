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
import java.util.Collection;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.Vector;

/**
 *
 * @author Yuezhong Zhang
 */
public abstract class DataAccessObject implements DataTap{
    private ConnectionPool _connectionPool;
    PreparedStatement pstmt;
    public DataAccessObject() {
    }
    public Collection<Integer> getAllFeaturesID(){
        Collection<Integer> results = new LinkedList<Integer>();
        try {
            Connection con = _connectionPool.getConnection();
            pstmt = con.prepareStatement("SELECT ID FROM FEATURE_TABLE");
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                results.add(rs.getInt(1));
            }
            _connectionPool.closeConnection(con);
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
            Connection con = _connectionPool.getConnection();
            pstmt = con.prepareStatement("SELECT ID FROM FEATURE_TABLE WHERE FEATURE_NAME=?");
            pstmt.setString(1,featureName);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()){
                results.add(rs.getInt(1));
            }
            _connectionPool.closeConnection(con);
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
        try {
            Connection con = _connectionPool.getConnection();
            pstmt = con.prepareStatement("SELECT * FROM FEATURE_TABLE WHERE ID=? AND FEATURE_NAME=?");
            pstmt.setInt(1,id);
            pstmt.setString(2,featureName);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                FeatureInfo info = new FeatureInfo();
                info.setId(rs.getInt(1));
                info.setFeatureName(rs.getString(2));
                info.setImage(rs.getString(3));
                StringTokenizer tokenizer = new StringTokenizer(rs.getString(4),",");
                Vector<Double> v = new Vector<Double>(tokenizer.countTokens());
                while(tokenizer.hasMoreElements()){
                    v.add(Double.parseDouble((String)tokenizer.nextElement()));
                }
                info.setVector(v);
                tokenizer = null;
                v = null;
                return info;
            }
            _connectionPool.closeConnection(con);
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
            Connection con = _connectionPool.getConnection();
            pstmt = con.prepareStatement("SELECT * FROM FEATURE_TABLE WHERE ID=?");
            pstmt.setInt(1,id);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()) {
                info = new FeatureInfo();
                info.setId(rs.getInt(1));
                info.setFeatureName(rs.getString(2));
                info.setImage(rs.getString(3));
                StringTokenizer tokenizer = new StringTokenizer(rs.getString(4),",");
                Vector<Double> v = new Vector<Double>(tokenizer.countTokens());
                while(tokenizer.hasMoreElements()){
                    v.add(Double.parseDouble((String)tokenizer.nextElement()));
                }
                info.setVector(v);
                tokenizer = null;
                v = null;
            }
            _connectionPool.closeConnection(con);
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
            Connection con = _connectionPool.getConnection();
            pstmt = con.prepareStatement("SELECT * FROM FEATURE_TABLE WHERE IMAGE=?");
            pstmt.setString(1,image);
            ResultSet rs = pstmt.executeQuery();
            while(rs.next()) {
                FeatureInfo info = new FeatureInfo();
                info.setId(rs.getInt(1));
                info.setFeatureName(rs.getString(2));
                info.setImage(rs.getString(3));
                StringTokenizer tokenizer = new StringTokenizer(rs.getString(4),",");
                Vector<Double> v = new Vector<Double>(tokenizer.countTokens());
                while(tokenizer.hasMoreElements()){
                    v.add(Double.parseDouble((String)tokenizer.nextElement()));
                }
                info.setVector(v);
                tokenizer = null;
                v = null;
                results.add(info);
            }
            _connectionPool.closeConnection(con);
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
    
    public int add(FeatureInfo feature){
        return  0;
    }
    public boolean update(FeatureInfo feature){
        return false;
    }
    public boolean remove(int id){
        return false;
    }
    public boolean remove(FeatureInfo feature){
        return false;
    }
    public boolean remove(String image){
        return false;
    }
}
