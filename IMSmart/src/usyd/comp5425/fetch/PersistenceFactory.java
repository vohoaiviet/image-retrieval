/*
 * PersistenceFactory.java
 *
 * Created on 13 April 2007, 06:24
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.fetch;

// Import the Java classes
import java.util.*;
import java.io.*;

/**
 * Responsible for creating persistence engines
 */
public class PersistenceFactory
{
  private static Map engineMap;

  public static PersistenceEngine getPersistenceEngine( String type ) 
      throws  PersistenceException
  {
    // Lazy load the engine map from a properties file
    if( engineMap == null )
    {
      engineMap = new TreeMap();
      try
      {
        Properties p = new Properties();
        p.load( new FileInputStream( System.getProperty( "user.dir" ) + 
                       File.separator +
                       "config" +
                       File.separator +
                       "cache.properties" ) );
        Enumeration e = p.propertyNames();
        while( e.hasMoreElements() )
        {
          String key = ( String )e.nextElement();
          String value = p.getProperty( key );
          System.out.println( "DEBUG: PersistenceEngine: " + key + "=" + value );
          engineMap.put( key, value );
        }
      }
      catch( Exception ex )
      {
        ex.printStackTrace();
        throw new PersistenceException( ex );
      }
    }

    // See if we know about this type
    if( !engineMap.containsKey( type ) )
    {
      throw new PersistenceException( 
            "Could not find a persistence engine for type: " + type );
    }

    try
    {
      // Load our PersistenceEngine
      String persistenceEngineClassName = ( String )engineMap.get( type );
      PersistenceEngine pe = ( PersistenceEngine )Class.forName( 
                    persistenceEngineClassName ).newInstance();
      return pe;
    }
    catch( Exception e )
    {
      throw new PersistenceException( e );
    }
  }
}