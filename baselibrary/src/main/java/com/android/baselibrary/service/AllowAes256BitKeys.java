package com.android.baselibrary.service;
import javax.crypto.Cipher; 
import java.lang.reflect.Constructor; 
import java.lang.reflect.Field; 
import java.lang.reflect.Modifier; 
import java.util.Map; 
public class AllowAes256BitKeys {

	public static void fixKeyLength() 
    { 
     String errorString = 
      "Unable to manually override key-length permissions."; 
     int newMaxKeyLength; 
     try 
     { 
      if ((newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES")) < 256) 
      { 
       Class<?> c = 
        Class.forName("javax.crypto.CryptoAllPermissionCollection"); 
       Constructor con = c.getDeclaredConstructor(); 
       con.setAccessible(true); 
       Object allPermissionCollection = con.newInstance(); 
       Field f = c.getDeclaredField("all_allowed"); 
       f.setAccessible(true); 
       f.setBoolean(allPermissionCollection, true); 

       c = Class.forName("javax.crypto.CryptoPermissions"); 
       con = c.getDeclaredConstructor(); 
       con.setAccessible(true); 
       Object allPermissions = con.newInstance(); 
       f = c.getDeclaredField("perms"); 
       f.setAccessible(true); 
       // Warnings suppressed because CryptoPermissions uses a raw Map 
       @SuppressWarnings({"unchecked"}) 
       Object junk = // Only need this so we can use @SuppressWarnings 
        ((Map) f.get(allPermissions)).put("*", allPermissionCollection); 
//    ((Map) f.get(allPermissions)).put("*", allPermissionCollection); 

       c = Class.forName("javax.crypto.JceSecurityManager"); 
       f = c.getDeclaredField("defaultPolicy"); 
       f.setAccessible(true); 
       Field mf = Field.class.getDeclaredField("modifiers"); 
       mf.setAccessible(true); 
       mf.setInt(f, f.getModifiers() & ~Modifier.FINAL); 
       f.set(null, allPermissions); 

       newMaxKeyLength = Cipher.getMaxAllowedKeyLength("AES"); 
      } 
     } 
     catch (Exception e) 
     { 
      throw new RuntimeException(errorString, e); 
     } 
    if (newMaxKeyLength < 256) 
     throw new RuntimeException(errorString); // hack failed 
    } 

}
