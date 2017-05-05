package io.github.tranngoclam.fastlist;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by lam on 5/3/17.
 */
public class PreferenceService {

  static final int DEFAULT_AMOUNT = 5;

  static final String KEY_DEFAULT_AMOUNT = "default_amount";

  private final SharedPreferences mSharedPreferences;

  public PreferenceService(Context context) {
    mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
  }

  public int getDefaultAmount() {
    return mSharedPreferences.getInt(KEY_DEFAULT_AMOUNT, DEFAULT_AMOUNT);
  }
}
