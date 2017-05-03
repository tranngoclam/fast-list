package io.github.tranngoclam.fastlist.ui.adapter;

import android.support.v7.widget.RecyclerView;

import java.util.List;

/**
 * Created by lam on 5/2/17.
 */

public abstract class BehavioralAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

  public abstract void add(T data);

  public abstract void add(List<T> data);

  public abstract void add(int index, T data);

  public abstract void clear();

  public abstract void remove(int index);

  public abstract void remove(T data);

  public abstract void set(List<T> data);

  public abstract void set(int index, T data);
}
