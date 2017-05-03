package io.github.tranngoclam.fastlist;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.backends.okhttp3.OkHttpImagePipelineConfigFactory;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

import android.app.Application;

import javax.inject.Inject;

import io.github.tranngoclam.fastlist.di.AppComponent;
import io.github.tranngoclam.fastlist.di.AppModule;
import io.github.tranngoclam.fastlist.di.DaggerAppComponent;
import okhttp3.OkHttpClient;
import timber.log.Timber;

/**
 * Created by lam on 4/30/17.
 */

public class App extends Application {

  @Inject OkHttpClient mOkHttpClient;

  private AppComponent appComponent;

  @Override
  public void onCreate() {
    super.onCreate();

    // di
    if (appComponent == null) {
      appComponent = DaggerAppComponent.builder()
          .appModule(new AppModule(this))
          .build();
    }

    getAppComponent().inject(this);

    // fresco
    ImagePipelineConfig imagePipelineConfig = OkHttpImagePipelineConfigFactory.newBuilder(this, mOkHttpClient)
        .setDownsampleEnabled(true)
        .build();
    Fresco.initialize(this, imagePipelineConfig);

    // logging
    if (BuildConfig.DEBUG) {
      Timber.plant(new Timber.DebugTree());
    }
  }

  public AppComponent getAppComponent() {
    return appComponent;
  }
}
