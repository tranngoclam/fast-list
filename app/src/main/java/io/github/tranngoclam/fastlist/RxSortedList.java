package io.github.tranngoclam.fastlist;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
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

  private SnappySortedList<T> mData;

  public Observable<Integer> add(T item) {
    return Observable.fromCallable(() -> mData.add(item))
        .compose(onBackground());
  }

  public Completable addCompletable(T item) {
    return Completable.fromAction(() -> mData.add(item));
  }

  public Single<Integer> addSingle(T item) {
    return Single.fromCallable(() -> mData.add(item));
  }

  public T get(int position) {
    return mData.get(position);
  }

  public Observable<Boolean> remove(T item) {
    return Observable.fromCallable(() -> mData.remove(item))
        .compose(onBackground());
  }

  public Completable removeItemAt(int index) {
    return Completable.fromAction(() -> mData.removeItemAt(index))
        .compose(onCompletableTransformer());
  }

  public int size() {
    return mData.size();
  }

  public Observable<Void> updateItemAt(int index, T item) {
    return Observable.<Void>fromCallable(() -> {
      mData.updateItemAt(index, item);
      return null;
    }).compose(onBackground());
  }

  private <T1> ObservableTransformer<T1, T1> onBackground() {
    return observable -> observable
        .subscribeOn(Schedulers.from(sExecutor))
        .observeOn(AndroidSchedulers.mainThread());
  }

  private CompletableTransformer onCompletableTransformer() {
    return completable -> completable
        .subscribeOn(Schedulers.from(sExecutor))
        .observeOn(AndroidSchedulers.mainThread());
  }
}
