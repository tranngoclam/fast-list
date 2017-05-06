package io.github.tranngoclam.fastlist.util;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lam on 5/2/17.
 */

public final class Transformer {

  public static <T> ObservableTransformer<T, T> applyIoTransformer() {
    return observable -> observable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
