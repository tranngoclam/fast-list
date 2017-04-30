package io.github.tranngoclam.fastlist;

import io.github.tranngoclam.fastlist.model.Response;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lam on 4/30/17.
 */

public interface RestService {

  @GET("/")
  Observable<Response> getUsers(
      @Query("nat") String nationality,
      @Query("seed") String seed,
      @Query("results") int amount,
      @Query("gender") String gender
  );
}
