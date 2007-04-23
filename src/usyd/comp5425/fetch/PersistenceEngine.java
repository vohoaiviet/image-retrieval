/*
 * PersistenceEngine.java
 *
 * Created on 13 April 2007, 06:23
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.fetch;
// Import the Java classes
import java.util.*;

/**
 * A PersistenceEngine is responsible for putting things into the 
 * cache and retrieving them out of the cache
 */
public interface PersistenceEngine
{
  /**
   * Allows a PersistenceEngine to be initialized with a set of name/value pairs
   */
  public void init( Properties p );

  /**
   * Retrieves the requested object from the cache
   */
  public Object get( String key ) throws PersistenceException;

  /**
   * Puts the specified object into the cache with the specified key
   */
  public void put( String key, Object obj ) throws PersistenceException;

  /**
   * Returns a set of all cache keys
   */
  public Set getKeys();

  /**
   * Removes the specified object from the cache
   */
  public void remove( String key ) throws PersistenceException;

  /**
   * Removes all objects from the cache
   */
  public void removeAll() throws PersistenceException;

  /**
   * Sets PersistenceEngine specific properties
   */
  public void setProperty( String name, String value );
}