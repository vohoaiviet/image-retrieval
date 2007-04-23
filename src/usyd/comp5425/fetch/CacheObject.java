/*
 * CacheObject.java
 *
 * Created on 13 April 2007, 06:27
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package usyd.comp5425.fetch;

/**
 * Represents a cache object's key and lifecycle
 */
public class CacheObject implements java.io.Serializable
{
  /**
   * The cache object's key
   */
  private String key;

  /**
   * If the cache has an ABSOLUTE or TIMEOUT expiration then this value
   * represents the expiry value of the object
   */
  private long expiry;

  /**
   * The last time this object was accessed; this value is updated by calls
   * to touch()
   */
  private long lastAccessed;

  /**
   * Creates a new CacheObject with the specified key
   */
  public CacheObject( String key )
  {
    this.key = key;
    this.lastAccessed = System.currentTimeMillis();
  }

  /**
   * Creates a new CacheObject with the specified key and expiry value
   */
  public CacheObject( String key, long expiry )
  {
    this.key = key;
    this.expiry = expiry;
  }

  /**
   * Returns the CacheObject's key
   */
  public String getKey()
  {
    return this.key;
  }

  /**
   * Sets the CacheObject's key
   */
  public void setKey( String key )
  {
    this.key = key;
  }

  /**
   * Returns the CacheObject's expiry value
   */
  public long getExpiry()
  {
    return this.expiry;
  }

  /**
   * Returns the last time the CacheObject was accessed
   */
  public long getLastAccessed()
  {
    return this.lastAccessed;
  }

  /**
   * Updates the CacheObject's last accessed value
   */
  public void touch()
  {
    this.lastAccessed = System.currentTimeMillis();
  }
}