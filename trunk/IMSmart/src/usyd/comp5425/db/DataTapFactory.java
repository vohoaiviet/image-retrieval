/*
 * DataTapFactory.java
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
public class DataTapFactory {
    private static DataAccessObjectDerby derby = new DataAccessObjectDerby();
    public DataTapFactory() {
    }
    public static DataTap createDataTap(){
        return derby;
    }
    public static void close(){
        if(derby !=null){
            derby.close();
            derby = null;
        }
    }
}
