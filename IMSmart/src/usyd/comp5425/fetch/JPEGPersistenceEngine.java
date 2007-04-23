/*
 * JPEGPersistenceEngine.java
 *
 * Created on 13 April 2007, 06:28
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.fetch;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;
import java.util.TreeSet;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;



/**
 * Implements JPEG file persistence
 */
public class JPEGPersistenceEngine implements PersistenceEngine {
    /**
     * The directory where we store our JPEG images
     */
    private String cacheDir;
    
    /**
     * The compression quality of this JPEG persistence engine
     */
    private float compressionQuality = 0.95f;
    
    /**
     * Our set of keys; these are in fact simply the filenames in our directory,
     * but maintained in memory for speed
     */
    private Set keys = new TreeSet();
    
    
    /**
     * Creates a new JPEG Persistence Engine that reads and writes
     * between BufferedImages and JPEG files
     */
    public JPEGPersistenceEngine() {
    }
    
    /**
     * Puts the specified object into the cache with the specified key
     */
    public void put( String key, Object obj ) throws PersistenceException {
        try {
            String filename = this.cacheDir + key;
            
            // Cast the object to a BufferedImage
            BufferedImage bi = ( BufferedImage )obj;
            
            // Get Writer and set compression
            Iterator iter = ImageIO.getImageWritersByFormatName( "JPG" );
            if( iter.hasNext() ) {
                ImageWriter writer = (ImageWriter)iter.next();
                ImageWriteParam iwp = writer.getDefaultWriteParam();
                iwp.setCompressionMode( ImageWriteParam.MODE_EXPLICIT );
                iwp.setCompressionQuality( this.compressionQuality );
                MemoryCacheImageOutputStream mos = new MemoryCacheImageOutputStream( new FileOutputStream( filename ) );
                writer.setOutput( mos );
                IIOImage image = new IIOImage( bi, null, null);
                writer.write(null, image, iwp);
            }
        } catch( Exception e ) {
            e.printStackTrace();
        }
        
    }
    
    /**
     * Reads the specified filename and returns an associated object
     */
    /**
     * Retrieves the requested object from the cache
     */
    public Object get( String key ) throws PersistenceException {
        try {
            String filename = this.cacheDir + key;
            BufferedImage image = ImageIO.read( new File( filename ) );
            return image;
        } catch( Exception e ) {
            e.printStackTrace();
            throw new PersistenceException( e );
        }
    }
    
    /**
     * Returns a set of all cache keys
     */
    public Set getKeys() {
        // Load our keys from the filesystem
        if( this.keys.size() == 0 ) {
            if( this.cacheDir != null ) {
                File dir = new File( this.cacheDir );
                String[] filenames = dir.list();
                for( int i=0; i<filenames.length; i++ ) {
                    this.keys.add( filenames[ i ] );
                }
            }
        }
        
        // Return our keys
        return this.keys;
    }
    
    /**
     * Sets PersistenceEngine specific properties
     */
    public void setProperty( String name, String value ) {
        if( name.equalsIgnoreCase( "cache-dir" ) ) {
            this.cacheDir = System.getProperty( "user.dir" ) + File.separator + value + File.separator;
        } else if( name.equalsIgnoreCase( "compression-quality" ) ) {
            this.compressionQuality = Float.parseFloat( value );
        }
    }
    
    /**
     * Allows a PersistenceEngine to be initialized with a set of name/value pairs
     */
    public void init( Properties p ) {
        String cacheDirValue = p.getProperty( "cache-dir" );
        if( cacheDirValue == null ) {
            cacheDirValue = "cache";
        }
        this.cacheDir = System.getProperty( "user.dir" ) + File.separator + cacheDirValue + File.separator;
        
        String compressionQualityValue = p.getProperty( "compression-quality" );
        if( compressionQualityValue != null ) {
            this.compressionQuality = Float.parseFloat( compressionQualityValue );
        }
    }
    
    /**
     * Removes the specified object from the cache
     */
    public void remove( String key ) throws PersistenceException {
        try {
            File f = new File( this.cacheDir + key );
            f.delete();
        } catch( Exception e ) {
            throw new PersistenceException( e );
        }
    }
    
    /**
     * Removes all objects from the cache
     */
    public void removeAll() throws PersistenceException {
        try {
            File dir = new File( this.cacheDir );
            File[] files = dir.listFiles();
            for( int i=0; i<files.length; i++ ) {
                files[ i ].delete();
            }
        } catch( Exception e ) {
            throw new PersistenceException( e );
        }
    }
    
    public String toString() {
        return "persistenceEngine=JPEG, cacheDir=" + this.cacheDir + ", compressionQuality=" + this.compressionQuality;
    }
}

