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

  @GET("/?ext")
  Single<List<User>> getUsers(@Query("amount") int amount);
}
