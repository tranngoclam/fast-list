package io.github.tranngoclam.fastlist;

import android.support.v7.widget.RecyclerView;

public abstract class RxRecyclerViewAdapterCallback<T> extends RxSortedListCallback<T> {

  private final RecyclerView.Adapter mAdapter;

  public RxRecyclerViewAdapterCallback(RecyclerView.Adapter adapter) {
    mAdapter = adapter;
  }

  @Override
  public void onChanged(int position, int count) {
    mAdapter.notifyItemChanged(position, count);
  }

  @Override
  public void onInserted(int position, int count) {
    mAdapter.notifyItemRangeInserted(position, count);
  }

  @Override
  public void onMoved(int fromPosition, int toPosition) {
    mAdapter.notifyItemMoved(fromPosition, toPosition);
  }

  @Override
  public void onRemoved(int position, int count) {
    mAdapter.notifyItemRangeRemoved(position, count);
  }
}
