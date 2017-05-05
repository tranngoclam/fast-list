package io.github.tranngoclam.fastlist;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.util.SortedListAdapterCallback;

/**
 * Created by lam on 5/5/17.
 */

public class RxSortedListAdapterCallback<T> extends SortedListAdapterCallback<T> {

  /**
   * Creates a {@link SortedList.Callback} that will forward data change events to the provided
   * Adapter.
   *
   * @param adapter The Adapter instance which should receive events from the SortedList.
   */
  public RxSortedListAdapterCallback(RecyclerView.Adapter adapter) {
    super(adapter);
  }

  @Override
  public boolean areContentsTheSame(T oldItem, T newItem) {
    return false;
  }

  @Override
  public boolean areItemsTheSame(T item1, T item2) {
    return false;
  }

  @Override
  public int compare(T o1, T o2) {
    return 0;
  }
}
