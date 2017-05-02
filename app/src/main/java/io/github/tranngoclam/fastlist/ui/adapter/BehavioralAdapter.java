package io.github.tranngoclam.fastlist.ui.adapter;

import java.util.List;

/**
 * Created by lam on 5/2/17.
 */

public interface BehavioralAdapter<T> {

  void add(T data);

  void add(List<T> data);

  void add(int index, T data);

  void clear();

  void remove(T data);

  void remove(int index);

  void set(int index, T data);

  void set(List<T> data);
}
