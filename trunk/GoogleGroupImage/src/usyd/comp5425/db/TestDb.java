/*
 * TestDb.java
 *
 * Created on 10 April 2007, 00:15
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.db;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author Yuezhong Zhang
 */
public class TestDb {
    
    private static EntityManagerFactory emf = Persistence.createEntityManagerFactory("GoogleGroupImagePU");
    
    /** Creates a new instance of TestDb */
    public TestDb() {
    }
    
    public void persist(Object object, boolean modify) {
        EntityManager em = emf.createEntityManager();
        em.getTransaction().begin();
        try {
            if(modify)
                em.merge(object);
            else
                em.persist(object);
            em.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            em.getTransaction().rollback();
        } finally {
            em.close();
        }
    }
    public void findAll(){
        EntityManager em = emf.createEntityManager();
        
        ImageFeature f = em.find(ImageFeature.class,1);
        System.out.println(f.getVector());
        
    }
    public static void main(String[] args) {
        TestDb td = new TestDb();
//        ImageFeature f = new ImageFeature();
//        f.setPath("abcd/sdgdsg");
//        f.setFeatureID("AVERAGE");
//        td.persist(f,false);
//        Vector<DoubleValue> v = new Vector<DoubleValue>();
//        for(int i=20; i<40; i++) {
//            DoubleValue d = new DoubleValue();
//            d.setValue(Double.valueOf(i));
//            d.setImageFeature(f);
//            v.add(d);
//        }
//
//        f.setVector(v);
//        td.persist(f,true);
        td.findAll();
    }
}
