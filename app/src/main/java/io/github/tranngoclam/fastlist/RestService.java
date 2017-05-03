package io.github.tranngoclam.fastlist;

import java.util.List;

import io.github.tranngoclam.fastlist.model.User;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lam on 4/30/17.
 */

public interface RestService {

  @GET("/api/?ext")
  Single<User> getUser(@Query("region") String region);

  @GET("/api/?ext")
  Single<List<User>> getUsers(@Query("amount") int amount, @Query("region") String region);
}
