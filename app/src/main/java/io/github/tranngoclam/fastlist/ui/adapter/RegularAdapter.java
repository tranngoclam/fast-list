package io.github.tranngoclam.fastlist.ui.adapter;

import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.github.tranngoclam.fastlist.model.User;
import io.github.tranngoclam.fastlist.ui.UserViewHolder;

/**
 * Created by lam on 4/30/17.
 */

public class RegularAdapter extends BehavioralAdapter<User, UserViewHolder> {

  private final List<User> mUsers;

  public RegularAdapter() {
    mUsers = new ArrayList<>();
  }

  @Override
  public void add(User data) {
    mUsers.add(data);
  }

  @Override
  public void add(int index, User user) {
    mUsers.add(index, user);
    notifyItemInserted(index);
  }

  @Override
  public void addAll(List<User> data) {
    int positionStart = getItemCount();
    mUsers.addAll(data);
    notifyItemRangeInserted(positionStart, data.size());
  }

  @Override
  public void clear() {
    mUsers.clear();
  }

  @Override
  public User get(int index) {
    if (index >= 0 && index < getItemCount()) {
      return mUsers.get(index);
    }
    return null;
  }

  @Override
  public int getItemCount() {
    return mUsers.size();
  }

  @Override
  public void onBindViewHolder(UserViewHolder holder, int position) {
    holder.bind(mUsers.get(position));
  }

  @Override
  public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    return UserViewHolder.create(parent);
  }

  @Override
  public void remove(User data) {

  }

  @Override
  public void remove(int index) {
    mUsers.remove(index);
    notifyItemRemoved(index);
  }

  @Override
  public void set(int index, User data) {
    mUsers.set(index, data);
    notifyItemChanged(index);
  }

  @Override
  public void set(List<User> data) {
    mUsers.clear();
    mUsers.addAll(data);
    notifyDataSetChanged();
  }

  @Override
  public void setAndNotifyAll(List<User> users) {
    set(users);
  }
}
