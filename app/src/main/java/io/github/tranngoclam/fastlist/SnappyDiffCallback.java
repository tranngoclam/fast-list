package io.github.tranngoclam.fastlist;

import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v7.util.DiffUtil;

public class SnappyDiffCallback<D> extends DiffUtil.Callback {

  static <D> Pair<DiffUtil.DiffResult, D[]> calculate(RxSortedListCallback<D> callback, D[] oldData, D[] newData,
      int oldDataLength, int newDataLength, OnPayloadChangedListener<D> payloadCallback) {
    return Pair
        .create(DiffUtil.calculateDiff(new SnappyDiffCallback<>(callback, oldData, newData, oldDataLength, newDataLength, payloadCallback)),
            newData);
  }

  private final RxSortedListCallback<D> mCallback;

  private final D[] mNewData;

  private final int mNewDataLength;

  private final D[] mOldData;

  private final int mOldDataLength;

  private final OnPayloadChangedListener<D> mPayloadCallback;

  private SnappyDiffCallback(RxSortedListCallback<D> callback, D[] oldData, D[] newData, int oldDataLength, int newDataLength,
      OnPayloadChangedListener<D> payloadCallback) {
    mOldData = oldData;
    mNewData = newData;
    mCallback = callback;
    mOldDataLength = oldDataLength;
    mNewDataLength = newDataLength;
    mPayloadCallback = payloadCallback;
  }

  @Override
  public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
    return mCallback.areContentsTheSame(mOldData[oldItemPosition], mNewData[newItemPosition]);
  }

  @Override
  public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
    return mCallback.areItemsTheSame(mOldData[oldItemPosition], mNewData[newItemPosition]);
  }

  @Nullable
  @Override
  public Object getChangePayload(int oldItemPosition, int newItemPosition) {
    return mPayloadCallback.onPayloadChanged(mOldData[oldItemPosition], mNewData[newItemPosition]);
  }

  @Override
  public int getNewListSize() {
    return mNewDataLength;
  }

  @Override
  public int getOldListSize() {
    return mOldDataLength;
  }
}
