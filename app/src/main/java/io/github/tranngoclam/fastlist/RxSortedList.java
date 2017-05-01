package io.github.tranngoclam.fastlist;

import android.support.v7.util.SortedList;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lam on 4/30/17.
 */

public class RxSortedList<T> {

  private static Executor sExecutor = Executors.newSingleThreadExecutor();

  private SortedList<T> mSortedList;

  public Observable<Integer> add(T item) {
    return Observable.fromCallable(() -> mSortedList.add(item))
        .compose(onBackground());
  }

  public Completable addCompletable(T item) {
    return Completable.fromAction(() -> mSortedList.add(item));
  }

  public Single<Integer> addSingle(T item) {
    return Single.fromCallable(() -> mSortedList.add(item));
  }

  public int size() {
    return mSortedList.size();
  }

  private <T> ObservableTransformer<T, T> onBackground() {
    return observable -> observable
        .subscribeOn(Schedulers.from(sExecutor))
        .observeOn(AndroidSchedulers.mainThread());
  }
}
