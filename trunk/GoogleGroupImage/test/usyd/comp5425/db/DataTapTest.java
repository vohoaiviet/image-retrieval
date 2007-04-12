/*
 * DataTapTest.java
 * JUnit based test
 *
 * Created on 13 April 2007, 00:42
 */

package usyd.comp5425.db;

import junit.framework.*;
import java.util.Collection;
import java.util.Vector;

/**
 *
 * @author Yuezhong Zhang
 */
public class DataTapTest extends TestCase {
    
    public DataTapTest(String testName) {
        super(testName);
    }
    
    protected void setUp() throws Exception {
        System.setProperty("derby.user","yuezhong");
        System.setProperty("derby.password","yuezhong");
        System.setProperty("derby.url","jdbc:derby:myDB");
        System.setProperty("derby.system.home","C:\\Sun");
    }
    
    protected void tearDown() throws Exception {
       // DataTapFactory.close();
    }
    /**
     * Test of add method, of class usyd.comp5425.db.DataTap.
     */
    public void testAdd() {
        System.out.println("add");
        FeatureInfo feature = null;
        DataTap  instance = DataTapFactory.createDataTap();
        for(int i=0; i<1000; i++){
            feature = new FeatureInfo();
            feature.setFeatureName("Hello");
            feature.setImage("abc/hdd/sgsg.jpg");
            Vector<Double> v = new Vector<Double>(1024);
            for(int j=0; j<1024; j++)
                v.add( (double)(int)( Math.random() *100));
            feature.setVector(v);
            boolean expResult = true;
            boolean result = instance.add(feature);
            assertEquals(expResult, result);
        }
    }
    /**
     * Test of getAllFeaturesID method, of class usyd.comp5425.db.DataTap.
     */
    public void testGetAllFeaturesID() {
        System.out.println("getAllFeaturesID");
        DataTap  instance = DataTapFactory.createDataTap();
        
        Collection expResult = null;
        Collection result = instance.getAllFeaturesID();
        for(Object obj : result){
            System.out.println(obj);
        }
        if(result.size() !=1000){
            fail("size not equals to 10000");
        }
    }
//
//    /**
//     * Test of getAllFeaturesIDBy method, of class usyd.comp5425.db.DataTap.
//     */
//    public void testGetAllFeaturesIDBy() {
//        System.out.println("getAllFeaturesIDBy");
//
//        String featureName = "";
//        DataTap  instance = DataTapFactory.createDataTap();
//
//        Collection expResult = null;
//        Collection result = instance.getAllFeaturesIDBy(featureName);
//        assertEquals(expResult, result);
//
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFeatureBy method, of class usyd.comp5425.db.DataTap.
//     */
//    public void testGetFeatureBy() {
//        System.out.println("getFeatureBy");
//
//        int id = 0;
//        String featureName = "";
//        DataTap  instance = DataTapFactory.createDataTap();
//
//        FeatureInfo expResult = null;
//        FeatureInfo result = instance.getFeatureBy(id, featureName);
//        assertEquals(expResult, result);
//
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
//    /**
//     * Test of getFeaturesBy method, of class usyd.comp5425.db.DataTap.
//     */
//    public void testGetFeaturesBy() {
//        System.out.println("getFeaturesBy");
//
//        String image = "";
//        DataTap  instance = DataTapFactory.createDataTap();
//
//        Collection<FeatureInfo> expResult = null;
//        Collection<FeatureInfo> result = instance.getFeaturesBy(image);
//        assertEquals(expResult, result);
//
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
//
    
//
//    /**
//     * Test of remove method, of class usyd.comp5425.db.DataTap.
//     */
//    public void testRemove() {
//        System.out.println("remove");
//
//        int id = 0;
//        DataTap  instance = DataTapFactory.createDataTap();
//
//        boolean expResult = true;
//        boolean result = instance.remove(id);
//        assertEquals(expResult, result);
//
//        // TODO review the generated test code and remove the default call to fail.
//        fail("The test case is a prototype.");
//    }
    
}
