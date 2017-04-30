package io.github.tranngoclam.fastlist.di;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.github.tranngoclam.fastlist.App;
import io.github.tranngoclam.fastlist.RestService;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by lam on 4/30/17.
 */
@Module
public class AppModule {

  private final App app;

  public AppModule(App app) {
    this.app = app;
  }

  @Singleton
  @Provides
  OkHttpClient provideOkHttpClient() {
    return new OkHttpClient.Builder()
        .build();
  }

  @Singleton
  @Provides
  RestService provideRestService() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("http://api.randomuser.me")
        .build();
    return retrofit.create(RestService.class);
  }
}
