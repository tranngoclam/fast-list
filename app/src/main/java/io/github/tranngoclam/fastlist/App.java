package io.github.tranngoclam.fastlist;

import android.app.Application;

import io.github.tranngoclam.fastlist.di.AppComponent;
import io.github.tranngoclam.fastlist.di.AppModule;
import io.github.tranngoclam.fastlist.di.DaggerAppComponent;

/**
 * Created by lam on 4/30/17.
 */

public class App extends Application {

  private AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();
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
