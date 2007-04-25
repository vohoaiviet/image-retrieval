/*
 * TestDb.java
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

import java.util.Vector;

/**
 *
 * @author Yuezhong Zhang SID:305275631
 */
public class TestDb {
    
    /** Creates a new instance of TestDb */
    public TestDb() {
    }
    public static void main(String[] args) {
        System.setProperty("derby.user","yuezhong");
        System.setProperty("derby.password","yuezhong");
        System.setProperty("derby.url","jdbc:derby:myDB");
        System.setProperty("derby.system.home","C:\\Sun");
        DataTap  dt = DataTapFactory.createDataTap();
        for(int i =0; i<10000; i++){
            FeatureInfo f = new FeatureInfo();
            f.setFeatureName("Hello");
            f.setImage("abc/hdd/sgsg.jpg");
            Vector<Double> v = new Vector<Double>();
            for(int j=0; j<1024; j++)
                v.add( (double)(int)( Math.random() *100));
            f.setVector(v);
            boolean b=  dt.add(f);
            System.out.println("added =" + b + f.getId());
        }
    }
}
