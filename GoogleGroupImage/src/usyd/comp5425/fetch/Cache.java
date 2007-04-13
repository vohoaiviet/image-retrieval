/*
 * Cache.java
 *
 * Created on 13 April 2007, 06:26
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.fetch;

// Import the Java classes
import java.util.*;


/**
 * Abstract base-class for JavaSRC cache implementations
 */
public class Cache
{
  /**
   * Objects in this Cache do not expire; note that they can be replaced
   * once the max-size has been reached
   */
  public static final int EXPIRY_NEVER = 0;

  /**
   * Objects in this Cache expire at a specific and absolute time
   */
  public static final int EXPIRY_ABSOLUTE = 1;

  /**
   * Objects in this Cache expire in a specified amount of time
   */
  public static final int EXPIRY_COUNTDOWN = 2;

  /**
   * The maximum number of items that can be in the cache at any given time;
   * -1 means that there is not maximum size
   */
  protected int maxSize;

  /**
   * The expiry mode for this cache: EXPIRY_NEVER, EXPIRY_ABSOLUTE, or EXPIRY_COUNTDOWN
   */
  protected int expiryMode = EXPIRY_NEVER;

  /**
   * The PersistenceEngine is responsible for putting things in the cache and getting them out
   */
  protected PersistenceEngine persistenceEngine;

  /**
   * A Set of CacheObjects
   */
  protected Map cacheObjects = new TreeMap();

  /**
   * Statistics: how many get requests have been made to the cache
   */
  protected long totalRequests = 0;

  /**
   * Statistics: how many get requests have been serviced by the cache
   */
  protected long hits = 0;

  /**
   * Statistics: how many object have been removed from the cache
   */
  protected long passivationCount = 0;

  /**
   * Creates a new cache
   * 
   * @param type   The type of objects to be persisted
   * @param props   Initialization properties
   */
  public Cache( String type, Properties props, int maxSize ) throws PersistenceException
  {
    try
    {
      this.maxSize = maxSize;
      this.persistenceEngine = PersistenceFactory.getPersistenceEngine( type );
      this.persistenceEngine.init( props );

      // Build our set of cache objects 
      Set keys = this.persistenceEngine.getKeys();
      for( Iterator i=keys.iterator(); i.hasNext(); )
      {
        String key = ( String )i.next();
        this.cacheObjects.put( key, new CacheObject( key ) );
      }
    }
    catch( PersistenceException e )
    {
      throw new PersistenceException( e );
    }
  }

  /**
   * Returns an object from the cache with the specified key or null
   */
  public Object get( String key ) throws PersistenceException
  {
    // Add another request to our total
    this.totalRequests++;
    if( !this.contains( key ) )
    {
      return null;
    }

    // We have a hit, update the cache object and return the value
    this.hits++;
    CacheObject cacheObject = ( CacheObject )this.cacheObjects.get( key );
    cacheObject.touch();
    return this.persistenceEngine.get( key );
  }

  /**
   * Puts the specified object into the cache and marks it with the specified key
   */
  public void put( String key, Object value ) throws PersistenceException
  {
    if( this.cacheObjects.size() >= this.maxSize )
    {
      // We have to select an item from the cache to remove: find the least 
      // recently used object in the cache
      CacheObject oldest = null;
      for( Iterator i=this.cacheObjects.keySet().iterator(); i.hasNext(); )
      {
        String k = ( String )i.next();
        CacheObject cacheObject = ( CacheObject )this.cacheObjects.get( k );
        if( oldest == null || 
          cacheObject.getLastAccessed() < oldest.getLastAccessed() )
        {
          oldest = cacheObject;
        }
      }

      // Remove the oldest item from the cache
      this.cacheObjects.remove( oldest.getKey() );
      this.persistenceEngine.remove( oldest.getKey() );
      this.passivationCount++;
    }

    // Add the new object to the cache
    this.cacheObjects.put( key, new CacheObject( key ) );
    this.persistenceEngine.put( key, value );
  }

  /**
   * Returns true if the specified key is contained in the cache, else returns false
   */
  public boolean contains( String key )
  {
    return this.cacheObjects.containsKey( key );
  }

  /**
   * Returns true if the specified key is stale (expired), else false
   */
  public boolean isStale( String key )
  {
    return false;
  }

  /**
   * Returns the current number of objects in the cache
   */
  public int getCacheCount()
  {
    return this.cacheObjects.size();
  }

  /**
   * Returns the maximum size of the cache
   */
  public int getCacheSize()
  {
    return this.maxSize;
  }

  /**
   * Removes all of the contents of the cache
   */
  public void removeAll() throws PersistenceException
  {
    this.persistenceEngine.removeAll();
  }

  /**
   * Removes the specified object from the cache
   */
  public void remove( String key ) throws PersistenceException
  {
    this.persistenceEngine.remove( key );
  }

  /**
   * Removes all of the specified key (List of Strings) from the cache
   */
  public void remove( List keys ) throws PersistenceException
  {
    for( Iterator i=keys.iterator(); i.hasNext(); )
    {
      String key = ( String )i.next();
      this.persistenceEngine.remove( key );
    }
  }

  /**
   * Returns the expiry mode of the cache
   */
  public int getExpiryMode()
  {
    return this.expiryMode;
  }

  /**
   * Sets the expiry mode of the cache; must be EXPIRY_NEVER, EXPIRY_ABSOLUTE, or EXPIRY_COUNTDOWN
   */
  public void setExpiryMode( int expiryMode )
  {
    this.expiryMode = expiryMode;
  }

  /**
   * Returns the total number of requests made to the cache (number of gets)
   */
  public long getTotalRequests()
  {
    return this.totalRequests;
  }

  /**
   * Returns the number of requests that were satisfied by the cache
   */
  public long getHitCount()
  {
    return this.hits;
  }

  /**
   * Returns the number of requests that were not satisfied by the cache
   */
  public long getMissCount()
  {
    return this.totalRequests - this.hits;
  }
  /**
   * Returns the passivation count: the number of objects that have been removed from
   * a full cache to make room for new entries
   */
  public long getPassivationCount()
  {
    return this.passivationCount;
  }

  public String toString()
  {
    return "Cache[" + this.persistenceEngine + "]: " +
      ", cacheSize=" + this.cacheObjects.size() +
      ", maxSize=" + this.maxSize +
      ", totalRequests=" + this.totalRequests +
      ", hitCount=" + this.hits +
      ", missCount=" + this.getMissCount() +
      ", passivationCount=" + this.passivationCount;
  }
}