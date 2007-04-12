/*
 * DataTapFactory.java
 *
 * Created on 12 April 2007, 21:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.db;

/**
 *
 * @author Yuezhong Zhang
 */
public class DataTapFactory {
    private static DataAccessObjectDerby derby;
    public DataTapFactory() {
    }
    public static DataTap createDataTap(){
        if(derby == null){
            derby = new DataAccessObjectDerby();
        }
        return derby;
    }
    public static void close(){
        derby.close();
        derby = null;
    }
}
