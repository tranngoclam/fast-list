package io.github.tranngoclam.fastlist;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by lam on 4/30/17.
 */

public class RxSortedDiffList<T> {

  private static Executor sExecutor = Executors.newSingleThreadExecutor();

  private SnappySortedList<T> mSnappySortedList;

  public RxSortedDiffList(Class<T> clazz, RxSortedListCallback<T> callback) {
    mSnappySortedList = new SnappySortedList<>(clazz, callback);
  }

  public RxSortedDiffList(Class<T> clazz, RxSortedListCallback<T> callback, int initialCapacity) {
    mSnappySortedList = new SnappySortedList<>(clazz, callback, initialCapacity);
  }

  public Completable add(T item) {
    return Completable.fromAction(() -> mSnappySortedList.add(item))
        .compose(onCompletableBackground());
  }

  public Completable addAll(List<T> users) {
    return Completable.fromAction(() -> mSnappySortedList.addAll(users))
        .compose(onCompletableBackground());
  }

  public T get(int index) {
    return mSnappySortedList.get(index);
  }

  public Completable remove(T item) {
    return Completable.fromAction(() -> mSnappySortedList.remove(item))
        .compose(onCompletableBackground());
  }

  public Completable removeItemAt(int index) {
    return Completable.fromAction(() -> mSnappySortedList.removeItemAt(index))
        .compose(onCompletableBackground());
  }

  public Completable set(Collection<T> items) {
    return mSnappySortedList.set(items)
        .subscribeOn(Schedulers.from(sExecutor));
  }

  public Completable set(Collection<T> items, boolean isSorted) {
    return mSnappySortedList.set(items, isSorted)
        .subscribeOn(Schedulers.from(sExecutor));
  }

  public int size() {
    return mSnappySortedList.size();
  }

  public Completable updateItemAt(int index, T item) {
    return Completable.fromAction(() -> mSnappySortedList.updateItemAt(index, item))
        .compose(onCompletableBackground());
  }

  private CompletableTransformer onCompletableBackground() {
    return completable -> completable
        .subscribeOn(Schedulers.from(sExecutor))
        .observeOn(AndroidSchedulers.mainThread());
  }

  private <T1> ObservableTransformer<T1, T1> onObservableBackground() {
    return observable -> observable
        .subscribeOn(Schedulers.from(sExecutor))
        .observeOn(AndroidSchedulers.mainThread());
  }
}
