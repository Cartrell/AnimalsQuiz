package com.example.android.animalsquiz;

import android.content.Context;
import android.graphics.Typeface;

import java.util.Hashtable;

public class FontCache {

  /**
   * Prevents memory leaks when obtaining Typeface objects. Additional information here:
   * https://stackoverflow.com/a/16902532
   */

  //===============================================================
  // variables
  //===============================================================
  private static Hashtable<String, Typeface> sm_fontCache = new Hashtable<>();

  //===============================================================
  // public
  //===============================================================

  //---------------------------------------------------------------
  // Get
  //---------------------------------------------------------------
  public static Typeface Get(String name, Context context) {
    Typeface typeface = sm_fontCache.get(name);
    if (typeface == null) {
      try {
        typeface = Typeface.createFromAsset(context.getAssets(), name);
      } catch (Exception e) {
        return(null);
      }
      sm_fontCache.put(name, typeface);
    }
    return(typeface);
  }
}