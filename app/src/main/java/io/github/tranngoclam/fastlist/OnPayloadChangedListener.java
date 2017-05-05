package io.github.tranngoclam.fastlist;

/**
 * Created by lam on 5/5/17.
 */

public interface OnPayloadChangedListener<T> {

  Object onPayloadChanged(T oldItem, T newItem);
}
