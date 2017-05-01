package io.github.tranngoclam.fastlist;

import com.facebook.drawee.backends.pipeline.Fresco;

import android.app.Application;

import io.github.tranngoclam.fastlist.di.AppComponent;
import io.github.tranngoclam.fastlist.di.AppModule;
import io.github.tranngoclam.fastlist.di.DaggerAppComponent;
import timber.log.Timber;

/**
 * Created by lam on 4/30/17.
 */

public class App extends Application {

  private AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    Fresco.initialize(this);
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  public AppComponent getAppComponent() {
    if (appComponent == null) {
      appComponent = DaggerAppComponent.builder()
          .appModule(new AppModule(this))
          .build();
    }
    return appComponent;
  }
}
