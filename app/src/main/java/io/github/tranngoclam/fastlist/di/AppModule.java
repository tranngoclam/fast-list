package io.github.tranngoclam.fastlist.di;

import android.content.Context;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.tranngoclam.fastlist.App;
import io.github.tranngoclam.fastlist.RestService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lam on 4/30/17.
 */
@Module
public class AppModule {

  private final App mApp;

  public AppModule(App app) {
    this.mApp = app;
  }

  @Singleton
  @Provides
  Context provideContext() {
    return mApp.getApplicationContext();
  }

  @Singleton
  @Provides
  @Named("fresco_ok_http")
  OkHttpClient provideFrescoOkHttpClient() {
    return new OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .followRedirects(true)
        .build();
  }

  @Singleton
  @Provides
  HttpLoggingInterceptor provideHttpLoggingInterceptor() {
    HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
    interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
    return interceptor;
  }

  @Singleton
  @Provides
  @Named("ok_http")
  OkHttpClient provideOkHttpClient(HttpLoggingInterceptor interceptor) {
    return new OkHttpClient.Builder()
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .connectTimeout(30, TimeUnit.SECONDS)
        .followSslRedirects(true)
        .addInterceptor(interceptor)
        .build();
  }

  @Singleton
  @Provides
  RestService provideRestService(@Named("ok_http") OkHttpClient okHttpClient) {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://uinames.com/")
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
    return retrofit.create(RestService.class);
  }
}
