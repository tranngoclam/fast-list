package io.github.tranngoclam.fastlist.util;

import io.reactivex.CompletableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lam on 5/2/17.
 */

public final class Transformer {

  public static CompletableTransformer applyCompletableTransformer() {
    return completable -> completable
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }

  public static <T> SingleTransformer<T, T> applyIoSingleTransformer() {
    return single -> single
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread());
  }
}
