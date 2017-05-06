package io.github.tranngoclam.fastlist;

import java.util.List;

import io.github.tranngoclam.fastlist.model.User;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lam on 4/30/17.
 */

public interface RestService {

  @GET("/api/?ext")
  Observable<User> getUser(@Query("region") String region);

  @GET("/api/?ext")
  Observable<List<User>> getUsers(@Query("amount") int amount, @Query("region") String region);
}
